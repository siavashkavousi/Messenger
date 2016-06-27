package com.siavash.messenger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

/**
 * Created by sia on 6/26/16.
 */
public class FirstPageController {
    @FXML
    private ListView<ContactView> messagedContacts;

    @FXML
    private void initialize() {
        ObservableList<String> names = FXCollections.observableArrayList(
                "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");
        ObservableList<ContactView> contacts = FXCollections.observableArrayList(
                new ContactView(new Image("/images/contact.png"), "Siavash", "Kavousi"),
                new ContactView(new Image("/images/contact.png"), "kir", "kos"),
                new ContactView(new Image("/images/contact.png"), "John", "Targeryan")
        );
        messagedContacts.setItems(contacts);
    }
}
