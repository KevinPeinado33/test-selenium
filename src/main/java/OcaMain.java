import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class OcaMain {

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.ie.driver", "./src/main/resources/driver/IEDriverServer_1.exe");

        WebDriver driver = new InternetExplorerDriver();
        WebDriverWait ewait = new WebDriverWait(driver, Duration.ofSeconds(40));

        driver.manage().window().maximize();
        driver.get("http://localhost:8080/OCA3Web/jsp/oca_frames.jsp");

        ewait.until(ExpectedConditions.elementToBeClickable(By.name("Logon")));

        WebElement user = driver.findElement(By.name("j_username"));
        WebElement passw = driver.findElement(By.name("j_password"));

        user.clear();
        passw.clear();

        user.sendKeys("oca3");
        passw.sendKeys("oca3");

        driver.findElement(By.name("Logon")).click();

        System.out.println("Nos acabamos de logear");

        driver.switchTo().frame(3);

        // seleccionamos el rol
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                                        .withTimeout(Duration.ofSeconds(10))
                                        .pollingEvery(Duration.ofSeconds(3))
                                        .ignoring(NoSuchElementException.class);

        ewait.until(
                ExpectedConditions.presenceOfNestedElementsLocatedBy(
                        By.id("comboAcceso"), By.tagName("option")
                )
        );

        Select rol = new Select(driver.findElement(By.xpath("//select[@id='comboAcceso']")));

        rol.selectByVisibleText("MADRID B");

        Select privilegio = new Select(driver.findElement(By.xpath("//select[@id='sPerfilSeleccionado']")));

        privilegio.selectByVisibleText("Administracion");

        driver.findElement(By.id("Acceder")).click();

        System.out.println("Entramos al sistema!!!!");


        // hacemos el flujo para consultar envios

        driver.switchTo().defaultContent();
        driver.switchTo().frame(3);

        // espereamos hasta q el logo pueda verse
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cabecera")));

        // damos click en Envios
        driver.findElement(By.xpath("//ul[@id='menu_principal']/li[2]/a")).click();

        //esperamos hasta que nos aparezca el submenu
        ewait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//ul[@id='theMenu']/li[2]/h3/a")
                )
        );

        // damos click en consulta envios
        driver.findElement(By.xpath("//ul[@id='theMenu']/li[2]/h3/a")).click();

        System.out.println("Ya entramos a consulta envios!!!");


        // esperamos hasta que se pueda dar click el boton buscar
        ewait.until(ExpectedConditions.elementToBeClickable(By.id("filtrar")));

        WebElement codNacional = driver.findElement(By.id("_formulario__envio_sCodEnvioNacional"));
        WebElement codInternacional = driver.findElement(By.id("_formulario__envio_sCodEnvioInternacional"));
        WebElement codEnvioCanal = driver.findElement(By.id("_formulario__envio_sCodEnvioIntegrador"));

        codNacional.clear();
        codInternacional.clear();
        codEnvioCanal.clear();

        codNacional.sendKeys("QWEQEQWE");
        codInternacional.sendKeys("QWEQW");
        codEnvioCanal.sendKeys("WWWWWWWWW");


        driver.findElement(By.id("filtrar")).click();

        System.out.println("Estamos buscando!!!");

    }
}
