package com.example.pages.web;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WikipediaMainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "searchInput")
    private WebElement searchInput;

    @FindBy(css = ".central-featured-lang")
    private WebElement languageSection;

    public WikipediaMainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * Opens the Wikipedia main page.
     */
    public void open() {
        driver.get("https://www.wikipedia.org");
    }

    /**
     * Performs a search by typing the query and pressing Enter.
     *
     * @param query search term
     */
    public void searchFor(String query) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(query, Keys.ENTER);
    }

    /**
     * Clicks the language link by domain (e.g., "en.wikipedia.org").
     *
     * @param langDomain language domain like "en.wikipedia.org", "ru.wikipedia.org"
     */
    public void clickLanguage(String langDomain) {
        String cssSelector = String.format(".central-featured-lang a[href*='%s']", langDomain);
        try {
            WebElement langLink = wait.until(ExpectedConditions.elementToBeClickable(cssSelector));
            langLink.click();
        } catch (Exception e) {
            // Fallback to XPath if CSS fails
            String xpath = String.format("//a[contains(@href, '%s')]", langDomain);
            WebElement langLink = wait.until(ExpectedConditions.elementToBeClickable(xpath));
            langLink.click();
        }
    }

    // Convenience methods
    public void clickEnglishLanguage() {
        clickLanguage("en.wikipedia.org");
    }

    public void clickRussianLanguage() {
        clickLanguage("ru.wikipedia.org");
    }

    public void clickGermanLanguage() {
        clickLanguage("de.wikipedia.org");
    }

    /**
     * Checks if the language section is displayed.
     *
     * @return {@code true} if displayed, {@code false} otherwise
     */
    public boolean isLanguageSectionDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(languageSection));
            return languageSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}