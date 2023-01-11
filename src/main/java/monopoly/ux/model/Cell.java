package monopoly.ux.model;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import monopoly.ux.module.UIPlayer;

import java.util.ArrayList;
import java.util.List;

public class Cell extends AnchorPane {
    private double prefWidth;
    private double prefHeight;
    private final List<UIPlayer> players;
    private int homeNum;
    public Cell(Pane parent) {
        parent.getChildren().add(this);
        players = new ArrayList<>();
    }

    public void addPlayer(UIPlayer player) {
        if (players.contains(player)) return;
        players.add(player);
        getChildren().clear();
        updateHomeNum();
        updateChildrenList();
    }

    public void removePlayer(UIPlayer player) {
        players.remove(player);
        getChildren().clear();
        updateHomeNum();
        updateChildrenList();
    }

    public int getHomeNum() {
        return homeNum;
    }

    private void updateHomeNum() {
        if (homeNum == 5) {
            createHotel();
            return;
        }
        for (int i = 1; i <= homeNum; i++) {
            createHouse(i);
        }
    }

    private void createHotel() {
        Polygon polygon = new Polygon();
        Rectangle rect = new Rectangle();
        polygon.setFill(Color.RED);
        rect.setFill(Color.RED);
        placeHouse(getHotelPosition(), rect, polygon);
    }

    private void createHouse(int index) {
        Polygon polygon = new Polygon();
        Rectangle rect = new Rectangle();
        polygon.setFill(Color.GREEN);
        rect.setFill(Color.GREEN);
        placeHouse(getHousePosition(index), rect, polygon);
    }

    private void placeHouse(Point2D position, Rectangle rect, Polygon polygon) {
        rect.setLayoutX(position.getX());
        rect.setLayoutY(position.getY());
        rect.setWidth(getHomeSize());
        rect.setHeight(getHomeSize());

        getChildren().add(rect);
    }

    public void setHomeNum(int homeNum) {
        this.homeNum = homeNum;
        getChildren().clear();
        updateHomeNum();
        updateChildrenList();
    }

    private Point2D getHousePosition(int index) {
        double x;
        double y;
        double size = getHomeSize();
        if (index == 1 || index == 3) x = prefWidth / 2.0;
        else if (index == 2) x = prefWidth * 0.7;
        else x = prefWidth * 0.3;
        if (index == 2 || index == 4) y = prefHeight / 2.0;
        else if (index == 1) y = prefHeight * 0.3;
        else y = prefHeight * 0.7;
        x -= size / 2.0;
        y -= size / 2.0;
        return new Point2D(x, y);
    }

    private Point2D getHotelPosition() {
        return new Point2D(prefWidth / 2.0 - getHomeSize() / 2.0, prefHeight / 2.0 - getHomeSize() / 2.0);
    }
    
    private double getHomeSize() {
        return Math.min(prefWidth, prefHeight) / 5.6;
    }

    private void updateChildrenList() {
        for (int i = 0; i < players.size(); i++) {
            double hSpace = prefWidth / 3;
            double vSpace = prefHeight / 3;
            UIPlayer UIPlayer = players.get(i);
            UIPlayer.getCircle().setLayoutX(hSpace * (1 + i % 2));
            UIPlayer.getCircle().setLayoutY(vSpace * (1 + i / 2));
            getChildren().add(UIPlayer.getCircle());
        }
    }

    public void onResize(int position, double parentSize) {
        double cellHeight = 0.1312 * parentSize;
        double cellWidth = 0.0816 * parentSize;

        double x = 0, y = 0, w = cellWidth, h = cellHeight;

        if (position > 0 && position < 10) {
            x = cellHeight + (9 - position) * cellWidth;
            y = parentSize - cellHeight;
            w = cellWidth;
            h = cellHeight;
        }
        else if (position > 10 && position < 20) {
            x = 0;
            y = cellHeight + (19 - position) * cellWidth;
            w = cellHeight;
            h = cellWidth;
        }
        else if (position > 20 && position < 30) {
            x = cellHeight + (position - 21) * cellWidth;
            y = 0;
            w = cellWidth;
            h = cellHeight;
        }
        else if (position > 30 && position < 40) {
            x = parentSize - cellHeight;
            y = cellHeight + (position - 31) * cellWidth;
            w = cellHeight;
            h = cellWidth;
        }
        else if (position % 10 == 0) {
            w = cellHeight;
            h = cellHeight;
            if (position == 0) {
                x = parentSize - cellHeight;
                y = parentSize - cellHeight;
            }
            else if (position == 10) {
                x = 0;
                y = parentSize - cellHeight;
            }
            else if (position == 20) {
                x = 0;
                y = 0;
            }
            else {
                x = parentSize - cellHeight;
                y = 0;
            }
        }

        setLayoutX(x);
        setLayoutY(y);
        prefWidth(w);
        prefHeight(h);
        maxWidth(w);
        maxHeight(h);

        prefWidth = w;
        prefHeight = h;

        getChildren().clear();
        updateHomeNum();
        updateChildrenList();
    }
}
