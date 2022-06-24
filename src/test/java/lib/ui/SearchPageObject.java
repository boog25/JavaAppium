package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

import static org.junit.Assert.assertTrue;


abstract public class SearchPageObject extends MainPageObject {
    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_ARTICLE_FOR_TITLE_AND_DESCRIPTION,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_TITLE_ELEMENT,
            SEARCH_RESULT_CONTAINER,
            SEARCH_RESULTS_LIST,
            SECOND_RESULT_XPATH,
            SEARCH_INPUT_TEXT;

    public SearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
    /* TEMPLATES METHODS*/
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    private static String getResultSearchElementForTitleAndDescription(String substringTitle, String substringDesc)
    {
        return SEARCH_ARTICLE_FOR_TITLE_AND_DESCRIPTION
                .replace("{SUBSTRING_TITLE}",substringTitle)
                .replace("{SUBSTRING_DESC}",substringDesc);
    }
    /* TEMPLATES METHODS*/
    @Step("Initialize 'Search' field")
    public void initSearchInput()
    {
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
    }

    @Step("Wait for 'Close' button to appear")
    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    @Step("Wait for 'Close' button to disappear")
    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    @Step("Click 'Close' button")
    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    @Step("Type '{search_line}' to the search line")
    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input", 5);
    }


    @Step("Wait for search result to appear")
    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring " + substring);
    }

    @Step("Click article with the '{substring}' title")
    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

    @Step("Get element name/text attribute for the '{substring}' article and click that article")
    public String findElementNameBySubstringAndClick(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        WebElement element = this.waitForElementPresent(
                search_result_xpath,
                "Error! '" + substring + "' string is not found in search results.",
                10
        );
        String attribute = "name";
        if (Platform.getInstance().isAndroid()) {
            attribute = "text";
        }
        String element_name = "";
        if (Platform.getInstance().isMW()) {
            element_name = element.getText();
        } else {
            element_name = element.getAttribute(attribute);
        }
        element.click();
        return element_name.toLowerCase();
    }

    @Step("Get amount of found articles")
    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Wait for 'Empty Results' label to appear")
    public  void waitForEmptyResultLabel()
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    @Step("Assert there are no search results (without waiting)")
    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }
    @Step("Assert that necessary title is present")
    public void assertTitleIsPresent()
    {

        this.assertElementPresent(SEARCH_TITLE_ELEMENT, "Cannot find the title");
    }

    //метод дожидатся результата поиска по двум строкам - по заголовку и описанию. Если такого не появляется,
    // то ошибка.
    @Step("Wait for article with the '{title}' title and the '{description}' description to appear")
    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String searchResultXpath =  getResultSearchElementForTitleAndDescription(title, description);
        this.waitForElementPresent(
                searchResultXpath,
                "Cannot find element in search result by title and description \n" + searchResultXpath,
                10);
    }

    @Step("Make sure that there is the list of elements by search")
    public void assertForWordByResultsSearch (String keyWord)
    {
        List<WebElement> elementList = this.waitForElementsPresent(
                SEARCH_RESULT_CONTAINER,
                "List of elements is empty",
                5
        );
        for (WebElement webElement : elementList)
        {
            String elementAttribute = webElement.getAttribute("text");
            System.out.println(elementAttribute);
            assertTrue("Search result doesn't contain " + keyWord,elementAttribute.contains(keyWord));
        }
    }

    @Step("Check if there are multiple search results")
    public void checkForMultipleResults()
    {
        this.waitForElementPresent(
                SECOND_RESULT_XPATH,
                "Error! No multiple search results.",
                10
        );
        System.out.println("OK: There are multiple search results.");
    }

    @Step("Clear 'Search' input field")
    public void clearSearchInput()
    {
        this.waitForElementAndClear(
                SEARCH_INPUT_TEXT,
                "Error! Search input is not found.",
                5
        );
    }

    @Step("Check that search results disappear")
    public void checkThatResultsDisappear()
    {
        this.waitForElementNotPresent(
                SEARCH_RESULTS_LIST,
                "Error! Search results are still visible.",
                10
        );
        System.out.println("OK: There are no visible search results on the page anymore.");
    }
}