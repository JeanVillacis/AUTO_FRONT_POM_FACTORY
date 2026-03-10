Feature: Gestión de presupuesto personal E2E
  Como usuario nuevo de la aplicación de presupuesto
  Quiero registrarme, iniciar sesión y crear un primer egreso
  Para comenzar el control de mis finanzas personales

  Background:
    Given la aplicación de gestión de presupuesto está disponible

  @e2e
  Scenario: Registro exitoso, autenticación y creación de egreso visible en el listado
    When el usuario se registra con datos válidos
    Then el sistema confirma la creación exitosa de la cuenta
    And el usuario es redirigido a la página de inicio de sesión
    When el usuario inicia sesión con las credenciales registradas
    Then el sistema confirma el inicio de sesión exitoso
    And el usuario accede al panel principal de la aplicación
    When el usuario navega a la sección de transacciones
    And el usuario crea una nueva transacción de tipo egreso con datos válidos
    Then el sistema registra la transacción correctamente
    And la transacción aparece en el listado de transacciones