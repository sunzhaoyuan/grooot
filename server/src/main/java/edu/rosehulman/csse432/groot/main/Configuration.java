package edu.rosehulman.csse432.groot.main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.System.exit;

public class Configuration {

    private static Configuration instance;

    private int SERVER_PORT;
    private String SECRET_LOCATION;

    private String DB_URL;

    private Configuration() {
    }

    public static Configuration getInstance() {
        if (instance == null) {
            initialize();
        }
        return instance;
    }

    private static void initialize() {
        instance = new Configuration();

        JSONParser parser = new JSONParser();
        JSONObject js = null;
        try {
            js = (JSONObject) parser.parse(new FileReader("config.json"));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            exit(1);
        }

        JSONObject serverJS = (JSONObject) js.get("server");
        JSONObject dbJS = (JSONObject) js.get("database");

        instance.SERVER_PORT = Integer.parseInt((String) serverJS.get("port"));
        instance.SECRET_LOCATION = (String) serverJS.get("secret");

        instance.DB_URL = (String) dbJS.get("db_url");

    }

    public String getDB_URL() {
        return DB_URL;
    }

    public int getSERVER_PORT() {
        return SERVER_PORT;
    }

    public String getSECRET_LOCATION() {
        return SECRET_LOCATION;
    }

    public static void main(String[] args) {

    }
}
