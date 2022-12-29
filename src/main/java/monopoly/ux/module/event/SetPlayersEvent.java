package monopoly.ux.module.event;

import monopoly.ux.model.Player;

import java.util.List;

public class SetPlayersEvent extends UIEvent {
    private List<Player> players;

    public List<Player> getPlayer() {
        return players;
    }

    public void setPlayer(List<Player> players) {
        this.players = players;
    }
}
