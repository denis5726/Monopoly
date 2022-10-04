package monopoly.ux.window;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import monopoly.ux.settings.SettingsContainer;

import java.util.Optional;

public class DialogFabric {
    public static String showGameLogin() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Подключение по паролю");
        dialog.setHeaderText("Введите пароль для входа в игру");

        ButtonType loginButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField password = new PasswordField();
        password.setPromptText("Пароль");

        grid.add(new Label("Пароль:"), 0, 1);
        grid.add(password, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();

        return password.getText();
    }

    public static boolean showLoginDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Авторизация");
        dialog.setHeaderText(null);

        ButtonType loginButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Логин");
        PasswordField password = new PasswordField();
        password.setPromptText("Пароль");

        grid.add(new Label("Логин:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Пароль:"), 0, 1);
        grid.add(password, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(username::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        if (result.isPresent()) {
            /**
             * Авторизация
             */
            if (Math.random() > 0.5) {
                SettingsContainer.setLogin(username.getText());
                SettingsContainer.setPassword(password.getText());
                return true;
            }
            else return false;
        }
        else return false;
    }
}
