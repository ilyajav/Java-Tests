package com.example.tests.web;

import com.example.pages.web.WikipediaArticlePage;
import com.example.pages.web.WikipediaMainPage;
import com.example.pages.web.WikipediaSearchResultsPage;
import com.example.utils.WebDriverProvider; // Renamed to avoid naming conflict
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WikipediaWebTests {

    private WebDriver driver;
    private WikipediaMainPage mainPage;

    @BeforeClass
    public void setUp() {
        driver = WebDriverProvider.getDriver();
        mainPage = new WikipediaMainPage(driver);
    }

    @AfterClass
    public void tearDown() {
        WebDriverProvider.quitDriver();
    }

    @Test(description = "Verify that the Wikipedia main page loads correctly")
    public void testOpenMainPage() {
        mainPage.open();
        String pageTitle = mainPage.getPageTitle();
        Assert.assertTrue(pageTitle.contains("Wikipedia"),
                "Page title should contain 'Wikipedia', but was: " + pageTitle);
        Assert.assertTrue(mainPage.isLanguageSectionDisplayed(),
                "Language selection section should be displayed");
    }

    @Test(description = "Search for an article and verify search results are shown")
    public void testSearchArticle() {
        WikipediaSearchResultsPage resultsPage = mainPage.open().searchFor("Java programming language");

        Assert.assertTrue(resultsPage.isSearchResultsDisplayed(),
                "Search results should be displayed");
        Assert.assertTrue(resultsPage.getSearchResultsCount() > 0,
                "At least one search result should be found");
    }

    @Test(description = "Open an article and verify its heading and content are displayed")
    public void testOpenArticleAndCheckTitle() {
        WikipediaArticlePage articlePage = mainPage.open()
                .searchFor("Metallica")
                .clickFirstSearchResult();

        String heading = articlePage.getArticleHeading();
        Assert.assertNotNull(heading, "Article heading should not be null");
        Assert.assertFalse(heading.isEmpty(), "Article heading must not be empty");
        Assert.assertTrue(articlePage.isArticleContentDisplayed(),
                "Article content should be displayed");
    }

    @Test(description = "Verify language switching updates the URL")
    public void testLanguageSwitching() {
        mainPage.open();
        String initialUrl = driver.getCurrentUrl();

        mainPage.clickEnglishLanguage();

        String englishUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(initialUrl, englishUrl,
                "URL should change after switching to English");
        Assert.assertTrue(englishUrl.contains("en.wikipedia.org"),
                "URL should contain 'en.wikipedia.org', but was: " + englishUrl);
    }

    @Test(description = "Verify navigation menu is displayed on article pages")
    public void testNavigationMenu() {
        WikipediaArticlePage articlePage = mainPage.open()
                .searchFor("Test")
                .clickFirstSearchResult();

        Assert.assertTrue(articlePage.isNavigationMenuDisplayed(),
                "Navigation menu should be displayed on the article page");
    }

    @Test(description = "Search for an article in Russian and verify results appear")
    public void testSearchInRussianLanguage() {
        WikipediaSearchResultsPage resultsPage = mainPage.open()
                .clickRussianLanguage()
                .searchFor("Автоматизация тестирования");

        Assert.assertTrue(resultsPage.isSearchResultsDisplayed(),
                "Search results should be displayed when searching in Russian");
    }
}