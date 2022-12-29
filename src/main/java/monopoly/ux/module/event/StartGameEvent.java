package monopoly.ux.module.event;

import monopoly.ux.model.Game;

public class StartGameEvent extends UIEvent{
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
