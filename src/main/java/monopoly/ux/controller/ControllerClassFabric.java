package monopoly.ux.controller;

public class ControllerClassFabric {
    public static SceneController getSceneController(String nameScene) {
        return switch (nameScene) {
            case "mainMenu" -> new MainMenuController();
            case "createGame" -> new CreateGameController();
            case "connectGame" -> new ConnectGameController();
            case "settings" -> new SettingsController();
            case "rules" -> new RulesController();
            case "waitingPlayers" -> new WaitingPlayersController();
            case "game" -> new GameController();
            default -> throw new RuntimeException("Controller with name scene " + nameScene + " is not found");
        };
    }
}
