package monopoly.ux.model;

import monopoly.ux.controller.game.UIPlayer;

import java.util.List;

public class UIGame {
    private List<UIPlayer> players;

    public List<UIPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<UIPlayer> players) {
        this.players = players;
    }
}
