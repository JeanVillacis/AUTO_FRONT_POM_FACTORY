package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import steps.BudgetSteps;

public class BudgetStepDefinitions {

    @Steps
    BudgetSteps budgetSteps;

    @Given("la aplicación de gestión de presupuesto está disponible")
    public void laAplicacionEstaDisponible() {
        budgetSteps.openRegisterPage();
    }

    @When("el usuario se registra con datos válidos")
    public void elUsuarioSeRegistraConDatosValidos() {
        budgetSteps.fillRegistrationForm();
        budgetSteps.submitRegistration();
    }

    @Then("el sistema confirma la creación exitosa de la cuenta")
    public void elSistemaConfirmaLaCreacionExitosa() {
        budgetSteps.verifyRegistrationSuccess();
    }

    @And("el usuario es redirigido a la página de inicio de sesión")
    public void elUsuarioEsRedirigidoALogin() {
        budgetSteps.verifyRedirectToLogin();
    }

    @When("el usuario inicia sesión con las credenciales registradas")
    public void elUsuarioIniciaSesion() {
        budgetSteps.fillLoginForm();
        budgetSteps.submitLogin();
    }

    @Then("el sistema confirma el inicio de sesión exitoso")
    public void elSistemaConfirmaElInicioSesion() {
        budgetSteps.verifyLoginSuccess();
    }

    @And("el usuario accede al panel principal de la aplicación")
    public void elUsuarioAccedeAlPanel() {
        budgetSteps.verifyAccessToDashboard();
    }

    @When("el usuario navega a la sección de transacciones")
    public void elUsuarioNavegarATransacciones() {
        budgetSteps.navigateToTransactions();
    }

    @And("el usuario crea una nueva transacción de tipo egreso con datos válidos")
    public void elUsuarioCreaUnaTransaccion() {
        budgetSteps.openNewTransactionModal();
        budgetSteps.fillTransactionForm();
        budgetSteps.submitTransaction();
    }

    @Then("el sistema registra la transacción correctamente")
    public void elSistemaRegistraLaTransaccion() {
        budgetSteps.verifyTransactionCreated();
    }

    @And("la transacción aparece en el listado de transacciones")
    public void laTransaccionApareceEnElListado() {
        budgetSteps.verifyTransactionInTable();
    }
}