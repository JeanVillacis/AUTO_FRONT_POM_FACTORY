package steps;

import net.serenitybdd.annotations.Step;
import static org.assertj.core.api.Assertions.assertThat;
import pages.LoginPage;
import pages.RegisterPage;
import pages.TransactionPage;
import utils.TestDataConstants;

public class BudgetSteps {

    private String lastTransactionDescription;

    private RegisterPage registerPage;
    private LoginPage loginPage;
    private TransactionPage transactionPage;

    @Step("El usuario abre la página de registro")
    public void openRegisterPage() {
        registerPage.openPage(TestDataConstants.REGISTER_URL);
    }

    @Step("El usuario completa el formulario de registro con nombre '{0}' y correo '{1}'")
    public void fillRegistrationForm(String displayName, String email) {
        registerPage.enterDisplayName(displayName);
        registerPage.enterEmail(email);
        registerPage.enterPassword(TestDataConstants.REG_PASSWORD);
        registerPage.enterConfirmPassword(TestDataConstants.REG_CONFIRM_PASSWORD);
    }

    @Step("El usuario envía el formulario de registro")
    public void submitRegistration() {
        registerPage.submitRegistration();
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

    @Step("El usuario completa el formulario de login con correo '{0}'")
    public void fillLoginForm(String email) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(TestDataConstants.REG_PASSWORD);
    }

    @Step("El usuario envía el formulario de login")
    public void submitLogin() {
        loginPage.submitLogin();
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
        transactionPage.openNewTransactionDialog();
        assertThat(transactionPage.isModalDisplayed())
            .as("El modal de nueva transacción debe abrirse")
            .isTrue();
    }

    @Step("El usuario completa el formulario: tipo '{0}', descripción '{1}', monto '{2}', categoría '{3}'")
    public void fillTransactionForm(String tipo, String descripcion, String monto, String categoria) {
        lastTransactionDescription = descripcion;
        transactionPage.selectTransactionType(tipo);
        transactionPage.enterDescription(descripcion);
        transactionPage.enterAmount(monto);
        transactionPage.selectCategory(categoria);
        transactionPage.enterDate(TestDataConstants.TX_DATE);
    }

    @Step("El usuario envía el formulario de transacción")
    public void submitTransaction() {
        transactionPage.submitTransaction();
    }

    @Step("El sistema registra la transacción correctamente")
    public void verifyTransactionCreated() {
        assertThat(transactionPage.isSuccessToastDisplayed())
            .as("Debe mostrarse un mensaje de éxito al crear la transacción")
            .isTrue();
    }

    @Step("La transacción aparece en el listado")
    public void verifyTransactionInTable() {
        assertThat(transactionPage.isTransactionInTable(lastTransactionDescription))
            .as("La transacción creada debe estar visible en la tabla")
            .isTrue();
    }
}
