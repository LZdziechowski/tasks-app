package com.crud.tasks.webApp;

import com.crud.tasks.config.WebDriverConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

public class CrudAppTestSuite {

    public static final String BASE_URL = "https://lzdziechowski.github.io/";
    private WebDriver driver;
    private Random generator;

    @BeforeEach
    public void initTests() {
        driver = WebDriverConfig.getDriver(WebDriverConfig.FIREFOX);
        driver.get(BASE_URL);
        generator = new Random();
    }

    @AfterEach
    public void cleanUpAfterTest() {
        driver.close();
    }

    private String createCrudAppTestTask() throws InterruptedException {
        final String XPATH_TASK_NAME = "//input[@id='task_name_input']";
        final String XPATH_TASK_CONTENT = "//textarea[@id='task_content_textarea']";
        final String XPATH_ADD_BUTTON = "//button[@id='add_task_button']";
        String taskName = "TaskNumber " + generator.nextInt(100000);
        String taskContent = taskName + " content";
        WebElement name = driver.findElement(By.xpath(XPATH_TASK_NAME));
        name.sendKeys(taskName);
        WebElement content = driver.findElement(By.xpath(XPATH_TASK_CONTENT));
        content.sendKeys(taskContent);
        WebElement addButton = driver.findElement(By.xpath(XPATH_ADD_BUTTON));
        addButton.click();
        Thread.sleep(2000);
        return taskName;
    }

    private void sendTestTaskToTrello(String taskName) throws InterruptedException {
        driver.navigate().refresh();
        while (!driver.findElement(By.xpath("//select[1]")).isDisplayed()) ;
        driver.findElements(
                By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm -> anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                        .getText().equals(taskName))
                .forEach(theForm -> {
                    WebElement selectElement = theForm.findElement(By.xpath(".//select[1]"));
                    Select select = new Select(selectElement);
                    select.selectByIndex(1);
                    WebElement buttonCreateCard =
                            theForm.findElement(By.xpath(".//button[contains(@class, \"card-creation\")]"));
                    buttonCreateCard.click();
                });
        Thread.sleep(3000);
    }

    private boolean checkTestTaskExistingInTrello(String taskName) throws InterruptedException {
        final String TRELLO_URL = "https://trello.com/login";
        boolean result;
        String trelloUsername = System.getenv("TRELLO_USERNAME");
        String trelloPassword = System.getenv("TRELLO_PASSWORD");
        WebDriver driverTrello = WebDriverConfig.getDriver(WebDriverConfig.FIREFOX);
        driverTrello.get(TRELLO_URL);
        driverTrello.findElement(By.id("user")).sendKeys(trelloUsername);
        driverTrello.findElement(By.id("login")).submit();
        Thread.sleep(6000);
        driverTrello.findElement(By.id("password")).sendKeys(trelloPassword);
        driverTrello.findElement(By.id("login-submit")).submit();
        Thread.sleep(15000);
        driverTrello.findElements(By.xpath("//div[@class=\"board-tile-details is-badged\"]")).stream()
                .filter(aHref -> aHref.findElements(By.xpath(".//div[@title=\"Application\"]")).size() > 0)
                .forEach(WebElement::click);
        Thread.sleep(8000);
        result = driverTrello.findElements(By.xpath("//span")).stream()
                .anyMatch(theSpan -> theSpan.getText().equals(taskName));
        driverTrello.close();
        return result;
    }

    private void deleteTestTaskFromCrudApp(String taskName) throws InterruptedException {
        driver.switchTo().alert().accept();
        while (!driver.findElement(By.xpath("//select[1]")).isDisplayed()) ;
        driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm -> anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                        .getText().equals(taskName))
                .forEach(theElement -> theElement.findElement(By.xpath(".//button[contains(@class, \"datatable__button\")][4]")).click());
        Thread.sleep(3000);
    }

    @Test
    void shouldCreateTrelloCard() throws InterruptedException {
        String taskName = createCrudAppTestTask();
        System.out.println("Test task name: " + taskName);
        sendTestTaskToTrello(taskName);
        deleteTestTaskFromCrudApp(taskName);
        Assertions.assertTrue(checkTestTaskExistingInTrello(taskName));
    }
}
