package monopoly.ux.module.event;

public class SetStepCountdownEvent extends UIEvent {
    private int countdown;

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }
}
