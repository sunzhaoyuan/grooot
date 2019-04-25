package Main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Configuration {

    private static Configuration instance;

    private Configuration() {}

    public static Configuration getInstance() {
        if (instance == null) {
            initialize();
        }
        return instance;
    }

    private static void initialize() {
        instance = new Configuration();

        JSONParser parser = new JSONParser();
        JSONObject js;
        try {
            js = (JSONObject) parser.parse(new FileReader("config.json"));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: add db settings

    }
}
