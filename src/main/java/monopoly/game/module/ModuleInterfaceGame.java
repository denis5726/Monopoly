package monopoly.game.module;

import java.util.List;

public interface ModuleInterfaceGame {

    void startActivity(GameActivity activity);

    GamePlayerInformation getPlayerInfo(String playerName);

    void placeAuctionBet(int money);

    void buyHouse(String propertyName);

    void sellHouse(String propertyName);

    void mortgageProperty(String propertyName);

    PropertyInformation getPropertyInformation(String name);

    void payForOutOfJail();

    void useCartForOutOfJail();

    void sendResponse(GameQuestion question);

    boolean haveJailCard(String playerName);

    List<String> getPropertyForSell(String playerName);

    void exit();
}
