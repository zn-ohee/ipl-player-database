module com.example.fx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires jdk.compiler;
    requires java.desktop;

    exports com.example.fx.controllers;
    opens com.example.fx.controllers to javafx.fxml;
    exports com.example.fx.models;
    opens com.example.fx.models to javafx.fxml;
    exports com.example.fx.database;
    opens com.example.fx.database to javafx.fxml;
    exports com.example.fx.server;
    opens com.example.fx.server to javafx.fxml;
    exports com.example.fx.utils;
    opens com.example.fx.utils to javafx.fxml;
    exports com.example.fx.client;
    opens com.example.fx.client to javafx.fxml;
}