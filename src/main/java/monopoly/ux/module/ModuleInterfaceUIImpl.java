package monopoly.ux.module;

import monopoly.ux.controller.SceneController;
import monopoly.ux.model.*;
import monopoly.ux.module.event.UIEvent;
import monopoly.ux.module.event.UIEventType;

import java.util.List;

public class ModuleInterfaceUIImpl implements ModuleInterfaceUI {
    @Override
    public void addPlayer(Player player) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.ADD_PLAYER);
        event.setPlayer(player);
        SceneController.pollEvent(event);
    }

    @Override
    public void setPlayers(List<Player> players) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.SET_PLAYERS);
        event.setPlayers(players);
        SceneController.pollEvent(event);
    }

    @Override
    public void removePlayer(Player player) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.REMOVE_PLAYER);
        event.setPlayer(player);
        SceneController.pollEvent(event);
    }

    @Override
    public void addGame(CreatedGame createdGame) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.ADD_GAME);
        event.setCreatedGame(createdGame);
        SceneController.pollEvent(event);
    }

    @Override
    public void setGames(List<CreatedGame> gameList) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.SET_GAMES);
        event.setCreatedGames(gameList);
        SceneController.pollEvent(event);
    }

    @Override
    public void removeGame(CreatedGame createdGame) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.REMOVE_GAME);
        event.setCreatedGame(createdGame);
        SceneController.pollEvent(event);
    }

    @Override
    public void setPlayerMoney(String playerName, int money) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.SET_PLAYER_MONEY);
        event.setPlayerName(playerName);
        event.setAmount(money);
        SceneController.pollEvent(event);
    }

    @Override
    public void removePlayerTo(String playerName, int position) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.REMOVE_PLAYER_TO);
        event.setPlayerName(playerName);
        event.setPosition(position);
        SceneController.pollEvent(event);
    }

    @Override
    public void setHomeNum(int position, int num) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.SET_HOME_NUM);
        event.setAmount(num);
        event.setPosition(position);
        SceneController.pollEvent(event);
    }

    @Override
    public void addLog(String player, String text) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.ADD_LOG);
        event.setActor(player);
        event.setMessage(text);
        SceneController.pollEvent(event);
    }

    @Override
    public void showDialog(GameQuestion question, int waitingTime) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.SHOW_DIALOG);
        event.setQuestion(question);
        event.setTime(waitingTime);
        SceneController.pollEvent(event);
    }

    @Override
    public void showDices(int value_1, int value_2) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.SHOW_DICE);
        event.setValue_1(value_1);
        event.setValue_2(value_2);
        SceneController.pollEvent(event);
    }

    @Override
    public void startGame(UIGame UIGame) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.START_GAME);
        event.setGame(UIGame);
        SceneController.pollEvent(event);
    }

    @Override
    public void setInJail(boolean inJail) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.SET_IN_JAIL);
        event.setInJail(inJail);
        SceneController.pollEvent(event);
    }

    @Override
    public void addMessageChat(String text) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.ADD_MESSAGE_CHAT);
        event.setMessage(text);
        SceneController.pollEvent(event);
    }

    @Override
    public void setNextStep(String playerName) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.SET_NEXT_STEP);
        event.setPlayerName(playerName);
        SceneController.pollEvent(event);
    }

    @Override
    public void setProperty(String name, int amount) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.SET_PROPERTY);
        event.setPropertyName(name);
        event.setAmount(amount);
        SceneController.pollEvent(event);
    }

    @Override
    public void setStepCountdown(int stepCountdown) {
        UIEvent event = new UIEvent();
        event.setType(UIEventType.SET_STEP_COUNTDOWN);
        event.setTime(stepCountdown);
        SceneController.pollEvent(event);
    }
}
