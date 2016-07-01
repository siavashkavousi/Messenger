package com.siavash.messenger.controllers;

import com.siavash.messenger.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sia on 7/1/16.
 */
public class Profile implements ParentProvider {
    private static Logger log = LoggerFactory.getLogger(Profile.class.getSimpleName());
    private ScreenManager parent;
    @FXML
    private Label userName;
    @FXML
    private Label password;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label phoneNumber;
    @FXML
    private Button back;
    @FXML
    private Button edit;

    @FXML
    private void initialize() {
        setUp();
    }

    private void setUp() {
        if (Util.user != null) {
            User user = Util.user;
            userName.setText(user.getUserName());
            password.setText(user.getPassword());
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            phoneNumber.setText(user.getPhoneNumber());
        }

        edit.setOnAction(event -> {
            parent.loadScreen(Screens.EDIT_PROFILE.id, Screens.EDIT_PROFILE.resource);
            parent.setScreen(Screens.EDIT_PROFILE.id);
        });

        back.setOnAction(event -> parent.setScreen(Screens.FIRST_PAGE.id));
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
