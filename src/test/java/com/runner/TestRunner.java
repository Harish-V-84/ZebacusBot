package com.runner;

import com.utils.TestUtils;

import java.awt.*;
import java.io.File;
import java.util.Map;

public class TestRunner extends TestUtils {

    static String filePath = "/AppData/API_Data.json";
    static String filePathOfBot = "/Release/net8.0/appsettings.json";
    static File botRunner = new File(System.getProperty("user.dir")+"/Release/net8.0/Pibase_Bot.exe");

    @SuppressWarnings(value = "unchecked")
    public static void runner(){

        Desktop desktop = Desktop.getDesktop();

        deleteFolder("/Release/net8.0/Data_Logs");

        deleteFolder("/Release/net8.0/Error_logs");

        Map<Object, Object> apiData = jsonReader(filePath);

        int startCount = toInteger(apiData.get("startCount"));
        int endCount = toInteger(apiData.get("endCount"));

        for (int i = startCount; i <= endCount; i++) {

            Map<Object, Object> apis = (Map<Object, Object>) apiData.get(String.valueOf(i));

            String api = toString(apis.get("API"));

            boolean byLimit = (boolean) apis.get("BuyLimit");
            boolean sellLimit = (boolean) apis.get("SellLimit");
            boolean buyMarket = (boolean) apis.get("BuyMarket");
            boolean sellMarket = (boolean) apis.get("SellMarket");

            Map<Object, Object> apiBot = jsonReader(filePathOfBot);

            Map<Object, Object> appSettings = (Map<Object, Object>) apiBot.get("AppSettings");

            StringBuilder botID = new StringBuilder();
            botID.append(toString(apiData.get("botID")));
            botID.append(i);

            switch (toString(apis.get("userType"))){

                case "client": {
                    Map<Object, Object> accountType = (Map<Object, Object>) apiData.get("normalAccount");
                    appSettings.put("start_qty", accountType.get("start_qty"));
                    appSettings.put("end_qty", accountType.get("end_qty"));
                }break;
                case "marketMaker" :{
                    Map<Object, Object> accountType = (Map<Object, Object>) apiData.get("marketMakerAccount");
                    appSettings.put("start_qty", accountType.get("start_qty"));
                    appSettings.put("end_qty", accountType.get("end_qty"));
                }
            }

            if (byLimit){

                appSettings.put("buyLimit", 1);

            }else {

                appSettings.put("buyLimit", 0);

            }

            if (sellLimit){

                appSettings.put("sellLimit", 1);

            }else {

                appSettings.put("sellLimit", 0);
            }

            if (buyMarket){

                appSettings.put("buyMarket", 1);

            }else {

                appSettings.put("buyMarket", 0);
            }

            if (sellMarket){

                appSettings.put("sellMarket", 1);

            }else {

                appSettings.put("sellMarket", 0);
            }

            appSettings.put("AuthorizationKey", api);
            appSettings.put("botid", botID.toString());

            try {

                jsonPayloadWriter(apiBot, filePathOfBot);

                Map<Object, Object> botIDCheck = (Map<Object, Object>) jsonReader(filePathOfBot).get("AppSettings");

                if (toString(botIDCheck.get("botid")).equals(botID.toString())){

                    System.out.println(botIDCheck.get("botid"));
                }else {
                    System.out.println("check the Bot ID");
                    break;
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (!Desktop.isDesktopSupported()){

                System.out.println("system not supported");
                break;
            }

            try {
                if (botRunner.exists()){

                    desktop.open(botRunner);
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {

        runner();
    }
}
