package monopoly.ux.settings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsContainer {
    private static final String path = "setting.properties";
    private static Properties properties;

    public static void load() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(path, false);
                fileOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void setLogin(String login) {
        properties.setProperty("login", login);
        store();
    }

    public static void setPassword(String password) {
        properties.setProperty("password", password);
        store();
    }

    public static String getLogin() {
        return (String) properties.get("login");
    }

    public static String getPassword() {
        return (String) properties.get("password");
    }

    private static void store() {
        try {
            properties.store(new FileOutputStream(path, false), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
