package monopoly.ux.module.event;

import monopoly.ux.model.Player;

public class AddPlayerEvent extends UIEvent {
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
