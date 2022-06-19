package lib.ui;

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
            SEARCH_RESULT_CONTAINER;

    public SearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

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

    public void initSearchInput()
    {
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input", 5);
    }


    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring " + substring);
    }
    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

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

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public  void waitForEmptyResultLabel()
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    public void assertTitleIsPresent()
    {
        this.assertElementPresent(SEARCH_TITLE_ELEMENT, "Cannot find the title");
    }

    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String searchResultXpath =  getResultSearchElementForTitleAndDescription(title, description);

        this.waitForElementPresent(
                searchResultXpath,
                "Cannot find element in search result by title and description \n" + searchResultXpath,
                10);
    }

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
}