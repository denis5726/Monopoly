package monopoly.ux;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import monopoly.context.Context;
import monopoly.game.module.ModuleInterfaceGameImpl;
import monopoly.log.Logger;
import monopoly.net.module.ModuleInterfaceNetImpl;
import monopoly.ux.controller.SceneController;
import monopoly.ux.model.Game;
import monopoly.ux.model.GamePlayer;
import monopoly.ux.model.Player;
import monopoly.ux.module.ModuleInterfaceUI;
import monopoly.ux.module.ModuleInterfaceUIImpl;
import monopoly.settings.SettingsContainer;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonopolyApplication extends Application {
    private static Stage stage;
    private static boolean running = true;
    private static SceneController currentScene;

    @Override
    public void start(Stage stage) {
        MonopolyApplication.stage = stage;

        Logger.trace(Font.loadFont(loadResource("fonts/kabelctt.ttf").
                toExternalForm(), 10).getName());

        SettingsContainer.load();

        loadApplicationContext();

        stage.setTitle("Monopoly");
        stage.setWidth(1300);
        stage.setHeight(770);
        stage.setMinWidth(770);
        stage.setMinHeight(570);

        addDebugSystem();

        ChangeListener<? super Number> resizeListener = (observableValue, oldValue, newValue) -> {
            currentScene.onResize();
        };

        stage.widthProperty().addListener(resizeListener);
        stage.heightProperty().addListener(resizeListener);

        setScene("mainMenu", new SceneContext());

        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, (event) -> close());

        stage.show();
    }

    private void addDebugSystem() {
        String[] playersNames = new String[] {
                "denis57", "niktug", "NaGiBaToR228",
                "SHaRIT.pro", "luckyCoban", "ryzhenkov",
                "semenGatchinov", "aue228", "capitalist1337",
                "fredMonopolist"
        };

        String[] log = new String[] {
                "купил дом на Арбате", "помог дагестанским учёным разработать очко барану",
                "продал почку за долги", "поддержал спецоперацию на Украине",
                "написал Монополию на JavaFX", "был умыт в труху"
        };

        String[] messages = new String[] {
                "привет", "есть косарь до понедельника?",
                "крутите кейсы в LuckyCoban", "есть у кого 7 лаба по БД?",
                "дом на Маяковского по чём?", "ищу девушку 11 лет",
                "го обмен домами не наёб"
        };

        List<String> players = new ArrayList<>(Arrays.asList(playersNames));
        List<String> playersInGame = new ArrayList<>();

        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            String code = event.getText();

            if (!event.isControlDown()) return;

            ModuleInterfaceUI moduleInterfaceUI = (ModuleInterfaceUI) Context.get("moduleInterfaceUI");
            switch (code) {
                case "a" -> {
                    if (players.size() == 0) return;
                    int index = (int) (Math.random() * players.size());
                    String name = players.get(index);
                    Player player = new Player();
                    player.setName(name);
                    moduleInterfaceUI.addPlayer(player);
                    players.remove(name);
                    playersInGame.add(name);
                }
                case "r" -> {
                    if (playersInGame.size() == 0) return;
                    int index = (int) (Math.random() * playersInGame.size());
                    String name = playersInGame.get(index);
                    Player player = new Player();
                    player.setName(name);
                    moduleInterfaceUI.removePlayer(player);
                    playersInGame.remove(name);
                    players.add(name);
                }
                case "s" -> {
                    Game game = new Game();
                    List<GamePlayer> gamePlayers = new ArrayList<>();
                    for (String playerName : playersInGame) {
                        GamePlayer gamePlayer = new GamePlayer();
                        gamePlayer.setName(playerName);
                        gamePlayers.add(gamePlayer);
                    }
                    game.setPlayers(gamePlayers);
                    moduleInterfaceUI.startGame(game);
                }
                case "l" -> {
                    if (playersInGame.size() == 0) return;
                    int indexPlayer = (int) (Math.random() * playersInGame.size());
                    int indexLog = (int) (Math.random() * log.length);
                    moduleInterfaceUI.addLog(playersInGame.get(indexPlayer), log[indexLog]);
                }
                case "m" -> {
                    if (playersInGame.size() == 0) return;
                    int indexPlayer = (int) (Math.random() * playersInGame.size());
                    int indexMessage = (int) (Math.random() * messages.length);
                    moduleInterfaceUI.addMessageChat(playersInGame.get(indexPlayer)
                            + ": " + messages[indexMessage]);
                }
            }
        });
    }

    public static void close() {
        SettingsContainer.storeAll();
        stage.close();
        running = false;
    }

    public static SceneController getCurrentScene() {
        return currentScene;
    }

    public static void setScene(String nameScene, SceneContext context) {
        if (currentScene != null) currentScene.onChangeScene();

        Scene scene = SceneController.getScene(nameScene);
        stage.setScene(scene);
        currentScene = (SceneController) Context.get(nameScene);
        currentScene.onCreateScene(context);

        if (currentScene == null) Logger.error("Scene with name " + nameScene + " is not found");
    }

    public static boolean isRunning() {
        return running;
    }

    public static void main(String[] args) {
        launch();
    }

    public static URL loadResource(String path) {
        return MonopolyApplication.class.getResource(path);
    }

    private static void loadApplicationContext() {
        Context.put("mainWindow", stage);
        Context.put("moduleInterfaceUI", new ModuleInterfaceUIImpl());
        Context.put("moduleInterfaceGame", new ModuleInterfaceGameImpl());
        Context.put("moduleInterfaceNet", new ModuleInterfaceNetImpl());
    }
}