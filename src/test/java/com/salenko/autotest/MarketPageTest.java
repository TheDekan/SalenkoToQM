package com.salenko.autotest;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.core.annotation.Order;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MarketPageTest {

    private static WebDriver driver;

    public static void sleepTime(int timeSleep) {
        try {
            TimeUnit.SECONDS.sleep(timeSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/SalenkoToQMTestProj/");
        MarketPageTest.sleepTime(3);
    }

    @Test
    public void aActionTest() {
        WebElement nameField = driver.findElement(By.id("name"));
        nameField.sendKeys("A");
        WebElement countField = driver.findElement(By.id("productCount"));
        countField.sendKeys("3");
        WebElement saveButton = driver.findElement(By.xpath("//button[text()='Save']"));
        saveButton.click();
        MarketPageTest.sleepTime(2);
        WebElement checkList = driver.findElement(By.xpath("//div[contains(text(),'Total Price')]"));
        String check = checkList.getText();
        Assert.assertTrue(check.contains("Total Price : 1.3"));
    }

    @Test
    public void bUpdateDealTest() {
        WebElement deal = driver.findElement(By.cssSelector("div.ng-scope.ngRow.even"));
        deal.click();
        MarketPageTest.sleepTime(2);
        WebElement countField = driver.findElement(By.id("productCount"));
        countField.sendKeys("\b" + "2");
        WebElement saveButton = driver.findElement(By.xpath("//button[text()='Save']"));
        saveButton.click();
        MarketPageTest.sleepTime(2);
        WebElement checkList = driver.findElement(By.xpath("//div[contains(text(),'Total Price')]"));
        String check = checkList.getText();
        Assert.assertTrue(check.contains("Total Price : 1.0"));
    }

    @Test
    public void cGiftTest() {
        WebElement nameField = driver.findElement(By.id("name"));
        nameField.sendKeys("D");
        WebElement countField = driver.findElement(By.id("productCount"));
        countField.sendKeys("2");
        WebElement saveButton = driver.findElement(By.xpath("//button[text()='Save']"));
        saveButton.click();
        MarketPageTest.sleepTime(2);
        WebElement checkList = driver.findElement(By.xpath("//div[contains(text(),'E..............1.0')]"));
        String check = checkList.getText();
        Assert.assertTrue(check.contains("E..............1.0"));
    }

    @Test
    public void dDealsDeletedTest() {
        WebElement deleteRow = driver.findElement(By.cssSelector("span.glyphicon.glyphicon-remove.remove.ng-scope"));
        deleteRow.click();
        MarketPageTest.sleepTime(2);
        deleteRow = driver.findElement(By.cssSelector("span.glyphicon.glyphicon-remove.remove.ng-scope"));
        deleteRow.click();
        MarketPageTest.sleepTime(2);
        String checkList = driver.findElements(By.xpath("//div[contains(text(),'Total Price')]")).toString();
        Assert.assertFalse(checkList.contains("Total Price"));
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

}
