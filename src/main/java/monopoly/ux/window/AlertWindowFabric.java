package monopoly.ux.window;

import javafx.scene.control.Alert;

public class AlertWindowFabric {
    public static void showErrorAlert(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);

        alert.setContentText(text);
        alert.showAndWait();
    }
}
