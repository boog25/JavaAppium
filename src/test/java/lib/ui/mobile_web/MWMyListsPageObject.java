package lib.ui.mobile_web;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListsPageObject extends MyListsPageObject {
    static {
        ARTICLE_BY_TITLE_TPL ="xpath://ul[contains(@class, 'watchlist')]//h3[contains(text(), '{TITLE}')]";
        SEARCH_RESULT_ELEMENTS_IN_MY_LIST = "id:???"; /*should know */
        REMOVE_FROM_SAVED_BY_LINK_TPL = "xpath://ul[contains(@class, 'watchlist')]//a[@href='{LINK_TEXT}']/../a[contains(@class, 'watched')]";
        REMOVE_FROM_SAVED_BUTTON_TPL = "xpath://ul[contains(@class, 'watchlist')]//h3[contains(text(), '{TITLE}')]/../../a[contains(@class, 'watched')]";
    }
    public MWMyListsPageObject (RemoteWebDriver driver) {
        super(driver);
    }
}
