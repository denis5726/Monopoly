module monopoly.monopoly {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens monopoly.ux to javafx.fxml;
    exports monopoly.ux;
    exports monopoly.ux.module;
    exports monopoly.ux.controller;
    exports monopoly.ux.model;
    exports monopoly.ux.module.event;
    exports monopoly.ux.window;
    exports monopoly.game.model;
    exports monopoly.ux.controller.game;
}