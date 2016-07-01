package com.siavash.messenger.controllers;

import com.siavash.messenger.*;
import com.siavash.messenger.views.ContactView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sia on 6/29/16.
 */
public class ContactList implements ParentProvider {
    private ScreenManager parent;
    @FXML
    private Button createNewContact;
    @FXML
    private ListView<ContactView> contactList;

    @FXML
    private void initialize() {
        createNewContact.setOnAction(event -> parent.setScreen(Screens.ADD_CONTACT.id));

        if (!Util.contacts.isEmpty())
            addContacts(Util.contacts);
    }

    public void addContacts(List<Contact> contacts) {
        contactList.setItems(FXCollections.observableArrayList(coupleContactsToViews(contacts)));
    }

    private List<ContactView> coupleContactsToViews(List<Contact> contacts) {
        return contacts.stream()
                .map(contact -> new ContactView(new Image("/images/contact.png"), contact))
                .collect(Collectors.toList());
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
