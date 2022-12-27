package monopoly.game.module;

import monopoly.game.model.GameActivity;
import monopoly.game.model.PlayerInfo;
import monopoly.ux.model.Player;

public interface ModuleInterfaceGame {

    void startActivity(GameActivity activity);

    PlayerInfo getPlayerInfo(Player player);

    void exit();
}
