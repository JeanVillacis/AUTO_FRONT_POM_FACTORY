package pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends PageObject {

    @FindBy(id = "displayName")
    private WebElementFacade displayNameInput;

    @FindBy(id = "email")
    private WebElementFacade emailInput;

    @FindBy(id = "password")
    private WebElementFacade passwordInput;

    @FindBy(id = "confirmPassword")
    private WebElementFacade confirmPasswordInput;

    @FindBy(xpath = "//button[@type='submit' and contains(., 'Crear Cuenta')]")
    private WebElementFacade registerSubmitButton;

    public void openPage(String url) {
        getDriver().get(url);
    }

    public void enterDisplayName(String displayName) {
        displayNameInput.waitUntilVisible();
        displayNameInput.clear();
        displayNameInput.sendKeys(displayName);
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

    public void enterConfirmPassword(String confirmPassword) {
        confirmPasswordInput.waitUntilVisible();
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(confirmPassword);
    }

    public void submitRegistration() {
        registerSubmitButton.waitUntilClickable();
        registerSubmitButton.click();
    }

    public void waitUntilRedirectedToLogin() {
        waitForCondition().until(driver -> driver.getCurrentUrl().contains("/login"));
    }

    public boolean isOnLoginPage() {
        return getDriver().getCurrentUrl().contains("/login");
    }
}