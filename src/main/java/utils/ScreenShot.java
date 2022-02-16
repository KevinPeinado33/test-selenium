package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenShot {

    public static void screenShot(WebDriver driver, String directory, String name) {

        String path = ( directory != null )
                        ? directory + name + ".png"
                        : "./src/main/resources/images/" + name + ".png";

        File screenShot = ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE );

        try {
            FileUtils.copyFile(screenShot, new File(path));
        } catch ( IOException exception ) {
            exception.printStackTrace();
        }

    }

}
