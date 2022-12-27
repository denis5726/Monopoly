package monopoly.context;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private static final Map<Class<?>, Object> instances = new HashMap<>();

    public static void put(Class<?> key, Object value) {
        instances.put(key, value);
    }

    public static Object get(Class<?> key) {
        return instances.get(key);
    }
}
