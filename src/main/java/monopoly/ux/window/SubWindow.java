package monopoly.ux.window;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.log.Logger;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.controller.SceneController;

public class SubWindow extends Popup {
    private static SubWindow lastInstance;
    private VBox root;
    private Node content;
    public SubWindow() {
        lastInstance = this;
    }

    public void onClick() {

    }

    protected static SubWindow load(String name, String title) {
        Scene scene = SceneController.getScene(name);
        if (scene == null) {
            Logger.error("Scene not found");
            return null;
        }
        SubWindow instance = lastInstance;

        MonopolyApplication.addAutoClosingToSubWindow(instance);

        instance.root = new VBox();
        instance.root.setPrefSize(scene.getWidth(), scene.getHeight() + 30);
        instance.root.setAlignment(Pos.TOP_CENTER);
        instance.root.getStylesheets().add(MonopolyApplication.loadResource("styles/main.css").toExternalForm());
        instance.root.getStylesheets().add(MonopolyApplication.loadResource("styles/subWindow.css").toExternalForm());
        instance.root.getStyleClass().add("background-sub");
        Label label = new Label(title);
        label.getStyleClass().add("title");
        instance.root.getChildren().addAll(label, scene.getRoot());
        instance.content = scene.getRoot();
        instance.getContent().add(instance.root);
        return instance;
    }

    protected void setContent(Node node) {
        root.getChildren().remove(content);
        content = node;
        root.getChildren().add(content);
    }

    protected static void show(String name, String title) {
        SubWindow window = load(name, title);
        if (window != null) window.show((Stage) Context.get("mainWindow"));
    }
}
