package monopoly.ux.window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.ux.MonopolyApplication;

public class ActivityWindow extends SubWindow {
    @FXML
    public Button buyPropertyButton;
    @FXML
    public Button sellPropertyButton;
    @FXML
    public Button buyEscapeJailButton;

    public static void showDialog() {
        ActivityWindow window = (ActivityWindow) load("startActivity", "Виды сделок");
        if (window != null) {
            window.init();
            window.show((Stage) Context.get("mainWindow"));
        }
    }

    @Override
    public void onClick() {
        hide();
        MonopolyApplication.removeAutoClosingInSubWindow(this);
    }

    private void init() {

    }

    public void buyPropertyAction(ActionEvent event) {

    }

    public void sellPropertyAction(ActionEvent event) {

    }

    public void buyEscapeJailAction(ActionEvent event) {

    }
}
