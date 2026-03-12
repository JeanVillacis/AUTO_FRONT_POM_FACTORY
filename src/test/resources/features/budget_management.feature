Feature: Gestión de presupuesto personal E2E
  Como usuario nuevo de la aplicación de presupuesto
  Quiero registrarme, iniciar sesión y registrar transacciones
  Para comenzar el control de mis finanzas personales

  Background:
    Given la aplicación de gestión de presupuesto está disponible

  @e2e
  Scenario Outline: Registro exitoso, autenticación y creación de transacción visible en el listado
    When el usuario se registra con nombre "<nombre>" y correo "<email>"
    Then el sistema confirma la creación exitosa de la cuenta
    And el usuario es redirigido a la página de inicio de sesión
    When el usuario inicia sesión con el correo "<email>"
    Then el sistema confirma el inicio de sesión exitoso
    And el usuario accede al panel principal de la aplicación
    When el usuario navega a la sección de transacciones
    And el usuario crea una transacción de tipo "<tipo>" con descripción "<descripcion>", monto "<monto>" y categoría "<categoria>"
    Then el sistema registra la transacción correctamente
    And la transacción aparece en el listado de transacciones

    Examples:
      | nombre          | email                           | tipo    | descripcion                   | monto  | categoria    |
      | Carlos Andrade  | carlos.andrade2@pruebas.com     | EXPENSE | Compra supermercado quincenal | 85.50  | Alimentación |
      | Maria Lopez     | maria.lopez2@pruebas.com        | EXPENSE | Cena en restaurante           | 45.00  | Alimentación |
