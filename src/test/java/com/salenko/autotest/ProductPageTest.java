package com.salenko.autotest;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductPageTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/SalenkoToQMTestProj/priceChangePage.html");
        MarketPageTest.sleepTime(3);
    }

    @Test
    public void aProductAddTest() {
        WebElement nameField = driver.findElement(By.id("name"));
        nameField.sendKeys("Z");
        WebElement priceField = driver.findElement(By.id("price"));
        priceField.sendKeys("1");
        WebElement calcField = driver.findElement(By.id("calculationType"));
        calcField.sendKeys("Units");
        WebElement saveButton = driver.findElement(By.xpath("//button[text()='Save']"));
        saveButton.click();
        MarketPageTest.sleepTime(2);
        String productRow = driver.findElements(By.xpath("//span[contains(text(),'Z')]")).toString();
        Assert.assertTrue(productRow.contains("Z"));
    }

    @Test
    public void bProductEditTest() {
        WebElement productNameField = driver.findElement(By.xpath("//span[contains(text(),'Z')]"));
        productNameField.click();
        MarketPageTest.sleepTime(1);
        WebElement priceField = driver.findElement(By.id("price"));
        priceField.sendKeys("2");
        WebElement saveButton = driver.findElement(By.xpath("//button[text()='Save']"));
        saveButton.click();
        MarketPageTest.sleepTime(2);
        String findPriceField = driver.findElement(By.xpath("//span[contains(text(),'12')]")).toString();
        Assert.assertTrue(findPriceField.contains("12"));

    }

    @Test
    public void cProductDeleteTest() {
        WebElement productNameField = driver.findElement(By.xpath("//span[contains(text(),'Z')]"));
        WebElement productRow = productNameField.findElement(By.xpath(".."));
        productRow = productRow.findElement(By.xpath(".."));
        productRow = productRow.findElement(By.xpath(".."));
        productRow = productRow.findElement(By.xpath(".."));
        WebElement c = productRow.findElement(By.cssSelector("span.glyphicon.glyphicon-remove.remove.ng-scope"));
        c.click();
        MarketPageTest.sleepTime(3);
        String wasDeleted = driver.findElements(By.xpath("//span[contains(text(),'Z')]")).toString();
        Assert.assertFalse(wasDeleted.contains("Z"));
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

}
