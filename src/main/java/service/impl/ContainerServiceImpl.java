package service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import service.ContainerService;
import utils.ScreenShot;
import utils.SeleniumUtil;
import utils.SystemStart;
import utils.TableUtil;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class ContainerServiceImpl implements ContainerService {

    Logger logger = Logger.getLogger("MyLog");

    @Override
    public void flowGenerateContainer(
            WebDriver driver,
            WebDriverWait ewait,
            String countryOrigin,
            String destinationCountry,
            String idContainer,
            String numContainer,
            String numSeal
    ) throws InterruptedException {

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
                    driver,"//table/tbody[@id='form:tblListadoRecipientesSinContenedor_data']/tr[" + ( i + 1 ) + "]/td[3]/*"
            );

            if (column.size() == 0) {

                SeleniumUtil.findElementById( driver, "form:tblListadoRecipientesSinContenedor:" + i + ":btnSelectedRecipiente" ).click();

                logger.info("Recipiente " + ( i + 1 ) + " seleccionado: ✅");

                // recalculando tamanio de la lista
                numRecipients = TableUtil.getSizeFromTable( driver, "form:tblListadoRecipientesSinContenedor" );

                if ( numRecipients > ( i + 1 ) ) {

                    SeleniumUtil.waitClickableElementById( ewait,"form:tblListadoRecipientesSinContenedor:" + ( i+  1 ) + ":btnSelectedRecipiente" );

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

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                                        .withTimeout(Duration.ofSeconds(10))
                                        .pollingEvery(Duration.ofSeconds(3))
                                        .ignoring(NoSuchElementException.class);

        wait.until(ExpectedConditions.visibilityOf(SeleniumUtil.findElementById(driver, "form:btnAsignarContenedor")));

        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {

                WebElement button = driver.findElement(By.xpath("//button[contains(@id, 'form:btnAsignarContenedor')]"));

                String enabled = null;

                try {

                    enabled = button.getAttribute("aria-disabled");

                } catch (Exception e) {

                    System.out.println(e.getMessage());
                    enabled = "false";

                }

                return ("true".equals(enabled));

            }
        });


        // llenado datos del formulario
        WebElement txtNnumContenedor = SeleniumUtil.findElementById( driver,"form:lblNumMatricula");
        WebElement txtNumPrecinto = SeleniumUtil.findElementById(driver, "form:lblNumPrecinto");

        txtNnumContenedor.clear();
        txtNumPrecinto.clear();

        txtNnumContenedor.sendKeys( numContainer );
        txtNumPrecinto.sendKeys( numSeal );

        logger.info("Datos del formulario del contenedor rellenados correctamente!");

        SeleniumUtil.findElementByXPath(driver,"//form/h2").click();

        // enviamos el DSDT
        SeleniumUtil.findElementById(driver, "form:btnEnviarContDsdt").click();
        logger.info("Modal abierto ..!!");

        // damos clic en la alerta de dialogo para enviar el DSDT
        SeleniumUtil.findElementByXPath(driver, "//div[@id='form:enviarContenedorNOUEPopup']/div/div/div/button[1]").click();
        logger.info("Se acaban de enviar los dsdt ... proceda enviado las ALTA_SUMARIA_SINTRA!!!");

        Thread.sleep(Duration.ofSeconds(6).toMillis());

        driver.quit();

    }


    @Override
    public void flowSendAltaEnvioTaric( WebDriver driver, WebDriverWait ewait, String idContainer) throws InterruptedException {
        SystemStart.enterWindowByMenuAndSubMenu(
                driver,
                ewait,
                "Contenedores",
                "Creación contenedor"
        );

        logger.info("Dentro de la pagina: ✅");

        // flujo para ingresar el id del contenedor
        SeleniumUtil.waitClickableElementById( ewait,"form:bCacesa");

        WebElement idGroup = SeleniumUtil.findElementById( driver,"form:lblIdAgrupacion");

        idGroup.clear();
        idGroup.sendKeys( idContainer );

        SeleniumUtil.findElementByXPath(driver,"//form/h2").click();

        logger.info("Id del contenedor ingresado correctamente: ✅");

        // esperamos hasta que el boton de enviar altas pueda estar disponible
        SeleniumUtil.waitClickableElementById( ewait,"form:btnEnviarAltaTaric");

        // enviamos las altas envio taric
        SeleniumUtil.findElementById(driver, "form:btnEnviarAltaTaric").click();
        logger.info("Modal abierto ..!!");

        // damos clic en la alerta de dialogo para enviar el DSDT
        SeleniumUtil.findElementByXPath(driver, "//div[@id='form:enviarAltaTaricPopup']/div/div/div/button[1]").click();
        logger.info("Se están enviando las ALTA_ENVIO_TARIC!");

        Thread.sleep(Duration.ofSeconds(180).toMillis());

    }

    @Override
    public void flowSendContenedorAgrupado(
            WebDriver driver,
            WebDriverWait ewait,
            String idContainer
    ) throws InterruptedException {

        SystemStart.enterWindowByMenuAndSubMenu(
                driver,
                ewait,
                "Contenedores",
                "Creación contenedor"
        );

        logger.info("Dentro de la pagina: ✅");

        // flujo para ingresar el id del contenedor
        SeleniumUtil.waitClickableElementById( ewait,"form:bCacesa");

        WebElement idGroup = SeleniumUtil.findElementById( driver,"form:lblIdAgrupacion");

        idGroup.clear();
        idGroup.sendKeys( idContainer );

        SeleniumUtil.findElementByXPath(driver,"//form/h2").click();

        logger.info("Id del contenedor ingresado correctamente: ✅");

        // esperamos hasta que el boton de enviar contenedor agrupado pueda estar disponible
        SeleniumUtil.waitClickableElementById( ewait,"form:btnEnviarContenedor");

        // enviamos el contenedor agrupado
        SeleniumUtil.findElementById(driver, "form:btnEnviarContenedor").click();
        logger.info("Modal abierto ..!!");

        ScreenShot.screenShot(driver, null, "paso final");

        // damos clic en la alerta de dialogo para enviar el DSDT
        SeleniumUtil.findElementByXPath(driver, "//div[@id='form:enviarContenedorPopup']/div/div/div/button[1]").click();
        logger.info("Se están enviando los CONTENEDOR_AGRUPADO!");

        Thread.sleep(Duration.ofSeconds(120).toMillis());

    }

    @Override
    public void flowConsultaAgrupaPendienteInfo(WebDriver driver, WebDriverWait ewait) {

        SystemStart.enterWindowByMenuAndSubMenu(
                driver,
                ewait,
                "Contenedores",
                "Consulta agrupaciones pendientes"
        );

        // esperamos hasta q el boton melilla pueda darse clic
        ewait.until(ExpectedConditions.elementToBeClickable(By.id("form:melilla")));

        // damos
        driver.findElement(By.id("form:melilla")).click();


        // esperamos hasta que aparezca algo en la tabla
        ewait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form:tblListadoContenedores_data")));

        // esperamos hasta que se pueda dar clic en el boton de la flechita
        ewait.until(ExpectedConditions.elementToBeClickable(By.id("form:tblListadoContenedores:0:btnSelectContenedor")));

        driver.findElement(By.id("form:tblListadoContenedores:0:btnSelectContenedor")).click();


        // esperamos hasta que aparezca el formulario
        ewait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form:renderedEnvios")));

        // empezamos a rellenar los datos

        WebElement txtCodMrn = driver.findElement(By.id("form:lblCodMrn"));

        txtCodMrn.clear();

        txtCodMrn.sendKeys("PRUEBA01212");

        driver.findElement(By.id("form:lblUbicacion_label")).click();
        driver.findElement(By.xpath("//li[text()='ES005611ALMPOS']")).click();

        List<WebElement> txtEnvioForm = driver.findElements(By.xpath("//span[@id='form:renderedEnvios']/*"));

        int numEnviosList = txtEnvioForm.size();

        for (int i = 0; i < numEnviosList; i++) {

            WebElement inputEnvio = driver.findElement(
                    By.xpath("//span[@id='form:renderedEnvios']/div["+(i+1)+"]/div[3]/input")
            );

            inputEnvio.clear();

            inputEnvio.sendKeys("PRUEBA"+(i+1));

        }

        ewait.until(ExpectedConditions.elementToBeClickable(By.id("form:btnEnviarValores")));

        driver.findElement(By.id("form:btnEnviarValores")).click();

        driver.findElement(By.xpath("//div[@id='form:enviarValoresContenedor']/div/div/div/button[1]")).click();

        System.out.println("Flujo termiando!!!!");

    }

}
