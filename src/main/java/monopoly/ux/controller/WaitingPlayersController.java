package monopoly.ux.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;

public class WaitingPlayersController extends SceneController {
    @FXML
    public Label header;
    @FXML
    public VBox playersList;
    @FXML
    public Button back;

    @Override
    public void setContext(SceneContext sceneContext) {

    }

    public void backAction(ActionEvent actionEvent) {
        MonopolyApplication.setScene("mainMenu", new SceneContext());
    }
}
