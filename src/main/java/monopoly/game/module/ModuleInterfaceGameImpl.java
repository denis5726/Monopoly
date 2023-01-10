package monopoly.game.module;

import monopoly.context.Context;
import monopoly.game.Game;
import monopoly.game.IOLayer;
import monopoly.game.model.GameActivity;
import monopoly.game.model.GamePlayerInformation;
import monopoly.log.Logger;
import monopoly.ux.model.GameQuestion;
import monopoly.game.model.PropertyInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleInterfaceGameImpl implements ModuleInterfaceGame {
    @Override
    public void startActivity(GameActivity activity) {

    }

    @Override
    public GamePlayerInformation getPlayerInfo(String playerName) {
        return getIOLayer().getGamePlayerInformation(playerName);
    }

    @Override
    public List<String> getPropertyForSell(String playerName) {
        return getIOLayer().getPropertyForSell(playerName);
    }

    @Override
    public void placeAuctionBet(int money) {

    }

    @Override
    public void buyHouse(String propertyName) {

    }

    @Override
    public void sellHouse(String propertyName) {

    }

    @Override
    public void mortgageProperty(String propertyName) {

    }

    @Override
    public PropertyInformation getPropertyInformation(String name) {
        return getIOLayer().getPropertyInformation(name);
    }

    @Override
    public boolean haveJailCard(String playerName) {
        return getIOLayer().havePlayerJailCard(playerName);
    }

    @Override
    public void payForOutOfJail() {

    }

    @Override
    public void useCartForOutOfJail() {

    }

    @Override
    public void sendResponse(GameQuestion question) {
        Logger.trace("Type question: " + question.getType().toString() + ", Response: " + question.isChoose() + " "
            + question.getPropertyInformation());
        getIOLayer().sendResponse(question);
    }

    @Override
    public void exit() {

    }

    private IOLayer getIOLayer() {
        return ((Game) Context.get("currentGame")).getIOLayer();
    }
}
