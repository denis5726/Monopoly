package monopoly.ux.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import monopoly.context.Context;
import monopoly.net.module.ModuleInterfaceNet;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.ux.model.Game;
import monopoly.ux.model.GamePlayer;
import monopoly.ux.model.GameQuestion;
import monopoly.ux.window.DialogFabric;

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
    public ScrollPane playersPane;
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

        for (GamePlayer player : playerList) {
            Label playerName = new Label(player.getName());
            playerName.getStyleClass().add("player");
            playersVBox.getChildren().add(playerName);
        }

        super.onCreateScene(sceneContext);
    }

    @Override
    protected void onSetPlayerMoney(GamePlayer gamePlayer, int money) {

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

    }

    @Override
    protected void onShowDices(int value_1, int value_2) {

    }

    @Override
    protected void onAddLog(String player, String text) {
        Label message = new Label(player + " " + text);
        message.getStyleClass().add("log");
        chatVBox.getChildren().add(message);
    }

    @Override
    protected void onAddMessageChat(String text) {
        Label message = new Label(text);
        message.getStyleClass().add("chat");
        chatVBox.getChildren().add(message);
    }

    public void backButtonAction(ActionEvent actionEvent) {
        if (DialogFabric.showQuitGame()) MonopolyApplication.setScene("mainMenu", new SceneContext());
    }

    public void sendMessageAction(ActionEvent actionEvent) {
        if (chatMessageField.getText().isEmpty()) return;
        Label message = new Label("Вы: " + chatMessageField.getText());
        message.getStyleClass().add("chat");
        chatVBox.getChildren().add(message);
        ModuleInterfaceNet moduleInterfaceNet = (ModuleInterfaceNet) Context.get("moduleInterfaceNet");
        moduleInterfaceNet.sendChatMessage(chatMessageField.getText());
        chatMessageField.setText("");
    }

    public void beginDialAction(ActionEvent actionEvent) {

    }

    public void dropDiceAction(ActionEvent actionEvent) {

    }
}
