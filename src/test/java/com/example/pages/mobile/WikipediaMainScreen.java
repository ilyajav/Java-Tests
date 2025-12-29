package com.example.pages.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WikipediaMainScreen {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // Locators
    private static final By SEARCH_CONTAINER_LOCATOR = By.id("org.wikipedia:id/search_container");
    private static final By SEARCH_INPUT_LOCATOR = By.id("org.wikipedia:id/search_src_text");
    private static final By MAIN_TOOLBAR_LOCATOR = By.id("org.wikipedia:id/main_toolbar");
    private static final By SEARCH_CLOSE_BUTTON_LOCATOR = By.id("org.wikipedia:id/search_close_btn");

    public WikipediaMainScreen(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Clicks the search container to open the search interface.
     */
    public void clickSearchContainer() {
        WebElement searchContainer = wait.until(
                ExpectedConditions.elementToBeClickable(SEARCH_CONTAINER_LOCATOR)
        );
        searchContainer.click();
        // No Thread.sleep — next action should wait for its own element
    }

    /**
     * Enters a search query into the search field.
     *
     * @param query the search term to enter
     */
    public void enterSearchQuery(String query) {
        WebElement searchInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT_LOCATOR)
        );
        searchInput.clear();
        searchInput.sendKeys(query);
        // Avoid Thread.sleep — let the next step handle its own waiting
    }

    /**
     * Checks whether the main toolbar is displayed.
     *
     * @return {@code true} if the toolbar is present and visible, {@code false} otherwise
     */
    public boolean isMainToolbarDisplayed() {
        return isElementDisplayed(MAIN_TOOLBAR_LOCATOR);
    }

    /**
     * Checks whether the search container is displayed.
     *
     * @return {@code true} if the search container is present and visible, {@code false} otherwise
     */
    public boolean isSearchContainerDisplayed() {
        return isElementDisplayed(SEARCH_CONTAINER_LOCATOR);
    }

    /**
     * Closes the search view if the close button is visible.
     */
    public void closeSearchIfOpen() {
        try {
            WebElement closeButton = driver.findElement(SEARCH_CLOSE_BUTTON_LOCATOR);
            if (closeButton.isDisplayed()) {
                closeButton.click();
                // Optional: wait for search input to disappear
                // wait.until(ExpectedConditions.invisibilityOfElementLocated(SEARCH_INPUT_LOCATOR));
            }
        } catch (Exception ignored) {
            // Close button not present — search is not open
        }
    }

    // === Helper method ===

    private boolean isElementDisplayed(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}