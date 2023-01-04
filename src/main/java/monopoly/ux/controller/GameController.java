package monopoly.ux.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.game.model.PlayerInfo;
import monopoly.game.module.ModuleInterfaceGame;
import monopoly.net.module.ModuleInterfaceNet;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.ux.controller.game.Cell;
import monopoly.ux.controller.game.UIPlayer;
import monopoly.ux.model.Game;
import monopoly.ux.model.GamePlayer;
import monopoly.ux.module.event.UIEvent;
import monopoly.ux.window.DialogFabric;
import monopoly.ux.window.PlayerInfoWindow;

import java.util.ArrayList;
import java.util.List;

public class GameController extends SceneController {
    @FXML
    public Pane pane;
    @FXML
    public HBox hBox;
    @FXML
    public VBox leftVBox;
    @FXML
    public Button back;
    @FXML
    public VBox rightPanelVBox;
    @FXML
    public ScrollPane chatPane;
    @FXML
    public TextArea chatMessageField;
    @FXML
    public Button sendMessageButton;
    @FXML
    public VBox rightVBox;
    @FXML
    public GridPane valuesGridPane;
    @FXML
    public HBox gameHBox;
    @FXML
    public VBox leftPanelVBox;
    @FXML
    public ScrollPane propertyPane;
    @FXML
    public Button beginDialButton;
    @FXML
    public Button dropDiceButton;
    @FXML
    public VBox chatVBox;
    @FXML
    public VBox playersVBox;
    @FXML
    public AnchorPane gamePane;
    @FXML
    public Label stepTime;
    @FXML
    public ImageView gameImage;
    @FXML
    public Label balance;
    @FXML
    public Label currentStepPlayer;
    @FXML
    public VBox propertyVBox;
    private PlayerInfoWindow playerInfoWindow;
    private Game game;
    private List<UIPlayer> playerList;
    private Cell[] cells;

    @Override
    public void onResize() {
        Stage stage = (Stage) Context.get("mainWindow");
        double w = stage.getWidth();
        double h = stage.getHeight();

        pane.setPrefSize(w, h);
        hBox.setPrefSize(w, h);

        rightVBox.setPrefSize(w * 0.75, h);
        leftVBox.setPrefSize(w * 0.25, h);

        leftPanelVBox.setPrefSize(0.234 * w, 0.77 * h);

        valuesGridPane.setPrefSize(0.75 * w, 0.11 * h);
        gameHBox.setPrefSize(0.75 * w, 0.888 * h);

        playersVBox.setPrefSize(0.234 * w, 0.29 * h);
        chatMessageField.setPrefSize(0.721 * leftPanelVBox.getPrefWidth(), 0.115 * h);

        double gameImageSize = Math.min(0.47 * w, 0.888 * h);
        gamePane.setPrefSize(gameImageSize, gameImageSize);
        gamePane.setMaxSize(gameImageSize, gameImageSize);
        rightPanelVBox.setPrefSize(0.25 * w, 0.888 * h);

        gameImage.setFitWidth(gameImageSize);
        gameImage.setFitHeight(gameImageSize);

        propertyPane.setPrefSize(0.25 * w, 0.58 * h);

        for (int i = 0; i < cells.length; ++i) {
            cells[i].onResize(i, gameImage.getFitWidth());
        }
    }

    private Color getColor(int index) {
        return switch (index) {
            case 0 -> Color.RED;
            case 1 -> Color.BLUE;
            case 2 -> Color.YELLOW;
            case 3 -> Color.GREEN;
            default -> null;
        };
    }

    @Override
    public void onCreateScene(SceneContext sceneContext) {
        game = (Game) sceneContext.getProperty("game");

        playerList = new ArrayList<>();

        for (int i = 0; i < game.getPlayers().size(); i++) {
            UIPlayer UIPlayer = new UIPlayer();
            UIPlayer.setName(game.getPlayers().get(i).getName());
            UIPlayer.setBalance(1500);
            Circle circle = new Circle();
            circle.setRadius(10);
            circle.setFill(getColor(i));
            UIPlayer.setCircle(circle);
            playerList.add(UIPlayer);
        }

        cells = new Cell[40];

        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell(gamePane);
        }

        for (int i = 0; i < playerList.size(); i++) {
            getPlayerLabel(i, 0).setText(playerList.get(i).getName());
            getPlayerLabel(i, 1).setText("1500$");
            GridPane gridPane = (GridPane) playersVBox.getChildren().get(i);
            gridPane.setOnContextMenuRequested((event) -> {
                String playerName = ((Label) gridPane.getChildren().get(0)).getText();
                List<UIPlayer> players = playerList.stream()
                        .filter((obj) -> obj.getName().equals(playerName))
                        .toList();
                GamePlayer gamePlayer = new GamePlayer();
                gamePlayer.setName(players.get(0).getName());
                showPlayerInfo(gamePlayer);
            });
            ((Circle) gridPane.getChildren().get(2)).setFill(getColor(i));
        }

        for (int i = playerList.size(); i < playersVBox.getChildren().size(); ++i) {
            playersVBox.getChildren().get(i).setVisible(false);
        }

