package monopoly.ux.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.ux.settings.SettingsContainer;
import monopoly.ux.window.DialogFabric;

public class MainMenuController implements SceneController{
    @FXML
    public Button createGame;
    @FXML
    public Button connectGame;
    @FXML
    public Button settings;
    @FXML
    public Button rules;
    @FXML
    public Button quit;

    @Override
    public void setContext(SceneContext sceneContext) {

    }

    public void createGameAction(ActionEvent actionEvent) {
        if (SettingsContainer.getLogin() == null) {
            if (!DialogFabric.showLoginDialog()) return;
        }
        MonopolyApplication.setScene("createGame", new SceneContext());
    }

    public void connectGameAction(ActionEvent actionEvent) {
        if (SettingsContainer.getLogin() == null) {
            if (!DialogFabric.showLoginDialog()) return;
        }
        MonopolyApplication.setScene("connectGame", new SceneContext());
    }

    public void settingsAction(ActionEvent actionEvent) {
        MonopolyApplication.setScene("settings", new SceneContext());
    }

    public void rulesAction(ActionEvent actionEvent) {
        MonopolyApplication.setScene("rules", new SceneContext());
    }

    public void quitAction(ActionEvent actionEvent) {
        MonopolyApplication.close();
    }
}