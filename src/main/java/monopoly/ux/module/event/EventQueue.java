package monopoly.ux.module.event;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class EventQueue {
    private static final Node node = new Button();

    public static <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<? super T> handler) {
        node.addEventHandler(eventType, handler);
    }

    public static void handleEvent(Event event) {
        Event.fireEvent(node, event);
    }
}
