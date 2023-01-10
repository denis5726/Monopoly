package monopoly.ux.window;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import monopoly.log.Logger;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.controller.SceneController;

public class SubWindow extends Popup implements AutoCloseableWindow {
    private static SubWindow lastInstance;
    private Pane root;
    private Node content;
    protected static final int WINDOW_WIDTH = 576;
    protected static final int WINDOW_HEIGHT = 301;

    public SubWindow() {
        lastInstance = this;
    }

    @Override
    public void onClick() {
        hide();
        MonopolyApplication.removeAutoClosingInSubWindow(this);
    }

    protected static SubWindow load(String name) {
        Scene scene = null;
        if (name == null) {
            new SubWindow();
        }
        else {
            scene = SceneController.getScene(name);
            if (scene == null) {
                Logger.error("Scene not found");
                return null;
            }
        }

        SubWindow instance = lastInstance;
        MonopolyApplication.addAutoClosingToSubWindow(instance);

        Scene windowScene = SceneController.getScene("dialog");
        if (windowScene == null) return null;

        instance.root = (Pane) windowScene.getRoot();
        ((HBox)((VBox)instance.root.getChildren().get(0))
                .getChildren().get(0))
                .getChildren().get(1).addEventHandler(ActionEvent.ACTION, (event) -> instance.hide());
        if (scene != null) instance.setContent(scene.getRoot());
        instance.getContent().add(instance.root);
        return instance;
    }

    protected static SubWindow loadWithoutContent() {
        return load(null);
    }

    protected void setText(String text) {
        Text t = new Text(text);
        t.getStyleClass().add("text");
        t.setWrappingWidth(0.816 * WINDOW_WIDTH);
        setContent(t);
    }

    protected void setContent(Node node) {
        VBox contentVBox = (VBox)((VBox)root.getChildren().get(0))
                .getChildren().get(1);
        contentVBox.getChildren().remove(content);
        content = node;
        contentVBox.getChildren().add(content);
        root.setPrefWidth(Math.max(contentVBox.getWidth(), WINDOW_WIDTH) + 24);
        root.setPrefHeight(Math.max(contentVBox.getHeight(), WINDOW_HEIGHT) + 201);
    }

    protected void setTitle(String title) {
        ((Label)((HBox)((HBox)((VBox)
                root
                .getChildren().get(0))
                .getChildren().get(0))
                .getChildren().get(0))
                .getChildren().get(0))
                .setText(title);
    }

    protected void addButton(Button button) {
        button.getStyleClass().add("dialog-button");
        ((HBox) ((VBox) root.getChildren().get(0)).getChildren().get(2)).getChildren().add(button);
    }
}
