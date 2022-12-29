package monopoly.ux.module.event;

import monopoly.ux.model.CreatedGame;

import java.util.List;

public class SetGamesEvent extends UIEvent {
    private List<CreatedGame> games;

    public List<CreatedGame> getGames() {
        return games;
    }

    public void setGames(List<CreatedGame> games) {
        this.games = games;
    }
}
