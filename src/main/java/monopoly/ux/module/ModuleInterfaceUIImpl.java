package monopoly.ux.module;

import monopoly.ux.controller.SceneController;
import monopoly.ux.model.*;
import monopoly.ux.module.event.*;

import java.util.List;

public class ModuleInterfaceUIImpl implements ModuleInterfaceUI {
    @Override
    public void addPlayer(Player player) {
        AddPlayerEvent event = new AddPlayerEvent();
        event.setPlayer(player);
        SceneController.pollEvent(event);
    }

    @Override
    public void setPlayers(List<Player> players) {
        SetPlayersEvent event = new SetPlayersEvent();
        event.setPlayer(players);
        SceneController.pollEvent(event);
    }

    @Override
    public void removePlayer(Player player) {
        RemovePlayerEvent event = new RemovePlayerEvent();
        event.setPlayer(player);
        SceneController.pollEvent(event);
    }

    @Override
    public void addGame(CreatedGame createdGame) {
        AddGameEvent event = new AddGameEvent();
        event.setCreatedGame(createdGame);
        SceneController.pollEvent(event);
    }

    @Override
    public void setGames(List<CreatedGame> gameList) {
        SetGamesEvent event = new SetGamesEvent();
        event.setGames(gameList);
        SceneController.pollEvent(event);
    }

    @Override
    public void removeGame(CreatedGame createdGame) {
        RemoveGameEvent event = new RemoveGameEvent();
        event.setCreatedGame(createdGame);
        SceneController.pollEvent(event);
    }

    @Override
    public void setPlayerMoney(GamePlayer gamePlayer, int money) {
        SetPlayerMoneyEvent event = new SetPlayerMoneyEvent();
        event.setPlayer(gamePlayer);
        event.setMoney(money);
        SceneController.pollEvent(event);
    }

    @Override
    public void removePlayerTo(GamePlayer gamePlayer, int position) {
        RemovePlayerToEvent event = new RemovePlayerToEvent();
        event.setPlayer(gamePlayer);
        event.setPosition(position);
        SceneController.pollEvent(event);
    }

    @Override
    public void setHomeNum(int position, int num) {
        SetHomeNumEvent event = new SetHomeNumEvent();
        event.setNum(num);
        event.setPosition(position);
        SceneController.pollEvent(event);
    }

    @Override
    public void addLog(String player, String text) {
        AddLogEvent event = new AddLogEvent();
        event.setPlayer(player);
        event.setText(text);
        SceneController.pollEvent(event);
    }

    @Override
    public void showDialog(GameQuestion question, int waitingTime) {
        ShowDialogEvent event = new ShowDialogEvent();
        event.setQuestion(question);
        event.setWaitingTime(waitingTime);
        SceneController.pollEvent(event);
    }

    @Override
    public void showDices(int value_1, int value_2) {
        ShowDiceEvent event = new ShowDiceEvent();
        event.setValue_1(value_1);
        event.setValue_2(value_2);
        SceneController.pollEvent(event);
    }

    @Override
    public void startGame(Game game) {
        StartGameEvent event = new StartGameEvent();
        event.setGame(game);
        SceneController.pollEvent(event);
    }

    @Override
    public void addMessageChat(String text) {
        AddMessageChatEvent event = new AddMessageChatEvent();
        event.setText(text);
        SceneController.pollEvent(event);
    }

    @Override
    public void setStepCountdown(int stepCountdown) {
        SetStepCountdownEvent event = new SetStepCountdownEvent();
        event.setCountdown(stepCountdown);
        SceneController.pollEvent(event);
    }
}
