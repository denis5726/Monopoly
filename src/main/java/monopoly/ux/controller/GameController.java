package monopoly.ux.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.game.Game;
import monopoly.game.module.GamePlayerInformation;
import monopoly.game.module.ModuleInterfaceGame;
import monopoly.net.module.ModuleInterfaceNet;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.ux.model.Cell;
import monopoly.ux.model.Dice;
import monopoly.ux.module.UIPlayer;
import monopoly.ux.module.UIGame;
import monopoly.game.module.GameQuestion;
import monopoly.game.module.GameQuestionType;
import monopoly.ux.module.event.UIEvent;
import monopoly.ux.window.*;

import java.util.ArrayList;
import java.util.List;

public class GameController extends SceneController {
    @FXML
    public VBox pane;
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
    @FXML
    public Button escapeJailButton;
    private Dice dice1;
    private Dice dice2;
    private UIGame uiGame;
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

        playersVBox.setPrefSize(0.234 * w, 0.294 * h);
        chatPane.setPrefSize(0.24 * w, 0.261 * h);
        chatMessageField.setPrefSize(0.184 * w, 0.115 * h);

        double gameHBoxHeight = 0.865 * h;
        double gameImageSize = Math.min(0.479 * w, gameHBoxHeight);
        gamePane.setPrefSize(gameImageSize, gameImageSize);
        gamePane.setMaxSize(gameImageSize, gameImageSize);

        double rightVBoxWidth = 0.247 * w;

        rightPanelVBox.setPrefSize(rightVBoxWidth, gameHBoxHeight);

        gameImage.setFitWidth(gameImageSize);
        gameImage.setFitHeight(gameImageSize);

        propertyPane.setPrefSize(rightVBoxWidth, 0.625 * h);

        propertyVBox.setPrefWidth(rightVBoxWidth);
        propertyVBox.setPrefHeight(propertyPane.getHeight());

        beginDialButton.setPrefWidth(rightVBoxWidth);
        escapeJailButton.setPrefWidth(rightVBoxWidth);

