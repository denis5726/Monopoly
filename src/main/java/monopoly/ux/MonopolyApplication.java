package monopoly.ux;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import monopoly.ux.controller.SceneController;
import monopoly.ux.controller.SceneControllerFabric;
import monopoly.ux.settings.SettingsContainer;

import java.io.IOException;

public class MonopolyApplication extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) {
        MonopolyApplication.stage = stage;

        System.out.println(Font.loadFont(MonopolyApplication.class.getResource("fonts/kabelctt.ttf").toExternalForm(), 10).getName());

        SettingsContainer.load();

        stage.setTitle("Monopoly");
        setScene("mainMenu", new SceneContext());
        stage.show();
    }

    public static void close() {
        stage.close();
    }

    public static void setScene(String nameScene, SceneContext context) {
        FXMLLoader fxmlLoader = new FXMLLoader(MonopolyApplication.class.getResource("views/" + nameScene + ".fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            SceneController sceneController = SceneControllerFabric.getSceneController(nameScene);
            if (sceneController != null) sceneController.setContext(context);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Scene with name " + nameScene + " is not found");
        }

    }

    public static void main(String[] args) {
        launch();
    }
}