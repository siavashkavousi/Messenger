package com.siavash.messenger.controllers;

import com.siavash.messenger.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Created by sia on 7/1/16.
 */
public class ContactProfile extends BorderPane implements ParentProvider, ModelProvider {
    private ScreenManager parent;
    @FXML
    private Label userName;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Button sendMessage;

    private ProfileModel model;

    private void setUp() {
        String contactUserName = model.getUserName();
        Contact contact = findContactInfo(contactUserName);
        if (contact != null) {
            userName.setText(contactUserName);
            firstName.setText(contact.getFirstName());
            lastName.setText(contact.getLastName());

            sendMessage.setOnAction(event -> parent.setScreen(Screens.CONTACT_MESSAGES.id));
        }
    }

    private Contact findContactInfo(String userName) {
        if (Util.contacts.isEmpty())
            return null;
        for (Contact contact : Util.contacts) {
            if (contact.getContactUserName().equals(userName)) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public void provideModel(Object model) {
        this.model = (ProfileModel) model;
        setUp();
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
