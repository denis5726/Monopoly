package monopoly.ux.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import monopoly.log.Logger;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.context.Context;

public abstract class SceneController {
    public SceneController() {
        Context.put(getClassName(), this);
    }

    private String getClassName() {
        StringBuilder stringBuilder = new StringBuilder(this.getClass().getName());
        stringBuilder.delete(0, stringBuilder.lastIndexOf(".") + 1);
        stringBuilder.setCharAt(0, (char) (stringBuilder.charAt(0) - 'A' + 'a'));
        stringBuilder.delete(stringBuilder.lastIndexOf("Controller"), stringBuilder.length());
        return stringBuilder.toString();
    }

    public Scene getScene() {
        String nameScene = "views/" + getClassName() + ".fxml";

        FXMLLoader fxmlLoader = new FXMLLoader(MonopolyApplication.loadResource(nameScene));

        try {
            return new Scene(fxmlLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error("Scene with name " + nameScene + " is not found");
        }

        return null;
    }

    public void onResize() {

    }

    public void onChangeScene() {

    }

    public void onCreateScene(SceneContext sceneContext) {

    }
}
