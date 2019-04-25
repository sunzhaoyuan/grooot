package Main;

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

    private String DB_INSTANCE;
    private String DB_URL;
    private String DB_NAME;
    private String DB_USERNAME;
    private String DB_PASSWORD;

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            exit(1);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            exit(1);
        }

        JSONObject serverJS = (JSONObject) js.get("server");
        JSONObject dbJS = (JSONObject) js.get("database");
        JSONObject clientJS = (JSONObject) js.get("client");

        instance.SERVER_PORT = Integer.parseInt((String) serverJS.get("port"));

        instance.DB_INSTANCE = (String) dbJS.get("db_instance");
        instance.DB_NAME = (String) dbJS.get("db_dbname");
        instance.DB_USERNAME = (String) dbJS.get("db_username");
        instance.DB_PASSWORD = (String) dbJS.get("db_password");
        instance.DB_URL = (String) dbJS.get("db_dburl");

    }

    public String getDB_URL() {
        return DB_URL;
    }

    public int getSERVER_PORT() {
        return SERVER_PORT;
    }

    public String getDB_INSTANCE() {
        return DB_INSTANCE;
    }

    public String getDB_NAME() {
        return DB_NAME;
    }

    public String getDB_USERNAME() {
        return DB_USERNAME;
    }

    public String getDB_PASSWORD() {
        return DB_PASSWORD;
    }

    public static void main(String[] args) {

    }
}
