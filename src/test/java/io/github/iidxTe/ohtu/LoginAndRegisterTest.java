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
public class LoginAndRegisterTest {

    @BeforeAll
    public static void launchApp(HtmlUnitDriver driver) {
        OhtuApplication.main();
    }

    @Test
    public void registeredUserCanLogin(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/register");
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("register")).click();
        assertTrue(driver.getPageSource().contains("Kirjaudu sisään"));
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("login")).click();
        assertTrue(driver.getPageSource().contains("Lukuvinkit"));
        driver.close();
    }

    @Test
    public void notExistingUserCantLogin(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080");
        driver.findElement(By.id("username")).sendKeys("wrong");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("login")).click();
        assertTrue(driver.getPageSource().contains("Väärä käyttäjätunnus tai salasana"));
        driver.close();
    }

    @Test
    public void ExistingUserWithWrongPasswordCantLogin(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/register");
        driver.findElement(By.id("username")).sendKeys("test2");
        driver.findElement(By.id("password")).sendKeys("test2");
        driver.findElement(By.id("register")).click();
        assertTrue(driver.getPageSource().contains("Kirjaudu sisään"));
        driver.findElement(By.id("username")).sendKeys("test2");
        driver.findElement(By.id("password")).sendKeys("wrong");
        driver.findElement(By.id("login")).click();
        assertTrue(driver.getPageSource().contains("Väärä käyttäjätunnus tai salasana"));
        driver.close();
    }

    @Test
    public void CantRegisterWithTooShortUsername(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/register");
        driver.findElement(By.id("username")).sendKeys("aaa");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("register")).click();
        assertTrue(driver.getPageSource().contains("Rekisteröidy"));
        driver.close();
    }

    @Test
    public void CantRegisterWithTooShortPassword(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/register");
        driver.findElement(By.id("username")).sendKeys("test3");
        driver.findElement(By.id("password")).sendKeys("aaa");
        driver.findElement(By.id("register")).click();
        assertTrue(driver.getPageSource().contains("Rekisteröidy"));
        driver.close();
    }

    @Test
    public void CantRegisterWithExistingUser(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/register");
        driver.findElement(By.id("username")).sendKeys("test3");
        driver.findElement(By.id("password")).sendKeys("test3");
        driver.findElement(By.id("register")).click();
        assertTrue(driver.getPageSource().contains("Kirjaudu sisään"));
        driver.get("http://localhost:8080/register");
        driver.findElement(By.id("username")).sendKeys("test3");
        driver.findElement(By.id("password")).sendKeys("test3");
        driver.findElement(By.id("register")).click();
        assertTrue(driver.getPageSource().contains("Rekisteröidy"));
        driver.close();
    }
}
