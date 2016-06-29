package com.siavash.messenger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sia on 6/29/16.
 */
public class ContactListView extends VBox {
    @FXML
    private Button createNewContact;
    @FXML
    private ListView<ContactView> contactList;

    public ContactListView(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setRoot(this);
            loader.setController(this);

            loader.load(Util.getInputStream(Constants.CONTACT_LIST_VIEW_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        createNewContact.setOnAction(event -> {
            if(stage.isShowing())
                stage.close();
            stage.setScene(new Scene(new AddContactView()));
            stage.show();
        });
    }

    public void addContacts(List<Contact> contacts) {
        contactList.setItems(FXCollections.observableArrayList(coupleContactsToViews(contacts)));
    }

    private List<ContactView> coupleContactsToViews(List<Contact> contacts) {
        return contacts.stream()
                .map(contact -> new ContactView(new Image("/images/contact.png"), contact))
                .collect(Collectors.toList());
    }
}
