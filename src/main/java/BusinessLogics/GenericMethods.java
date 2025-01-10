package BusinessLogics;

import BaseTest.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class GenericMethods extends BaseClass {




    public static void OpenURL() throws Exception {

        String url = excelReader.getCellData("urlDetails", 1, 0);
        System.out.println("URL ="+url);
        driver.get(url);
        excelReader.close();
    }








    public static WebElement Wait_Element(WebElement element, int timeoutSeconds) throws TimeoutException {

        if (element == null) {
            throw new IllegalArgumentException("Element is null");
        }
        if (timeoutSeconds <= 0) {
            throw new IllegalArgumentException("Timeout must be greater than zero");
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        WebElement visibleElement;

        try {
            visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Element does not exist: " + element);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while waiting for element: " + e.getMessage());
        }

        return visibleElement;
    }





    public static void SendkeyMethod(WebElement xpath, String value) throws Exception {
        if (xpath == null ) {
            throw new IllegalArgumentException("XPath is null or empty");
        }
        else if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value is null or empty");
        }
        else {

            Wait_Element(xpath, 25).sendKeys(value);
        }
    }



    public static void VerifyPageTitleName(String expectedTitle) throws TimeoutException {


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        wait.until(ExpectedConditions.titleIs(expectedTitle));
        System.out.println("Page title verification successful: " + driver.getTitle());
    }


    public static void VerifyPlaceHolderText(WebElement element,String text)
    {
        String actualText = element.getAttribute("value");;
        // Expected text
        String expectedText =text;
        Assert.assertEquals(actualText,expectedText);
    }


    public static boolean isElementVisible(WebElement element) {
        try {
            return element.isDisplayed(); // Checks if the element is visible
        } catch (Exception e) {
            return false; // Return false if an exception occurs (element is not visible or doesn't exist)
        }
    }


    public static boolean isValidPassword(String password) {
        // Check for at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return false;
        }
        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        // Check for at least one digit
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        // Check for at least one special character or space
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>\\s].*")) {
            return false;
        }
        // Check for at least 8 characters
        if (password.length() < 8) {
            return false;
        }
        // Check for leading or trailing spaces
        if (password.startsWith(" ") || password.endsWith(" ")) {
            return false;
        }
        return true;
    }






    public static String FakerName()
    {
        String username= faker.name().firstName();
        System.out.println("Faker name : "+username);


     //   PropertiesFileReader.SaveValueToPropertyFile("UserName" ,username);
        return username;
    }

    public static String UserEmail() throws Exception {
        String username=PropertiesFileReader.data("UserName");
        String UserGmail=username+"@yopmail.com";
        System.out.println("Faker Gmail "+UserGmail);
        PropertiesFileReader.SaveValueToPropertyFile("UserGmail" ,UserGmail);
        return UserGmail;
    }


   public static void Click(WebElement xpath) throws TimeoutException {

       if (xpath == null ) {
           throw new IllegalArgumentException("XPath is null or empty");
       }
       Wait_Element(xpath,25).click();

   }




    public static void Get_Code_from_yopmail() throws IOException, InterruptedException {


        ((ChromeDriver) driver).executeScript("window.open()");
        String secondTab = driver.getWindowHandles().toArray()[1].toString();
        driver.switchTo().window(secondTab);

        driver.get("https://yopmail.com/");
        driver.manage().window().maximize();



        // Fetch the username from the Excel file after it has been saved
        ExcelFileReadAndWrite.ExcelReader excelReader = new ExcelFileReadAndWrite.ExcelReader(ExcelFileLocation);
        String gmail = excelReader.getCellData("UserLoginDetails", 1, 1);
        excelReader.close();





        WebElement e=driver.findElement(By.xpath("//input[@id='login']"));
        e.clear();
        e.sendKeys(gmail);

        Thread.sleep(25);

        driver.findElement(By.xpath("//i[@class='material-icons-outlined f36']")).click();
        Thread.sleep(25);
        driver.findElement(By.xpath("//button[@id='refresh']")).click();

        Thread.sleep(25);
        driver.switchTo().frame("ifinbox");
        List<WebElement> yopmailElements = driver.findElements(By.xpath("//div[@class='m']"));
        int size=yopmailElements.size();
        Thread.sleep(25);
        for (int i = size - 1; i >= 0; i--) {
            Thread.sleep(25);
            WebElement yopmailElement = yopmailElements.get(i);
            String yopmailAddress = yopmailElement.getText();

            Thread.sleep(25);
            // Check for the specific email name
            if (yopmailAddress.contains("no-reply@verificationemail.com")) {
                yopmailElement.click();
                Thread.sleep(25);
                driver.switchTo().defaultContent();

                driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
                driver.switchTo().frame("ifmail");

                Thread.sleep(10);
                WebElement emailContentElement = driver.findElement(By.xpath("//div[@id='mail']"));
                String emailContent = emailContentElement.getText();


                String verificationCode = emailContent.replaceAll("[^\\d]", "");

                PropertiesFileReader.SaveValueToPropertyFile("verificationCode", verificationCode);
            }
        }

        String firstTab = driver.getWindowHandles().toArray()[0].toString();
        driver.switchTo().window(firstTab);



    }




public static void select_DropDown(WebElement element, String text)
{
    select =new Select(element);
    select.selectByVisibleText(text);
}















}
