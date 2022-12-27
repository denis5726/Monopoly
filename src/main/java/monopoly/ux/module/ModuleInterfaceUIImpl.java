package monopoly.ux.module;

import monopoly.ux.model.Game;
import monopoly.ux.model.GamePlayer;
import monopoly.ux.model.GameQuestion;
import monopoly.ux.model.Player;

import java.util.List;

public class ModuleInterfaceUIImpl implements ModuleInterfaceUI {
    @Override
    public void addPlayer(Player player) {

    }

    @Override
    public void setPlayers(List<Player> players) {

    }

    @Override
    public void removePlayer(Player player) {

    }

    @Override
    public void setPlayerMoney(GamePlayer gamePlayer, int money) {

    }

    @Override
    public void removePlayerTo(GamePlayer gamePlayer, int position) {

    }

    @Override
    public void setHomeNum(int position, int num) {

    }

    @Override
    public void addLog(String player, String text) {

    }

    @Override
    public String showDialog(GameQuestion question, int waitingTime) {

        return null;
    }

    @Override
    public void showDices(int value_1, int value_2) {

    }

    @Override
    public void startGame(Game game) {

    }

    @Override
    public void setStepCountdown(int stepCountdown) {

    }
}
