package monopoly.game;

import monopoly.context.Context;
import monopoly.game.model.*;
import monopoly.net.module.ModuleInterfaceNet;
import monopoly.ux.controller.game.UIPlayer;
import monopoly.ux.model.GameQuestion;
import monopoly.ux.model.UIGame;
import monopoly.ux.module.ModuleInterfaceUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IOLayer {
    private String currentPlayerName;
    private final ModuleInterfaceUI moduleInterfaceUI;
    private final ModuleInterfaceNet moduleInterfaceNet;
    private final Game game;

    public IOLayer(Game game, UIGame uiGame) {
        this.game = game;

        for (UIPlayer uiPlayer: uiGame.getPlayers()) {
            if (uiPlayer.isCurrent()) {
                currentPlayerName = uiPlayer.getName();
                break;
            }
        }

        moduleInterfaceUI = (ModuleInterfaceUI) Context.get("moduleInterfaceUI");
        moduleInterfaceNet = (ModuleInterfaceNet) Context.get("moduleInterfaceNet");
    }

    public boolean isCurrent(GamePlayer gamePlayer) {
        return gamePlayer.getName().equals(currentPlayerName);
    }

    public void setPlayerMoney(GamePlayer gamePlayer, int money) {
        moduleInterfaceUI.setPlayerMoney(gamePlayer.getName(), money);
    }

    public void removePlayerTo(GamePlayer gamePlayer, int position) {
        moduleInterfaceUI.removePlayerTo(gamePlayer.getName(), position);
    }

    public void setHomeNum(int position, int num) {

    }

    public void sendResponse(GameQuestion question) {

    }

    public void sendQuestion(GamePlayer gamePlayer, GameQuestion question, int waitingTime) {
        if (isCurrent(gamePlayer)) moduleInterfaceUI.showDialog(question, waitingTime);
        else moduleInterfaceNet.sendQuestion(gamePlayer.getName(), question, waitingTime);
    }

    public void setInJail(boolean inJail) {
        moduleInterfaceUI.setInJail(inJail);
    }

    public void setStepCountdown(int stepCountdown) {

    }

    public void showDices(int value_1, int value_2) {
        moduleInterfaceUI.showDices(value_1, value_2);
    }

    public void setNextStep(GamePlayer gamePlayer) {
        moduleInterfaceUI.setNextStep(gamePlayer.getName());
    }

    public void setProperty(String name, int amount) {

    }

    public PropertyInformation getPropertyInformation(String name) {
        return PropertyData.getPropertyInformation(name);
    }

    public GamePlayerInformation getGamePlayerInformation(String playerName) {
        return findPlayerByName(playerName).getGamePlayerInformation();
    }

    public boolean havePlayerJailCard(String playerName) {
        return findPlayerByName(playerName).isHaveJailCard();
    }

    private GamePlayer findPlayerByName(String playerName) {
        return game.getPlayers().stream()
                .filter((obj) -> obj.getName().equals(playerName))
                .toList()
                .get(0);
    }

    public List<String> getPropertyForSell(String playerName) {
        List<String> propertyInformation = new ArrayList<>();
        Map<Property, Integer> properties = findPlayerByName(playerName).getProperties();
        for (Property property: properties.keySet()) {
            if (properties.get(property) == 0) propertyInformation.add(property.getPropertyInformation().getName());
        }
        return propertyInformation;
    }
}
