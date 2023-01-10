package monopoly.game.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GamePlayer {
    private String name;
    private int balance;
    private final Map<Property, Integer> properties;
    private boolean haveJailCard;
    private int position;

    public GamePlayer(String name) {
        this.name = name;
        properties = new HashMap<>();
        balance = 1500;
        haveJailCard = false;
        position = 0;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Map<Property, Integer> getProperties() {
        return properties;
    }

    public void addProperty(Property property, int status) {
        properties.put(property, status);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GamePlayerInformation getGamePlayerInformation() {
        GamePlayerInformation gamePlayerInformation = new GamePlayerInformation();
        gamePlayerInformation.setName(name);
        gamePlayerInformation.setBalance(balance);
        gamePlayerInformation.setHaveJailCard(haveJailCard);
        Map<String, Integer> playerProperties = new HashMap<>();
        for (Property property : properties.keySet()) {
            playerProperties.put(property.getPropertyInformation().getName(),
                    properties.get(property));
        }
        gamePlayerInformation.setProperties(playerProperties);
        return gamePlayerInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePlayer that = (GamePlayer) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
