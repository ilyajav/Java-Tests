package com.example.pages.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WikipediaArticlePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "firstHeading")
    private WebElement articleHeading;

    @FindBy(id = "mw-content-text")
    private WebElement articleContent;

    @FindBy(css = "#p-lang .interlanguage-link-target")
    private List<WebElement> languageLinks;

    @FindBy(css = "nav#p-navigation")
    private WebElement navigationMenu;

    public WikipediaArticlePage(WebDriver driver, List<WebElement> languageLinks) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.languageLinks = languageLinks;
        PageFactory.initElements(driver, this);
    }

    /**
     * Returns the main article heading text.
     * Waits for the heading to become visible before retrieving text.
     *
     * @return heading text
     * @throws org.openqa.selenium.TimeoutException if heading is not found within timeout
     */
    public String getArticleHeading() {
        wait.until(ExpectedConditions.visibilityOf(articleHeading));
        return articleHeading.getText();
    }

    /**
     * Checks whether the main article content is displayed.
     * Safe for use in assertions — returns {@code false} if not visible or not present.
     *
     * @return {@code true} if the content element is present and visible
     */
    public boolean isArticleContentDisplayed() {
        try {
            return articleContent.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the full text content of the article.
     * Waits for the content block to appear before extraction.
     *
     * @return article content as plain text
     * @throws org.openqa.selenium.TimeoutException if content is not loaded in time
     */
    public String getArticleContentText() {
        wait.until(ExpectedConditions.visibilityOf(articleContent));
        return articleContent.getText();
    }

    /**
     * Returns the number of available interlanguage links.
     * Waits up to the implicit timeout for links to potentially appear.
     *
     * @return number of language links (may be 0)
     */
    public int getLanguageLinksCount() {
        try {
            // Wait up to 3 seconds for links — shorter than global timeout
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.numberOfElementsToBeMoreThan(
                            By.cssSelector("#p-lang .interlanguage-link-target"), 0));
        } catch (Exception ignored) {
            // It's acceptable to have no interlanguage links
        }
        return languageLinks.size();
    }

    /**
     * Checks whether the navigation menu is displayed.
     *
     * @return {@code true} if menu is present and visible
     */
    public boolean isNavigationMenuDisplayed() {
        try {
            return navigationMenu.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the current browser page title.
     *
     * @return page title from the browser tab
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
}