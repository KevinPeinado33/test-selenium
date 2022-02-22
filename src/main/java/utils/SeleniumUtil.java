package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SeleniumUtil {

    public static void waitClickableElementById(WebDriverWait ewait, String id) {
        ewait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
    }

    public static void waitClickableElementByXPath(WebDriverWait ewait, String xpath) {
        ewait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public static WebElement findElementById(WebDriver driver, String id) {
        return driver.findElement(By.id(id));
    }

    public static WebElement findElementByXPath(WebDriver driver, String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public static List<WebElement> findElementsById(WebDriver driver, String id) {
        return driver.findElements(By.id(id));
    }

    public static List<WebElement> findElementsByXPath(WebDriver driver, String xpath) {
        return driver.findElements(By.xpath(xpath));
    }

    public static WebElement findElementByName(WebDriver driver, String name) {
        return driver.findElement(By.name(name));
    }

}
