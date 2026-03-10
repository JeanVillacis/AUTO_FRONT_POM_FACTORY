package pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends PageObject {

    @FindBy(id = "displayName")
    private WebElement displayNameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//button[@type='submit' and contains(., 'Crear Cuenta')]")
    private WebElement registerSubmitButton;

    public void openPage(String url) {
        getDriver().get(url);
    }

    public void enterDisplayName(String displayName) {
        waitFor(displayNameInput).waitUntilVisible();
        displayNameInput.clear();
        displayNameInput.sendKeys(displayName);
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

    public void enterConfirmPassword(String confirmPassword) {
        waitFor(confirmPasswordInput).waitUntilVisible();
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(confirmPassword);
    }

    public void clickRegisterButton() {
        waitFor(registerSubmitButton).waitUntilClickable();
        registerSubmitButton.click();
    }

    public void waitUntilRedirectedToLogin() {
        waitForCondition().until(driver -> driver.getCurrentUrl().contains("/login"));
    }

    public boolean isOnLoginPage() {
        return getDriver().getCurrentUrl().contains("/login");
    }
}