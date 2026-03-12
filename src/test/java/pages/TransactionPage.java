package pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.Locale;

public class TransactionPage extends PageObject {

    private static final Duration TABLE_WAIT = Duration.ofSeconds(20);

    @FindBy(xpath = "//button[.//span[contains(text(), 'Nueva Transacción')]]")
    private WebElementFacade newTransactionButton;

    @FindBy(xpath = "//div[@data-slot='dialog-content']//h2[contains(text(), 'Nueva Transacción')]")
    private WebElementFacade newTransactionDialogTitle;

    @FindBy(xpath = "//label[normalize-space()='Tipo']/following::button[@role='combobox'][1]")
    private WebElementFacade typeSelectTrigger;

    @FindBy(xpath = "//*[@role='option' and (.='Egreso' or .//*[normalize-space()='Egreso'])]")
    private WebElementFacade typeOptionExpense;

    @FindBy(xpath = "//*[@role='option' and (.='Ingreso' or .//*[normalize-space()='Ingreso'])]")
    private WebElementFacade typeOptionIncome;

    @FindBy(css = "input[placeholder='Ej: Supermercado']")
    private WebElementFacade descriptionInput;

    @FindBy(css = "input[type='number'][placeholder='0.00']")
    private WebElementFacade amountInput;

    @FindBy(xpath = "//label[contains(normalize-space(), 'Categor')]/following::button[@role='combobox'][1]")
    private WebElementFacade categorySelectTrigger;

    @FindBy(css = "input[type='date']")
    private WebElementFacade dateInput;

    @FindBy(xpath = "//div[@data-slot='dialog-content']//button[@type='submit' and contains(., 'Crear Transacción')]")
    private WebElementFacade createTransactionSubmitButton;

    @FindBy(xpath = "//li[@data-sonner-toast]")
    private WebElementFacade transactionSuccessToast;

    public void openPage(String url) {
        getDriver().get(url);
    }

    public void openNewTransactionDialog() {
        newTransactionButton.waitUntilClickable();
        newTransactionButton.click();
    }

    public boolean isModalDisplayed() {
        newTransactionDialogTitle.waitUntilVisible();
        return newTransactionDialogTitle.isDisplayed();
    }

    public void selectTransactionType(String type) {
        typeSelectTrigger.waitUntilClickable();
        String currentType = typeSelectTrigger.getText();

        TransactionType targetType = TransactionType.from(type);
        if (targetType == TransactionType.EXPENSE && currentType.contains("Egreso")) {
            return;
        }

        typeSelectTrigger.click();

        if (targetType == TransactionType.EXPENSE) {
            typeOptionExpense.waitUntilClickable();
            typeOptionExpense.click();
        } else {
            typeOptionIncome.waitUntilClickable();
            typeOptionIncome.click();
        }
    }

    public void enterDescription(String description) {
        descriptionInput.waitUntilVisible();
        descriptionInput.clear();
        descriptionInput.sendKeys(description);
    }

    public void enterAmount(String amount) {
        amountInput.waitUntilVisible();
        evaluateJavascript(
            "var nativeSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
                + "nativeSetter.call(arguments[0], arguments[1]);"
                + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
                + "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
            amountInput,
            amount
        );
    }

    public void selectCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de categoría no puede ser vacío");
        }

        categorySelectTrigger.waitUntilClickable();
        categorySelectTrigger.click();

        By categoryOption = By.xpath(
            "//*[@role='option' and (normalize-space()="
                + xpathLiteral(category)
                + " or .//*[normalize-space()="
                + xpathLiteral(category)
                + "])]"
        );

        $(categoryOption).waitUntilClickable();
        $(categoryOption).click();
    }

    public void enterDate(String date) {
        dateInput.waitUntilVisible();
        evaluateJavascript(
            "arguments[0].value = arguments[1];"
                + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
                + "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
            dateInput,
            date
        );
    }

    public void submitTransaction() {
        createTransactionSubmitButton.waitUntilClickable();
        createTransactionSubmitButton.click();
    }

    public boolean isSuccessToastDisplayed() {
        transactionSuccessToast.waitUntilVisible();
        return transactionSuccessToast.isDisplayed();
    }

    public boolean isTransactionInTable(String description) {
        String descriptionLower = description.toLowerCase(Locale.ROOT);
        By rowLocator = By.xpath(
            "//table//td[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                + descriptionLower
                + "')]"
        );
        $(rowLocator).withTimeoutOf(TABLE_WAIT).waitUntilVisible();
        return $(rowLocator).isDisplayed();
    }

    private String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }

        String[] parts = value.split("'");
        StringBuilder builder = new StringBuilder("concat(");
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                builder.append(", \"'\", ");
            }
            builder.append("'").append(parts[i]).append("'");
        }
        builder.append(")");
        return builder.toString();
    }

    private enum TransactionType {
        EXPENSE,
        INCOME;

        private static TransactionType from(String value) {
            if ("EXPENSE".equalsIgnoreCase(value)) {
                return EXPENSE;
            }
            if ("INCOME".equalsIgnoreCase(value)) {
                return INCOME;
            }
            throw new IllegalArgumentException("Tipo de transacción no soportado: " + value);
        }
    }
}
