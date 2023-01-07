package monopoly.ux.window;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.game.model.PlayerInfo;
import monopoly.ux.MonopolyApplication;

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

    public static void showWindow(PlayerInfo playerInfo) {
        PlayerInfoWindow window = (PlayerInfoWindow) load("playerInfo", "Информация об игроке");
        if (window != null) {
            window.init(playerInfo);
            window.show((Stage) Context.get("mainWindow"));
        }
    }

    private void init(PlayerInfo playerInfo) {
        playerName.setText(playerInfo.getName());
        List<Label> properties = new ArrayList<>();
        for (String key : playerInfo.getProperties().keySet()) {
            int amount = playerInfo.getProperties().get(key);
            String amountString;
            if (amount == 5) amountString = "Отель";
            else if (amount == 1) amountString = "1 дом";
            else if (amount == 0) amountString = "Нет домов";
            else amountString = amount + " дома";
            properties.add(new Label(key + " - " + amountString));
        }
        this.properties.getChildren().addAll(properties);

        addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> hide());

        balance.setText(String.valueOf(playerInfo.getBalance()));
        jailCardTitle.setText((playerInfo.isHaveJailCard()) ? "Есть карточка освобождения из тюрьмы" :
                "Нет карточки освобождения из тюрьмы");
    }

    @Override
    public void onClick() {
        hide();
        MonopolyApplication.removeAutoClosingInSubWindow(this);
    }
}
