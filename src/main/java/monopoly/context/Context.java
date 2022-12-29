package monopoly.context;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private static final Map<String, Object> instances = new HashMap<>();

    public static void put(String key, Object value) {
        instances.put(key, value);
    }

    public static Object get(String key) {
        return instances.get(key);
    }
}
