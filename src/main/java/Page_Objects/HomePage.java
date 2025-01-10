package Page_Objects;

import BaseTest.BaseClass;
import BusinessLogics.GenericMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BaseClass {


    public static By Signin_Signup = By.xpath("//a[normalize-space()='Sign-in/Sign-up']");
    public static WebElement SignIn_SignUp = driver.findElement(Signin_Signup);






    public static void Click_SignInSignUP() throws Exception {
        GenericMethods.Wait_Element(SignIn_SignUp,25);
        GenericMethods.isElementVisible(SignIn_SignUp);
        GenericMethods.Wait_Element(SignIn_SignUp, 25).click();

    }



}





