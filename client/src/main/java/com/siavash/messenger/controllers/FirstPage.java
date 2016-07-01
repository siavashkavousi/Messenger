package com.siavash.messenger.controllers;

import com.siavash.messenger.*;
import com.siavash.messenger.views.ContactView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by sia on 6/26/16.
 */
public class FirstPage implements ParentProvider {
    private static Logger log = LoggerFactory.getLogger(FirstPage.class.getSimpleName());
    private ScreenManager parent;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<ContactView> messagedContacts;
    @FXML
    private AnchorPane messageArea;

    @FXML
    private void initialize() {
        searchField.setOnKeyPressed(event -> {
            messagedContacts.getItems().clear();
            if (event.getCode().equals(KeyCode.ENTER)) {
                if (!searchField.getText().isEmpty()) {
                    findSpecificContact(searchField.getText(),
                            contacts -> Platform.runLater(() -> messagedContacts.setItems(coupleContactsToViews(contacts))));
                } else {
                    requestContacts((contacts) -> {
                        Util.contactViews = coupleContactsToViews(contacts);
                        Platform.runLater(() -> messagedContacts.setItems(Util.contactViews));
                    });
                }
            }
        });

        requestContacts((contacts) -> {
            Util.contactViews = coupleContactsToViews(contacts);
            messagedContacts.setItems(Util.contactViews);
        });

        messagedContacts.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, contactView) -> {
                    log.info("messaged contacts view - selected item name: " + contactView.getContactFirstName());
                    try {
                        messageArea.getChildren().remove(0);
                    } catch (IndexOutOfBoundsException e) {
                        log.info("No child in AnchorPane - " + e.getMessage());
                    }

                    ScreenManager manager = new ScreenManager();

                    ContactMessagesModel model = ContactMessagesModel.newContactMessagesModel()
                            .clientUserName(contactView.getClientUserName())
                            .contactUserName(contactView.getContactUserName())
                            .contactFirstName(contactView.getContactFirstName())
                            .contactLastName(contactView.getContactLastName()).build();
                    manager.loadScreen(Screens.CONTACT_MESSAGES.id,
                            Screens.CONTACT_MESSAGES.resource, model);

                    manager.setScreen(Screens.CONTACT_MESSAGES.id);
                    messageArea.getChildren().add(manager);
                });
    }

    private void requestContacts(Consumer<List<Contact>> postResult) {
        Call<List<Contact>> request = MainApp.restApi.contacts(Util.user.getUserName());
        request.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> contacts = response.body();
                postResult.accept(contacts);
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
            }
        });

    }

    private ObservableList<ContactView> coupleContactsToViews(List<Contact> contacts) {
        List<ContactView> contactViews = contacts.stream()
                .map(contact -> new ContactView(new Image("/images/contact.png"), contact))
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(contactViews);
    }

    private void findSpecificContact(String name, Consumer<List<Contact>> postResult) {
        MainApp.restApi.findContact(Util.user.getUserName(), name).enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> contacts = response.body();
                postResult.accept(contacts);
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {

            }
        });
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
