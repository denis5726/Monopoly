package monopoly.ux.module.event;

public class AddLogEvent extends UIEvent {
    private String player;
    private String text;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
