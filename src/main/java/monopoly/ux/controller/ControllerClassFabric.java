package monopoly.ux.controller;

public class ControllerClassFabric {
    public static Class<?> getSceneController(String nameScene) {
        return switch (nameScene) {
            case "mainMenu" -> MainMenuController.class;
            case "createGame" -> CreateGameController.class;
            case "connectGame" -> ConnectGameController.class;
            case "settings" -> SettingsController.class;
            case "rules" -> RulesController.class;
            case "waitingPlayers" -> WaitingPlayersController.class;
            case "game" -> GameController.class;
            default -> throw new RuntimeException("Controller with name scene " + nameScene + " is not found");
        };
    }
}
