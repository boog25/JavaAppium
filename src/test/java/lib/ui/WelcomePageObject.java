package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject {

    private static final String
            STEP_LEARN_MORE_LINK = "id:Learn more about Wikipedia",
            STEP_NEW_WAY_TO_EXPLORE_TEXT = "id:New ways to explore",
            STEP_ADD_OR_EDIT_PREFERRED_LANG_TEXT = "id:Add or edit preferred languages",
            STEP_LEARN_MORE_ABOUT_DATA_COLLECTED = "id:Learn more about data collected",
            STEP_NEXT_BUTTON = "id:Next",
            STEP_GET_STARTED_BUTTON = "id:Get started",
            SKIP = "id:Skip";

    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink () {
        this.waitForElementPresent(STEP_LEARN_MORE_LINK, "Cannot find 'Learn more about Wikipedia' link", 10);
    }

    public void waitForNewWayToExploreText () {
        this.waitForElementPresent(STEP_NEW_WAY_TO_EXPLORE_TEXT, "Cannot find 'New ways to explore' link", 10);
    }

    public void waitForAddOrEditPreferredLangText () {
        this.waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED_LANG_TEXT, "Cannot find 'Add or edit preferred languages' link", 10);
    }

    public void waitForLearnMoreAboutDataCollected () {
        this.waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED, "Cannot find 'Learn more about data collected' link", 10);
    }

    public void clickNextButton () {
        this.waitForElementAndClick(STEP_NEXT_BUTTON, "Cannot find and click 'Next' button", 10);
    }

    public void clickGetStartedButton () {
        this.waitForElementAndClick(STEP_GET_STARTED_BUTTON, "Cannot find and click 'Get started' button", 10);
    }

    public void clickSkip() {
        this.waitForElementAndClick(SKIP, "Cannot find and click skip button", 5);
    }
}