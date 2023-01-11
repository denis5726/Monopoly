package monopoly.ux.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
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
import monopoly.net.module.CreatedGame;
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
    public VBox pane;
    @FXML
    public HBox hBox;
    @FXML
    public VBox vBox;
    @FXML
    public GridPane headerGridPane;
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

        double gridWidth = 0.727 * w;

        gameScroll.setPrefWidth(gridWidth);
        setGridSize(headerGridPane, gridWidth);
        for (Node node : ((VBox)gameScroll.getContent()).getChildren()) {
            setGridSize((GridPane) node, gridWidth);
        }

        gameScroll.setPrefHeight(0.597 * h);
    }

    private void setGridSize(GridPane gridPane, double gridWidth) {
        gridPane.setPrefWidth(gridWidth);
        gridPane.setMaxWidth(gridWidth);
        for (ColumnConstraints columnConstraints : gridPane.getColumnConstraints()) {
            columnConstraints.setMinWidth(gridWidth / 5);
            columnConstraints.setPrefWidth(gridWidth / 5);
        }
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

    private void addGames() {
        for (CreatedGame createdGame : games) addGame(createdGame);
    }

    private void addGame(CreatedGame createdGame) {
        GridPane gridPane = new GridPane();
        gridPane.setPrefHeight(84);
        gridPane.getStyleClass().add("game-record");
        gridPane.getStyleClass().add("middle-color");

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.SOMETIMES);
        columnConstraints.setPrefWidth(100);
        columnConstraints.setMinWidth(10);
        columnConstraints.setMaxWidth(471);
        columnConstraints.setHalignment(HPos.CENTER);

        gridPane.getColumnConstraints().addAll(
                columnConstraints, columnConstraints, columnConstraints, columnConstraints, columnConstraints
        );

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setMinHeight(10);
        rowConstraints.setPrefHeight(30);
        rowConstraints.setValignment(VPos.CENTER);

        gridPane.getRowConstraints().add(rowConstraints);

        gridPane.addRow(0,
                new Label(createdGame.getTitle()),
                new Label(createdGame.isCheckPassword() ? "Да" : "Нет"),
                new Label(String.valueOf(createdGame.getPlayersNum())),
                new Label(String.valueOf(createdGame.getWaitingTime())),
                new Label(String.valueOf(createdGame.getStepTime())));

        gridPane.setId(String.valueOf(games.indexOf(createdGame)));
        gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            String selectedClass = "selected-game-record";
            String notSelectedClass = "not-selected-game-record";
            if (selectedGameId != -1) {
                vBoxGameList.getChildren().forEach((obj) -> {
                    if (selectedGameId == Integer.parseInt(obj.getId())) {
                        obj.getStyleClass().remove(selectedClass);
                        obj.getStyleClass().add(notSelectedClass);
                    }
                });
            }
            selectedGameId = Integer.parseInt(((GridPane) event.getSource()).getId());
            GridPane source = (GridPane) event.getSource();
            source.getStyleClass().remove(notSelectedClass);
            source.getStyleClass().add(selectedClass);
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
