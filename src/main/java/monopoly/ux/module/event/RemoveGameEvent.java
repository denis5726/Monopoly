package monopoly.ux.module.event;

import monopoly.ux.model.CreatedGame;

public class RemoveGameEvent extends UIEvent {
    private CreatedGame createdGame;

    public CreatedGame getCreatedGame() {
        return createdGame;
    }

    public void setCreatedGame(CreatedGame createdGame) {
        this.createdGame = createdGame;
    }
}
