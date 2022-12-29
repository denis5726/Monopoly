package monopoly.ux.module.event;

import monopoly.ux.model.GameQuestion;

public class ShowDialogEvent extends UIEvent {
    private GameQuestion question;
    private int waitingTime;

    public GameQuestion getQuestion() {
        return question;
    }

    public void setQuestion(GameQuestion question) {
        this.question = question;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}
