package com.siavash.messenger.controllers;

import com.siavash.messenger.*;
import com.siavash.messenger.views.ContactMessages;
import com.siavash.messenger.views.ContactView;
import javafx.application.Platform;
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

import java.util.ArrayList;
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
    private ListView<ContactView> messagedContacts;
    @FXML
    private AnchorPane messageArea;
    @FXML
    private TextField message;

    private ContactView currentContact;
    private ContactMessages currentContactMessages;

    @FXML
    private void initialize() {
        requestContacts(contacts ->
                Util.addItemListToListView(messagedContacts, coupleContactsToViews(contacts)));

        messagedContacts.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, contactView) -> {
                    log.info("messaged contacts view - selected item name: " + contactView.getContactFirstName());
                    try {
                        messageArea.getChildren().remove(0);
                    } catch (IndexOutOfBoundsException e) {
                        log.info("No child in AnchorPane - " + e.getMessage());
                    }

                    ContactMessages view = new ContactMessages();
                    // Request messages from user to his/her contact
                    requestMessages(contactView.getClientUserName(),
                            contactView.getContactUserName(),
                            view::addClientMessages);
                    // Request messages from his/her contact to user
                    requestMessages(contactView.getContactUserName(),
                            contactView.getClientUserName(),
                            view::addContactMessages);
                    // Adds first name and last name of contact
                    view.setContactFirstName(contactView.getContactFirstName());
                    view.setContactLastName(contactView.getContactLastName());
                    messageArea.getChildren().add(view);

                    currentContact = contactView;
                    currentContactMessages = view;
                });

        message.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER) && !message.getText().isEmpty()) {
                List<String> contacts = new ArrayList<>();
                contacts.add(currentContact.getContactUserName());

                Message content = new Message(Util.user.getUserName(), message.getText(), contacts);
                sendMessage(content, () -> currentContactMessages.addClientMessage(content));
                message.clear();
            }
        });
    }

    private void requestContacts(Consumer<List<Contact>> postResult) {
        Call<List<Contact>> request = MainApp.restApi.contacts(Util.user.getUserName());
        request.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> contacts = response.body();
                Util.contacts = contacts;
                postResult.accept(contacts);
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

    private void requestMessages(String senderUserName, String receiverUserName, Consumer<List<Message>> postResult) {
        Call<List<Message>> request = MainApp.restApi.message(senderUserName, receiverUserName);
        request.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
                Platform.runLater(() -> postResult.accept(messages));
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    private void sendMessage(Message message, Runnable postResult) {
        MainApp.restApi.addMessage(message).enqueue(new Callback<com.siavash.messenger.Response>() {
            @Override
            public void onResponse(Call<com.siavash.messenger.Response> call, Response<com.siavash.messenger.Response> response) {
                log.info("sendMessage: onResponse -> response status code: " + response.code());
                if (!Util.checkResponseMessage(response))
                    return;

                com.siavash.messenger.Response message = response.body();
                if (message != null && message.getMessage().equals(Constants.HTTP_ACCEPTED)) {
                    log.info("sendMessage: onResponse -> success: " + message);
                    Platform.runLater(postResult);
                } else {
                    log.info("sendMessage: onResponse -> failure: " + message);
                }
            }

            @Override
            public void onFailure(Call<com.siavash.messenger.Response> call, Throwable t) {
            }
        });
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
