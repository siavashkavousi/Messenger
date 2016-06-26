package com.siavash.messenger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * Created by sia on 6/27/16.
 */
public class RootController {
    @FXML
    private Label settings;
    @FXML
    private Label contacts;
    @FXML
    private Label about;

    @FXML
    private void initialize() {
        settings.setOnMouseClicked(event -> {

        });

        contacts.setOnMouseClicked(event -> {

        });

        about.setOnMouseClicked(event -> {

        });
    }
}
