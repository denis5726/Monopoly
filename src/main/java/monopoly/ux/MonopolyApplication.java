package monopoly.ux;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import monopoly.context.Context;
import monopoly.game.module.ModuleInterfaceGame;
import monopoly.game.module.ModuleInterfaceGameImpl;
import monopoly.log.Logger;
import monopoly.net.module.ModuleInterfaceNet;
import monopoly.net.module.ModuleInterfaceNetImpl;
import monopoly.ux.controller.SceneController;
import monopoly.ux.controller.ControllerClassFabric;
import monopoly.ux.module.ModuleInterfaceUI;
import monopoly.ux.module.ModuleInterfaceUIImpl;
import monopoly.settings.SettingsContainer;

public class MonopolyApplication extends Application {
    private static Stage stage;
    private static boolean running = true;
    private static String currentScene;


    @Override
    public void start(Stage stage) {
        MonopolyApplication.stage = stage;

        Logger.trace(Font.loadFont(MonopolyApplication.class.
                getResource("fonts/kabelctt.ttf").
                toExternalForm(), 10).getName());

        SettingsContainer.load();

        loadApplicationContext();

        stage.setTitle("Monopoly");

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
        FXMLLoader fxmlLoader = new FXMLLoader(
                MonopolyApplication.class.getResource("views/" + nameScene + ".fxml"));

        try {
            if (currentScene != null) ((SceneController) Context.get(
                    ControllerClassFabric.getSceneController(currentScene))).onChangeScene();

            currentScene = nameScene;

            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            SceneController sceneController = (SceneController) Context.get(
                    ControllerClassFabric.getSceneController(nameScene));
            context.addProperty("root", scene.getRoot());
            if (sceneController != null) sceneController.onCreateScene(context);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error("Scene with name " + nameScene + " is not found");
        }

    }

    public static boolean isRunning() {
        return running;
    }

    public static void main(String[] args) {
        launch();
    }

    private static void loadApplicationContext() {
        Context.put(ModuleInterfaceUI.class, new ModuleInterfaceUIImpl());
        Context.put(ModuleInterfaceGame.class, new ModuleInterfaceGameImpl());
        Context.put(ModuleInterfaceNet.class, new ModuleInterfaceNetImpl());
    }
}