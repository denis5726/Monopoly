package monopoly.ux.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.net.module.Player;
import monopoly.ux.module.event.UIEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaitingPlayersController extends SceneController {
    @FXML
    public Label header;
    @FXML
    public VBox playersList;
    @FXML
    public Button back;
    @FXML
    public VBox pane;
    @FXML
    public HBox hBox;
    @FXML
    public VBox vBox;
    private final List<Player> players = new ArrayList<>();

    @Override
    public void onResize() {
        Stage window = (Stage) Context.get("mainWindow");

        double w = window.getWidth();
        double h = window.getHeight();

        pane.setPrefSize(w, h);
        hBox.setPrefSize(w, h);
    }

    @Override
    public void onAddPlayer(UIEvent uiEvent) {

        players.add(uiEvent.getPlayer());
        setPlayersList();
    }

    @Override
    public void onRemovePlayer(UIEvent uiEvent) {
        players.remove(uiEvent.getPlayer());
        setPlayersList();
    }

    @Override
    protected void onStartGame(UIEvent uiEvent) {
        SceneContext sceneContext = new SceneContext();
        sceneContext.addProperty("game", uiEvent.getGame());
        MonopolyApplication.setScene("game", sceneContext);
    }

    private void setPlayersList() {
        ObservableList<Node> children = playersList.getChildren();
        for (int i = 0; i < players.size(); ++i) {
            ((Label)((HBox) children.get(i)).getChildren().get(0)).setText(players.get(i).getName());
            children.get(i).setVisible(true);
        }
        for (int i = players.size(); i < children.size(); i++) {
            children.get(i).setVisible(false);
        }
    }

    private void updatingHeaderService() {
        Service<String> updateHeader = new Service<>() {
            private final IntegerProperty count = new SimpleIntegerProperty(0);
            @Override
            protected Task<String> createTask() {
                return new Task<>() {
                    @Override
                    protected String call() throws Exception {
                        Thread.sleep(1000);
                        count.set((count.get() + 1) % 3);

                        char[] str = new char[count.get()];
                        Arrays.fill(str, '.');

                        return "Ожидание игроков" + new String(str);
                    }
                };
            }
        };

        updateHeader.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (event) -> {
            header.setText(updateHeader.getValue());
            if (isEnabled()) updateHeader.restart();
        });

        updateHeader.start();
    }

    @Override
    public void onCreateScene(SceneContext sceneContext) {
        updatingHeaderService();

        ObservableList<Node> children = playersList.getChildren();

        for (Node child : children) child.setVisible(false);

        super.onCreateScene(sceneContext);
    }

    public void backAction(ActionEvent actionEvent) {
        MonopolyApplication.setScene("mainMenu", new SceneContext());
    }
}
