package pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Locale;

public class TransactionPage extends PageObject {

    private static final Duration DEFAULT_WAIT = Duration.ofSeconds(5);
    private static final Duration TABLE_WAIT = Duration.ofSeconds(20);

    @FindBy(xpath = "//button[.//span[contains(text(), 'Nueva Transacción')]]")
    private WebElement newTransactionButton;

    @FindBy(xpath = "//div[@data-slot='dialog-content']//h2[contains(text(), 'Nueva Transacción')]")
    private WebElement newTransactionDialogTitle;

    @FindBy(xpath = "//label[normalize-space()='Tipo']/following::button[@role='combobox'][1]")
    private WebElement typeSelectTrigger;

    @FindBy(xpath = "//*[@role='option' and (.='Egreso' or .//*[normalize-space()='Egreso'])]")
    private WebElement typeOptionExpense;

    @FindBy(xpath = "//*[@role='option' and (.='Ingreso' or .//*[normalize-space()='Ingreso'])]")
    private WebElement typeOptionIncome;

    @FindBy(css = "input[placeholder='Ej: Supermercado']")
    private WebElement descriptionInput;

    @FindBy(css = "input[type='number'][placeholder='0.00']")
    private WebElement amountInput;

    @FindBy(xpath = "//label[contains(normalize-space(), 'Categor')]/following::button[@role='combobox'][1]")
    private WebElement categorySelectTrigger;

    @FindBy(css = "input[type='date']")
    private WebElement dateInput;

    @FindBy(xpath = "//div[@data-slot='dialog-content']//button[@type='submit' and contains(., 'Crear Transacción')]")
    private WebElement createTransactionSubmitButton;

    @FindBy(xpath = "//li[@data-sonner-toast]")
    private WebElement transactionSuccessToast;

    public void openPage(String url) {
        getDriver().get(url);
    }

    public void clickNewTransactionButton() {
        waitFor(newTransactionButton).waitUntilClickable();
        newTransactionButton.click();
    }

    public boolean isModalDisplayed() {
        waitFor(newTransactionDialogTitle).waitUntilVisible();
        return newTransactionDialogTitle.isDisplayed();
    }

    public void selectTransactionType(String type) {
        waitFor(typeSelectTrigger).waitUntilClickable();
        String currentType = typeSelectTrigger.getText();

        TransactionType targetType = TransactionType.from(type);
        if (targetType == TransactionType.EXPENSE && currentType.contains("Egreso")) {
            return;
        }

        typeSelectTrigger.click();
        WebDriverWait wait = defaultWait();

        if (targetType == TransactionType.EXPENSE) {
            wait.until(ExpectedConditions.elementToBeClickable(typeOptionExpense));
            typeOptionExpense.click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(typeOptionIncome));
            typeOptionIncome.click();
        }
    }

    public void enterDescription(String description) {
        waitFor(descriptionInput).waitUntilVisible();
        descriptionInput.clear();
        descriptionInput.sendKeys(description);
    }

    public void enterAmount(String amount) {
        waitFor(amountInput).waitUntilVisible();
        amountInput.clear();
        amountInput.sendKeys(amount);
    }

    public void selectCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de categoría no puede ser vacío");
        }

        waitFor(categorySelectTrigger).waitUntilClickable();
        categorySelectTrigger.click();

        By categoryOption = By.xpath(
            "//*[@role='option' and (normalize-space()="
                + xpathLiteral(category)
                + " or .//*[normalize-space()="
                + xpathLiteral(category)
                + "])]"
        );

        WebElement option = defaultWait().until(ExpectedConditions.elementToBeClickable(categoryOption));
        option.click();
    }

    public void enterDate(String date) {
        waitFor(dateInput).waitUntilVisible();
        evaluateJavascript(
            "arguments[0].value = arguments[1];"
                + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
                + "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
            dateInput,
            date
        );
    }

    public void clickCreateTransactionButton() {
        waitFor(createTransactionSubmitButton).waitUntilClickable();
        createTransactionSubmitButton.click();
    }

    public boolean isSuccessToastDisplayed() {
        waitFor(transactionSuccessToast).waitUntilVisible();
        return transactionSuccessToast.isDisplayed();
    }

    public boolean isTransactionInTable(String description) {
        WebDriverWait wait = new WebDriverWait(getDriver(), TABLE_WAIT);
        String descriptionLower = description.toLowerCase(Locale.ROOT);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath(
                "//table//td[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                    + descriptionLower
                    + "')]"
            )
        )).isDisplayed();
    }

    private WebDriverWait defaultWait() {
        return new WebDriverWait(getDriver(), DEFAULT_WAIT);
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