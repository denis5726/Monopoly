package monopoly.ux.window;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;

public class AlertWindowFabric {
    public static void showWarningAlertWithHeaderText(String title, String headerText, String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(text);

        alert.showAndWait();
    }
    public static void showWarningAlertWithoutHeaderText(String title,String text) {
        showWarningAlertWithHeaderText(title, null, text);
    }

    public static void showErrorAlert(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);

        alert.setContentText(text);
        alert.showAndWait();
    }
}
