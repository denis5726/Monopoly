package monopoly.ux.model;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GamePlayer {
    private String name;
    private final ObjectProperty<Map<String, Integer>> properties;
    private final IntegerProperty balance;
    private final BooleanProperty haveJailCard;
    private boolean current;

    public GamePlayer() {
        properties = new ObjectPropertyBase<>() {
            @Override
            public Object getBean() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }
        };
        properties.set(new HashMap<>());
        balance = new SimpleIntegerProperty();
        haveJailCard = new SimpleBooleanProperty();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProperty(String name, int amount) {
        properties.get().put(name, amount);
    }

    public void removeProperty(String name) {
        properties.get().remove(name);
    }

    public Map<String, Integer> getProperties() {
        return properties.get();
    }

    public int getBalance() {
        return balance.get();
    }

    public void setBalance(int balance) {
        this.balance.set(balance);
    }

    public boolean isHaveJailCard() {
        return haveJailCard.get();
    }

    public void setHaveJailCard(boolean haveJailCard) {
        this.haveJailCard.set(haveJailCard);
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public void addPropertyHandler(ChangeListener<Map<String, Integer>> listener) {
        properties.addListener(listener);
    }

    public void addBalanceHandler(ChangeListener<Number> listener) {
        balance.addListener(listener);
    }

    public void addHaveJailCardHandler(ChangeListener<Boolean> listener) {
        haveJailCard.addListener(listener);
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
