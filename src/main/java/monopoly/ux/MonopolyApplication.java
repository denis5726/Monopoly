package monopoly.ux;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import monopoly.context.Context;
import monopoly.game.module.PropertyColor;
import monopoly.game.module.PropertyInformation;
import monopoly.game.module.PropertyType;
import monopoly.game.module.GameQuestion;
import monopoly.game.module.ModuleInterfaceGameImpl;
import monopoly.game.module.GameQuestionType;
import monopoly.log.Logger;
import monopoly.net.module.ModuleInterfaceNetImpl;
import monopoly.net.module.Player;
import monopoly.ux.controller.SceneController;
import monopoly.ux.module.UIGame;
import monopoly.ux.module.UIPlayer;
import monopoly.ux.module.ModuleInterfaceUI;
import monopoly.ux.module.ModuleInterfaceUIImpl;
import monopoly.settings.SettingsContainer;
import monopoly.ux.window.AutoCloseableWindow;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class MonopolyApplication extends Application {
    private static Stage stage;
    private static boolean running = true;
    private static SceneController currentScene;

    private static List<AutoCloseableWindow> autoCloseableSubWindows;
    private static boolean inc = true;

    @Override
    public void start(Stage stage) {
        MonopolyApplication.stage = stage;

        Logger.trace(Font.loadFont(loadResource("fonts/kabelctt.ttf").
                toExternalForm(), 10).getName());
        Logger.trace(Font.loadFont(loadResource("fonts/kabelcttBold.ttf").
                toExternalForm(), 10).getName());
        Logger.trace(Font.loadFont(loadResource("fonts/kabelLight.ttf").
                toExternalForm(), 10).getName());

        stage.getIcons().add(new Image(loadResource("icon.png").toExternalForm()));

        SettingsContainer.load();

        loadApplicationContext();

        stage.setTitle("Monopoly");
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setMinWidth(770);
        stage.setMinHeight(570);

        addDebugSystem();

        ChangeListener<? super Number> resizeListener = (observableValue, oldValue, newValue) -> {
            currentScene.onResize();
        };

        autoCloseableSubWindows = new ArrayList<>();

        stage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            for (int i = 0; i < autoCloseableSubWindows.size(); i++) {
                autoCloseableSubWindows.get(i).onClick();
            }
        });

        stage.widthProperty().addListener(resizeListener);
        stage.heightProperty().addListener(resizeListener);

        setScene("mainMenu", new SceneContext());

        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, (event) -> close());

        stage.show();
    }

    public static void addAutoClosingToSubWindow(AutoCloseableWindow autoCloseableWindow) {
        autoCloseableSubWindows.add(autoCloseableWindow);
    }

    public static void removeAutoClosingInSubWindow(AutoCloseableWindow autoCloseableWindow) {
        autoCloseableSubWindows.remove(autoCloseableWindow);
    }

    private void addDebugSystem() {
        String[] playersNames = new String[] {
                "denis57", "niktug", "NaGiBaToR228",
                "SHaRIT.pro", "luckyKoban", "ryzhenkov",
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

        String[] propertyNames = new String[] {
                "Mediter-Ranean Avenue", "Baltic Avenue",
                "Oriental Avenue", "Vermont Avenue",
                "Connecticut Avenue", "St. Charles Place",
                "Status Avenue"
        };

        List<String> players = new ArrayList<>(Arrays.asList(playersNames));
        List<String> playersInGame = new ArrayList<>();

        Consumer<PropertyInformation> randomPropertyInformation = (obj) -> {
            int nameIndex = (int) (Math.random() * propertyNames.length);
            obj.setName(propertyNames[nameIndex]);
            obj.setPrice((int) (Math.random() * 1000));
            obj.setHousePrice((int) (Math.random() * 200));
            obj.setHotelPrice((int) (Math.random() * 300));
            obj.setRent((int) (Math.random() * 100));
            obj.setRent1House((int) (Math.random() * 200));
            obj.setRent2House((int) (Math.random() * 300));
            obj.setRent3House((int) (Math.random() * 400));
            obj.setRent4House((int) (Math.random() * 500));
            obj.setRentHotel((int) (Math.random() * 1000));
            obj.setId((int) (Math.random() * 40));
            obj.setMortgagePrice((int) (Math.random() * 170));
            obj.setColor(PropertyColor.values()[(int) (Math.random() *
                    PropertyColor.values().length)]);
            obj.setType(PropertyType.values()[(int) (Math.random()
                    * PropertyType.values().length)]);
        };

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
                    UIGame UIGame = new UIGame();
                    List<UIPlayer> playersList = new ArrayList<>();
                    for (String playerName : playersInGame) {
                        UIPlayer uiPlayer = new UIPlayer();
                        uiPlayer.setName(playerName);
                        playersList.add(uiPlayer);
                    }
                    int currentPlayerIndex = (int) (Math.random() * playersList.size());
                    playersList.get(currentPlayerIndex).setCurrent(true);
                    UIGame.setPlayers(playersList);
                    moduleInterfaceUI.startGame(UIGame);
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
                case "b" -> {
                    if (playersInGame.size() == 0) return;
                    int indexPlayer = (int) (Math.random() * playersInGame.size());
                    moduleInterfaceUI.setPlayerMoney(playersInGame.get(indexPlayer), (int) (Math.random() * 100000));
                }
                case "g" -> {
                    if (playersInGame.size() == 0) return;
                    int indexPlayer = (int) (Math.random() * playersInGame.size());
                    int pos = (int) (Math.random() * 40);
                    moduleInterfaceUI.removePlayerTo(playersInGame.get(indexPlayer), pos);
                }
                case "t" -> {
                    int time = (int) (Math.random() * 107);
                    moduleInterfaceUI.setStepCountdown(time);
                }
                case "h" -> {
                    int position = (int) (Math.random() * 40);
                    int amount = (int)(Math.random() * 6);
                    Logger.trace("Set " + position + " " + amount + " home level");
                    moduleInterfaceUI.setHomeNum(position, amount);
                }
                case "p" -> {
                    String name = propertyNames[(int) (Math.random() * propertyNames.length)];
                    int amount = -1 + ((int)(Math.random() * 7));
                    moduleInterfaceUI.setProperty(name, amount);
                }
                case "d" -> {
                    int value_1 = 1 + (int)(Math.random() * 6);
                    int value_2 = 1 + (int)(Math.random() * 6);
                    moduleInterfaceUI.showDices(value_1, value_2);
                }
                case "j" -> {
                    boolean inJail = Math.random() > 0.5;
                    moduleInterfaceUI.setInJail(inJail);
                }
                case "c" -> {
                    GameQuestion question = new GameQuestion();
                    question.setType(GameQuestionType.valueOf(GameQuestionType.values()[(int) (Math.random() *
                                                GameQuestionType.values().length)].toString()));
                    if (question.getType() == GameQuestionType.BUY_CONFIRMATION) {
                        PropertyInformation propertyInformation = new PropertyInformation();
                        randomPropertyInformation.accept(propertyInformation);
                        question.setPropertyInformation(propertyInformation);
                    }
                    else if (question.getType() == GameQuestionType.AUCTION_CONFIRMATION) {
                        PropertyInformation propertyInformation = new PropertyInformation();
                        randomPropertyInformation.accept(propertyInformation);
                        question.setPropertyInformation(propertyInformation);
                    }
                    else {
                        int listSize = (int) (Math.random() * 28);
                        for (int i = 0; i < listSize; i++) {
                            PropertyInformation propertyInformation = new PropertyInformation();
                            randomPropertyInformation.accept(propertyInformation);
                            question.getPropertiesInformation().add(propertyInformation);
                        }
                    }

                    moduleInterfaceUI.showDialog(question, 10);
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

        if (inc) stage.setWidth(stage.getWidth() + 1);
        else stage.setWidth(stage.getWidth() - 1);
        inc = !inc;


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