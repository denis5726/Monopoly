package monopoly.ux.window;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.game.model.PlayerInfo;
import monopoly.log.Logger;
import monopoly.ux.controller.SceneController;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfoWindow extends Popup {
    @FXML
    public Label playerName;
    @FXML
    public Label balance;
    @FXML
    public Label jailCardTitle;
    @FXML
    public VBox properties;
    private static PlayerInfoWindow instance;
    private static Scene scene;
    private static PlayerInfo playerInfo;

    public PlayerInfoWindow() {
        instance = this;
    }

    public static PlayerInfoWindow getInstance() {
        instance.init();
        return instance;
    }

    public static void load(PlayerInfo playerInfo) {
        scene = SceneController.getScene("playerInfo");
        PlayerInfoWindow.playerInfo = playerInfo;
    }

    private void init() {
        if (scene != null) getContent().add(scene.getRoot());
        else Logger.error("Player info window file not found");

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
        balance.setText(String.valueOf(playerInfo.getBalance()));
        jailCardTitle.setText((playerInfo.isHaveJailCard()) ? "Есть карточка освобождения из тюрьмы" :
                "Нет карточки освобождения из тюрьмы");

        show((Stage) Context.get("mainWindow"));
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        hide();
    }
}
