package monopoly.ux;

import java.util.HashMap;
import java.util.Map;

public class SceneContext {
    private Map<String, String> context;

    public SceneContext() {
        context = new HashMap<>();
    }

    public void addProperty(String key, String value) {
        context.put(key, value);
    }

    public String getProperty(String key) {
        return context.get(key);
    }
}
