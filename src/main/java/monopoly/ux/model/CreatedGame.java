package monopoly.ux.model;

public class CreatedGame {
    private String title;
    private boolean checkPassword;
    private String password;
    private int playersNum;
    private int waitingTime;
    private int stepTime;

    public CreatedGame() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(boolean checkPassword) {
        this.checkPassword = checkPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPlayersNum() {
        return playersNum;
    }

    public void setPlayersNum(int playersNum) {
        this.playersNum = playersNum;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getStepTime() {
        return stepTime;
    }

    public void setStepTime(int stepTime) {
        this.stepTime = stepTime;
    }

    @Override
    public String toString() {
        return "CreatedGame{" +
                "title='" + title + '\'' +
                ", checkPassword=" + checkPassword +
                ", password='" + password + '\'' +
                ", playersNum=" + playersNum +
                ", waitingTime=" + waitingTime +
                ", stepTime=" + stepTime +
                '}';
    }
}
