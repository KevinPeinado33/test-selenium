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

        logger.info("Dentro de la pagina: ✅");

        // flujo para seleccionar origen
        SeleniumUtil.waitClickableElementById( ewait,"form:" + countryOrigin + "");
        SeleniumUtil.findElementById(driver,"form:" + countryOrigin + "").click();

        logger.info("Origen seleccionado: ✅");

        // flujo para el destino
        SeleniumUtil.waitClickableElementById( ewait,"form:" + destinationCountry + "");
        SeleniumUtil.findElementById(driver,"form:" + destinationCountry + "").click();

        logger.info("Destino Seleccionado: ✅");

        // flujo para ingresar id contenedor
        SeleniumUtil.waitClickableElementById( ewait,"form:btnLimpiarVentana");

        WebElement idGroup = SeleniumUtil.findElementById( driver,"form:lblIdAgrupacion");

        idGroup.clear();
        idGroup.sendKeys( idContainer );

        SeleniumUtil.findElementByXPath(driver,"//form/h2").click();

        logger.info("Id del contenedor ingresado correctamente: ✅");

        // flujo para seleccionar recipientes
        int numRecipients = TableUtil.getSizeFromTable( driver, "form:tblListadoRecipientesSinContenedor" );

        logger.info("Obteniendo cantidad de datos por tabla => " + numRecipients );

        for (int i = 0; i < numRecipients; i ++) {

            // obteniendo la cantidad de elementos q hay en la columan 3
            List<WebElement> column = SeleniumUtil.findElementsByXPath(
                    driver,"//table/tbody[@id='form:tblListadoRecipientesSinContenedor_data']/tr["+(i+1)+"]/td[3]/*"
            );

            if (column.size() == 0) {

                SeleniumUtil.findElementById( driver, "form:tblListadoRecipientesSinContenedor:"+i+":btnSelectedRecipiente" ).click();

                logger.info("Recipiente " + ( i + 1 ) + " seleccionado: ✅");

                // recalculando tamanio de la lista
                numRecipients = TableUtil.getSizeFromTable( driver, "form:tblListadoRecipientesSinContenedor" );

                if ( numRecipients > ( i+1 ) ) {

                    SeleniumUtil.waitClickableElementById( ewait,"form:tblListadoRecipientesSinContenedor:"+(i+1)+":btnSelectedRecipiente" );

                } else {

                    logger.info("Ya no hay mas recipientes libres!!");

                    break;

                }


            }

        }

        // flujo para asingar el contenedor
        SeleniumUtil.waitClickableElementById(ewait, "form:btnAsignarContenedor");
        SeleniumUtil.findElementById(driver, "form:btnAsignarContenedor").click();

        logger.info("Contenedor registrado correctamente!");


        // flujo para mandar el contenedor por DSDT
        SeleniumUtil.waitClickableElementById(ewait, "form:lblNumMatricula");
        String isNumContenedor = SeleniumUtil.findElementById(driver, "form:lblNumMatricula").getAttribute("disabled");

        System.out.println(isNumContenedor);

        if (isNumContenedor == null && !"disabled".equals( isNumContenedor )) {

            logger.info("Por alguna razón no se activa el campo de texto 🤡!");

            System.exit(0);

        }

        WebElement txtNnumContenedor = SeleniumUtil.findElementById( driver,"form:lblIdAgrupacion");
        WebElement txtNumPrecinto = SeleniumUtil.findElementById(driver, "form:lblNumPrecinto");

        txtNnumContenedor.clear();
        txtNumPrecinto.clear();

        txtNnumContenedor.sendKeys( numContainer );
        txtNumPrecinto.sendKeys( numSeal );

        String isBtnSenDSDT = SeleniumUtil.findElementById(driver, "form:btnEnviarContDsdt").getAttribute("disabled");

        System.out.println(isBtnSenDSDT);

        if (isBtnSenDSDT == null && !"disabled".equals( isBtnSenDSDT )) {

            logger.info("No se habilita el botón de enviar DSDT 🤡!");

            System.exit(0);

        }

        // espereamos hasta que el boton de DSDT pueda ser cliceado
        SeleniumUtil.waitClickableElementById(ewait, "form:btnEnviarContDsdt");
        SeleniumUtil.findElementById(driver, "form:btnEnviarContDsdt").click();

        // esperamos hasta que el alert dialog nos salga
    }

    @Override
    public void windowRecoverContainer( WebDriver driver, WebDriverWait ewait ) {

    }

    @Override
    public void windowFlowCacesaInGenerateContainer(  WebDriver driver, WebDriverWait ewait  ) {

    }

}
