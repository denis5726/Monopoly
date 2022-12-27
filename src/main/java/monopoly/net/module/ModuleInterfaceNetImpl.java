package monopoly.net.module;

import monopoly.settings.SettingsContainer;
import monopoly.ux.model.CreatedGame;
import monopoly.ux.model.GamePlayer;

import java.util.ArrayList;
import java.util.List;

public class ModuleInterfaceNetImpl implements ModuleInterfaceNet {
    @Override
    public String createGame(CreatedGame createdGame) {
        if (Math.random() > 0.5) return "Success";
        else return "Игра с таким именем уже создана.";
    }

    @Override
    public String login(String login, String password) {
        if (Math.random() > 0.5) {
            SettingsContainer.setLogin(login);
            SettingsContainer.setPassword(password);
            return "Success";
        }
        else if (Math.random() > 0.5) {
            return "Неправильный пароль";
        }
        else return "Игрок не найден";
    }

    @Override
    public String register(String login, String password) {
        return null;
    }

    @Override
    public List<CreatedGame> getConnectedGames() {
        List<CreatedGame> list = new ArrayList<>();

        int listSize = (int) (Math.random() * 30);
        for (int i = 0; i < listSize; i++) {
            CreatedGame game = new CreatedGame();
            game.setTitle("Title " + i);
            game.setCheckPassword(Math.random() > 0.5);
            if (game.isCheckPassword()) game.setPassword("password");
            game.setPlayersNum((int) (Math.random() * 3 + 2));
            game.setWaitingTime((int) (Math.random() * 25 + 5));
            game.setStepTime((int) (Math.random() * 10 + 5));

            list.add(game);
        }

        return list;
    }

    @Override
    public String connectToGame(CreatedGame createdGame) {
        String response;
        if (Math.random() > 0.5) response = "Error";
        else response = "Success";
        return response;
    }

    @Override
    public List<GamePlayer> getPlayersList(CreatedGame createdGame) {
        int size = (int) (Math.random() * (createdGame.getPlayersNum() + 1));
        List<GamePlayer> playerList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            GamePlayer gamePlayer = new GamePlayer();
            gamePlayer.setName("Player " + i);
            playerList.add(gamePlayer);
        }

        return playerList;
    }

    @Override
    public void leaveGameQueue() {

    }
}
