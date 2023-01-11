package monopoly.ux.module;

import javafx.scene.shape.Circle;

import java.util.Objects;

public class UIPlayer {
    private String name;
    private int balance;
    private boolean current;
    private Circle circle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    @Override
    public String toString() {
        return "UIPlayer{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", current=" + current +
                ", circle=" + circle +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UIPlayer that = (UIPlayer) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, balance, current, circle);
    }
}
