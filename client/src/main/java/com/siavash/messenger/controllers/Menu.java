package com.siavash.messenger.controllers;

import com.siavash.messenger.ScreenManager;
import com.siavash.messenger.Screens;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Created by sia on 6/27/16.
 */
public class Menu {
    @FXML
    private Label settings;
    @FXML
    private Label contacts;
    @FXML
    private Label about;

    @FXML
    private void initialize() {
        Stage stage = new Stage();
        ScreenManager manager = new ScreenManager();

        settings.setOnMouseClicked(event -> {

        });

        contacts.setOnMouseClicked(event -> {
            manager.loadScreen(Screens.CONTACT_LIST.id, Screens.CONTACT_LIST.resource);
            manager.loadScreen(Screens.ADD_CONTACT.id, Screens.ADD_CONTACT.resource);

            manager.setScreen(Screens.CONTACT_LIST.id);

            Group root = new Group();
            root.getChildren().addAll(manager);
            stage.setScene(new Scene(root));
            stage.show();
        });

        about.setOnMouseClicked(event -> {

        });
    }
}
