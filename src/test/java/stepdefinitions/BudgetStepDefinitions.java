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

    @When("el usuario se registra con nombre {string} y correo {string}")
    public void elUsuarioSeRegistraConNombreYCorreo(String nombre, String email) {
        budgetSteps.fillRegistrationForm(nombre, email);
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

    @When("el usuario inicia sesión con el correo {string}")
    public void elUsuarioIniciaSesionConCorreo(String email) {
        budgetSteps.fillLoginForm(email);
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

    @And("el usuario crea una transacción de tipo {string} con descripción {string}, monto {string} y categoría {string}")
    public void elUsuarioCreaUnaTransaccion(String tipo, String descripcion, String monto, String categoria) {
        budgetSteps.openNewTransactionModal();
        budgetSteps.fillTransactionForm(tipo, descripcion, monto, categoria);
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
