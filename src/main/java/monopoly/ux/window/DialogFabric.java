package monopoly.ux.window;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import monopoly.context.Context;
import monopoly.game.model.PropertyInformation;
import monopoly.net.module.ModuleInterfaceNet;

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
            ModuleInterfaceNet moduleInterfaceNet = (ModuleInterfaceNet) Context.get("moduleInterfaceNet");
            String response = moduleInterfaceNet.login(username.getText(), password.getText());
            if (response.equals("Success")) return true;
            else {
                AlertWindowFabric.showErrorAlert("Ошибка авторизации", null, response);
                return false;
            }
        }
        else return false;
    }

    public static boolean showQuitGame() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Подтверждение выхода");
        dialog.setHeaderText("Вы уверены, что хотите выйти из игры?");

        ButtonType okButtonType = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        dialog.showAndWait();

        return dialog.getResult().getText().equals("Да");
    }

    public static boolean showBuyConfirmation(PropertyInformation propertyInformation) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Подтверждение покупки недвижимости");
        dialog.setHeaderText("Хотите ли вы приобрести этот участок?");

        ButtonType okButtonType = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label(propertyInformation.getName()), 0, 0);
        grid.add(new Label("Стоимость участка: " + propertyInformation.getPrice() + "$"), 0, 1);
        grid.add(new Label("Стоимость за дом: " + propertyInformation.getHousePrice() + "$"), 0, 2);
        grid.add(new Label("Стоимость отеля : " + propertyInformation.getHotelPrice() + "$"), 0, 3);
        grid.add(new Label("Стоимость аренды: " + propertyInformation.getRent() + "$"), 0, 4);
        grid.add(new Label("Стоимость аренды с 1 домом: " + propertyInformation.getRent1House() + "$"), 0, 5);
        grid.add(new Label("Стоимость аренды с 2 домами: " + propertyInformation.getRent2House() + "$"), 0, 6);
        grid.add(new Label("Стоимость аренды с 3 домами: " + propertyInformation.getRent3House() + "$"), 0, 7);
        grid.add(new Label("Стоимость аренды с 4 домами: " + propertyInformation.getRent4House() + "$"), 0, 8);
        grid.add(new Label("Стоимость аренды с отелем: " + propertyInformation.getRentHotel() + "$"), 0, 9);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();

        return dialog.getResult().getText().equals("Да");
    }
}
