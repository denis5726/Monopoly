package monopoly.game.module;

import monopoly.game.model.GameActivity;
import monopoly.game.model.PlayerInfo;
import monopoly.ux.model.GamePlayer;
import monopoly.ux.model.GameQuestion;
import monopoly.game.model.PropertyInformation;

import java.util.HashMap;
import java.util.Map;

public class ModuleInterfaceGameImpl implements ModuleInterfaceGame {
    @Override
    public void startActivity(GameActivity activity) {

    }

    @Override
    public PlayerInfo getPlayerInfo(GamePlayer player) {
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setName(player.getName());
        playerInfo.setHaveJailCard(Math.random() > 0.3);
        playerInfo.setBalance((int) (Math.random() * 100000));
        Map<String, Integer> properties = new HashMap<>();
        properties.put("Нагатинская улица", 1);
        properties.put("Малая Бронная", 0);
        properties.put("улица Огарёва", 3);
        playerInfo.setProperties(properties);

        return playerInfo;
    }

    @Override
    public void placeAuctionBet(int money) {

    }

    @Override
    public void buyHouse(String propertyName) {

    }

    @Override
    public void sellHouse(String propertyName) {

    }

    @Override
    public void mortgageProperty(String propertyName) {

    }

    @Override
    public PropertyInformation getPropertyInformation(String name) {
        return null;
    }

    @Override
    public void payForOutOfJail() {

    }

    @Override
    public void useCartForOutOfJail() {

    }

    @Override
    public void sendResponse(GameQuestion question) {

    }

    @Override
    public void exit() {

    }
}
