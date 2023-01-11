package monopoly.game;

import monopoly.context.Context;
import monopoly.game.model.*;
import monopoly.game.module.GamePlayerInformation;
import monopoly.game.module.GameQuestionType;
import monopoly.game.module.PropertyInformation;
import monopoly.log.Logger;
import monopoly.net.module.ModuleInterfaceNet;
import monopoly.ux.module.UIPlayer;
import monopoly.game.module.GameQuestion;
import monopoly.ux.module.UIGame;
import monopoly.ux.module.ModuleInterfaceUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IOLayer {
    private String currentPlayerName;
    private final ModuleInterfaceUI moduleInterfaceUI;
    private final ModuleInterfaceNet moduleInterfaceNet;
    private final Game game;
    private Thread responseObserverThread;
    private Thread nextStepObserverThread;
    private int currentValue_1;
    private int currentValue_2;
    private GameQuestion currentResponse;
    private boolean dicesUpdated;

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

    private GameQuestion sendQuestion(GamePlayer gamePlayer, GameQuestion question, int waitingTime) {
        if (isCurrent(gamePlayer)) moduleInterfaceUI.showDialog(question, waitingTime);
        else moduleInterfaceNet.sendQuestion(gamePlayer.getName(), question, waitingTime);

        GameQuestion response = null;

        responseObserverThread = Thread.currentThread();

        try {
            Thread.sleep(waitingTime * 1000L);
        } catch (InterruptedException e) {
            response = currentResponse;
        }

        return response;
    }

    public boolean sendBuyConfirmation(GamePlayer gamePlayer, Property property, int waitingTime) {
        GameQuestion gameQuestion = new GameQuestion();
        gameQuestion.setType(GameQuestionType.BUY_CONFIRMATION);
        gameQuestion.setPropertyInformation(property.getPropertyInformation());

        GameQuestion response = sendQuestion(gamePlayer, gameQuestion, waitingTime);

        if (response == null) return false;
        else return response.isChoose();
    }

    public boolean sendAuctionConfirmation(GamePlayer gamePlayer, Property property, int waitingTime) {
        GameQuestion gameQuestion = new GameQuestion();
        gameQuestion.setType(GameQuestionType.AUCTION_CONFIRMATION);
        gameQuestion.setPropertyInformation(property.getPropertyInformation());

        GameQuestion response = sendQuestion(gamePlayer, gameQuestion, waitingTime);

        if (response == null) return false;
        else return response.isChoose();
    }

    public List<Property> sendMortgagePropertyChoosing(GamePlayer gamePlayer, int waitingTime) {
        List<PropertyInformation> propertyForMortgage = new ArrayList<>();
        for (Property property: gamePlayer.getProperties().keySet()) {
            propertyForMortgage.add(property.getPropertyInformation());
        }

        GameQuestion gameQuestion = new GameQuestion();
        gameQuestion.setType(GameQuestionType.MORTGAGE_PROPERTY_CHOOSING);
        gameQuestion.setPropertiesInformation(propertyForMortgage);

        GameQuestion response = sendQuestion(gamePlayer, gameQuestion, waitingTime);

        if (response == null) {
            response = new GameQuestion();
            response.setPropertiesInformation(game.getDefaultPropertiesForMortgage(propertyForMortgage));
        }

        List<Property> selectedPropertiesForMortgage = new ArrayList<>();

        for (Property property : gamePlayer.getProperties().keySet()) {
            if (response.getPropertiesInformation().contains(property.getPropertyInformation())) {
                selectedPropertiesForMortgage.add(property);
            }
        }

        return selectedPropertiesForMortgage;
    }

    public void setInJail(boolean inJail) {
        moduleInterfaceUI.setInJail(inJail);
    }

    public void waitToNextStep(int stepCountdown) {
        nextStepObserverThread = Thread.currentThread();

        try {
            while (stepCountdown > 0) {
                moduleInterfaceUI.setStepCountdown(stepCountdown);
                stepCountdown--;
                Thread.sleep(1000);
            }
        } catch (InterruptedException ignored) {

        }

        if (!dicesUpdated) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {

            }
        }

        if (!dicesUpdated) {
            Logger.error("Server connection lost");
            game.exit();
            return;
        }

        game.onNextStep(currentValue_1, currentValue_2);

        dicesUpdated = false;
    }

    public void setNextStepDices(int value_1, int value_2) {
        currentValue_1 = value_1;
        currentValue_2 = value_2;
        dicesUpdated = true;

        nextStepObserverThread.interrupt();

        moduleInterfaceUI.showDices(value_1, value_2);
    }

    public void setNextStep(GamePlayer gamePlayer) {
        moduleInterfaceUI.setNextStep(gamePlayer.getName());
    }

    public void setProperty(GamePlayer gamePlayer, Property property) {
        if (isCurrent(gamePlayer)) moduleInterfaceUI.setProperty(property.getPropertyInformation().getName(),
                property.getAmount());
        moduleInterfaceUI.setHomeNum(property.getPropertyInformation().getId(), property.getAmount());
    }

    public void sendResponse(GameQuestion question) {
        currentResponse = question;
        responseObserverThread.interrupt();
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
