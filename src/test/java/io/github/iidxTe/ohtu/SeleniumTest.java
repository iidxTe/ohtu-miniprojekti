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
public class SeleniumTest {

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
    public void registeredUserCanLogin(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/register");
        driver.findElement(By.id("username")).sendKeys("test2");
        driver.findElement(By.id("password")).sendKeys("test2");
        driver.findElement(By.id("register")).click();
        assertTrue(driver.getPageSource().contains("Kirjaudu sisään"));
        driver.findElement(By.id("username")).sendKeys("test2");
        driver.findElement(By.id("password")).sendKeys("test2");
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
    public void existingUserWithWrongPasswordCantLogin(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080");
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("wrong");
        driver.findElement(By.id("login")).click();
        assertTrue(driver.getPageSource().contains("Väärä käyttäjätunnus tai salasana"));
        driver.close();
    }

    @Test
    public void cantRegisterWithTooShortUsername(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/register");
        driver.findElement(By.id("username")).sendKeys("aaa");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("register")).click();
        assertTrue(driver.getPageSource().contains("Rekisteröidy"));
        driver.close();
    }

    @Test
    public void cantRegisterWithTooShortPassword(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/register");
        driver.findElement(By.id("username")).sendKeys("test3");
        driver.findElement(By.id("password")).sendKeys("aaa");
        driver.findElement(By.id("register")).click();
        assertTrue(driver.getPageSource().contains("Rekisteröidy"));
        driver.close();
    }

    @Test
    public void cantRegisterWithExistingUser(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/register");
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("register")).click();
        assertTrue(driver.getPageSource().contains("Rekisteröidy"));
        driver.close();
    }

    @Test
    public void logoutButtonReturnsToLoginScreen(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("login")).click();
        assertTrue(driver.getPageSource().contains("Lukuvinkit"));
        driver.findElement(By.id("logout")).click();
        assertTrue(driver.getPageSource().contains("Kirjaudu sisään"));
        driver.close();
    }

    @Test
    public void addedBookIsFoundOnList(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("login")).click();
        assertTrue(driver.getPageSource().contains("Lukuvinkit"));
        driver.findElement(By.id("title")).sendKeys("mahtava kirja");
        driver.findElement(By.id("author")).sendKeys("kirjoittaja");
        driver.findElement(By.id("isbn")).sendKeys("123");
        driver.findElement(By.id("addBook")).click();
        assertTrue(driver.getPageSource().contains("mahtava kirja"));
        driver.close();
    }

    @Test
    public void whenBookIsSetReadItShowsAsReadOnList(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("title")).sendKeys("mahtava kirja");
        driver.findElement(By.id("author")).sendKeys("kirjoittaja");
        driver.findElement(By.id("isbn")).sendKeys("123");
        driver.findElement(By.id("addBook")).click();
        assertTrue(driver.getPageSource().contains("ei"));
        driver.findElement(By.id("editBook")).click();
        driver.findElement(By.id("checkbox")).click();
        driver.findElement(By.id("muokkaa")).click();
        assertTrue(driver.getPageSource().contains("kyllä"));
        driver.close();
    }

    @Test
    public void whenBookIsDeletedItDoesntShowOnList(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/register");
        driver.findElement(By.id("username")).sendKeys("testx");
        driver.findElement(By.id("password")).sendKeys("testx");
        driver.findElement(By.id("register")).click();
        driver.findElement(By.id("username")).sendKeys("testx");
        driver.findElement(By.id("password")).sendKeys("testx");
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("title")).sendKeys("kiva kirja");
        driver.findElement(By.id("author")).sendKeys("kirjoittaja");
        driver.findElement(By.id("isbn")).sendKeys("123");
        driver.findElement(By.id("addBook")).click();
        assertTrue(driver.getPageSource().contains("kiva kirja"));
        driver.findElement(By.id("editBook")).click();
        driver.findElement(By.id("delete")).click();
        assertTrue(!driver.getPageSource().contains("kiva kirja"));
        driver.close();
    }

    @Test
    public void whenDisplayNameIsChangedItShowsOnMainPage(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("login")).click();
        assertTrue(driver.getPageSource().contains("test"));
        driver.findElement(By.id("settingsPage")).click();
        driver.findElement(By.id("displayName")).sendKeys("mahtityyppi");
        driver.findElement(By.id("settings")).click();
        assertTrue(driver.getPageSource().contains("mahtityyppi"));
        driver.close();
    }

    @Test
    public void groupMembersCanSeeOthersBooks(HtmlUnitDriver driver) {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("title")).sendKeys("mahtava kirja");
        driver.findElement(By.id("author")).sendKeys("kirjoittaja");
        driver.findElement(By.id("isbn")).sendKeys("123");
        driver.findElement(By.id("addBook")).click();
        assertTrue(driver.getPageSource().contains("mahtava kirja"));
        driver.findElement(By.id("settingsPage")).click();
        driver.findElement(By.id("group")).sendKeys("moimoi");
        driver.findElement(By.id("settings")).click();
        driver.findElement(By.id("logout")).click();
        driver.get("http://localhost:8080/register");
        driver.findElement(By.id("username")).sendKeys("test3");
        driver.findElement(By.id("password")).sendKeys("test3");
        driver.findElement(By.id("register")).click();
        driver.findElement(By.id("username")).sendKeys("test3");
        driver.findElement(By.id("password")).sendKeys("test3");
        driver.findElement(By.id("login")).click();
        assertTrue(driver.getPageSource().contains("Lukuvinkit"));
        assertTrue(!driver.getPageSource().contains("mahtava kirja"));
        driver.findElement(By.id("settingsPage")).click();
        driver.findElement(By.id("group")).sendKeys("moimoi");
        driver.findElement(By.id("settings")).click();
        assertTrue(driver.getPageSource().contains("mahtava kirja"));
        driver.close();
    }
}
