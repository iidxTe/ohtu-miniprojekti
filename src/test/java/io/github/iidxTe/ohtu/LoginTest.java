package io.github.iidxTe.ohtu;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import io.github.bonigarcia.seljup.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class LoginTest {

    @BeforeAll
    public static void launchApp(HtmlUnitDriver driver) {
        OhtuApplication.main();
        
        driver.get("http://localhost:8080/register");
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("register")).click();
        assertTrue(driver.getPageSource().contains("Kirjaudu sisään"));
    }

    @Test
    public void correctPassword(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080");
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("login")).click();
        assertTrue(driver.getPageSource().contains("Lukuvinkit"));
    }

    @Test
    public void wrongPassword(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080");
        driver.findElement(By.id("username")).sendKeys("wrong");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("login")).click();
        assertTrue(driver.getPageSource().contains("Väärä käyttäjätunnus tai salasana"));
    }
}
