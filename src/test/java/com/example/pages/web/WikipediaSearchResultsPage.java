package com.example.pages.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class WikipediaSearchResultsPage {
    private final WebDriver driver;

    @FindBy(id = "firstHeading")
    private WebElement pageHeading;

    @FindBy(css = ".mw-search-result-heading a")
    private List<WebElement> searchResultLinks;

    @FindBy(css = ".mw-search-results")
    private WebElement searchResultsContainer;

    @FindBy(id = "searchInput")
    private WebElement searchInput;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    public WikipediaSearchResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPageHeading() {
        return pageHeading.getText();
    }

    public int getSearchResultsCount() {
        return searchResultLinks.size();
    }

    public void clickFirstSearchResult() {
        if (!searchResultLinks.isEmpty()) {
            searchResultLinks.get(0).click();
        }
    }

    public boolean isSearchResultsDisplayed() {
        return searchResultsContainer.isDisplayed();
    }

    public void searchFor(String query) {
        searchInput.clear();
        searchInput.sendKeys(query);
        searchButton.click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}

