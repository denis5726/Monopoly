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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.log.Logger;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.ux.model.Player;

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
    public Pane pane;
    @FXML
    public HBox hBox;
    @FXML
    public VBox vBox;

    @Override
    public void onResize() {
        Stage window = (Stage) Context.get("mainWindow");

        double w = window.getWidth();
        double h = window.getHeight();

        pane.setPrefSize(w, h);
        hBox.setPrefSize(w, h);
    }

    private void setPlayersList(List<Player> playerList) {
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
            if (isEnabled()) updateHeader.restart();
        });

        updateHeader.start();
    }

    @Override
    public void onCreateScene(SceneContext sceneContext) {
        Logger.trace(this.toString());

        updatingHeaderService();

        super.onCreateScene(sceneContext);
    }
    public void backAction(ActionEvent actionEvent) {
        MonopolyApplication.setScene("mainMenu", new SceneContext());
    }
}
