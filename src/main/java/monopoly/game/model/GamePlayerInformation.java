package monopoly.game.model;

import java.util.Map;

public class GamePlayerInformation {
    private String name;
    private Map<String, Integer> properties;
    private int balance;
    private boolean haveJailCard;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Integer> properties) {
        this.properties = properties;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isHaveJailCard() {
        return haveJailCard;
    }

    public void setHaveJailCard(boolean haveJailCard) {
        this.haveJailCard = haveJailCard;
    }
}