        for (int i = 0; i < cells.length; ++i) {
            cells[i].onResize(i, gameImage.getFitWidth());
        }
    }

    private Color getPlayerColor(int index) {
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
        uiGame = (UIGame) sceneContext.getProperty("game");

        loadPlayersList();
        loadCells();
        loadPlayersPanel();

        Game game = new Game(uiGame);
        Context.put("currentGame", game);

        super.onCreateScene(sceneContext);
    }

    private void loadPlayersPanel() {
        for (int i = 0; i < playerList.size(); i++) {
            getPlayerLabel(i, 0).setText(playerList.get(i).getName());
            getPlayerLabel(i, 1).setText("1500$");
            GridPane gridPane = (GridPane) playersVBox.getChildren().get(i);
            gridPane.setOnContextMenuRequested((event) -> showPlayerInfo(((Label)gridPane.getChildren().get(0)).getText()));
            ((Circle) gridPane.getChildren().get(2)).setFill(getPlayerColor(i));
        }

        for (int i = playerList.size(); i < playersVBox.getChildren().size(); ++i) {
            playersVBox.getChildren().get(i).setVisible(false);
        }
    }

    private void loadCells() {
        cells = new Cell[40];

        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell(gamePane);
        }
    }

    private void loadPlayersList() {
        playerList = new ArrayList<>();

        for (int i = 0; i < uiGame.getPlayers().size(); i++) {
            UIPlayer UIPlayer = new UIPlayer();
            UIPlayer.setName(uiGame.getPlayers().get(i).getName());
            UIPlayer.setBalance(1500);
            UIPlayer.setCurrent(uiGame.getPlayers().get(i).isCurrent());
            Circle circle = new Circle();
            circle.setRadius(10);
            circle.setFill(getPlayerColor(i));
            UIPlayer.setCircle(circle);
            playerList.add(UIPlayer);
        }
    }

    private void showPlayerInfo(String playerName) {
        ModuleInterfaceGame moduleInterfaceGame = (ModuleInterfaceGame) Context.get("moduleInterfaceGame");
        GamePlayerInformation gamePlayerInformation = moduleInterfaceGame.getPlayerInfo(playerName);
        PlayerInfoWindow.showWindow(gamePlayerInformation);
    }

    private Label getPlayerLabel(int row, int column) {
        return (Label)((GridPane) playersVBox.getChildren().get(row)).getChildren().get(column);
    }

    @Override
    protected void onSetPlayerMoney(UIEvent uiEvent) {
        UIPlayer UIPlayer = new UIPlayer();
        UIPlayer.setName(uiEvent.getPlayerName());
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
                setPaneStyleClassByStatus(childPane, uiEvent.getAmount());
                return;
            }
        }
        GridPane pane = new GridPane();
        setPropertyPane(pane, uiEvent.getPropertyName(), uiEvent.getAmount());
        propertyVBox.getChildren().add(pane);
    }

    private void setPaneStyleClassByStatus(GridPane pane, int propertyStatus) {
        if (propertyStatus == -1) {
            pane.getStyleClass().add("in-mortgage");
        }
        else pane.getStyleClass().remove("in-mortgage");
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
        constraints1.setPrefWidth(170);
        ColumnConstraints constraints2 = new ColumnConstraints();
        constraints2.setPrefWidth(130);
        pane.getStyleClass().add("property");
        pane.getStyleClass().add("dark-color");
        setPaneStyleClassByStatus(pane, propertyStatus);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(3);
        pane.setPrefHeight(38);
        pane.getColumnConstraints().addAll(constraints1, constraints2);
        Label name = new Label(propertyName);
        Label status = new Label(getPropertyStatusName(propertyStatus));
        pane.addRow(0, name, status);
    }

    @Override
    protected void onRemovePlayerTo(UIEvent uiEvent) {
        UIPlayer UIPlayer = playerList.stream()
                .filter((obj) -> obj.getName().equals(
                        uiEvent.getPlayerName())).toList().get(0);
        for (Cell cell : cells) cell.removePlayer(UIPlayer);
        cells[uiEvent.getPosition()].addPlayer(UIPlayer);
    }

    @Override
    protected void onSetHomeNum(UIEvent uiEvent) {
        cells[uiEvent.getPosition()].setHomeNum(uiEvent.getAmount());
    }

    @Override
    protected void onShowDialog(UIEvent uiEvent) {
        if (uiEvent.getQuestion().getType() == GameQuestionType.BUY_CONFIRMATION) {
            DialogFabric.showBuyConfirmation(uiEvent.getQuestion(), this);
        }
        else if (uiEvent.getQuestion().getType() == GameQuestionType.AUCTION_CONFIRMATION) {
            DialogFabric.showAuctionConfirmation(uiEvent.getQuestion(), this);
        }
        else DialogFabric.showMortgagePropertyChoosingDialog(uiEvent.getQuestion(), this);
    }

    public void sendResponse(GameQuestion gameQuestion) {
        ModuleInterfaceGame moduleInterfaceGame = (ModuleInterfaceGame) Context.get("moduleInterfaceGame");
        moduleInterfaceGame.sendResponse(gameQuestion);
    }

    @Override
    protected void onSetNextStep(UIEvent uiEvent) {
        currentStepPlayer.setText("Ходит " + uiEvent.getPlayerName());
    }

    @Override
    protected void onSetStepCountdown(UIEvent uiEvent) {
        stepTime.setText("Время: " + (uiEvent.getTime() / 60) + ":" + (uiEvent.getTime() % 60));
    }

    @Override
    protected void onShowDices(UIEvent uiEvent) {
        if (dice1 == null) dice1 = new Dice(gamePane, 0);
        if (dice2 == null) dice2 = new Dice(gamePane, 1);
        dice1.startValue(uiEvent.getValue_1());
        dice2.startValue(uiEvent.getValue_2());
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

    @Override
    protected void onSetInJail(UIEvent uiEvent) {
        escapeJailButton.setDisable(uiEvent.isInJail());
    }

    public void backButtonAction(ActionEvent actionEvent) {
        DialogFabric.showQuitGame(this);
    }

    public void goBackTransition() {
        MonopolyApplication.setScene("mainMenu", new SceneContext());
    }

    public void sendMessageAction(ActionEvent actionEvent) {
        if (chatMessageField.getText().isEmpty()) return;
        addChatMessage("Вы: " + chatMessageField.getText());
        ModuleInterfaceNet moduleInterfaceNet = (ModuleInterfaceNet) Context.get("moduleInterfaceNet");
        moduleInterfaceNet.sendChatMessage(chatMessageField.getText());
        chatMessageField.setText("");
    }

    public void beginDialAction(ActionEvent actionEvent) {
        ActivityWindow.showDialog(playerList);
    }

    public void escapeJailAction(ActionEvent actionEvent) {
        EscapeJailDialog.showDialog();
    }
}
