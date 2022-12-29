package monopoly.ux.module.event;

public class ShowDiceEvent extends UIEvent {
    private int value_1;
    private int value_2;

    public int getValue_1() {
        return value_1;
    }

    public void setValue_1(int value_1) {
        this.value_1 = value_1;
    }

    public int getValue_2() {
        return value_2;
    }

    public void setValue_2(int value_2) {
        this.value_2 = value_2;
    }
}
