package monopoly.ux;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import monopoly.context.Context;
import monopoly.game.module.ModuleInterfaceGameImpl;
import monopoly.log.Logger;
import monopoly.net.module.ModuleInterfaceNetImpl;
import monopoly.ux.controller.SceneController;
import monopoly.ux.controller.ControllerClassFabric;
import monopoly.ux.module.ModuleInterfaceUIImpl;
import monopoly.settings.SettingsContainer;

import java.net.URL;

public class MonopolyApplication extends Application {
    private static Stage stage;
    private static boolean running = true;
    private static SceneController currentScene;


    @Override
    public void start(Stage stage) {
        MonopolyApplication.stage = stage;

        Logger.trace(Font.loadFont(loadResource("fonts/kabelctt.ttf").
                toExternalForm(), 10).getName());

        SettingsContainer.load();

        loadApplicationContext();

        stage.setTitle("Monopoly");
        stage.setWidth(1300);
        stage.setHeight(770);
        stage.setMinWidth(770);
        stage.setMinHeight(570);

        ChangeListener<? super Number> resizeListener = (observableValue, oldValue, newValue) -> {
            currentScene.onResize();
        };

        stage.widthProperty().addListener(resizeListener);
        stage.heightProperty().addListener(resizeListener);

        setScene("mainMenu", new SceneContext());

        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, (event) -> close());

        stage.show();
    }

    public static void close() {
        SettingsContainer.storeAll();
        stage.close();
        running = false;
    }

    public static void setScene(String nameScene, SceneContext context) {
        if (currentScene != null) currentScene.onChangeScene();

        SceneController currentSceneInitializer = ControllerClassFabric.getSceneController(nameScene);

        Scene scene = currentSceneInitializer.getScene();

        stage.setScene(scene);

        currentScene = (SceneController) Context.get(nameScene);

        currentScene.onCreateScene(context);

        if (currentScene == null) Logger.error("Scene with name " + nameScene + " is not found");
    }

    public static boolean isRunning() {
        return running;
    }

    public static void main(String[] args) {
        launch();
    }

    public static URL loadResource(String path) {
        return MonopolyApplication.class.getResource(path);
    }

    private static void loadApplicationContext() {
        Context.put("mainWindow", stage);
        Context.put("moduleInterfaceUI", new ModuleInterfaceUIImpl());
        Context.put("moduleInterfaceGame", new ModuleInterfaceGameImpl());
        Context.put("moduleInterfaceNet", new ModuleInterfaceNetImpl());
    }
}