package steps;

import net.serenitybdd.annotations.Step;
import static org.assertj.core.api.Assertions.assertThat;
import pages.LoginPage;
import pages.RegisterPage;
import pages.TransactionPage;
import utils.TestDataConstants;

public class BudgetSteps {

    private RegisterPage registerPage;
    private LoginPage loginPage;
    private TransactionPage transactionPage;

    @Step("El usuario abre la página de registro")
    public void openRegisterPage() {
        registerPage.openPage(TestDataConstants.REGISTER_URL);
    }

    @Step("El usuario completa el formulario de registro con datos válidos")
    public void fillRegistrationForm() {
        registerPage.enterDisplayName(TestDataConstants.REG_DISPLAY_NAME);
        registerPage.enterEmail(TestDataConstants.REG_EMAIL);
        registerPage.enterPassword(TestDataConstants.REG_PASSWORD);
        registerPage.enterConfirmPassword(TestDataConstants.REG_CONFIRM_PASSWORD);
    }

    @Step("El usuario envía el formulario de registro")
    public void submitRegistration() {
        registerPage.clickRegisterButton();
    }

    @Step("El sistema confirma la creación exitosa de la cuenta")
    public void verifyRegistrationSuccess() {
        registerPage.waitUntilRedirectedToLogin();
        assertThat(registerPage.isOnLoginPage())
            .as("La URL actual debe corresponder a la página de login")
            .isTrue();
    }

    @Step("El usuario es redirigido a la página de login")
    public void verifyRedirectToLogin() {
        assertThat(registerPage.isOnLoginPage())
            .as("Se esperaba redirección a login después del registro")
            .isTrue();
    }

    @Step("El usuario abre la página de login")
    public void openLoginPage() {
        loginPage.openPage(TestDataConstants.LOGIN_URL);
    }

    @Step("El usuario completa el formulario de login con credenciales válidas")
    public void fillLoginForm() {
        loginPage.enterEmail(TestDataConstants.REG_EMAIL);
        loginPage.enterPassword(TestDataConstants.REG_PASSWORD);
    }

    @Step("El usuario envía el formulario de login")
    public void submitLogin() {
        loginPage.clickLoginButton();
    }

    @Step("El sistema confirma el inicio de sesión exitoso")
    public void verifyLoginSuccess() {
        loginPage.waitUntilDashboardIsVisible();
        assertThat(loginPage.isDashboardSidebarDisplayed())
            .as("El dashboard debe mostrar el sidebar tras autenticarse")
            .isTrue();
    }

    @Step("El usuario accede al panel principal")
    public void verifyAccessToDashboard() {
        assertThat(loginPage.isDashboardSidebarDisplayed())
            .as("El usuario debe permanecer en el panel principal")
            .isTrue();
    }

    @Step("El usuario navega a la página de transacciones")
    public void navigateToTransactions() {
        transactionPage.openPage(TestDataConstants.TRANSACTIONS_URL);
    }

    @Step("El usuario abre el modal de nueva transacción")
    public void openNewTransactionModal() {
        transactionPage.clickNewTransactionButton();
        assertThat(transactionPage.isModalDisplayed())
            .as("El modal de nueva transacción debe abrirse")
            .isTrue();
    }

    @Step("El usuario completa el formulario de transacción con datos válidos")
    public void fillTransactionForm() {
        transactionPage.selectTransactionType(TestDataConstants.TX_TYPE);
        transactionPage.enterDescription(TestDataConstants.TX_DESCRIPTION);
        transactionPage.enterAmount(TestDataConstants.TX_AMOUNT);
        transactionPage.selectCategory(TestDataConstants.TX_CATEGORY);
        transactionPage.enterDate(TestDataConstants.TX_DATE);
    }

    @Step("El usuario envía el formulario de transacción")
    public void submitTransaction() {
        transactionPage.clickCreateTransactionButton();
    }

    @Step("El sistema registra la transacción correctamente")
    public void verifyTransactionCreated() {
        assertThat(transactionPage.isSuccessToastDisplayed())
            .as("Debe mostrarse un mensaje de éxito al crear la transacción")
            .isTrue();
    }

    @Step("La transacción aparece en el listado")
    public void verifyTransactionInTable() {
        assertThat(transactionPage.isTransactionInTable(TestDataConstants.TX_DESCRIPTION))
            .as("La transacción creada debe estar visible en la tabla")
            .isTrue();
    }
}