package service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface ContainerService {

    /**
     * Method to be able to test the entire
     * normal flow 'Generar Contenedor' window
     *
     * @param driver
     * @param ewait
     */
    void windowGenerateContainer(
            WebDriver driver,
            WebDriverWait ewait,
            String countryOrigin,
            String destinationCountry,
            String idContainer,
            String numContainer,
            String numSeal
    );

    /**
     * Method for testing container recovery
     *
     * @param driver
     * @param ewait
     */
    void windowRecoverContainer( WebDriver driver, WebDriverWait ewait );


    /**
     * Method of generating a container for
     * the flow of cacesa
     *
     * @param driver
     * @param ewait
     */
    void windowFlowCacesaInGenerateContainer(  WebDriver driver, WebDriverWait ewait  );

}
