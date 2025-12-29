package com.example.tests.mobile;

import com.example.pages.mobile.WikipediaMainScreen;
import com.example.pages.mobile.WikipediaSearchResultsScreen;
import com.example.utils.AppiumDriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class WikipediaMobileTests {

    private WikipediaMainScreen mainScreen;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        AndroidDriver driver = AppiumDriverManager.getDriver();
        mainScreen = new WikipediaMainScreen(driver);
        // Не используем Thread.sleep — страницы должны уметь ждать своей готовности
    }

    @AfterClass
    public void tearDown() {
        AppiumDriverManager.quitDriver();
    }

    @Test(description = "Verifying the main screen is displayed")
    public void testMainScreenDisplay() {
        Assert.assertTrue(
                mainScreen.isSearchContainerDisplayed(),
                "The search container should be displayed on the main screen."
        );
    }

    @Test(description = "Search for an article and verify that search results are displayed.")
    public void testSearchArticle() {
        WikipediaSearchResultsScreen resultsScreen = mainScreen
                .clickSearchContainer()
                .enterSearchQuery("Java");

        int resultsCount = resultsScreen.getSearchResultsCount();
        Assert.assertTrue(
                resultsCount > 0,
                "Search results should be found. Found:" + resultsCount
        );
    }

    @Test(description = "Open the first article and verify that the search results screen is no longer displayed.")
    public void testOpenArticleAndCheckNavigation() {
        WikipediaSearchResultsScreen resultsScreen = mainScreen
                .clickSearchContainer()
                .enterSearchQuery("Java");

        // Проверяем, что результаты есть
        Assert.assertTrue(
                resultsScreen.getSearchResultsCount() > 0,
                "Search results must be found before opening the article."
        );

        String firstResultTitle = resultsScreen.getFirstResultTitle();
        Assert.assertFalse(
                firstResultTitle.isEmpty(),
                "The title of the first search result must not be empty."
        );

        // Открываем статью
        resultsScreen.clickFirstSearchResult();

        // После открытия статьи экран результатов должен исчезнуть
        Assert.assertFalse(
                resultsScreen.isSearchResultsDisplayed(),
                "After opening the article, the search results screen should not be displayed."
        );
    }
}