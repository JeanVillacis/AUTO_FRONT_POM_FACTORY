package pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject {

    @FindBy(css = "input#email, input[name='email'], input[type='email']")
    private WebElementFacade emailInput;

    @FindBy(css = "input#password, input[name='password'], input[type='password']")
    private WebElementFacade passwordInput;

    @FindBy(xpath = "//button[@type='submit' and contains(., 'Iniciar Sesión')]")
    private WebElementFacade loginSubmitButton;

    @FindBy(css = "[data-slot='sidebar']")
    private WebElementFacade dashboardSidebar;

    public void openPage(String url) {
        getDriver().get(url);
    }

    public void enterEmail(String email) {
        emailInput.waitUntilVisible();
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.waitUntilVisible();
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void submitLogin() {
        loginSubmitButton.waitUntilClickable();
        loginSubmitButton.click();
    }

    public void waitUntilDashboardIsVisible() {
        dashboardSidebar.waitUntilVisible();
    }

    public boolean isDashboardSidebarDisplayed() {
        dashboardSidebar.waitUntilVisible();
        return dashboardSidebar.isDisplayed();
    }
}