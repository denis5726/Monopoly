package monopoly.ux.window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.game.module.ModuleInterfaceGame;
import monopoly.ux.MonopolyApplication;

public class EscapeJailDialog extends SubWindow {
    @FXML
    public Button payMoneyButton;
    @FXML
    public Button useButton;

    public static void showDialog() {
        EscapeJailDialog window = (EscapeJailDialog) load("escapeJail");
        if (window != null) {
            window.setTitle("Освобождение из тюрьмы");
            window.show((Stage) Context.get("mainWindow"));
        }
    }

    public void payMoneyAction(ActionEvent event) {
        ModuleInterfaceGame moduleInterfaceGame = (ModuleInterfaceGame) Context.get("moduleInterfaceGame");
        moduleInterfaceGame.payForOutOfJail();
        hide();
    }

    public void useAction(ActionEvent event) {
        ModuleInterfaceGame moduleInterfaceGame = (ModuleInterfaceGame) Context.get("moduleInterfaceGame");
        moduleInterfaceGame.useCartForOutOfJail();
        hide();
    }
}
