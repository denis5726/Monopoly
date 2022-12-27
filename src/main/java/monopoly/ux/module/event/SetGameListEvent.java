package monopoly.ux.module.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import monopoly.ux.model.CreatedGame;

import java.util.List;

public class SetGameListEvent extends Event {
    private static final EventType<SetGameListEvent> eventType = new EventType<>("setGameListEvent");

    private List<CreatedGame> gameList;

    public SetGameListEvent(EventType<? extends Event> eventType) {
        super(SetGameListEvent.eventType);
    }

    public SetGameListEvent(Object o, EventTarget eventTarget, EventType<? extends Event> eventType) {
        super(o, eventTarget, SetGameListEvent.eventType);
    }

    public SetGameListEvent(List<CreatedGame> gameList) {
        super(SetGameListEvent.eventType);
        this.gameList = gameList;
    }

    public SetGameListEvent() {
        super(SetGameListEvent.eventType);
    }

    public static EventType<SetGameListEvent> getType() {
        return eventType;
    }

    public List<CreatedGame> getGameList() {
        return gameList;
    }
}
