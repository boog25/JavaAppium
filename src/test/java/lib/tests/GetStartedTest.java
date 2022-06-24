package lib.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

@Epic("Tests for 'Get started' screen")
public class GetStartedTest extends CoreTestCase
{

    @Test
    @Feature(value = "Welcome screen (IOS)")
    @DisplayName("Pass IOS welcome screen")
    @Description("Click all welcome screen buttons in IOS app")
    @Step("Start test 'testPassThroughWelcome'")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testPastThrowWelcome()
    {
        if ((Platform.getInstance().isAndroid()) || (Platform.getInstance().isMW())) {
            return;
        }
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);
        WelcomePage.waitForLearnMoreLink();
        WelcomePage.clickNextButton();
        WelcomePage.waitForNewWayToExploreText();
        WelcomePage.clickNextButton();
        WelcomePage.waitForAddOrEditPreferredLangText();
        WelcomePage.clickNextButton();
        WelcomePage.waitForLearnMoreAboutDataCollectedText();
        WelcomePage.clickGetStartedButton();
    }
}