        super.onCreateScene(sceneContext);
    }

    private void showPlayerInfo(GamePlayer gamePlayer) {
        if (playerInfoWindow != null) playerInfoWindow.hide();

        ModuleInterfaceGame moduleInterfaceGame = (ModuleInterfaceGame) Context.get("moduleInterfaceGame");

        PlayerInfo playerInfo = moduleInterfaceGame.getPlayerInfo(gamePlayer);

        PlayerInfoWindow.load(playerInfo);
        playerInfoWindow = PlayerInfoWindow.getInstance();
    }

    private Label getPlayerLabel(int row, int column) {
        return (Label)((GridPane) playersVBox.getChildren().get(row)).getChildren().get(column);
    }

    @Override
    protected void onSetPlayerMoney(UIEvent uiEvent) {
        UIPlayer UIPlayer = new UIPlayer();
        UIPlayer.setName(uiEvent.getGamePlayer().getName());
        int index = playerList.indexOf(UIPlayer);
        UIPlayer original = playerList.get(index);
        if (original.isCurrent()) balance.setText("Баланс: " + uiEvent.getAmount());
        getPlayerLabel(index, 1).setText(uiEvent.getAmount() + "$");
    }

    @Override
    protected void onSetProperty(UIEvent uiEvent) {
        ObservableList<Node> children = propertyVBox.getChildren();
        for (Node child: children) {
            GridPane childPane = (GridPane) child;
            Label propertyName = (Label) childPane.getChildren().get(0);
            Label propertyStatus = (Label) childPane.getChildren().get(1);
            if (propertyName.getText().equals(uiEvent.getPropertyName())) {
                propertyStatus.setText(getPropertyStatusName(uiEvent.getAmount()));
                setStylePropertyByStatus(childPane, uiEvent.getAmount());
                return;
            }
        }
        GridPane pane = new GridPane();
        setPropertyPane(pane, uiEvent.getPropertyName(), uiEvent.getAmount());
        propertyVBox.getChildren().add(pane);
    }

    private void setStylePropertyByStatus(GridPane pane, int propertyStatus) {
        if (propertyStatus == -1) {
            pane.styleProperty().setValue("-fx-background-color: rgb(230, 100, 100);");
        }
        else pane.styleProperty().setValue("");
    }

    private String getPropertyStatusName(int propertyStatus) {
        String propertyStatusString;
        if (propertyStatus == -1) propertyStatusString = "Заложено";
        else if (propertyStatus < 5) {
            if (propertyStatus == 0) propertyStatusString = "Нет домов";
            else if (propertyStatus == 1) propertyStatusString = "1 дом";
            else propertyStatusString = propertyStatus + " дома";
        }
        else {
            propertyStatusString = "Отель";
        }
        return propertyStatusString;
    }

    private void setPropertyPane(GridPane pane, String propertyName, int propertyStatus) {
        ColumnConstraints constraints1 = new ColumnConstraints();
        constraints1.setPrefWidth(200);
        ColumnConstraints constraints2 = new ColumnConstraints();
        constraints2.setPrefWidth(100);
        pane.getStyleClass().add("property");
        setStylePropertyByStatus(pane, propertyStatus);
        pane.setHgap(10);
        pane.setVgap(3);
        pane.setPrefHeight(30);
        pane.getColumnConstraints().addAll(constraints1, constraints2);
        Label name = new Label(propertyName);
        Label status = new Label(getPropertyStatusName(propertyStatus));
        pane.addRow(0, name, status);
    }

    @Override
    protected void onRemovePlayerTo(UIEvent uiEvent) {
        UIPlayer UIPlayer = playerList.stream()
                .filter((obj) -> obj.getName().equals(
                        uiEvent.getGamePlayer().getName())).toList().get(0);
        for (Cell cell : cells) cell.removePlayer(UIPlayer);
        cells[uiEvent.getPosition()].addPlayer(UIPlayer);
    }

    @Override
    protected void onSetHomeNum(UIEvent uiEvent) {
        cells[uiEvent.getPosition()].setHomeNum(uiEvent.getAmount());
    }

    @Override
    protected void onShowDialog(UIEvent uiEvent) {

    }

    @Override
    protected void onSetNextStep(UIEvent uiEvent) {
        currentStepPlayer.setText("Ходит " + uiEvent.getGamePlayer());
    }

    @Override
    protected void onSetStepCountdown(UIEvent uiEvent) {
        stepTime.setText("Время: " + (uiEvent.getTime() / 60) + ":" + (uiEvent.getTime() % 60));
    }

    @Override
    protected void onShowDices(UIEvent uiEvent) {

    }

    @Override
    protected void onAddLog(UIEvent uiEvent) {
        addChatLog(uiEvent.getActor() + " " + uiEvent.getMessage());
    }

    @Override
    protected void onAddMessageChat(UIEvent uiEvent) {
        addChatMessage(uiEvent.getMessage());
    }

    private void addChatMessage(String text) {
        Text message = new Text(text);
        message.getStyleClass().add("chat");
        addChatText(message);
    }

    private void addChatText(Text text) {
        text.setWrappingWidth(chatPane.getWidth() - 30);
        chatVBox.getChildren().add(text);
        chatPane.setVvalue(1);
    }

    private void addChatLog(String text) {
        Text log = new Text(text);
        log.getStyleClass().add("log");
        log.setFill(Color.RED);
        addChatText(log);
    }

    public void backButtonAction(ActionEvent actionEvent) {
        if (DialogFabric.showQuitGame()) MonopolyApplication.setScene("mainMenu", new SceneContext());
    }

    public void sendMessageAction(ActionEvent actionEvent) {
        if (chatMessageField.getText().isEmpty()) return;
        addChatMessage("Вы: " + chatMessageField.getText());
        ModuleInterfaceNet moduleInterfaceNet = (ModuleInterfaceNet) Context.get("moduleInterfaceNet");
        moduleInterfaceNet.sendChatMessage(chatMessageField.getText());
        chatMessageField.setText("");
    }

    public void beginDialAction(ActionEvent actionEvent) {

    }

    public void dropDiceAction(ActionEvent actionEvent) {

    }
}
