package Page_Objects;

import BaseTest.BaseClass;
import BusinessLogics.GenericMethods;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeoutException;

public class VerificationCodePage extends BaseClass {

    public static WebElement VerificationCodeHeader =
            driver.findElement(By.xpath(
                    "//h3[normalize-space()='Confirm your account']"));

    public static WebElement VerificationCode =
            driver.findElement(By.xpath("//input[@id='verification_code']"));


    public static WebElement ConfirmAccount =
            driver.findElement(By.xpath(
                    "//button[normalize-space()='Confirm account']"));



 /*========================================================================================*/

    public static void Enter_Code(String value) throws Exception {
        GenericMethods.Wait_Element(VerificationCodeHeader , 25);
        GenericMethods.isElementVisible(VerificationCodeHeader);

        GenericMethods.SendkeyMethod(VerificationCode, value);
    }



    public static void Click_ConformBtn() throws Exception {
        GenericMethods.Wait_Element(ConfirmAccount , 25);
        GenericMethods.isElementVisible(ConfirmAccount);

        GenericMethods.Wait_Element(ConfirmAccount , 25).click();

    }





}
