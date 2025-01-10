package ThrucareTestCases;

import BaseTest.BaseClass;
import BusinessLogics.GenericMethods;
import Page_Objects.*;
import BusinessLogics.PropertiesFileReader;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;




public class TC_001_CreateProvider extends BaseClass {

    @Test()
    public void ProviderLogin() throws Exception {
        test=report.startTest("ProviderLogin");
        setupBrowser("chrome");
        GenericMethods.OpenURL();
        HomePage.Click_SignInSignUP();
        SelectRolePage.Click_ProviderSelect("Provider");
        SignInPage.Click_SignUp();
        SignUpPage.UserName();
        SignUpPage.UserEmail();
        SignUpPage.Password(PropertiesFileReader.data("password"));
        SignUpPage.Cli_SignUpBtn();
        GenericMethods.Get_Code_from_yopmail();
        VerificationCodePage.Enter_Code(PropertiesFileReader.data("verificationCode"));
        VerificationCodePage.Click_ConformBtn();
        ProviderDetailsPage.Enter_FirstName();
        ProviderDetailsPage.Enter_MiddleName();
        ProviderDetailsPage.Enter_LastName();
        ProviderDetailsPage.Enter_DOB();
        ProviderDetailsPage.Select_Gender("Male");
        ProviderDetailsPage.Select_Country("India");
        ProviderDetailsPage.Enter_MobileNumber();


   }
}


