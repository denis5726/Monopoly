package monopoly.game.module;

public class GameActivity {
    private GameActivityType type;
    private String to;
    private String property;

    public GameActivityType getType() {
        return type;
    }

    public void setType(GameActivityType type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
