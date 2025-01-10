package Page_Objects;

import BaseTest.BaseClass;
import BusinessLogics.GenericMethods;
import BusinessLogics.PropertiesFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SelectRolePage extends BaseClass {

    public static WebElement SelectBtn1 = driver.findElement(By.xpath("(//button[normalize-space()='Select'])[1]"));

    public static WebElement PatientRole = driver.findElement(By.xpath("//h5[normalize-space()='Patient']"));

    public static WebElement PatientSelectBtn = driver.findElement(By.xpath("//div[h5[normalize-space()='Patient']]//following-sibling::div//button[@type='button'][normalize-space()='Select'][1]"));

    public static WebElement ProviderSelectBtn = driver.findElement(By.xpath(
            "//div[h5[normalize-space()='Provider']]//following-sibling::div//button[@type='button'][normalize-space()='Select'][1]"));




    public static void Click_ProviderSelect(String inputText) throws Exception
    {
        GenericMethods.VerifyPageTitleName(PropertiesFileReader.data("SelectRoleTitle"));
        GenericMethods.Wait_Element(PatientRole , 25);
        GenericMethods.isElementVisible(PatientRole);

        WebElement selectButton;

        if ("Patient".equalsIgnoreCase(inputText)) {
            // Click the button inside the "providerlogin" section
            selectButton = driver.findElement(By.xpath("//div[h5[normalize-space()='Patient']]//following-sibling::div//button[@type='button'][normalize-space()='Select'][1]"));
            selectButton.click();
        } else if ("Provider".equalsIgnoreCase(inputText)) {
            // Click the button inside the "patientlogin" section
            selectButton = driver.findElement(By.xpath("//div[h5[normalize-space()='Provider']]//following-sibling::div//button[@type='button'][normalize-space()='Select'][1]"));
            selectButton.click();
        } else {
            System.out.println("Invalid input text: " + inputText);
            return;
        }


    }





}
