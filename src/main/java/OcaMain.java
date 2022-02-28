import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OcaMain {

    public static void main(String[] args) {

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


        // seleeccionamos el rol
        ewait.until(ExpectedConditions.elementToBeClickable(By.id("Acceder")));

        Select rol = new Select(driver.findElement(By.id("comboAcceso")));

        rol.selectByVisibleText("MADRID B");

        Select privilegio = new Select(driver.findElement(By.id("sPerfilSeleccionado")));

        privilegio.selectByVisibleText("Administracion");

        driver.findElement(By.id("Acceder")).click();

        System.out.println("Entramos al sistema!!!!");

    }
}
