package io.github.iidxTe.ohtu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class LoginTest {
	
	@BeforeAll
	public static void launchApp() {
		OhtuApplication.main();
	}

	@Test
	public void testLogin(FirefoxDriver driver) {
		driver.get("http://localhost:8080");
		// TODO when changing to real login security, this must be changed
		driver.findElement(By.id("username")).sendKeys("test");
		driver.findElement(By.id("password")).sendKeys("test");
		driver.findElement(By.className("btn")).click();
		// TODO when we have page to land after login, verify that it is correct
	}
}
