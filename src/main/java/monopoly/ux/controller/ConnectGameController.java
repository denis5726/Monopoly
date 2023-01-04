package monopoly.ux.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.net.module.ModuleInterfaceNet;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.ux.model.CreatedGame;
import monopoly.ux.module.event.UIEvent;
import monopoly.ux.window.AlertWindowFabric;
import monopoly.ux.window.DialogFabric;

import java.util.ArrayList;
import java.util.List;

public class ConnectGameController extends SceneController {
    @FXML
    public Button back;
    @FXML
    public ScrollPane gameScroll;
    @FXML
    public VBox vBoxGameList;
    @FXML
    public Button next;
    @FXML
    public Pane pane;
    @FXML
    public HBox hBox;
    @FXML
    public VBox vBox;
    private int selectedGameId = -1;
    private List<CreatedGame> games;

    public ConnectGameController() {
        games = new ArrayList<>();
        gameScroll = new ScrollPane();
    }

    @Override
    public void onCreateScene(SceneContext sceneContext) {
        games = ((ModuleInterfaceNet)Context.get("moduleInterfaceNet")).getConnectedGames();

        addGames();

        super.onCreateScene(sceneContext);
    }

    @Override
    public void onResize() {
        Stage window = (Stage) Context.get("mainWindow");

        double w = window.getWidth();
        double h = window.getHeight();

        pane.setPrefSize(w, h);

        hBox.setPrefSize(w, h);
    }

    @Override
    protected void onAddGame(UIEvent uiEvent) {
        games.add(uiEvent.getCreatedGame());
        addGames();
    }

    @Override
    protected void onSetGames(UIEvent uiEvent) {
        games = uiEvent.getCreatedGames();
        addGames();
    }

    @Override
    protected void onRemoveGame(UIEvent uiEvent) {
        games.remove(uiEvent.getCreatedGame());
        addGames();
    }

    private VBox getVBox() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefHeight(200);
        vBox.setMinWidth(100);
        return vBox;
    }

    private void addGames() {
        for (CreatedGame createdGame : games) addGame(createdGame);
    }

    private void addGame(CreatedGame createdGame) {
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(926);
        gridPane.setMinWidth(926);
        gridPane.setPrefHeight(84);
        gridPane.getStyleClass().add("game-record");

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setMinWidth(10);
        columnConstraints.setPrefWidth(100);
        gridPane.getColumnConstraints().addAll(
                columnConstraints, columnConstraints, columnConstraints, columnConstraints, columnConstraints
        );
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setMinHeight(10);
        rowConstraints.setPrefHeight(30);
        gridPane.getRowConstraints().add(rowConstraints);

        VBox titleVBox = getVBox();
        VBox passwordVBox = getVBox();
        VBox playersNumVBox = getVBox();
        VBox waitingTimeVBox = getVBox();
        VBox stepTimeVBox = getVBox();

        Label title = new Label(createdGame.getTitle());
        Label password = new Label(createdGame.isCheckPassword() ? "Да" : "Нет");
        Label playersNum = new Label(String.valueOf(createdGame.getPlayersNum()));
        Label waitingTime = new Label(String.valueOf(createdGame.getWaitingTime()));
        Label stepTime = new Label(String.valueOf(createdGame.getStepTime()));

        titleVBox.getChildren().add(title);
        passwordVBox.getChildren().add(password);
        playersNumVBox.getChildren().add(playersNum);
        waitingTimeVBox.getChildren().add(waitingTime);
        stepTimeVBox.getChildren().add(stepTime);

        gridPane.addRow(0, titleVBox, passwordVBox, playersNumVBox, waitingTimeVBox, stepTimeVBox);

        gridPane.setVisible(true);
        gridPane.setId(String.valueOf(games.indexOf(createdGame)));
        gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (selectedGameId != -1) {
                vBoxGameList.getChildren().forEach((obj) -> {
                    if (obj.getId() != null
                            && selectedGameId == Integer.parseInt(obj.getId()))
                        obj.setStyle("-fx-border-width: 0px");
                });
            }
            selectedGameId = Integer.parseInt(((GridPane) event.getSource()).getId());
            ((GridPane) event.getSource()).setStyle("-fx-border-width: 3px");
        });

        vBoxGameList.getChildren().add(gridPane);
    }

    public void backAction(ActionEvent actionEvent) {
        MonopolyApplication.setScene("mainMenu", new SceneContext());
    }

    public void nextAction(ActionEvent actionEvent) {
        if (selectedGameId == -1) {
            AlertWindowFabric.showErrorAlert("Ошибка", "Игра для подключения не выбрана", null);
            return;
        }
        CreatedGame game = games.get(selectedGameId);
        if (game.isCheckPassword()) {
            String password = DialogFabric.showGameLogin();
            if (password.isEmpty()) return;
            if (!password.equals(game.getPassword())) {
                AlertWindowFabric.showErrorAlert("Ошибка", "Неверный пароль игры", null);
                return;
            }
        }
        ModuleInterfaceNet moduleInterfaceNet = (ModuleInterfaceNet) Context.get("moduleInterfaceNet");
        String response = moduleInterfaceNet.connectToGame(game);
        if (response.equals("Success")) {
            SceneContext context = new SceneContext();
            context.addProperty("game", game);
            MonopolyApplication.setScene("waitingPlayers", context);
        } else AlertWindowFabric.showErrorAlert("Ошибка", "Ошибка подключения к выбранной игре", response);
    }
}
