package com.uniovi.sdimywallapop;

import com.uniovi.sdimywallapop.pageobjects.*;
import com.uniovi.sdimywallapop.services.OffersService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SdiMywallapopApplicationTests {
    static String PathFirefox = "C:\\Archivos de programa\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver = "C:\\Users\\Aladino España\\Desktop\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";
    @Autowired
    private OffersService offersService;
    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }
    @BeforeEach
    public void setUp(){
        driver.navigate().to(URL);
    }
    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }
    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {}
    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        //Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

    /**
     * PR01. Registro de Usuario con datos válidos
     */

    @Test
    public void PR01() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "test_", "test_", "test@email.com",
                "123456", "123456");


    }

    /**
     * PR02. Registro de Usuario con datos inválidos (email vacío, nombre vacío,
     * apellidos vacíos)
     */

    @Test
    public void PR02() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");

        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "awffw", "test_", "a",
                "123456", "123456");

        // Comprobamos el error de email vacío.
        PO_SignUpView.checkElementByKey(driver,"Error.signup.name.length", PO_Properties.getSPANISH());

        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, " ", "fawf", "test_",
                "123456", "123456");
        // Comprobamos el error de nombre vacío.
        PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length",
                PO_Properties.getSPANISH());

        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "test@email.com", " ", "test@email.com",
                "123456", "123456");
        // Comprobamos el error de apellidos vacío.
        PO_SignUpView.checkElementByKey(driver, "Error.signup.lastName.length",
                PO_Properties.getSPANISH());

        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "test_", "test_", "test@email.com",
                " ", " ");
        // Comprobamos el error de contraseña vacía.
        PO_SignUpView.checkElementByKey(driver, "Error.signup.password.length",
                PO_Properties.getSPANISH());
    }

    /**
     * PR03. Registro de Usuario con datos inválidos (repetición de contraseña
     * inválida).
     */

    @Test
    public void PR03() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "test", "test", "test@email.com",
                "123456", "123457");
        // Comprobamos que el error existe
        PO_SignUpView.checkElementByKey(driver, "Error.signup.passwordConfirm.coincidence",
                PO_Properties.getSPANISH());
    }

    /**
     * PR04. Registro de Usuario con datos inválidos (email existente).
     */

    @Test
    public void PR04() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "test", "test", "admin@email.com",
                "123456", "123456");
        // Comprobamos el error de email repetido.
        PO_SignUpView.checkElementByKey(driver, "Error.signup.email.duplicate",
                PO_Properties.getSPANISH());
    }

    /**
     * PR05. Inicio de sesión con datos válidos (administrador).
     */

    @Test
    public void PR05() {
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        // Comprobamos que es el admin
        PO_NavView.checkIsAdmin(driver);
    }

    /**
     * PR06. Inicio de sesión con datos válidos (usuario estándar).
     */

    @Test
    public void PR06() {
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "miguel@email.com", "password");
        // Comprobamos que es el admin
        PO_NavView.checkIsUser(driver);
    }

    /**
     * PR07. Inicio de sesión con datos inválidos (usuario estándar, campo email
     * y contraseña vacíos).
     */

    @Test
    public void PR07() {
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "   ", "   ");
        // Comprobamos que el error existe
        PO_LoginView.checkElementByKey(driver, "login.error",
                PO_Properties.getSPANISH());
    }

    /**
     * PR08. Inicio de sesión con datos válidos (usuario estándar, email
     * existente, pero contraseña incorrecta).
     */

    @Test
    public void PR08() {
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "miguel@email.com", "123456789");
        // Comprobamos que el error existe
        PO_LoginView.checkElementByKey(driver, "login.error",
                PO_Properties.getSPANISH());
    }

    /**
     * PR09. Inicio de sesión con datos inválidos (usuario estándar, email no
     * existente en la aplicación).
     */

    @Test
    public void PR09() {
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "noexisto@email.com", "123456");
        // Comprobamos que el error existe
        PO_LoginView.checkElementByKey(driver, "login.error",
                PO_Properties.getSPANISH());
    }

    /**
     * PR10. Hacer click en la opción de salir de sesión y comprobar que se
     * redirige a la página de inicio de sesión (Login).
     */

    @Test
    public void PR10() {
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admim@email.com", "admin");
        // Salimos de sesión
        List<WebElement> elementos = PO_View.checkElementBy(driver, "free",
                "//li[contains(@id, 'desconexion')]/a");
        elementos.get(0).click();
        // Comprobamos que entramos en la página de login
        PO_LoginView.checkElementBy(driver, "id", "login");
    }

    /**
     * PR11. Comprobar que el botón cerrar sesión no está visible si el usuario
     * no está autenticado.
     */

    @Test
    public void PR11() {
        PO_View.checkElementByKey(driver, "logout.message",
                PO_Properties.getSPANISH());
    }

    /**
     * PR12. Mostrar el listado de usuarios y comprobar que se muestran todos
     * los que existen en el sistema.
     */

    @Test
    public void PR12() {
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        // Vamos a la lista de usuarios
        PO_UserList.goToPage(driver);
        // Conseguimos los usuarios
        List<WebElement> elementos = PO_UserList.checkElementBy(driver, "class",
                "checkBox");
        assertTrue(elementos.size() == 5);
        PO_UserList.checkElementBy(driver, "text", "miguel@email.com");
        PO_UserList.checkElementBy(driver, "text", "alfredo@email.com");
        PO_UserList.checkElementBy(driver, "text", "paco@email.com");
        PO_UserList.checkElementBy(driver, "text", "maria@hotmail.es");
        PO_UserList.checkElementBy(driver, "text", "alvaro@email.com");
    }

    /**
     * PR13. Ir a la lista de usuarios, borrar el primer usuario de la lista,
     * comprobar que la lista se actualiza y dicho usuario desaparece.
     */

    @Test
    public void PR13() {
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        // Vamos a la lista de usuarios
        PO_UserList.goToPage(driver);
        // Conseguimos los usuarios
        PO_UserList.deleteUser(driver, 0);
        List<WebElement> elementos = PO_UserList.checkElementBy(driver, "class",
                "checkBox");
        assertTrue(elementos.size() == 4);
        PO_UserList.checkElementBy(driver, "text", "alfredo@email.com");
        PO_UserList.checkElementBy(driver, "text", "paco@email.com");
        PO_UserList.checkElementBy(driver, "text", "maria@hotmail.es");
        PO_UserList.checkElementBy(driver, "text", "alvaro@email.com");
    }

    /**
     * PR14. Ir a la lista de usuarios, borrar el último usuario de la lista,
     * comprobar que la lista se actualiza y dicho usuario desaparece.
     */

    @Test
    public void PR14() {
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        // Vamos a la lista de usuarios
        PO_UserList.goToPage(driver);
        // Conseguimos los usuarios
        PO_UserList.deleteUser(driver, 4);
        List<WebElement> elementos = PO_UserList.checkElementBy(driver, "class",
                "checkBox");
        assertTrue(elementos.size() == 4);
        PO_UserList.checkElementBy(driver, "text", "miguel@email.com");
        PO_UserList.checkElementBy(driver, "text", "alfredo@email.com");
        PO_UserList.checkElementBy(driver, "text", "paco@email.com");
        PO_UserList.checkElementBy(driver, "text", "maria@hotmail.es");
    }

    @Test
    @Order(1)
    public void PR20() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "99999990A", "123456");
        // introducimos un campo vacío y buscamos
        driver.get("http://localhost:8090/offer/list?size=100&searchText=");
        // seleccionamos todas las que aparecen
        List<WebElement> rows = driver.findElements(By.className("filas-list-offers"));
        // comprobamos que el número de ofertas que aparecen son las que hay en el servicio
        Assertions.assertEquals(offersService.getOffers().size(), rows.size());
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    @Test
    @Order(2)
    public void PR21() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "99999990A", "123456");
        driver.get("http://localhost:8090/offer/list?size=100");
        // introducimos un campo que no existe en el campo de búsqueda
        WebElement input = driver.findElement(By.name("searchText"));
        input.click();
        input.clear();
        input.sendKeys("cdcc");
        // seleccionamos el botón de buscar
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button")).click();
        // seleccionamos todas las que aparecen
        List<WebElement> rows = driver.findElements(By.className("filas-list-offers"));
        // efectivamente comprobamos que no existe ninguna con ese título
        Assertions.assertEquals(offersService.getOffers().stream()
                .filter(offer -> offer.getTitle().equals("cdcc")).toList().size(), rows.size());
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    @Test
    @Order(3)
    public void PR22() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "99999990A", "123456");
        // mostramos todas las ofertas
        driver.get("http://localhost:8090/offer/list?size=100");
        // introducimos un campo que existe en el campo de búsqueda
        WebElement input = driver.findElement(By.name("searchText"));
        input.click();
        input.clear();
        input.sendKeys("Oferta 1");
        // buscamos la oferta
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button")).click();
        // la compramos
        driver.findElement(By.xpath("//*[@id=\"tableOffers\"]/tbody/tr/td[5]/div/a")).click();
        double value = (Double.parseDouble(driver.findElement
                (By.xpath("//*[@id=\"main-container\"]/div[1]/h4")).getText()));
        // comprobamos que se descuenta correctamente el marcador
        Assertions.assertEquals(100 - offersService.getOffers().stream()
                .filter(offer -> offer.isSold()).toList().get(0).getPrice(), value);
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    @Test
    @Order(4)
    public void PR23() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "99999992C", "123456");
        // mostramos todas las ofertas
        driver.get("http://localhost:8090/offer/list?size=100");
        // introducimos un campo que existe en el campo de búsqueda
        WebElement input = driver.findElement(By.name("searchText"));
        input.click();
        input.clear();
        input.sendKeys("Oferta 2");
        // buscamos la oferta
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button")).click();
        // la compramos
        driver.findElement(By.xpath("//*[@id=\"tableOffers\"]/tbody/tr/td[5]/div/a")).click();
        double value = (Double.parseDouble(driver.findElement
                (By.xpath("//*[@id=\"main-container\"]/div[1]/h4")).getText()));
        // comprobamos que se descuenta correctamente el marcador (está a cero)
        Assertions.assertEquals(100 - offersService.getOffers().stream()
                .filter(offer -> offer.isSold()).toList().get(0).getPrice(), value);
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    @Test
    @Order(5)
    public void PR24() {
        // nos logueamos
        PO_PrivateView.refactorLogging(driver, "99999993D", "123456");
        // mostramos todas las ofertas
        driver.get("http://localhost:8090/offer/list?size=100");
        // introducimos un campo que existe en el campo de búsqueda
        WebElement input = driver.findElement(By.name("searchText"));
        input.click();
        input.clear();
        input.sendKeys("Oferta 3");
        // buscamos la oferta
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button")).click();
        // la intentamos comprar
        driver.findElement(By.xpath("//*[@id=\"tableOffers\"]/tbody/tr/td[5]/div/a")).click();
        double value = (Double.parseDouble(driver.findElement
                (By.xpath("//*[@id=\"main-container\"]/div[1]/h4")).getText()));
        // comprobamos que el marcador sigue igual (a 100) porque no se pudo comprar
        Assertions.assertEquals(value, 100);
        // seleccionamos el mensaje que aparece
        String textFail = driver.findElement(By.xpath("//*[@id=\"main-container\"]/div[2]/div/span")).getText();
        // comprobamos que se corresponde con el mensaje de saldo no suficiente
        Assertions.assertEquals("El precio de la oferta es superior a su saldo (Saldo no suficiente)", textFail);
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

    @Test
    @Order(6)
    public void PR25() {
        // login
        PO_PrivateView.refactorLogging(driver, "99999977E", "123456");
        // mostramos las ofertas
        driver.get("http://localhost:8090/offer/list?size=100");
        // buscamos por título
        WebElement input = driver.findElement(By.name("searchText"));
        input.click();
        input.clear();
        input.sendKeys("Oferta 6");
        // seleccionamos buscar
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/form/button")).click();
        // la compramos
        driver.findElement(By.xpath("//*[@id=\"tableOffers\"]/tbody/tr/td[5]/div/a")).click();
        // vamos a la vista de ofertas compradas
        driver.get("http://localhost:8090/offer/listBuy");
        // seleccionamos todas las ofertas que aparecen
        List<WebElement> rows = driver.findElements(By.className("filas-listBuy-offers"));
        // vemos que solo puede haber una
        Assertions.assertEquals(offersService.getOffers().stream()
                .filter(offer -> offer.isSold() && offer.getDniComprador().equals("99999977E")).toList().size(), rows.size());
        // logout
        PO_PrivateView.refactorLogout(driver, "logout");
    }

}
