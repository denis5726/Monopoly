package monopoly.ux.controller.game;

import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import monopoly.game.model.PropertyColor;
import monopoly.game.model.PropertyInformation;

public class Card extends AnchorPane {
    private static final Font font = new Font("KabelCTT Medium", 12);
    private static final Font fontBold = new Font("KabelCTT Medium Bold", 1);
    private final PropertyInformation propertyInformation;
    private boolean selected;
    private final Rectangle card;

    public Card(PropertyInformation propertyInformation, double size) {
        this.propertyInformation = propertyInformation;
        selected = false;
        setPrefSize(size, 1.6 * size);
        double width = getPrefWidth();
        double height = getPrefHeight();
        double wrapperWidth = 0.882 * width;
        double wrapperHeight = 0.926 * height;
        double headerRectangleWidth = 0.824 * width;
        double headerRectangleHeight = 0.185 * height;
        double headerTitleHeight = 0.12 * headerRectangleHeight;
        double separatorLineY = 0.729 * height;
        double separatorLineWidth = 0.8 * wrapperWidth;
        double gridPane1Y = 0.294 * height;
        double gridPane1Height = 0.4 * height;
        double gridPane2Y = 0.747 * height;
        double titleY = 0.07 * width;
        double padding = 0.023 * width;
        double headerTitleFontSize = 0.04 * width;
        double titleFontSize = 0.067 * width;

        card = new Rectangle(0, 0, width, height);
        card.setStrokeWidth(3);
        card.setFill(Color.WHITE);

        Rectangle wrapper = new Rectangle((width - wrapperWidth) / 2, (height - wrapperHeight) / 2,
                wrapperWidth, wrapperHeight);
        wrapper.setFill(Color.WHITE);
        wrapper.setStroke(Color.BLACK);
        wrapper.setStrokeWidth(1);

        Rectangle headerRectangle = new Rectangle(wrapper.getX() +
                (wrapperWidth - headerRectangleWidth) / 2.0, wrapper.getY() +
                (wrapperWidth - headerRectangleWidth) / 2.0, headerRectangleWidth, headerRectangleHeight);
        headerRectangle.setFill(getColorByPropertyColor(propertyInformation.getColor()));
        headerRectangle.setStroke(Color.BLACK);
        headerRectangle.setStrokeWidth(1);

        Text headerTitle = new Text("КАРТОЧКА СОБСТВЕННИКА");
        headerTitle.setFill(Color.BLACK);
        headerTitle.setFont(new Font(fontBold.getName(), headerTitleFontSize));
        setFont(headerTitle, fontBold, headerTitleFontSize);
        headerTitle.setX(headerRectangle.getX() +
                (headerRectangle.getWidth() - headerTitle.getBoundsInLocal().getWidth()) / 2.0);
        headerTitle.setY(headerRectangle.getY() + 2 * headerTitleHeight);

        Text title = new Text(propertyInformation.getName());
        title.setFill(Color.BLACK);
        setFont(title, fontBold, titleFontSize);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setX(headerRectangle.getX() +
                (headerRectangle.getWidth() - headerTitle.getBoundsInLocal().getWidth()) / 2.0);
        title.setWrappingWidth(headerTitle.getBoundsInLocal().getWidth());
        title.setY(headerRectangle.getY() + 2.7 * headerTitleHeight + titleY);

        GridPane gridPane1 = new GridPane();
        gridPane1.setLayoutX(headerRectangle.getX());
        gridPane1.setLayoutY(gridPane1Y);

        gridPane1.setPadding(new Insets(0, padding, 0, padding));

        for (int i = 0; i < 6; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(gridPane1Height / 6);
            gridPane1.getRowConstraints().add(rowConstraints);
        }

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPrefWidth(0.553 * width);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPrefWidth(0.259 * width);
        gridPane1.getColumnConstraints().addAll(column1, column2);

        setInGrid(gridPane1, 0, "Рента", propertyInformation.getRent() + "$");
        setInGrid(gridPane1, 1, "C 1 домом", propertyInformation.getRent1House() + "$");
        setInGrid(gridPane1, 2, "C 2 домами", propertyInformation.getRent2House() + "$");
        setInGrid(gridPane1, 3, "C 3 домами", propertyInformation.getRent3House() + "$");
        setInGrid(gridPane1, 4, "C 4 домами", propertyInformation.getRent4House() + "$");
        setInGrid(gridPane1, 5, "C отелем", propertyInformation.getRentHotel() + "$");

        Line separatorLine = new Line();
        separatorLine.setStartX(wrapper.getX() + (wrapper.getWidth() - separatorLineWidth) / 2.0);
        separatorLine.setStartY(separatorLineY);
        separatorLine.setEndX(separatorLine.getStartX() + separatorLineWidth);
        separatorLine.setEndY(separatorLineY);
        separatorLine.setStrokeWidth(1);
        separatorLine.setFill(Color.BLACK);

        GridPane gridPane2 = new GridPane();
        gridPane2.setLayoutX(headerRectangle.getX());
        gridPane2.setLayoutY(gridPane2Y);

        gridPane2.setPadding(new Insets(0, padding, 0, padding));

        for (int i = 0; i < 3; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(gridPane1Height / 6);
            gridPane2.getRowConstraints().add(rowConstraints);
        }

        gridPane2.getColumnConstraints().addAll(column1, column2);

        setInGrid(gridPane2, 0, "Стоимость дома", propertyInformation.getHousePrice() + "$");
        setInGrid(gridPane2, 1, "Стоимость отеля", propertyInformation.getHotelPrice() + "$");
        setInGrid(gridPane2, 2, "Залог",  propertyInformation.getMortgagePrice() + "$");

        getChildren().addAll(card, wrapper, headerRectangle, headerTitle, title, separatorLine, gridPane1, gridPane2);
    }

    private static void setFont(Text text, Font font, double size) {
        text.setStyle("-fx-font: " + size + " '" + font.getName() + "', Arial;");
    }

    private void setInGrid(GridPane grid, int i, String text1, String text2) {
        Text t1 = new Text(text1);
        t1.setWrappingWidth(grid.getColumnConstraints().get(0).getPrefWidth());
        setFont(t1, font, 0.07 * getPrefWidth());
        Text t2 = new Text(text2);
        t2.setWrappingWidth(grid.getColumnConstraints().get(1).getPrefWidth() - grid.getPadding().getRight());
        t2.setTextAlignment(TextAlignment.RIGHT);
        setFont(t2, font, 0.07 * getPrefWidth());
        grid.addRow(i, t1, t2);
    }

    private Color getColorByPropertyColor(PropertyColor propertyColor) {
        return switch (propertyColor) {
            case BROWN -> Color.BROWN;
            case BLUE -> new Color(0.38, 0.7, 1, 1);
            case PINK -> new Color(1, 0.3, 0.3, 1);
            case ORANGE -> new Color(1, 0.47, 0, 1);
            case RED -> Color.RED;
            case YELLOW -> Color.YELLOW;
            case GREEN -> Color.GREEN;
            case DARKBLUE -> Color.DARKBLUE;
        };
    }

    public Card(PropertyInformation propertyInformation) {
        this(propertyInformation, 300);
    }

    public PropertyInformation getPropertyInformation() {
        return propertyInformation;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) card.setStroke(Color.RED);
        else card.setStroke(Color.WHITE);
    }
}
