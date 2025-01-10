package Page_Objects;

import BaseTest.BaseClass;
import BusinessLogics.GenericMethods;
import BusinessLogics.PropertiesFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInPage extends BaseClass {


    public static WebElement SignInPageHeader =
            driver.findElement(By.xpath(
                    "(//span[normalize-space()='Sign in with your username and password'])[2]"));

    public static WebElement SignUpLink =
            driver.findElement(By.xpath(
                    "(//a[contains(text(),'Sign up')])[2]"));


    public static void Click_SignUp() throws Exception {

        GenericMethods.VerifyPageTitleName(PropertiesFileReader.data("SignIn"));
        GenericMethods.Wait_Element(SignInPageHeader , 25);
        GenericMethods.isElementVisible(SignInPageHeader);
        GenericMethods.Wait_Element(SignUpLink , 25).click();
    }
}
