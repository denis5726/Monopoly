package monopoly.ux.module.event;

public class SetHomeNumEvent extends UIEvent {
    private int position;
    private int num;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
