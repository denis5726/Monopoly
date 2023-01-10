package monopoly.ux.window;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Pair;
import monopoly.context.Context;
import monopoly.game.model.PropertyInformation;
import monopoly.game.module.ModuleInterfaceGame;
import monopoly.net.module.ModuleInterfaceNet;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.controller.GameController;
import monopoly.ux.controller.game.Card;
import monopoly.ux.model.GameQuestion;

import java.util.ArrayList;
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

    public static void showQuitGame(GameController gameController) {
        Button ok = new Button("Да");
        Button cancel = new Button("Нет");
        SubWindow subWindow = SubWindow.loadWithoutContent();
        if (subWindow != null) {
            subWindow.setTitle("Подтверждение выхода");
            subWindow.setText("Вы действительно хотите выйти?");
            subWindow.addButton(ok);
            ok.addEventHandler(ActionEvent.ACTION, (event) -> {
                gameController.goBackTransition();
                subWindow.hide();
            });
            cancel.addEventHandler(ActionEvent.ACTION, (event) -> {
                subWindow.hide();
            });
            subWindow.addButton(cancel);
            subWindow.show((Stage) Context.get("mainWindow"));
        }
    }

    public static void showBuyConfirmation(GameQuestion gameQuestion, GameController gameController) {
        showPropertyDialog("Подтверждение покупки", "Хотите ли вы приобрести этот участок?",
                gameQuestion, gameController);
    }

    public static void showAuctionConfirmation(GameQuestion gameQuestion, GameController gameController) {
        showPropertyDialog("Участие в аукционе", "Хотите ли вы участвовать в аукционе за эту недвижимость?",
                gameQuestion, gameController);
    }

    private static void showPropertyDialog(String title, String header, GameQuestion gameQuestion, GameController gameController) {
        Button ok = new Button("Да");
        Button cancel = new Button("Нет");
        SubWindow subWindow = SubWindow.loadWithoutContent();
        if (subWindow != null) {
            subWindow.setTitle(title);
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.TOP_CENTER);
            vBox.getStylesheets().add(MonopolyApplication.loadResource("styles/subWindow.css").toExternalForm());
            vBox.setSpacing(7);
            Text text = new Text(header);
            text.getStyleClass().add("text");
            text.setWrappingWidth(0.816 * SubWindow.WINDOW_WIDTH);
            text.setTextAlignment(TextAlignment.CENTER);
            vBox.getChildren().addAll(text, new Card(gameQuestion.getPropertyInformation()));
            subWindow.setContent(vBox);
            subWindow.addButton(ok);
            ok.addEventHandler(ActionEvent.ACTION, (event) -> {
                gameQuestion.setChoose(true);
                gameController.sendResponse(gameQuestion);
                subWindow.hide();
            });
            cancel.addEventHandler(ActionEvent.ACTION, (event) -> {
                gameQuestion.setChoose(false);
                gameController.sendResponse(gameQuestion);
                subWindow.hide();
            });
            subWindow.addButton(cancel);
            subWindow.show((Stage) Context.get("mainWindow"));
        }
    }

    public static void showMortgagePropertyChoosingDialog
            (GameQuestion gameQuestion, GameController gameController) {
        Button selectButton = new Button("Выбрать");
        SubWindow subWindow = SubWindow.loadWithoutContent();
        if (subWindow == null) return;
        subWindow.setTitle("Выбор закладываемого имущества");

        subWindow.addButton(selectButton);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        HBox hBox = new HBox();

        for (int i = 0; i < gameQuestion.getPropertiesInformation().size(); i++) {
            Card card = new Card(gameQuestion.getPropertiesInformation().get(i));

            card.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
                card.setSelected(!card.isSelected());
            });

            hBox.getChildren().add(card);
            hBox.setPrefHeight(card.getPrefHeight());
        }

        selectButton.addEventHandler(ActionEvent.ACTION, (event) -> {
            List<PropertyInformation> propertyInformationList = new ArrayList<>();
            for (Node node: hBox.getChildren()) {
                Card card = (Card)node;
                if (card.isSelected()) propertyInformationList.add(card.getPropertyInformation());
            }
            gameQuestion.setPropertiesInformation(propertyInformationList);
            gameController.sendResponse(gameQuestion);
            subWindow.hide();
        });

        hBox.setSpacing(3);

        scrollPane.setContent(hBox);
        scrollPane.setPrefHeight(hBox.getPrefHeight() + 30);
        scrollPane.setPrefWidth(600);
        scrollPane.setPrefHeight(hBox.getPrefHeight());

        subWindow.setContent(scrollPane);

        subWindow.show((Stage) Context.get("mainWindow"));
    }
}
