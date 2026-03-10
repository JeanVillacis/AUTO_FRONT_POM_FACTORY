package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class TestDataConstants {

    private static final String DEFAULT_BASE_URL = "http://localhost:3000";

    public static final String BASE_URL = resolveBaseUrl();
    public static final String REGISTER_URL = buildUrl("/register");
    public static final String LOGIN_URL = buildUrl("/login");
    public static final String DASHBOARD_URL = buildUrl("/dashboard");
    public static final String TRANSACTIONS_URL = buildUrl("/transactions");

    public static final String REG_DISPLAY_NAME = "Carlos Andrade";
    // Unique email per execution to avoid duplicate-user registration errors.
    public static final String REG_EMAIL = "carlos.andrade." + System.currentTimeMillis() + "@pruebas.com";
    public static final String REG_PASSWORD = "Prueba2026!";
    public static final String REG_CONFIRM_PASSWORD = "Prueba2026!";

    public static final String TX_TYPE = "EXPENSE";
    public static final String TX_DESCRIPTION = "Compra supermercado quincenal";
    public static final String TX_AMOUNT = "85.50";
    public static final String TX_CATEGORY = "Alimentación";
    public static final String TX_DATE = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

    private static String resolveBaseUrl() {
        String configuredBaseUrl = System.getProperty("webdriver.base.url", DEFAULT_BASE_URL);
        if (configuredBaseUrl.endsWith("/")) {
            return configuredBaseUrl.substring(0, configuredBaseUrl.length() - 1);
        }
        return configuredBaseUrl;
    }

    private static String buildUrl(String path) {
        return BASE_URL + path;
    }

    private TestDataConstants() {}
}