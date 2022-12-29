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

    private final EventObserver eventObserver = new EventObserver();

    public SceneController() {
        Context.put(getClassName(), this);
    }

    public static void pollEvent(UIEvent uiEvent) {
        eventQueue.add(uiEvent);
    }

    private String getClassName() {
        StringBuilder stringBuilder = new StringBuilder(this.getClass().getName());
        stringBuilder.delete(0, stringBuilder.lastIndexOf(".") + 1);
        stringBuilder.setCharAt(0, (char) (stringBuilder.charAt(0) - 'A' + 'a'));
        stringBuilder.delete(stringBuilder.lastIndexOf("Controller"), stringBuilder.length());
        return stringBuilder.toString();
    }

    public static Scene getScene(String name) {
        String nameScene = "views/" + name + ".fxml";

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

    protected void onAddPlayer(Player player) {

    }

    protected void onSetPlayers(List<Player> players) {

    }

    protected void onRemovePlayer(Player player) {

    }

    protected void onAddGame(CreatedGame createdGame) {

    }

    protected void onSetGames(List<CreatedGame> gameList) {

    }

    protected void onRemoveGame(CreatedGame createdGame) {

    }

    protected void onSetPlayerMoney(GamePlayer gamePlayer, int money) {

    }

    protected void onRemovePlayerTo(GamePlayer gamePlayer, int position) {

    }

    protected void onSetHomeNum(int position, int num) {

    }

    protected void onShowDialog(GameQuestion question, int waitingTime) {

    }

    protected void onSetStepCountdown(int stepCountdown) {

    }

    protected void onShowDices(int value_1, int value_2) {

    }

    protected void onAddLog(String player, String text) {

    }

    protected void onAddMessageChat(String text) {

    }

    protected void onStartGame(Game game) {

    }

    private static class EventObserver {
        private final Service<UIEvent> eventObserver;
        public EventObserver() {
            SceneController sceneController = MonopolyApplication.getCurrentScene();

            if (sceneController != null) sceneController.eventObserver.eventObserver.cancel();

            eventObserver = new Service<>() {
                @Override
                protected Task<UIEvent> createTask() {
                    return new Task<>() {
                        @Override
                        protected UIEvent call() throws Exception {
                            while (eventQueue.isEmpty()) {
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    return null;
                                }
                            }
                            return eventQueue.peek();
                        }
                    };
                }
            };

            eventObserver.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, (event) -> {
                if (eventObserver.getValue() == null) return;

                SceneController controller = MonopolyApplication.getCurrentScene();

                if (controller == null || eventQueue.isEmpty()) {
                    eventObserver.restart();
                    return;
                }

                eventQueue.remove();

                UIEvent uiEvent = eventObserver.getValue();

                if (uiEvent instanceof AddPlayerEvent)
                    controller.onAddPlayer(((AddPlayerEvent) uiEvent).getPlayer());
                else if (uiEvent instanceof SetPlayersEvent)
                    controller.onSetPlayers(((SetPlayersEvent) uiEvent).getPlayer());
                else if (uiEvent instanceof RemovePlayerEvent)
                    controller.onRemovePlayer(((RemovePlayerEvent) uiEvent).getPlayer());
                else if (uiEvent instanceof AddGameEvent)
                    controller.onAddGame(((AddGameEvent) uiEvent).getCreatedGame());
                else if (uiEvent instanceof SetGamesEvent)
                    controller.onSetGames(((SetGamesEvent) uiEvent).getGames());
                else if (uiEvent instanceof RemoveGameEvent)
                    controller.onRemoveGame(((RemoveGameEvent) uiEvent).getCreatedGame());
                else if (uiEvent instanceof SetPlayerMoneyEvent)
                    controller.onSetPlayerMoney(((SetPlayerMoneyEvent) uiEvent).getPlayer(),
                            ((SetPlayerMoneyEvent) uiEvent).getMoney());
                else if (uiEvent instanceof RemovePlayerToEvent)
                    controller.onRemovePlayerTo(((RemovePlayerToEvent) uiEvent).getPlayer(),
                            ((RemovePlayerToEvent) uiEvent).getPosition());
                else if (uiEvent instanceof SetHomeNumEvent)
                    controller.onSetHomeNum(((SetHomeNumEvent) uiEvent).getPosition(),
                            ((SetHomeNumEvent) uiEvent).getNum());
                else if (uiEvent instanceof ShowDialogEvent)
                    controller.onShowDialog(((ShowDialogEvent) uiEvent).getQuestion(),
                            ((ShowDialogEvent) uiEvent).getWaitingTime());
                else if (uiEvent instanceof SetStepCountdownEvent)
                    controller.onSetStepCountdown(((SetStepCountdownEvent) uiEvent).getCountdown());
                else if (uiEvent instanceof ShowDiceEvent)
                    controller.onShowDices(((ShowDiceEvent) uiEvent).getValue_1(),
                            ((ShowDiceEvent) uiEvent).getValue_2());
                else if (uiEvent instanceof AddLogEvent)
                    controller.onAddLog(((AddLogEvent) uiEvent).getPlayer(),
                            ((AddLogEvent) uiEvent).getText());
                else if (uiEvent instanceof AddMessageChatEvent)
                    controller.onAddMessageChat(((AddMessageChatEvent) uiEvent).getText());
                else if (uiEvent instanceof StartGameEvent)
                    controller.onStartGame(((StartGameEvent) uiEvent).getGame());

                eventObserver.restart();
            });

            eventObserver.start();
        }
    }
}
