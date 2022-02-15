package service.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import service.ContainerService;
import utils.SeleniumUtil;
import utils.SystemStart;
import utils.TableUtil;

import java.util.List;
import java.util.logging.Logger;

public class ContainerServiceImpl implements ContainerService {

    Logger logger = Logger.getLogger("MyLog");

    @Override
    public void windowGenerateContainer(
            WebDriver driver,
            WebDriverWait ewait,
            String countryOrigin,
            String destinationCountry,
            String idContainer,
            String numContainer,
            String numSeal
    ) {

        SystemStart.enterWindowByMenuAndSubMenu(
                driver,
                ewait,
                "Contenedores",
                "Creación contenedor"
        );

        logger.info("Dentro de la pagina");

        // flow origin
        SeleniumUtil.waitClickableElementById( ewait,"form:" + countryOrigin + "");
        SeleniumUtil.findElementById(driver,"form:" + countryOrigin + "").click();

        logger.info("Origen seleccionado");

        // flow destination
        SeleniumUtil.waitClickableElementById( ewait,"form:" + destinationCountry + "");
        SeleniumUtil.findElementById(driver,"form:" + destinationCountry + "").click();

        logger.info("Destino Seleccionado");

        // flow id container
        SeleniumUtil.waitClickableElementById( ewait,"form:btnLimpiarVentana");

        WebElement idGroup = SeleniumUtil.findElementById( driver,"form:lblIdAgrupacion");

        idGroup.clear();
        idGroup.sendKeys( idContainer );

        SeleniumUtil.findElementByXPath(driver,"//form/h2").click();

        logger.info("Id del contenedor ingresado correctamente.");

        // flow select recipients
        int numRecipients = TableUtil.getSizeFromTable( driver, "form:tblListadoRecipientesSinContenedor" );

        logger.info("Obteniendo cantidad de datos por tabla => " + numRecipients );

        for (int i = 0; i < numRecipients; i ++) {

            logger.info("Evaluando recipiente numero: " +  (i+1) );

            List<WebElement> column = SeleniumUtil.findElementsByXPath(
                    driver,"//table/tbody[@id='form:tblListadoRecipientesSinContenedor_data']/tr["+(i+1)+"]/td[3]/*"
            );

            logger.info("Longitud de elementos en la columna seleccionada " + column.size() );

            if (column.size() == 0) {

                SeleniumUtil.findElementById( driver, "form:tblListadoRecipientesSinContenedor:"+i+":btnSelectedRecipiente" ).click();

                if (numRecipients > (i+1)) SeleniumUtil.waitClickableElementById( ewait,"form:tblListadoRecipientesSinContenedor:"+(1+1)+":btnSelectedRecipiente" );

            }

        }

        // save change of container
        // ewait.until(ExpectedConditions.elementToBeClickable(By.id("form:btnAsignarContenedor")));
        // driver.findElement(By.id("form:btnAsignarContenedor"));

    }

    @Override
    public void windowRecoverContainer( WebDriver driver, WebDriverWait ewait ) {

    }

    @Override
    public void windowFlowCacesaInGenerateContainer(  WebDriver driver, WebDriverWait ewait  ) {

    }

}
