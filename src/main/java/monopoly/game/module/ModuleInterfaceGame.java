package monopoly.game.module;

import monopoly.game.model.GameActivity;
import monopoly.game.model.PlayerInfo;
import monopoly.ux.model.GamePlayer;
import monopoly.ux.model.GameQuestion;
import monopoly.game.model.PropertyInformation;

public interface ModuleInterfaceGame {

    void startActivity(GameActivity activity);

    PlayerInfo getPlayerInfo(GamePlayer player);

    void placeAuctionBet(int money);

    void buyHouse(String propertyName);

    void sellHouse(String propertyName);

    void mortgageProperty(String propertyName);

    PropertyInformation getPropertyInformation(String name);

    void payForOutOfJail();

    void useCartForOutOfJail();

    void sendResponse(GameQuestion question);

    void exit();
}
