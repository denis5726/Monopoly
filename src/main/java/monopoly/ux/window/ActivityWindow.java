package monopoly.ux.window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import monopoly.context.Context;
import monopoly.game.module.GameActivity;
import monopoly.game.module.GameActivityType;
import monopoly.game.module.ModuleInterfaceGame;
import monopoly.ux.MonopolyApplication;
import monopoly.ux.controller.SceneController;
import monopoly.ux.module.UIPlayer;

import java.util.List;
import java.util.Map;

public class ActivityWindow extends SubWindow {
    @FXML
    public Button buyPropertyButton;
    @FXML
    public Button sellPropertyButton;
    @FXML
    public Button buyEscapeJailButton;
    private static List<UIPlayer> playerList;

    public static void showDialog(List<UIPlayer> playerList) {
        ActivityWindow.playerList = playerList;
        ActivityWindow window = (ActivityWindow) load("startActivity");
        if (window != null) {
            window.init();
            window.show((Stage) Context.get("mainWindow"));
        }
    }

    private void init() {
        setTitle("Выбор вида сделок");
    }

    public void buyPropertyAction(ActionEvent event) {
        showPlayersChoose(GameActivityType.BUY);
    }

    public void sellPropertyAction(ActionEvent event) {
        if (checkPlayerProperties(currentPlayer())) showPlayersChoose(GameActivityType.SELL);
    }

    private UIPlayer currentPlayer() {
        return playerList.stream().filter(UIPlayer::isCurrent).toList().get(0);
    }

    private boolean checkPlayerProperties(UIPlayer player) {
        List<String> properties = ((ModuleInterfaceGame)Context.get("moduleInterfaceGame"))
                .getPropertyForSell(player.getName());
        if (properties == null) {
            String text;
            if (player.isCurrent()) text = "У вас нет подходящего участка для продажи";
            else text = "У " + player.getName() + " нет подходящего участка для покупки";
            showLabel(text);
            return false;
        }
        return true;
    }

    private void showLabel(String text) {
        VBox vBox = new VBox();
        vBox.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        vBox.setAlignment(Pos.CENTER);
        vBox.getStylesheets().add(MonopolyApplication.loadResource("styles/startActivity.css").toExternalForm());
        Text t = new Text(text);
        t.getStyleClass().add("text-sub");
        t.setWrappingWidth(0.816 * WINDOW_WIDTH);
        t.setTextAlignment(TextAlignment.CENTER);
        vBox.getChildren().add(t);
        setContent(vBox);
    }

    private UIPlayer findByName(String name) {
        return playerList.stream().filter((obj) -> obj.getName().equals(name)).toList().get(0);
    }

    private void showPlayersChoose(GameActivityType type) {
        Scene scene = SceneController.getScene("choosePlayer");
        setTitle("Выбор игрока");
        if (scene == null) return;
        setContent(scene.getRoot());

        for (int i = 0; i < playerList.size(); i++) {
            Button button = (Button)((VBox)scene.getRoot()).getChildren().get(i);
            button.setText(playerList.get(i).getName());
            button.addEventHandler(ActionEvent.ACTION, (e) -> {
                ModuleInterfaceGame moduleInterfaceGame = (ModuleInterfaceGame) Context.get("moduleInterfaceGame");
                String playerName;
                if (type == GameActivityType.BUY) {
                    playerName = button.getText();
                    if (!checkPlayerProperties(findByName(playerName))) return;
                }
                else {
                    playerName = currentPlayer().getName();
                }
                showPropertyChoose(moduleInterfaceGame.getPlayerInfo(playerName).getProperties(), type);
            });
        }

        for (int i = playerList.size(); i < 4; i++) {
            ((VBox)scene.getRoot()).getChildren().get(i).setVisible(false);
        }
    }

    private void showPropertyChoose(Map<String, Integer> properties, GameActivityType gameActivityType) {
        setTitle("Выбор недвижимости");
        Scene scene = SceneController.getScene("chooseProperty");
        if (scene == null) return;
        VBox vBox = (VBox)((ScrollPane)((VBox)scene.getRoot()).getChildren().get(0)).getContent();

        for (String name : properties.keySet()) {
            Button button = new Button(name);
            button.setPrefWidth(0.816 * WINDOW_WIDTH);
            button.getStylesheets().add(MonopolyApplication.loadResource("styles/main.css").toExternalForm());
            button.addEventHandler(ActionEvent.ACTION, (event) -> {
                ModuleInterfaceGame moduleInterfaceGame = (ModuleInterfaceGame) Context.get("moduleInterfaceGame");
                GameActivity gameActivity = new GameActivity();
                gameActivity.setType(gameActivityType);
                gameActivity.setProperty(button.getText());
                moduleInterfaceGame.startActivity(gameActivity);
                hide();
            });
            vBox.getChildren().add(button);
            setContent(vBox);
        }
    }

    public void buyEscapeJailAction(ActionEvent event) {
        Scene scene = SceneController.getScene("choosePlayer");
        if (scene == null) return;
        setContent(scene.getRoot());

        ModuleInterfaceGame moduleInterfaceGame = (ModuleInterfaceGame) Context.get("moduleInterfaceGame");

        int buttonIndex = 0;

        for (UIPlayer player : playerList) {
            if (moduleInterfaceGame.haveJailCard(player.getName())) {
                Button button = (Button) ((VBox) scene.getRoot()).getChildren().get(buttonIndex);
                button.setText(player.getName());
                button.addEventHandler(ActionEvent.ACTION, (e) -> {
                    GameActivity gameActivity = new GameActivity();
                    gameActivity.setType(GameActivityType.BUY_JAIL_CARD);
                    gameActivity.setTo(button.getText());
                    moduleInterfaceGame.startActivity(gameActivity);
                    hide();
                });
                buttonIndex++;
            }
        }

        for (int i = buttonIndex; i < 4; i++) {
            ((VBox)scene.getRoot()).getChildren().get(i).setVisible(false);
        }
    }
}
