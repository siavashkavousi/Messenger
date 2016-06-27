package com.siavash.messenger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sia on 6/26/16.
 */
public class FirstPageController {
    private static Logger logger = LoggerFactory.getLogger(FirstPageController.class.getSimpleName());
    @FXML
    private ListView<ContactView> messagedContacts;
    @FXML
    private AnchorPane messageArea;
    @FXML
    private TextField message;

    @FXML
    private void initialize() {
        requestContacts(messagedContacts);

        messagedContacts.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    logger.info("messaged contacts view - selected item name: " + oldValue.getContactFirstName());
                    try {
                        messageArea.getChildren().remove(0);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("No child in AnchorPane - " + e.getMessage());
                    }

                    ContactMessageView view = new ContactMessageView();
                    // Request messages from user to his/her contact
                    requestReceiverMessages(oldValue, view);
                    // Request messages from his/her contact to user
                    requestSentMessages(oldValue, view);
                    messageArea.getChildren().add(new ContactMessageView());
                });
    }

    private void requestContacts(ListView<ContactView> contactsView) {
        // FIXME: 6/27/16 client username content (login)
        Call<List<Contact>> request = MainApp.restApi.contacts("siavash");
        request.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> contacts = response.body();
                contactsView.setItems(FXCollections.observableArrayList(coupleContactsToViews(contacts)));
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {

            }
        });
    }

    private List<ContactView> coupleContactsToViews(List<Contact> contacts) {
        return contacts.stream()
                .map(contact -> new ContactView(new Image("/images/contact.png"), contact))
                .collect(Collectors.toList());
    }

    private void requestReceiverMessages(ContactView contactView, ContactMessageView view) {
        Call<List<Message>> request = MainApp.restApi.message(contactView.getContactUserName(), contactView.getClientUserName());
        request.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
                view.addReceivedMessages(messages);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    private void requestSentMessages(ContactView contactView, ContactMessageView view) {
        Call<List<Message>> request = MainApp.restApi.message(contactView.getClientUserName(), contactView.getContactUserName());
        request.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
                view.addSentMessages(messages);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }
}
