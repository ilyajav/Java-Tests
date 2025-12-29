package com.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Locale;

public final class WebDriverProvider {

    private static WebDriver driver;

    private static final String DEFAULT_BROWSER = "chrome";
    private static final Duration IMPLICIT_WAIT = Duration.ofSeconds(10);

    private WebDriverProvider() {
        // Utility class — prevent instantiation
    }

    /**
     * Возвращает инициализированный WebDriver-инстанс (singleton).
     */
    public static synchronized WebDriver getDriver() {
        if (driver == null) {
            String browser = System.getProperty("browser", DEFAULT_BROWSER).toLowerCase(Locale.ROOT);
            driver = createDriver(browser);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT);
        }
        return driver;
    }

    private static WebDriver createDriver(String browser) {
        switch (browser) {
            case "chrome":
                return createChromeDriver();
            case "firefox":
                return createFirefoxDriver();
            default:
                throw new IllegalArgumentException("Поддерживаемые браузеры: chrome, firefox. Получен: " + browser);
        }
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");

        String binaryPath = System.getProperty("chrome.binary");
        if (isNonEmpty(binaryPath)) {
            options.setBinary(binaryPath);
        }

        try {
            return new ChromeDriver(options);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Не удалось запустить ChromeDriver. Убедитесь, что Google Chrome установлен, " +
                            "либо укажите путь через -Dchrome.binary=<путь_к_исполняемому_файлу>. " +
                            "Оригинальная ошибка: " + e.getMessage(), e);
        }
    }

    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        String binaryPath = System.getProperty("firefox.binary");
        if (isNonEmpty(binaryPath)) {
            options.setBinary(binaryPath);
        }

        try {
            return new FirefoxDriver(options);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Не удалось запустить GeckoDriver. Убедитесь, что Mozilla Firefox установлен, " +
                            "либо укажите путь через -Dfirefox.binary=<путь_к_исполняемому_файлу>. " +
                            "Оригинальная ошибка: " + e.getMessage(), e);
        }
    }

    public static synchronized void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private static boolean isNonEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}