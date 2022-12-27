package monopoly.ux.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import monopoly.context.Context;
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
    public Pane root;

    @Override
    public void setContext(SceneContext sceneContext) {

    }

    public void backAction(ActionEvent actionEvent) {
        MonopolyApplication.setScene("mainMenu", new SceneContext());
    }

    public void playersNumberChanged(KeyEvent inputMethodEvent) {
        String text = playersNumber.getText();
        if (!text.matches("[2-4]")) playersNumber.getStyleClass().add("error");
        else playersNumber.getStyleClass().removeAll("error");
    }

    public void waitingTimeChanged(KeyEvent inputMethodEvent) {
        String text = waitingTime.getText();
        if (!text.matches("(30|[12][0-9]|[5-9])")) waitingTime.getStyleClass().add("error");
        else waitingTime.getStyleClass().removeAll("error");
    }

    public void stepTimeChanged(KeyEvent inputMethodEvent) {
        String text = stepTime.getText();
        if (!text.matches("([5-9]|1[0-5])")) stepTime.getStyleClass().add("error");
        else stepTime.getStyleClass().removeAll("error");
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
            ModuleInterfaceNet moduleInterfaceNet = (ModuleInterfaceNet) Context.get(ModuleInterfaceNet.class);

            CreatedGame game = new CreatedGame();
            game.setTitle(gameTitle.getText());
            game.setCheckPassword(checkPassword.isSelected());
            game.setPassword(passwordField.getText());
            game.setWaitingTime(Integer.parseInt(waitingTime.getText()));
            game.setWaitingTime(Integer.parseInt(stepTime.getText()));

            String response = moduleInterfaceNet.createGame(game);
            if (response.equals("Success")) {
                SceneContext sceneContext = new SceneContext();
                sceneContext.addProperty("playersNum", playersNumber.getText());
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
