package BusinessLogics;

import BaseTest.BaseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileReader extends BaseClass {

    public static Properties pro;

    public static String data(String parameterName) throws Exception
    {
        File src=new File(System.getProperty("user.dir")+"\\PropertiesFile\\Config.properties");
        FileInputStream fis=new FileInputStream(src);
        pro=new Properties();
        pro.load(fis);

        String parameterValue=pro.getProperty(parameterName);
        return parameterValue;

    }


    public static void SaveValueToPropertyFile(String key, String value)
    {
        pro.setProperty(key, value);
        try (FileOutputStream output = new FileOutputStream(System.getProperty("user.dir")+"\\PropertiesFile\\Config.properties"))
        {
            pro.store(output, "Generate values");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
