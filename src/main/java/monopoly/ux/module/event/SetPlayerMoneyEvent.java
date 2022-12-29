package monopoly.ux.module.event;

import monopoly.ux.model.GamePlayer;

public class SetPlayerMoneyEvent extends UIEvent {
    private GamePlayer player;
    private int money;

    public GamePlayer getPlayer() {
        return player;
    }

    public void setPlayer(GamePlayer player) {
        this.player = player;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
