package monopoly.ux.controller.game;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import monopoly.ux.MonopolyApplication;

public class Dice extends AnchorPane {
    private final double size;
    private int value;
    private final ImageView imageView;

    public Dice(Pane parent, int number) {
        size = parent.getWidth() * 0.1;
        if (number == 0) {
            setLayoutX(parent.getWidth() / 2.0 - size * 1.7);
            setLayoutY(parent.getHeight() / 2.0 - size * 1.7);
        }
        else {
            setLayoutX(parent.getWidth() / 2.0 + size * 0.7);
            setLayoutY(parent.getHeight() / 2.0 + size * 0.7);
        }
        setPrefSize(size, size);
        imageView = new ImageView();
        imageView.setImage(new Image(MonopolyApplication.loadResource("images/dice.png").toExternalForm()));
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        parent.getChildren().add(this);
    }

    private void initChildren() {
        getChildren().clear();
        getChildren().add(imageView);
        Circle[] circles = new Circle[value];
        for (int i = 0; i < circles.length; i++) {
            circles[i] = new Circle();
            circles[i].setRadius(size * 0.1);
            circles[i].setFill(new Color(0.1, 0.1, 0.1, 1));
            getChildren().add(circles[i]);
        }

        double w, h;
        w = getPrefWidth();
        h = getPrefHeight();

        double squareVertexPos = w * 0.237;

        if (value == 1) {
            circles[0].setCenterX(w / 2);
            circles[0].setCenterY(h / 2);
        }
        else if (value == 2) {
            circles[0].setCenterX(squareVertexPos);
            circles[0].setCenterY(squareVertexPos);
            circles[1].setCenterX(w - squareVertexPos);
            circles[1].setCenterY(h - squareVertexPos);
        }
        else if (value == 3) {
            circles[0].setCenterX(squareVertexPos);
            circles[0].setCenterY(squareVertexPos);
            circles[1].setCenterX(w / 2);
            circles[1].setCenterY(h / 2);
            circles[2].setCenterX(w - squareVertexPos);
            circles[2].setCenterY(h - squareVertexPos);
        }
        else if (value == 4 || value == 5) {
            circles[0].setCenterX(squareVertexPos);
            circles[0].setCenterY(squareVertexPos);
            circles[1].setCenterX(w - squareVertexPos);
            circles[1].setCenterY(squareVertexPos);
            circles[2].setCenterX(w - squareVertexPos);
            circles[2].setCenterY(h - squareVertexPos);
            circles[3].setCenterX(squareVertexPos);
            circles[3].setCenterY(h - squareVertexPos);
            if (value == 5) {
                circles[4].setCenterX(w / 2.0);
                circles[4].setCenterY(h / 2.0);
            }
        }
        else {
            circles[0].setCenterX(squareVertexPos);
            circles[0].setCenterY(squareVertexPos);
            circles[1].setCenterX(w - squareVertexPos);
            circles[1].setCenterY(squareVertexPos);
            circles[2].setCenterX(squareVertexPos);
            circles[2].setCenterY(h / 2.0);
            circles[3].setCenterX(w - squareVertexPos);
            circles[3].setCenterY(h / 2.0);
            circles[4].setCenterX(squareVertexPos);
            circles[4].setCenterY(h - squareVertexPos);
            circles[5].setCenterX(w - squareVertexPos);
            circles[5].setCenterY(h - squareVertexPos);
        }
    }

    public void startValue(int v) {
        long stopTime = System.currentTimeMillis() + (long)(Math.random() * 1700);
        value = v;
        int startValue = value;

        Service<Integer> animation = new Service<>() {
            private final double dv = 17;
            private final long rotateTime = 17;
            private final int changeRateRotate = 10;
            private long rotatePreviousTime = System.currentTimeMillis();
            private long changePreviousTime = System.currentTimeMillis();
            private double a = 0.0;
            @Override
            protected Task<Integer> createTask() {
                return new Task<>() {
                    @Override
                    protected Integer call() throws Exception {
                        long rotateDiff = System.currentTimeMillis() - rotatePreviousTime;
                        long changeDiff = System.currentTimeMillis() - changePreviousTime;
                        int v = value;
                        if (changeDiff >= changeRateRotate * rotateTime) {
                            v = 1 + (int)(Math.random() * 6);
                            changePreviousTime = System.currentTimeMillis();
                        }
                        a += dv * rotateDiff / rotateTime;
                        setRotate(a);
                        rotatePreviousTime = System.currentTimeMillis();
                        Thread.sleep(10);
                        return v;
                    }
                };
            }
        };

        animation.setOnSucceeded((event) -> {
            if (animation.getValue() != value) {
                value = animation.getValue();
                initChildren();
            }
            if (MonopolyApplication.isRunning()
                && System.currentTimeMillis() < stopTime) animation.restart();
            else {
                value = startValue;
                initChildren();
            }
        });

        animation.start();
    }
}
