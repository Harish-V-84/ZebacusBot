package com.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

public class TestUtils {

    static ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings(value = "unchecked")
    public static Map<Object, Object> jsonReader(String appdata) {
        Map<Object, Object> json = null;

        try {

            json = mapper.readValue(Paths.get(System.getProperty("user.dir") + appdata).toFile(), Map.class);


        } catch (Exception ex) {

            System.out.println("problem on jsonRead method");
        }

        return json;
    }

    // Json file writer
    //para: Object - Map, filePath - json File path
    public static void jsonPayloadWriter(Object object, String appdata) {

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(System.getProperty("user.dir") + appdata), object);
        } catch (Exception ex) {
            System.out.println("problem on jsonPayloadWriter method");
        }
    }

    public static String toString(Object object){

        return String.valueOf(object);
    }

    public static Integer toInteger(Object object){

        return Integer.parseInt(toString(object));
    }

    public static void deleteFolder(String filePath) {

        try {

            File directory = new File(System.getProperty("user.dir") + File.separator + filePath);

            if (directory.exists()) {

                if(directory.isDirectory()){

                    File[] files = directory.listFiles();

                    if (files != null){

                        for (File file: files){

                            file.delete();
                        }
                    }
                }
            }
        } catch (Exception exception) {

            System.out.println(exception.getMessage());
        }
    }

    public static File[] listOfFiles(String filePath){

        try {

            File directory = new File(System.getProperty("user.dir") + File.separator + filePath);

            if (directory.exists()) {

                if(directory.isDirectory()){

                    return directory.listFiles();
                }
            }
        } catch (Exception exception) {

            System.out.println(exception.getMessage());
        }

        return null;
    }

}
