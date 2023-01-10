package monopoly.game;

import monopoly.game.model.GamePlayer;
import monopoly.ux.controller.game.UIPlayer;
import monopoly.ux.model.UIGame;

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

    public GamePlayer getCurrentStepPlayer() {
        return currentStepPlayer;
    }

    public monopoly.game.IOLayer getIOLayer() {
        return IOLayer;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }
}
