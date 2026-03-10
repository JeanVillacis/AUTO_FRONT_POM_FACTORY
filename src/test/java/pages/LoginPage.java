package pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject {

    @FindBy(css = "input#email, input[name='email'], input[type='email']")
    private WebElement emailInput;

    @FindBy(css = "input#password, input[name='password'], input[type='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit' and contains(., 'Iniciar Sesión')]")
    private WebElement loginSubmitButton;

    @FindBy(css = "[data-slot='sidebar']")
    private WebElement dashboardSidebar;

    public void openPage(String url) {
        getDriver().get(url);
    }

    public void enterEmail(String email) {
        waitFor(emailInput).waitUntilVisible();
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        waitFor(passwordInput).waitUntilVisible();
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        waitFor(loginSubmitButton).waitUntilClickable();
        loginSubmitButton.click();
    }

    public void waitUntilDashboardIsVisible() {
        waitFor(dashboardSidebar).waitUntilVisible();
    }

    public boolean isDashboardSidebarDisplayed() {
        waitFor(dashboardSidebar).waitUntilVisible();
        return dashboardSidebar.isDisplayed();
    }
}