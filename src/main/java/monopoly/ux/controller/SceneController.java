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
import monopoly.ux.module.event.*;

import java.util.LinkedList;
import java.util.Queue;

public abstract class SceneController {
    private boolean enabled;
    private static final Queue<UIEvent> eventQueue = new LinkedList<>();

    private final EventObserver eventObserver = new EventObserver();

    public SceneController() {
        Context.put(getClassName(), this);
        
        UIEvent.setHandler(UIEventType.ADD_GAME, this::onAddGame);
        UIEvent.setHandler(UIEventType.ADD_LOG, this::onAddLog);
        UIEvent.setHandler(UIEventType.ADD_MESSAGE_CHAT, this::onAddMessageChat);
        UIEvent.setHandler(UIEventType.ADD_PLAYER, this::onAddPlayer);
        UIEvent.setHandler(UIEventType.REMOVE_GAME, this::onRemoveGame);
        UIEvent.setHandler(UIEventType.REMOVE_PLAYER, this::onRemovePlayer);
        UIEvent.setHandler(UIEventType.REMOVE_PLAYER_TO, this::onRemovePlayerTo);
        UIEvent.setHandler(UIEventType.SET_NEXT_STEP, this::onSetNextStep);
        UIEvent.setHandler(UIEventType.SET_GAMES, this::onSetGames);
        UIEvent.setHandler(UIEventType.SET_HOME_NUM, this::onSetHomeNum);
        UIEvent.setHandler(UIEventType.SET_PLAYER_MONEY, this::onSetPlayerMoney);
        UIEvent.setHandler(UIEventType.SET_PLAYERS, this::onSetPlayers);
        UIEvent.setHandler(UIEventType.SET_STEP_COUNTDOWN, this::onSetStepCountdown);
        UIEvent.setHandler(UIEventType.SHOW_DIALOG, this::onShowDialog);
        UIEvent.setHandler(UIEventType.SHOW_DICE, this::onShowDices);
        UIEvent.setHandler(UIEventType.START_GAME, this::onStartGame);
    }

    protected void onSetNextStep(UIEvent uiEvent) {

    }

    protected void onStartGame(UIEvent uiEvent) {

    }

    protected void onShowDices(UIEvent uiEvent) {
        
    }

    protected void onShowDialog(UIEvent uiEvent) {
        
    }

    protected void onSetStepCountdown(UIEvent uiEvent) {
        
    }

    protected void onSetPlayers(UIEvent uiEvent) {
        
    }

    protected void onSetPlayerMoney(UIEvent uiEvent) {
        
    }

    protected void onSetHomeNum(UIEvent uiEvent) {
        
    }

    protected void onSetGames(UIEvent uiEvent) {
    }

    protected void onRemovePlayerTo(UIEvent uiEvent) {

    }

    protected void onRemovePlayer(UIEvent uiEvent) {

    }

    protected void onRemoveGame(UIEvent uiEvent) {

    }

    protected void onAddPlayer(UIEvent uiEvent) {

    }

    protected void onAddMessageChat(UIEvent uiEvent) {

    }

    protected void onAddLog(UIEvent uiEvent) {

    }

    protected void onAddGame(UIEvent uiEvent) {
        
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
                uiEvent.getHandler().handle(uiEvent);

                eventObserver.restart();
            });

            eventObserver.start();
        }
    }
}
