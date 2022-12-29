package monopoly.ux.controller;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import monopoly.log.Logger;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.SceneContext;
import monopoly.context.Context;
import monopoly.ux.model.*;
import monopoly.ux.module.event.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class SceneController {
    private boolean enabled;
    private static final Queue<UIEvent> eventQueue = new LinkedList<>();
    public SceneController() {
        Context.put(getClassName(), this);

        Service<UIEvent> eventObserver = new Service<>() {
            @Override
            protected Task<UIEvent> createTask() {
                return new Task<>() {
                    @Override
                    protected UIEvent call() throws Exception {
                        while (eventQueue.isEmpty()) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        return eventQueue.peek();
                    }
                };
            }
        };

        eventObserver.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (event) -> {
            if (enabled) eventQueue.remove();
            else {
                eventObserver.restart();
                return;
            }

            UIEvent uiEvent = eventObserver.getValue();

            if (uiEvent instanceof AddPlayerEvent)
                this.onAddPlayer(((AddPlayerEvent) uiEvent).getPlayer());
            else if (uiEvent instanceof SetPlayersEvent)
                this.onSetPlayers(((SetPlayersEvent) uiEvent).getPlayer());
            else if (uiEvent instanceof RemovePlayerEvent)
                this.onRemovePlayer(((RemovePlayerEvent) uiEvent).getPlayer());
            else if (uiEvent instanceof AddGameEvent)
                this.onAddGame(((AddGameEvent) uiEvent).getCreatedGame());
            else if (uiEvent instanceof SetGamesEvent)
                this.onSetGames(((SetGamesEvent) uiEvent).getGames());
            else if (uiEvent instanceof RemoveGameEvent)
                this.onRemoveGame(((RemoveGameEvent) uiEvent).getCreatedGame());
            else if (uiEvent instanceof SetPlayerMoneyEvent)
                this.onSetPlayerMoney(((SetPlayerMoneyEvent) uiEvent).getPlayer(),
                        ((SetPlayerMoneyEvent) uiEvent).getMoney());
            else if (uiEvent instanceof RemovePlayerToEvent)
                this.onRemovePlayerTo(((RemovePlayerToEvent) uiEvent).getPlayer(),
                        ((RemovePlayerToEvent) uiEvent).getPosition());
            else if (uiEvent instanceof SetHomeNumEvent)
                this.onSetHomeNum(((SetHomeNumEvent) uiEvent).getPosition(),
                        ((SetHomeNumEvent) uiEvent).getNum());
            else if (uiEvent instanceof ShowDialogEvent)
                this.onShowDialog(((ShowDialogEvent) uiEvent).getQuestion(),
                        ((ShowDialogEvent) uiEvent).getWaitingTime());
            else if (uiEvent instanceof SetStepCountdownEvent)
                this.onSetStepCountdown(((SetStepCountdownEvent) uiEvent).getCountdown());
            else if (uiEvent instanceof ShowDiceEvent)
                this.onShowDices(((ShowDiceEvent) uiEvent).getValue_1(),
                        ((ShowDiceEvent) uiEvent).getValue_2());
            else if (uiEvent instanceof AddLogEvent)
                this.onAddLog(((AddLogEvent) uiEvent).getPlayer(),
                        ((AddLogEvent) uiEvent).getText());
            else if (uiEvent instanceof AddMessageChatEvent)
                this.onAddMessageChat(((AddMessageChatEvent) uiEvent).getText());
            else if (uiEvent instanceof StartGameEvent)
                this.onStartGame(((StartGameEvent) uiEvent).getGame());

            eventObserver.restart();
        });
    }

    private String getClassName() {
        StringBuilder stringBuilder = new StringBuilder(this.getClass().getName());
        stringBuilder.delete(0, stringBuilder.lastIndexOf(".") + 1);
        stringBuilder.setCharAt(0, (char) (stringBuilder.charAt(0) - 'A' + 'a'));
        stringBuilder.delete(stringBuilder.lastIndexOf("Controller"), stringBuilder.length());
        return stringBuilder.toString();
    }

    public Scene getScene() {
        String nameScene = "views/" + getClassName() + ".fxml";

        FXMLLoader fxmlLoader = new FXMLLoader(MonopolyApplication.loadResource(nameScene));

        try {
            return new Scene(fxmlLoader.load());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error("Scene with name " + nameScene + " is not found");
        }

        return null;
    }

    public void onResize() {

    }

    public void onChangeScene() {
        enabled = false;
    }

    public void onCreateScene(SceneContext sceneContext) {
        this.onResize();
        enabled = true;
    }

    protected boolean isEnabled() {
        return enabled;
    }

    public void onAddPlayer(Player player) {

    }

    void onSetPlayers(List<Player> players) {

    }

    void onRemovePlayer(Player player) {

    }

    void onAddGame(CreatedGame createdGame) {

    }

    void onSetGames(List<CreatedGame> gameList) {

    }

    void onRemoveGame(CreatedGame createdGame) {

    }

    void onSetPlayerMoney(GamePlayer gamePlayer, int money) {

    }

    void onRemovePlayerTo(GamePlayer gamePlayer, int position) {

    }

    void onSetHomeNum(int position, int num) {

    }

    void onShowDialog(GameQuestion question, int waitingTime) {

    }

    void onSetStepCountdown(int stepCountdown) {

    }

    void onShowDices(int value_1, int value_2) {

    }

    void onAddLog(String player, String text) {

    }

    void onAddMessageChat(String text) {

    }

    void onStartGame(Game game) {

    }
}
