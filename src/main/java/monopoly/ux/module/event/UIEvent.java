package monopoly.ux.module.event;

import monopoly.ux.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UIEvent {
    private UIEventType type;
    private static final Map<UIEventType, UIEventHandler> handlers = new HashMap<>();
    private CreatedGame createdGame;
    private List<CreatedGame> createdGames;
    private Game game;
    private Player player;
    private GamePlayer gamePlayer;
    private List<Player> players;
    private String message;
    private String sender;
    private String actor;
    private int position;
    private int amount;
    private int time;
    private int value_1;
    private int value_2;
    private GameQuestion question;

    public UIEvent() {

    }

    public static void setHandler(UIEventType type, UIEventHandler handler) {
        handlers.put(type, handler);
    }

    public UIEventHandler getHandler() {
        return handlers.get(type);
    }

    public UIEventType getType() {
        return type;
    }

    public void setType(UIEventType type) {
        this.type = type;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public CreatedGame getCreatedGame() {
        return createdGame;
    }

    public void setCreatedGame(CreatedGame createdGame) {
        this.createdGame = createdGame;
    }

    public List<CreatedGame> getCreatedGames() {
        return createdGames;
    }

    public void setCreatedGames(List<CreatedGame> createdGames) {
        this.createdGames = createdGames;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getValue_1() {
        return value_1;
    }

    public void setValue_1(int value_1) {
        this.value_1 = value_1;
    }

    public int getValue_2() {
        return value_2;
    }

    public void setValue_2(int value_2) {
        this.value_2 = value_2;
    }

    public GameQuestion getQuestion() {
        return question;
    }

    public void setQuestion(GameQuestion question) {
        this.question = question;
    }
}
