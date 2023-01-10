package monopoly.ux.module;

import monopoly.ux.model.*;

import java.util.List;

public interface ModuleInterfaceUI {

    void addPlayer(Player player);

    void setPlayers(List<Player> players);

    void removePlayer(Player player);

    void addGame(CreatedGame createdGame);

    void setGames(List<CreatedGame> gameList);

    void removeGame(CreatedGame createdGame);

    void setPlayerMoney(String playerName, int money);

    void removePlayerTo(String playerName, int position);

    void setHomeNum(int position, int num);

    void showDialog(GameQuestion question, int waitingTime);

    void setInJail(boolean inJail);

    void setStepCountdown(int stepCountdown);

    void showDices(int value_1, int value_2);

    void addLog(String player, String text);

    void addMessageChat(String text);

    void setNextStep(String playerName);

    void setProperty(String name, int amount);

    void startGame(UIGame UIGame);
}
