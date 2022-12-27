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
import monopoly.context.Context;
import monopoly.log.Logger;
import monopoly.net.module.ModuleInterfaceNet;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.ux.model.CreatedGame;
import monopoly.ux.model.GamePlayer;

import java.util.Arrays;
import java.util.List;

public class WaitingPlayersController extends SceneController {
    @FXML
    public Label header;
    @FXML
    public VBox playersList;
    @FXML
    public Button back;

    private CreatedGame createdGame;

    private boolean isRunning = true;

    public WaitingPlayersController() {
        header = new Label();

        updatingHeaderService();
        checkingPlayersService();
    }

    private void checkingPlayersService() {
        Service<List<GamePlayer>> updateHeader = new Service<>() {
            @Override
            protected Task<List<GamePlayer>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<GamePlayer> call() throws Exception {
                        Thread.sleep(1000);

                        ModuleInterfaceNet moduleInterfaceNet = (ModuleInterfaceNet) Context.get(
                                ModuleInterfaceNet.class);

                        return moduleInterfaceNet.getPlayersList(createdGame);
                    }
                };
            }
        };

        updateHeader.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (event) -> {
            Logger.trace(updateHeader.getValue().toString());
            setPlayersList(updateHeader.getValue());
            if (updateHeader.getValue().size() == createdGame.getPlayersNum()) {
                SceneContext sceneContext = new SceneContext();
                sceneContext.addProperty("game", createdGame);
                sceneContext.addProperty("players", updateHeader.getValue());
                MonopolyApplication.setScene("game", sceneContext);
            }
            if (isRunning) updateHeader.restart();
        });

        updateHeader.start();
    }

    private void setPlayersList(List<GamePlayer> playerList) {
        ObservableList<Node> children = this.playersList.getChildren();
        for (int i = 0; i < playerList.size(); ++i) {
            ((Label)((HBox) children.get(i)).getChildren().get(0)).setText(playerList.get(i).getName());
            children.get(i).setVisible(true);
        }
        for (int i = playerList.size(); i < children.size(); i++) {
            children.get(i).setVisible(false);
        }
    }

    private void updatingHeaderService() {
        Service<String> updateHeader = new Service<>() {
            private IntegerProperty count = new SimpleIntegerProperty(0);
            @Override
            protected Task<String> createTask() {
                return new Task<>() {
                    @Override
                    protected String call() throws Exception {
                        Thread.sleep(1000);
                        count.set((count.get() + 1) % 3);

                        Logger.trace("running");

                        char[] str = new char[count.get()];
                        Arrays.fill(str, '.');

                        return "Ожидание игроков" + new String(str);
                    }
                };
            }
        };

        updateHeader.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (event) -> {
            header.setText(updateHeader.getValue());
            if (isRunning) updateHeader.restart();
        });

        updateHeader.start();
    }

    @Override
    public void onCreateScene(SceneContext sceneContext) {
        isRunning = true;
        createdGame = (CreatedGame) sceneContext.getProperty("game");
        Logger.trace(this.toString());
    }

    @Override
    public void onChangeScene() {
        isRunning = false;
    }


    public void backAction(ActionEvent actionEvent) {
        MonopolyApplication.setScene("mainMenu", new SceneContext());
    }
}
