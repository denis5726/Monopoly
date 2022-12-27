package monopoly.ux.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import monopoly.context.Context;
import monopoly.log.Logger;
import monopoly.net.module.ModuleInterfaceNet;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.ux.model.CreatedGame;
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
    private int selectedGameId = -1;
    private final IdCounter idCounter;
    private List<CreatedGame> games;

    public ConnectGameController() {
        vBoxGameList = new VBox();
        games = new ArrayList<>();
        idCounter = new IdCounter();
        gameScroll = new ScrollPane();
    }

    @Override
    public void setContext(SceneContext sceneContext) {
        List<CreatedGame> gameList = ((ModuleInterfaceNet)Context.get(ModuleInterfaceNet.class)).getConnectedGames();
        Logger.trace(gameList.toString());
        addGames(gameList);
    }

    private void addGames(List<CreatedGame> gameList) {
        games = gameList;

        ObservableList<Node> children = vBoxGameList.getChildren();

        for (int i = 0; i < games.size(); i++) {
            GridPane gridPane = (GridPane) children.get(i);
            Label title = (Label) ((VBox)gridPane.getChildren().get(0)).getChildren().get(0);
            Label password = (Label) ((VBox)gridPane.getChildren().get(1)).getChildren().get(0);
            Label playersNum = (Label) ((VBox)gridPane.getChildren().get(2)).getChildren().get(0);
            Label waitingTime = (Label) ((VBox)gridPane.getChildren().get(3)).getChildren().get(0);
            Label stepTime = (Label) ((VBox)gridPane.getChildren().get(4)).getChildren().get(0);

            CreatedGame game = games.get(i);

            int id = idCounter.getCount();

            gridPane.setVisible(true);
            gridPane.setId(String.valueOf(id));
            gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
                if (selectedGameId != -1) {
                    vBoxGameList.getChildren().forEach((obj) -> {
                        if (obj.getId() != null
                            && selectedGameId == Integer.parseInt(obj.getId())) obj.setStyle("-fx-border-width: 0px");
                    });
                }
                selectedGameId = Integer.parseInt(((GridPane)event.getSource()).getId());
                ((GridPane) event.getSource()).setStyle("-fx-border-width: 3px");
            });

            title.setText(game.getTitle());
            password.setText(game.isCheckPassword() ? "Да" : "Нет");
            playersNum.setText(String.valueOf(game.getPlayersNum()));
            waitingTime.setText(String.valueOf(game.getWaitingTime()));
            stepTime.setText(String.valueOf(game.getStepTime()));
        }

        for (int i = games.size(); i < children.size(); ++i) {
            GridPane gridPane = (GridPane) children.get(i);
            gridPane.setVisible(false);
        }
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
        ModuleInterfaceNet moduleInterfaceNet = (ModuleInterfaceNet) Context.get(ModuleInterfaceNet.class);
        String response = moduleInterfaceNet.connectToGame(game);
        if (response.equals("Success")) {
            MonopolyApplication.setScene("waitingPlayers", new SceneContext());
        }
        else AlertWindowFabric.showErrorAlert("Ошибка", "Ошибка подключения к выбранной игре", response);
    }

    private static class IdCounter {
        private static int count = 0;

        public int getCount() {
            return count++;
        }
    }
}
