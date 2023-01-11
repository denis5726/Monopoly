package monopoly.ux.window;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.game.module.GamePlayerInformation;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfoWindow extends SubWindow {
    @FXML
    public Label playerName;
    @FXML
    public Label balance;
    @FXML
    public Label jailCardTitle;
    @FXML
    public VBox properties;

    public static void showWindow(GamePlayerInformation gamePlayerInformation) {
        PlayerInfoWindow window = (PlayerInfoWindow) load("playerInfo");
        if (window != null) {
            window.init(gamePlayerInformation);
            window.show((Stage) Context.get("mainWindow"));
        }
    }

    private void init(GamePlayerInformation gamePlayerInformation) {
        setTitle("Информация об игроке");
        playerName.setText(gamePlayerInformation.getName());
        List<Label> properties = new ArrayList<>();
        for (String key : gamePlayerInformation.getProperties().keySet()) {
            int amount = gamePlayerInformation.getProperties().get(key);
            String amountString;
            if (amount == 5) amountString = "Отель";
            else if (amount == 1) amountString = "1 дом";
            else if (amount == 0) amountString = "Нет домов";
            else amountString = amount + " дома";
            Label label = new Label(key + " - " + amountString);
            label.getStyleClass().add("property");
            properties.add(label);
        }

        this.properties.getChildren().addAll(properties);

        addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> hide());

        balance.setText(gamePlayerInformation.getBalance() + "$");
        jailCardTitle.setText((gamePlayerInformation.isHaveJailCard()) ? "Есть карточка освобождения из тюрьмы" :
                "Нет карточки освобождения из тюрьмы");
    }
}
