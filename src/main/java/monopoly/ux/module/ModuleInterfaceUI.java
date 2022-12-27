package monopoly.ux.module;

import monopoly.ux.model.*;

import java.util.List;

public interface ModuleInterfaceUI {

    void addPlayer(Player player);

    void setPlayers(List<Player> players);

    void removePlayer(Player player);

    void setPlayerMoney(GamePlayer gamePlayer, int money);

    void removePlayerTo(GamePlayer gamePlayer, int position);

    void setHomeNum(int position, int num);

    void addLog(String player, String text);

    String showDialog(GameQuestion question, int waitingTime);

    void setStepCountdown(int stepCountdown);

    void showDices(int value_1, int value_2);

    void startGame(Game game);
}
