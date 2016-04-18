package quickquote;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;


public class StepDefs {

    WebDriver driver = null;
    WebDriverWait wait = null;

    @Before
    public void beforeScenario() {
        driver = new FirefoxDriver();
        //driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void afterScenario() {
        driver.close();
    }

    @Given("^I am on the SydneyTesters Life Insurance page$")
    public void I_am_on_life_page() throws Throwable {
        driver.get("http://sydneytesters.herokuapp.com");
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".panel-title"), "Sydney Testers Insurance"));
        assertTrue(driver.getPageSource().contains("Sydney Testers Insurance"));
        driver.findElement(By.id("getlifequote")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".panel-title"), "Sydney Testers Life Insurance"));
        assertTrue(driver.getPageSource().contains("Sydney Testers Life Insurance"));
    }

    @When("^I submit my  details '(.*?)', '(.*?)', '(.*?)' & '(.*?)' for a life insurance quote$")
    public void i_submit_my_details_for_a_life_insurance_quote(String age, String gender, String state, String occupation) throws Throwable {
        driver.findElement(By.id("age")).sendKeys(age);
        driver.findElement(By.id("email")).sendKeys("test@gmail.com");
        Select selectState = new Select(driver.findElement(By.id("state")));
        selectState.selectByVisibleText(state);
        Select selectOccupation = new Select(driver.findElement(By.id("occupation")));
        selectOccupation.selectByVisibleText(occupation);
        driver.findElement(By.id(gender)).click();
        WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("getquote")));
        okButton.click();

    }

    @Then("^I should see the correct '(.*?)' shown$")
    public void i_should_see_the_correct_$_shown(String premium) throws Throwable {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("dt.panel-title"), "Monthly Premium"));
        assertTrue(driver.getPageSource().contains(premium));
    }

}