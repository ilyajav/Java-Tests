package com.example.pages.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WikipediaArticleScreen {

    private final WebDriverWait wait;

    private static final By ARTICLE_TITLE_LOCATOR =
            By.id("org.wikipedia:id/view_page_title_text");

    public WikipediaArticleScreen(AndroidDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Retrieves the visible title of the currently opened article.
     * Waits up to 10 seconds for the title element to appear.
     *
     * @return the article title text, or an empty string if not found
     */
    public String getArticleTitle() {
        try {
            WebElement titleElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(ARTICLE_TITLE_LOCATOR)
            );
            return titleElement.getText();
        } catch (Exception e) {
            // Element not found or not visible within timeout
            return "";
        }
    }
}