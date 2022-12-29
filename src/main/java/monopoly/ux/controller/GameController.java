package monopoly.ux.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import monopoly.context.Context;
import monopoly.game.model.PlayerInfo;
import monopoly.game.module.ModuleInterfaceGame;
import monopoly.net.module.ModuleInterfaceNet;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.ux.model.Game;
import monopoly.ux.model.GamePlayer;
import monopoly.ux.model.GameQuestion;
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
    private PlayerInfoWindow playerInfoWindow;
    private Game game;
    private List<GamePlayer> playerList;

    @Override
    public void onResize() {
        super.onResize();
    }

    @Override
    public void onCreateScene(SceneContext sceneContext) {
        game = (Game) sceneContext.getProperty("game");
        playerList = game.getPlayers();
        List<Circle> playerCirles = new ArrayList<>();

        for (int i = 0; i < playerList.size(); i++) {
            getPlayerLabel(i, 0).setText(playerList.get(i).getName());
            getPlayerLabel(i, 1).setText("1500$");
            GridPane gridPane = (GridPane) playersVBox.getChildren().get(i);
            gridPane.setOnContextMenuRequested((event) -> {
                String playerName = ((Label) gridPane.getChildren().get(0)).getText();
                List<GamePlayer> players = playerList.stream()
                        .filter((obj) -> obj.getName().equals(playerName))
                        .toList();
                showPlayerInfo(players.get(0), gridPane);
            });
            Circle player = new Circle();
            player.setRadius(10);
            player.setFill(new Color(Math.random(), Math.random(), Math.random(), 1));
            player.setLayoutX(Math.random() * gamePane.getPrefWidth());
            player.setLayoutY(Math.random() * gamePane.getPrefHeight());
            playerList.get(i).setCircle(player);
            playerCirles.add(player);
        }

        gamePane.getChildren().addAll(playerCirles);

        for (int i = playerList.size(); i < playersVBox.getChildren().size(); ++i) {
            playersVBox.getChildren().get(i).setVisible(false);
        }

        super.onCreateScene(sceneContext);
    }

    private void showPlayerInfo(GamePlayer gamePlayer, Node parentNode) {
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
    protected void onSetPlayerMoney(GamePlayer gamePlayer, int money) {
        int index = playerList.indexOf(gamePlayer);
        getPlayerLabel(index, 1).setText(money + "$");
    }

    @Override
    protected void onRemovePlayerTo(GamePlayer gamePlayer, int position) {

    }

    @Override
    protected void onSetHomeNum(int position, int num) {

    }

    @Override
    protected void onShowDialog(GameQuestion question, int waitingTime) {

    }

    @Override
    protected void onSetStepCountdown(int stepCountdown) {
        stepTime.setText("Время: " + (stepCountdown / 60) + ":" + (stepCountdown % 60));
    }

    @Override
    protected void onShowDices(int value_1, int value_2) {

    }

    @Override
    protected void onAddLog(String player, String text) {
        addChatLog(player + " " + text);
    }

    @Override
    protected void onAddMessageChat(String text) {
        addChatMessage(text);
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
