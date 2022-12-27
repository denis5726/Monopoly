package monopoly.ux;

import java.util.HashMap;
import java.util.Map;

public class SceneContext {
    private Map<String, Object> context;

    public SceneContext() {
        context = new HashMap<>();
    }

    public void addProperty(String key, Object value) {
        context.put(key, value);
    }

    public Object getProperty(String key) {
        return context.get(key);
    }
}
