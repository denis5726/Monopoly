package monopoly.ux.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;

public class SettingsController extends SceneController {
    @FXML
    public Button back;

    public void backAction(ActionEvent actionEvent) {
        MonopolyApplication.setScene("mainMenu", new SceneContext());
    }

    @Override
    public void onCreateScene(SceneContext sceneContext) {

    }
}
