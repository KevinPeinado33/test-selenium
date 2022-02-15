package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TableUtil {

    public static Integer getSizeFromTable(WebDriver driver, String idTable) {
        return getListElemntsFromTable(driver, idTable).size();
    }

    public static List<WebElement> getListElemntsFromTable (WebDriver driver, String idTable) {

        return SeleniumUtil.findElementsByXPath(
                driver,
                "//table/tbody[@id='"+idTable+"_data']/tr"
        );

    }

}
