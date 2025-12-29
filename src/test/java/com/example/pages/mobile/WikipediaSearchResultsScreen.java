package com.example.pages.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WikipediaSearchResultsScreen {

    private final WebDriverWait wait;

    private static final By SEARCH_RESULTS_CONTAINER = By.id("org.wikipedia:id/search_results_container");
    private static final By SEARCH_RESULT_TITLES = By.id("org.wikipedia:id/page_list_item_title");

    public WikipediaSearchResultsScreen(AndroidDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Checks whether the search results container is displayed.
     *
     * @return {@code true} if the container is visible, {@code false} otherwise
     */
    public boolean isSearchResultsDisplayed() {
        return isElementDisplayed();
    }

    /**
     * Returns the number of visible search results.
     *
     * @return count of search result items (may be 0)
     */
    public int getSearchResultsCount() {
        try {
            List<WebElement> titles = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(SEARCH_RESULT_TITLES)
            );
            return titles.size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Clicks the first visible search result.
     * Throws {@link org.openqa.selenium.TimeoutException} if no result is found within timeout.
     */
    public void clickFirstSearchResult() {
        WebElement firstResult = wait.until(
                ExpectedConditions.elementToBeClickable(SEARCH_RESULT_TITLES)
        );
        firstResult.click();
    }

    /**
     * Retrieves the title text of the first search result.
     *
     * @return title text, or empty string if not found
     */
    public String getFirstResultTitle() {
        try {
            WebElement firstResult = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(SEARCH_RESULT_TITLES)
            );
            return firstResult.getText();
        } catch (Exception e) {
            return "";
        }
    }

    // === Helper method ===

    private boolean isElementDisplayed() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(WikipediaSearchResultsScreen.SEARCH_RESULTS_CONTAINER));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}