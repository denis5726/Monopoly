package monopoly.game.module;

import monopoly.game.model.GameActivity;
import monopoly.game.model.PlayerInfo;
import monopoly.ux.model.GamePlayer;

public interface ModuleInterfaceGame {

    void startActivity(GameActivity activity);

    PlayerInfo getPlayerInfo(GamePlayer player);

    void exit();
}
