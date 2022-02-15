package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SystemStart {

    public static void startPageToLogin( WebDriver driver ) {

        // screen of page permit
        WebElement linkAccessLogin = SeleniumUtil.findElementByXPath(driver, "//span[contains(@id,'moreInfoContainer')]/a");

        linkAccessLogin.click();

        SeleniumUtil.findElementById(driver,"overridelink").click();

    }

    public static void startSession(  WebDriver driver, WebDriverWait ewait, String userName, String pass ) {

        // verifying if the button is clickable
        SeleniumUtil.waitClickableElementByXPath(ewait,"//input[contains(@value,'Entrar')]");

        // we create user variables
        WebElement username = SeleniumUtil.findElementByName(driver,"j_username");
        WebElement password = SeleniumUtil.findElementByName(driver,"j_password");

        username.clear();
        password.clear();

        username.sendKeys( userName );
        password.sendKeys( pass );

        SeleniumUtil.findElementByXPath(driver, "//input[contains(@value,'Entrar')]").click();

    }

    public static void selectRoles( WebDriver driver, WebDriverWait ewait, String rol ) {

        String xpathRolSelected = "//li[text()='" + rol + "']";

        // verifying if the button is clickable
        SeleniumUtil.waitClickableElementByXPath(ewait, "//input[contains(@value,'Acceder')]");

        SeleniumUtil.findElementById(driver, "form:listaRoles_label").click();
        SeleniumUtil.findElementByXPath(driver, xpathRolSelected).click();

        SeleniumUtil.findElementByXPath(driver, "//input[contains(@value,'Acceder')]").click();

    }

    public static void enterWindowByMenuAndSubMenu( WebDriver driver, WebDriverWait ewait, String menu, String subMenu ) {

        // verifying if the button is clickable
        SeleniumUtil.waitClickableElementByXPath(ewait,"//ul/li/a/span[text()='"+menu+"']");

        SeleniumUtil.findElementByXPath(driver, "//ul/li/a/span[text()='"+menu+"']").click();

        if ( subMenu != null ) {
            SeleniumUtil.findElementByXPath(driver, "//ul/li/ul/li/a/span[text()='"+subMenu+"']").click();
        }

    }

}
