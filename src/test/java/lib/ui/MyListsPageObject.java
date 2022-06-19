package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;


abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            SEARCH_RESULT_ELEMENTS_IN_MY_LIST,
            ARTICLE_IN_THE_LIST_BY_LINK_TPL,
            REMOVE_FROM_SAVED_BUTTON_TPL,
            REMOVE_FROM_SAVED_BY_LINK_TPL;


    public MyListsPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    private static String getFolderXpathByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }
    private static String getSavedMWArticleXpathByLink(String link_text)
    {
        return ARTICLE_IN_THE_LIST_BY_LINK_TPL.replace("{LINK_TEXT}", link_text);
    }

    private static String getRemoveButtonByLink(String link_text)
    {
        return REMOVE_FROM_SAVED_BY_LINK_TPL.replace("{LINK_TEXT}", link_text);
    }

    private static String getRemoveButtonByTitle(String article_title)
    {
        return REMOVE_FROM_SAVED_BUTTON_TPL.replace("{TITLE}", article_title);
    }

    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find find folder by name " + name_of_folder,
                15
        );
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(article_xpath, "Cannot find saved article by title " + article_title, 15);
    }

    public void waitForArticleToAppearByLink(String link_text)
    {
        String saved_article_xpath = getSavedMWArticleXpathByLink(link_text);
        this.waitForElementPresent(
                saved_article_xpath,
                "Error! '" + link_text + "' article is not found.",
                15
        );
    }

    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(article_xpath, "Saved article still present with title " + article_title, 15);
    }
    public void waitForArticleToDisappearByLink(String link_text)
    {
        String saved_article_xpath = getSavedMWArticleXpathByLink(link_text);
        this.waitForElementNotPresent(
                saved_article_xpath,
                "Error! '" + link_text + "' article was not deleted and is still in the List.",
                15
        );
    }

    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(
                article_xpath,
                "Cannot find saved article"
        );
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            this.swipeElementToLeft(
                    article_xpath,
                    "Error! No '" + article_title + "' article is found."
            );
        } else {
            String remove_locator = getRemoveButtonByTitle(article_title);
            this.waitForElementAndClick(
                    remove_locator,
                    "Error! 'Remove' button is not found.",
                    5
            );
        }

        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(
                    article_xpath,
                    "Error! No '" + article_title + "' article is found."
            );
        }

        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }

        this.waitForArticleToDisappearByTitle(article_title);
    }

    public int getAmountOfFoundArticlesInMyList()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENTS_IN_MY_LIST,
                "Cannot find any element by the request",
                15);

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENTS_IN_MY_LIST);
    }

    public void removeArticleToDeleteByLink(String link_text)
    {
        String remove_locator = getRemoveButtonByLink(link_text);
        this.waitForElementAndClick(
                remove_locator,
                "Error! 'Remove' button is not found.",
                5
        );

        driver.navigate().refresh();
    }
}