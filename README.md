# AUTO_FRONT_POM_FACTORY

Framework de automatizacion E2E para frontend con `Java + Serenity BDD + Cucumber + Gradle`, implementado con patron `POM + Page Factory`.

## Objetivo

Automatizar un flujo E2E completo sobre la aplicacion de presupuesto:

- Registro de usuario
- Inicio de sesion
- Creacion de una transaccion de egreso
- Verificacion de la transaccion en el listado

Aplicacion bajo prueba (AUT):

- Repositorio: `https://github.com/ChristopherPalloArias/Budget_Management_App/tree/release/1.2.1`
- Puerto esperado: `3000`
- Base URL esperada por los tests: `http://localhost:3000`

## Stack tecnico

- Lenguaje: `Java 17`
- Build tool: `Gradle`
- Framework E2E: `Serenity BDD`
- BDD/Runner: `Cucumber + CucumberWithSerenity`
- Patron UI: `Page Object Model (POM) + Page Factory (@FindBy)`
- Navegador: `Chrome`

## Prerequisitos

1. `JDK 17` instalado y en `PATH`
2. `Gradle Wrapper` (incluido en el proyecto)
3. `Google Chrome` instalado
4. Aplicacion `Budget_Management_App` ejecutandose en `http://localhost:3000`

## Configuracion del AUT (Budget_Management_App)

En otra terminal:

```bash
git clone -b release/1.2.1 https://github.com/ChristopherPalloArias/Budget_Management_App.git
cd Budget_Management_App
npm install
```

Levanta la app en puerto `3000`. Si el script `dev` no usa ese puerto por defecto, fuerza el puerto:

```bash
npm run dev -- --port 3000
```

Verifica que abra en `http://localhost:3000` antes de ejecutar los tests.

## Configuracion de este proyecto de pruebas

Archivo `serenity.properties`:

```properties
webdriver.driver=chrome
webdriver.base.url=http://localhost:3000
serenity.project.name=AUTO_FRONT_POM_FACTORY
serenity.take.screenshots=AFTER_EACH_STEP
headless.mode=false
```

## Ejecucion de pruebas

Desde la raiz de este proyecto (`AUTO_FRONT_POM_FACTORY/AUTO_FRONT_POM_FACTORY`):

```bash
./gradlew clean test
```

Runner configurado en:

- `src/test/java/runner/TestRunner.java`

Tag ejecutado por defecto:

- `@e2e`

Feature principal:

- `src/test/resources/features/budget_management.feature`

## Reportes

Serenity genera resultados en:

- `target/site/serenity/index.html`

Para abrir el reporte en macOS:

```bash
open target/site/serenity/index.html
```

## Estructura del proyecto

```text
src/test/java/
  pages/
    RegisterPage.java
    LoginPage.java
    TransactionPage.java
  steps/
    BudgetSteps.java
  stepdefinitions/
    BudgetStepDefinitions.java
  runner/
    TestRunner.java
  utils/
    TestDataConstants.java

src/test/resources/
  features/
    budget_management.feature
```

## Buenas practicas implementadas

- Separacion clara entre Gherkin, Step Definitions, Steps y Page Objects
- Localizadores UI centralizados con `@FindBy`
- Esperas explicitas para reducir flakiness
- Aserciones descriptivas con AssertJ
- Datos de prueba centralizados en `TestDataConstants`

## Troubleshooting rapido

1. Error `ERR_CONNECTION_REFUSED`
- La app AUT no esta levantada o no esta en `localhost:3000`.

2. Fallas por elementos no encontrados
- Verifica cambios de UI en la version `release/1.2.1`.
- Revisa localizadores en `src/test/java/pages`.

3. Chrome/CDP warnings
- Son advertencias comunes de version y no necesariamente rompen la ejecucion.

4. Se ejecuta Maven en vez de Gradle
- Este proyecto se ejecuta con `./gradlew`; `pom.xml` existe como referencia, pero el flujo principal aqui es Gradle.

