package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject {
    private static final String
            LOGIN_BUTTON = "xpath://body/div/div/a[text()='Log in']",
            LOGIN_INPUT = "css:input[name='wpName']",
            PASSWORD_INPUT = "css:input[name='wpPassword']",
            SUBMIT_BUTTON = "css:button#wpLoginAttempt";
    public AuthorizationPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    @Step("Click 'Login' button")
    public void clickAuthButton()
    {
        this.tryClickElementWithFewAttempts(
                LOGIN_BUTTON,
                "Error! 'Login' button is not found.",
                5);
    }

    @Step("Enter login credentials")
    public void enterLoginData(String login, String password)
    {
        this.waitForElementAndSendKeys(
                LOGIN_INPUT,
                login,
                "Error! 'Login' input is not found.",
                5
        );
        this.waitForElementAndSendKeys(
                PASSWORD_INPUT,
                password,
                "Error! 'Password' input is not found.",
                5
        );
    }

    @Step("Click 'Submit' button")
    public void submitForm()
    {
        this.waitForElementAndClick(
                SUBMIT_BUTTON,
                "Error! 'Submit' button is not found.",
                5
        );
    }
}