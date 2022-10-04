package monopoly.ux.controller;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.ux.model.CreatedGame;
import monopoly.ux.window.AlertWindowFabric;
import monopoly.ux.window.DialogFabric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectGameController {
    @FXML
    public Button back;
    @FXML
    public ScrollPane gameScroll;
    public VBox vBoxGameList;
    @FXML
    public Button next;
    public Button addGames;

    private int selectedGameId = -1;

    private IdCounter idCounter;

    private Map<Integer, CreatedGame> games;

    public ConnectGameController() {
        vBoxGameList = new VBox();
        games = new HashMap<>();
        idCounter = new IdCounter();
    }

    public void setGameList(List<CreatedGame> gameList) {
        for (CreatedGame game: gameList) addGame(game);
    }

    private void addGame(CreatedGame game) {
        int id = idCounter.getCount();
        games.put(id, game);

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("game-record");
        gridPane.setId(String.valueOf(Integer.valueOf(id)));
        gridPane.setMinWidth(gameScroll.getPrefWidth());
        gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (selectedGameId != -1) {
                vBoxGameList.getChildren().forEach((obj) -> {
                    if (selectedGameId == Integer.parseInt(obj.getId())) obj.setStyle("-fx-border-width: 0px");
                });
            }
            selectedGameId = Integer.parseInt(((GridPane)event.getSource()).getId());
            ((GridPane) event.getSource()).setStyle("-fx-border-width: 3px");
        });

        double cellWidth = gameScroll.getLayoutBounds().getWidth() / 5.0;

        VBox vBoxTitle = new VBox();
        vBoxTitle.getChildren().add(new Label(game.getTitle()));
        vBoxTitle.setAlignment(Pos.CENTER);
        vBoxTitle.setPrefWidth(cellWidth);
        VBox vBoxPassword = new VBox();
        vBoxPassword.getChildren().add(new Label(game.isCheckPassword() ? "Да" : "Нет"));
        vBoxPassword.setAlignment(Pos.CENTER);
        vBoxPassword.setPrefWidth(cellWidth);
        VBox vBoxPlayers = new VBox();
        vBoxPlayers.getChildren().add(new Label(String.valueOf(game.getPlayersNum())));
        vBoxPlayers.setAlignment(Pos.CENTER);
        vBoxPlayers.setPrefWidth(cellWidth);
        VBox vBoxWaiting = new VBox();
        vBoxWaiting.getChildren().add(new Label(String.valueOf(game.getWaitingTime())));
        vBoxWaiting.setAlignment(Pos.CENTER);
        vBoxWaiting.setPrefWidth(cellWidth);
        VBox vBoxStep = new VBox();
        vBoxStep.getChildren().add(new Label(String.valueOf(game.getStepTime())));
        vBoxStep.setAlignment(Pos.CENTER);
        vBoxStep.setPrefWidth(cellWidth);
        gridPane.addRow(0, vBoxTitle, vBoxPassword, vBoxPlayers, vBoxWaiting, vBoxStep);

        vBoxGameList.getChildren().add(gridPane);
        gameScroll.setContent(vBoxGameList);
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
        /**
         * Подключение к игре
         */
        MonopolyApplication.setScene("waitingPlayers", new SceneContext());
    }

    public void addGamesAction(ActionEvent actionEvent) {
        List<CreatedGame> list = new ArrayList<>();

        int listSize = (int) (Math.random() * 30);
        for (int i = 0; i < listSize; i++) {
            CreatedGame game = new CreatedGame();
            game.setTitle("Title " + i);
            game.setCheckPassword(Math.random() > 0.5);
            if (game.isCheckPassword()) game.setPassword("password");
            game.setPlayersNum((int) (Math.random() * 2 + 2));
            game.setWaitingTime((int) (Math.random() * 25 + 5));
            game.setStepTime((int) (Math.random() * 10 + 5));

            list.add(game);
        }

        setGameList(list);
    }

    private static class IdCounter {
        private static int count = 0;

        public int getCount() {
            return count++;
        }
    }
}
