package service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface ContainerService {

    /**
     * Method to be able to test the entire
     * normal flow 'Generar Contenedor' window
     * [DSDT]
     *
     * @param driver
     * @param ewait
     * @throws InterruptedException
     */
    void flowGenerateContainer(
            WebDriver driver,
            WebDriverWait ewait,
            String countryOrigin,
            String destinationCountry,
            String idContainer,
            String numContainer,
            String numSeal
    ) throws InterruptedException;


    /**
     * Flow for send alta envio taric of
     * container created
     *
     * @param driver
     * @param ewait
     * @param idContainer
     * @throws InterruptedException
     */
    void flowSendAltaEnvioTaric(
            WebDriver driver,
            WebDriverWait ewait,
            String idContainer
    ) throws InterruptedException ;

    /**
     * Flow for send ContenedorAgrupado
     * of container created
     *
     * @param driver
     * @param ewait
     * @param idContainer
     * @throws InterruptedException
     */
    void flowSendContenedorAgrupado(
            WebDriver driver,
            WebDriverWait ewait,
            String idContainer
    ) throws InterruptedException;

}
