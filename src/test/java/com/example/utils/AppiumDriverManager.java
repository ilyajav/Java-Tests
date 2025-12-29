package com.example.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public class AppiumDriverManager {

    private static AndroidDriver driver;

    private static final String APPIUM_SERVER_URL = "http://localhost:4723";

    // Default app package and activity
    private static final String DEFAULT_APP_PACKAGE = "org.wikipedia";
    private static final String DEFAULT_APP_ACTIVITY = ".main.MainActivity";

    private AppiumDriverManager() {
        // Prevent instantiation
    }

    public static synchronized AndroidDriver getDriver() throws MalformedURLException {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }

    private static AndroidDriver createDriver() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();

        configureDevice(options);
        configureApp(options);

        AndroidDriver androidDriver = new AndroidDriver(URI.create(APPIUM_SERVER_URL).toURL(), String.valueOf(options));
        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return androidDriver;
    }

    private static void configureDevice(UiAutomator2Options options) {
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setNoReset(true);
        options.setFullReset(false);

        String deviceName = System.getProperty("deviceName", "emulator-5554");
        options.setDeviceName(deviceName);
        options.setUdid(deviceName);
    }

    private static void configureApp(UiAutomator2Options options) {
        String apkPath = System.getProperty("appPath");
        if (isNonEmpty(apkPath)) {
            options.setApp(apkPath);
            return;
        }

        String appPackage = System.getProperty("appPackage");
        String appActivity = System.getProperty("appActivity");

        // Use defaults if neither APK nor package is provided
        if (!isNonEmpty(appPackage)) {
            appPackage = DEFAULT_APP_PACKAGE;
            appActivity = DEFAULT_APP_ACTIVITY;
        }

        options.setAppPackage(appPackage);
        if (isNonEmpty(appActivity)) {
            options.setAppActivity(appActivity);
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