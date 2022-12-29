package monopoly.ux.module.event;

import monopoly.ux.model.GamePlayer;

public class RemovePlayerToEvent extends UIEvent {
    private GamePlayer player;
    private int position;

    public GamePlayer getPlayer() {
        return player;
    }

    public void setPlayer(GamePlayer player) {
        this.player = player;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
