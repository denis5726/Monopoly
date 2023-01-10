package monopoly.net.module;

import monopoly.ux.model.CreatedGame;
import monopoly.ux.model.GameQuestion;

import java.util.List;

public interface ModuleInterfaceNet {

    String createGame(CreatedGame createdGame);

    String login(String login, String password);

    String register(String login, String password);

    List<CreatedGame> getConnectedGames();

    String connectToGame(CreatedGame createdGame);

    void sendChatMessage(String message);

    void sendQuestion(String playerName, GameQuestion gameQuestion, int waitingTime);

    void leaveGameQueue();
}
