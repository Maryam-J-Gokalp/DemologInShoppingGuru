package com.orange.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class Driver {

    private Driver(){}

    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();


    public static WebDriver getDriver(){

        if(driverPool.get()==null){

            String browser = ConfigurationReader.getProperty("browser");

            switch (browser) {
                case "chrome" -> {
                    WebDriverManager.chromedriver().setup();
                    driverPool.set((new ChromeDriver()));
                    driverPool.get().manage().window().maximize();
                    driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                }
                case "firefox" -> {
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set((new FirefoxDriver()));
                    driverPool.get().manage().window().maximize();
                    driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                }
            }


        }
        return driverPool.get();
    }

    public static void closeDriver(){
        if(driverPool.get() !=null){
            driverPool.get().quit();
            driverPool.remove();
        }
    }


}
