package monopoly.game;

import monopoly.game.model.GamePlayer;
import monopoly.game.model.Property;
import monopoly.game.module.PropertyInformation;
import monopoly.ux.module.UIPlayer;
import monopoly.ux.module.UIGame;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final IOLayer IOLayer;
    private GamePlayer currentStepPlayer;

    private final List<GamePlayer> players;

    public Game(UIGame uiGame) {
        IOLayer = new IOLayer(this, uiGame);
        players = new ArrayList<>();
        for (UIPlayer uiPlayer : uiGame.getPlayers()) {
            players.add(new GamePlayer(uiPlayer.getName()));
        }
    }

    public void onNextStep(int value_1, int value_2) {

    }

    public void exit() {

    }

    public GamePlayer getCurrentStepPlayer() {
        return currentStepPlayer;
    }

    public monopoly.game.IOLayer getIOLayer() {
        return IOLayer;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public List<PropertyInformation> getDefaultPropertiesForMortgage(List<PropertyInformation> propertyInformationList) {
        return propertyInformationList;
    }
}
