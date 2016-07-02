package com.siavash.messenger.controllers;

import com.siavash.messenger.ParentProvider;
import com.siavash.messenger.ScreenManager;
import com.siavash.messenger.Screens;
import com.siavash.messenger.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * Created by sia on 6/29/16.
 */
public class ContactList implements ParentProvider {
    private ScreenManager parent;
    @FXML
    private Button createNewContact;
//    @FXML
//    private ListView<ContactView> contactList;
//
//    @FXML
//    private void initialize() {
//        createNewContact.setOnAction(event -> parent.setScreen(Screens.ADD_CONTACT.id));
//
//        contactList.setItems(Util.contactViews);
//    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
