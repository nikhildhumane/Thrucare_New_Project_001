package Page_Objects;

import BaseTest.BaseClass;
import BusinessLogics.ExcelFileReadAndWrite;
import BusinessLogics.GenericMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;


public class SignUpPage extends BaseClass {

    public static WebElement SignUpHeader =
            driver.findElement(By.xpath(
                    "(//span[normalize-space()='Sign up with a new account'])[1]"));



    public static WebElement UserName =
            driver.findElement(By.xpath(
                    "(//input[@placeholder='Username'])[1]"));



    public static WebElement Email =
            driver.findElement(By.xpath(
                    "(//input[@placeholder='name@host.com'])[1]"));


    public static WebElement Password =
            driver.findElement(By.xpath(
                    "(//input[@placeholder='Password'])[1]"));

    public static WebElement SignUpButton =
            driver.findElement(By.xpath(
                    "(//button[@name='signUpButton'][normalize-space()='Sign up'])[1]"));




    public static void UserName() throws Exception {
        // Wait and check visibility of elements
        GenericMethods.Wait_Element(SignUpHeader, 10);
        GenericMethods.isElementVisible(SignUpHeader);

        GenericMethods.Wait_Element(UserName, 10);
        GenericMethods.isElementVisible(UserName);

        // Generate a random username using Faker
        String username = faker.name().firstName();
        System.out.println("Faker name: " + username);

        // Define the Excel file path
        String filePath = "C://Users//nikhildhumane//IdeaProjects//ThruCareHealthCare//src//main//java//BusinessLogics//ExcelData//ThrucareData.xlsx";

        // Save the generated username to the Excel file
        ExcelFileReadAndWrite.saveDynamicDataToExcel(filePath, "UserLoginDetails", "UserName", username);

        // Enter the username into the UI
        GenericMethods.SendkeyMethod(UserName, username);
    }

    public static void UserEmail() throws Exception {
        // Wait and check visibility of the Email field
        GenericMethods.Wait_Element(Email, 10);
        GenericMethods.isElementVisible(Email);

        // Define the Excel file path
        String filePath = "C://Users//nikhildhumane//IdeaProjects//ThruCareHealthCare//src//main//java//BusinessLogics//ExcelData//ThrucareData.xlsx";

        // Fetch the username from the Excel file after it has been saved
        ExcelFileReadAndWrite.ExcelReader excelReader = new ExcelFileReadAndWrite.ExcelReader(filePath);
        String userName = excelReader.getCellData("UserLoginDetails", 1, 0);
        excelReader.close(); // Always close the reader after use

        if (userName == null || userName.isEmpty()) {
            throw new Exception("Username not found in Excel file");
        }

        // Create an email using the fetched username
        String userGmail = userName + "@yopmail.com";
        System.out.println("UGmail = " + userGmail);

        // Save the generated email to the Excel file
        ExcelFileReadAndWrite.saveDynamicDataToExcel(filePath, "UserLoginDetails", "Email", userGmail);

        // Enter the email into the UI
        GenericMethods.SendkeyMethod(Email, userGmail);
    }


    public static void Password( String value) throws Exception {

        if (!GenericMethods.isValidPassword(value)) {
            throw new Exception("Password does not meet the required criteria.");
        }

        GenericMethods.Wait_Element(Password,10);
        GenericMethods.isElementVisible(Password);
        GenericMethods.SendkeyMethod(Password, value);


    }


    public static void Cli_SignUpBtn() throws Exception {
        GenericMethods.isElementVisible(SignUpButton);
        GenericMethods.Wait_Element(SignUpButton,10).click();

    }











}
