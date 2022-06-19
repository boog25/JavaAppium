package lib.ui.factories;

import lib.Platform;
import lib.ui.android.AndroidMyListsPageObject;
import lib.ui.ios.iOSMyListsPageObject;
import lib.ui.mobile_web.MWMyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;
import lib.ui.MyListsPageObject;

public class MyListsPageObjectFactory {
    public static MyListsPageObject get(RemoteWebDriver driver)
    {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidMyListsPageObject(driver);
        } else if (Platform.getInstance().isIOS()) {
            return new iOSMyListsPageObject(driver);
        } else {
            return new MWMyListsPageObject(driver);
        }
    }
}
