package monopoly.ux.window;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import monopoly.context.Context;
import monopoly.game.model.PropertyInformation;
import monopoly.net.module.ModuleInterfaceNet;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.controller.game.Card;

import java.util.List;
import java.util.Optional;

public class DialogFabric {
    public static String showGameLogin() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Подключение по паролю");
        dialog.setHeaderText("Введите пароль для входа в игру");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField password = new PasswordField();
        password.setPromptText("Пароль");

        grid.add(new Label("Пароль:"), 0, 1);
        grid.add(password, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
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
        Dialog<ButtonType> dialog = createPropertyDialog(propertyInformation);
        dialog.setTitle("Подтверждение покупки недвижимости");
        dialog.setHeaderText("Хотите ли вы приобрести этот участок?");

        dialog.showAndWait();

        return dialog.getResult().getText().equals("Да");
    }

    public static boolean showAuctionConfirmation(PropertyInformation propertyInformation) {
        Dialog<ButtonType> dialog = createPropertyDialog(propertyInformation);
        dialog.setTitle("Подтверждение участия в аукционе");
        dialog.setHeaderText("Хотите ли вы участвовать в аукционе за этот участок?");

        dialog.showAndWait();

        return dialog.getResult().getText().equals("Да");
    }

    private static Dialog<ButtonType> createPropertyDialog(PropertyInformation propertyInformation) {
        Dialog<ButtonType> dialog = new Dialog<>();

        ButtonType okButtonType = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.getChildren().add(new Card(propertyInformation));
        dialog.getDialogPane().setContent(hBox);

        return dialog;
    }

    public static List<PropertyInformation> showMortgagePropertyChoosingDialog
            (List<PropertyInformation> propertyInformationList) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Выбор недвижимости");
        dialog.setTitle("Выберите имущество для продажи в залог");

        ButtonType buttonType = new ButtonType("Выбрать", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().clear();
        dialog.getDialogPane().getButtonTypes().add(buttonType);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        HBox hBox = new HBox();
        hBox.getStylesheets().add(MonopolyApplication.loadResource("styles/main.css").toExternalForm());
        hBox.getStyleClass().add("background");

        for (int i = 0; i < propertyInformationList.size(); i++) {
            Card card = new Card(propertyInformationList.get(i));
            card.setId("property " + i);

            card.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
                card.setSelected(!card.isSelected());
            });

            hBox.getChildren().add(card);
            hBox.setPrefHeight(card.getPrefHeight());
        }

        hBox.setSpacing(3);

        scrollPane.setContent(hBox);
        scrollPane.setPrefHeight(hBox.getPrefHeight() + 30);
        scrollPane.setPrefWidth(600);

        dialog.getDialogPane().setContent(scrollPane);

        dialog.showAndWait();

        return hBox.getChildren().stream()
                .filter((obj) -> ((Card)obj).isSelected())
                .map((obj) -> ((Card)obj).getPropertyInformation()).toList();
    }
}
