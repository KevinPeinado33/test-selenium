import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import service.ContainerService;
import service.impl.ContainerServiceImpl;
import utils.SystemStart;

import java.time.Duration;

public class Main {

    public static void main(String[] args) {

        System.setProperty("webdriver.ie.driver","./src/main/resources/driver/IEDriverServer_1.exe");

        ContainerService containerService = new ContainerServiceImpl();
        WebDriver driver                  = new InternetExplorerDriver();
        WebDriverWait ewait               = new WebDriverWait(driver, Duration.ofSeconds(40));

        driver.manage().window().maximize();
        driver.get("https://localhost:9443/duapost/login.jsp");

        SystemStart.startPageToLogin( driver );
        SystemStart.startSession( driver, ewait, "dua06","6666" );
        SystemStart.selectRoles( driver, ewait, "CZA_TENERIFE" );

        containerService.windowGenerateContainer(
                driver,
                ewait,
                "noUe",
                "lasPalmas",
                "PRUEBAFLUJO098",
                "CAMIONCITO",
                "123456"
        );

    }

}
