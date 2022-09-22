package monopoly.ux;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import monopoly.ux.controller.SceneController;
import monopoly.ux.controller.SceneControllerFabric;

import java.io.IOException;

public class MonopolyApplication extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        MonopolyApplication.stage = stage;

        stage.setTitle("Monopoly");
        setScene("mainMenu", new SceneContext());
        stage.show();
    }

    public static void setScene(String nameScene, SceneContext context) {
        FXMLLoader fxmlLoader = new FXMLLoader(MonopolyApplication.class.getResource(nameScene + ".fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            SceneController sceneController = SceneControllerFabric.getSceneController(nameScene);
            if (sceneController != null) sceneController.setContext(context);
        } catch (IOException e) {
            System.out.println("Scene with name " + nameScene + " is not found");
        }

    }

    public static void main(String[] args) {
        launch();
    }
}