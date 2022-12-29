package monopoly.ux.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.settings.SettingsContainer;
import monopoly.ux.window.DialogFabric;

public class MainMenuController extends SceneController {
    @FXML
    public Pane pane;
    @FXML
    public HBox hBox;
    @FXML
    public VBox vBox;
    @FXML
    public Separator separator1;
    @FXML
    public Separator separator2;
    @FXML
    public Separator separator3;
    @FXML
    public Separator separator4;
    @FXML
    public Separator separator5;
    @FXML
    public Separator separator6;
    @FXML
    public Separator separator7;
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
    public void onResize() {
        Stage window = (Stage) Context.get("mainWindow");

        double w = window.getWidth();
        double h = window.getHeight();

        pane.setPrefWidth(w);
        pane.setPrefHeight(h);
        pane.setMinWidth(w);
        pane.setMinHeight(h);
        pane.setMaxWidth(w);
        pane.setMinHeight(h);

        hBox.setPrefWidth(w);
        hBox.setPrefHeight(h);

        vBox.setPrefWidth(w * 0.518);
        vBox.setPrefHeight(h);

        double buttonWidth = 0.39 * w;
        double separatorHeight = 0.092 * h;

        separator1.setPrefHeight(h * 0.277);
        separator2.setPrefHeight(h * 0.277);
        createGame.setPrefWidth(buttonWidth);
        separator3.setPrefHeight(separatorHeight);
        connectGame.setPrefWidth(buttonWidth);
        separator4.setPrefHeight(separatorHeight);
        settings.setPrefWidth(buttonWidth);
        separator5.setPrefHeight(separatorHeight);
        rules.setPrefWidth(buttonWidth);
        separator6.setPrefHeight(separatorHeight);
        quit.setPrefWidth(buttonWidth);
        separator7.setPrefHeight(h * 0.277);
    }

    public void createGameAction(ActionEvent actionEvent) {
        if (SettingsContainer.getLogin() == null || SettingsContainer.getLogin().isEmpty()) {
            if (!DialogFabric.showLoginDialog()) return;
        }
        MonopolyApplication.setScene("createGame", new SceneContext());
    }

    public void connectGameAction(ActionEvent actionEvent) {
        if (SettingsContainer.getPassword() == null || SettingsContainer.getLogin().isEmpty()) {
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
