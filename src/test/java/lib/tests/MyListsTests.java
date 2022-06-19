package lib.tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.MyListsPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;


public class MyListsTests extends CoreTestCase {
    private static final String name_of_folder = "Learning programming";
    private static final String
            login = "boog_vv",
            password = "!qwert121";


    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isMW()) {
            ArticlePageObject.waitForTitleElement();
            article_title = ArticlePageObject.getArticleTitle();
        }
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
            if (Platform.getInstance().isIOS()) {
                ArticlePageObject.closeSyncDialog();
            }
        }

        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            Assert.assertEquals(
                    "We are not on the same page after login.",
                    article_title,
                    ArticlePageObject.getArticleTitle()
            );
        }
        ArticlePageObject.addArticlesToMySaved();
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            MyListPageObject.openFolderByName(name_of_folder);
        }
        if (Platform.getInstance().isMW()) {
            MyListPageObject.swipeByArticleToDelete(article_title);
        } else {
            MyListPageObject.swipeByArticleToDelete(article_title);
        }
    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        String search_text = "Java";
        String search_result_description_1 = "Object-oriented programming language";
        String search_result_description_2 = "Island of Indonesia";
        String list_item_1 = "object-oriented programming language";
        String list_item_2 = "island of Indonesia";
        String mw_link_1 = "/wiki/Java_(programming_language)";
        String mw_link_2 = "/wiki/Java";
        if (Platform.getInstance().isIOS()) {
            list_item_1 = "Object-oriented programming language";
            list_item_2 = "Indonesian island";
        }
        if (Platform.getInstance().isMW()) {
            search_result_description_2 = "Indonesian island";
        }
        String folder_name = "Must read!";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_text);
        String initial_article_full_title = SearchPageObject.findElementNameBySubstringAndClick(search_result_description_1);

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
            if (Platform.getInstance().isIOS()) {
                ArticlePageObject.closeSyncDialog();
            }
        }
        ArticlePageObject.closeArticle();
        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();
        }

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_text);
        SearchPageObject.clickByArticleWithSubstring(search_result_description_2);

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyExistingList(folder_name);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();
        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(folder_name);
        }

        if (Platform.getInstance().isMW()) {
            MyListsPageObject.removeArticleToDeleteByLink(mw_link_2);
            MyListsPageObject.waitForArticleToDisappearByLink(mw_link_2);
        } else {
            MyListsPageObject.swipeByArticleToDelete(list_item_2);
            MyListsPageObject.waitForArticleToDisappearByTitle(list_item_2);
        }

        if ((Platform.getInstance().isAndroid()) || (Platform.getInstance().isIOS())) {
            String current_article_full_title = SearchPageObject.findElementNameBySubstringAndClick(list_item_1);

            Assert.assertEquals(
                    "Error! Unexpected article title: '" + current_article_full_title + "' instead of '" + initial_article_full_title + "'.",
                    current_article_full_title,
                    initial_article_full_title);
        } else {
            MyListsPageObject.waitForArticleToAppearByLink(mw_link_1);
        }

        System.out.println("fine!");
    }
}
