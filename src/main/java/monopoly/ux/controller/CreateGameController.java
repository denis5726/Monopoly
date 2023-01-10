package monopoly.ux.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.log.Logger;
import monopoly.net.module.ModuleInterfaceNet;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.ux.model.CreatedGame;
import monopoly.ux.window.AlertWindowFabric;

public class CreateGameController extends SceneController {
    @FXML
    public Button back;
    @FXML
    public TextField gameTitle;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField playersNumber;
    @FXML
    public TextField waitingTime;
    @FXML
    public TextField stepTime;
    @FXML
    public CheckBox checkPassword;
    @FXML
    public Button next;
    @FXML
    public VBox pane;
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
    public GridPane gridPane;
    @FXML
    public HBox passwordHBox;
    @FXML
    public Separator passwordSeparator;
    @FXML
    public Pane checkPasswordPane;

    public void backAction(ActionEvent actionEvent) {
        MonopolyApplication.setScene("mainMenu", new SceneContext());
    }

    public void playersNumberChanged(KeyEvent inputMethodEvent) {
        validateField(playersNumber, 2, 4);
    }

    public void waitingTimeChanged(KeyEvent inputMethodEvent) {
        validateField(waitingTime, 5, 30);
    }

    public void stepTimeChanged(KeyEvent inputMethodEvent) {
        validateField(stepTime, 5, 15);
    }

    private void validateField(TextField field, int a, int b) {
        boolean res;
        try {
            int value = Integer.parseInt(field.getText());
            res = value >= a && value <= b;
        } catch (NumberFormatException e) {
            res = false;
        }
        if (res) field.getStyleClass().removeAll("error") ;
        else field.getStyleClass().add("error");
    }

    @Override
    public void onResize() {
        Stage stage = (Stage) Context.get("mainWindow");

        double w = stage.getWidth();
        double h = stage.getHeight();

        pane.setPrefSize(w, h);
        hBox.setPrefSize(w, h);
        vBox.setPrefSize(0.733 * w, h);
    }

    public void nextAction(ActionEvent actionEvent) {
        boolean nonEmptyTitle = !gameTitle.getText().isEmpty();
        boolean nonEmptyPassword = true;
        if (checkPassword.isSelected()) nonEmptyPassword = !passwordField.getText().isEmpty();
        boolean playersNumValid = !playersNumber.getStyleClass().contains("error");
        boolean waitingTimeValid = !waitingTime.getStyleClass().contains("error");
        boolean stepTimeValid = !stepTime.getStyleClass().contains("error");

        StringBuilder stringBuilder = new StringBuilder();

        if (!nonEmptyTitle) stringBuilder.append("Не указано название игры\n");
        if (!nonEmptyPassword) stringBuilder.append("Не указан пароль от игры\n");
        if (!playersNumValid) stringBuilder.append("Неправильно указано число игроков (от 2 до 4)\n");
        if (!waitingTimeValid) stringBuilder.append("Неправильно указано количество времени на принятие решения игроком (от 5 до 30)\n");
        if (!stepTimeValid) stringBuilder.append("Неправильно указано время хода на выполнение действия (от 5 до 15)");

        if (stringBuilder.isEmpty()) {
            ModuleInterfaceNet moduleInterfaceNet = (ModuleInterfaceNet) Context.get("moduleInterfaceNet");

            CreatedGame game = new CreatedGame();
            game.setTitle(gameTitle.getText());
            game.setCheckPassword(checkPassword.isSelected());
            game.setPassword(passwordField.getText());
            game.setPlayersNum(Integer.parseInt(playersNumber.getText()));
            game.setWaitingTime(Integer.parseInt(waitingTime.getText()));
            game.setStepTime(Integer.parseInt(stepTime.getText()));

            String response = moduleInterfaceNet.createGame(game);
            if (response.equals("Success")) {
                SceneContext sceneContext = new SceneContext();
                sceneContext.addProperty("game", game);
                MonopolyApplication.setScene("waitingPlayers", sceneContext);
            }
            else AlertWindowFabric.showErrorAlert("Ошибка", "Ошибка при создании игры на сервере", response);
        }
        else AlertWindowFabric.showErrorAlert("Ошибка", "Неправильные параметры игры", stringBuilder.toString());
    }

    public void checkPasswordAction(ActionEvent actionEvent) {
        passwordField.setEditable(checkPassword.isSelected());
    }
}
