package monopoly.ux.controller;

import monopoly.ux.SceneContext;
import monopoly.context.Context;

public abstract class SceneController {
    public SceneController() {
        Context.put(this.getClass(), this);
    }
    public abstract void setContext(SceneContext sceneContext);
}
