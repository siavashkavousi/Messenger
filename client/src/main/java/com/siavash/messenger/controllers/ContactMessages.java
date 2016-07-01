package com.siavash.messenger.controllers;

import com.siavash.messenger.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
 * Created by sia on 6/27/16.
 */
public class ContactMessages implements ParentProvider, ModelProvider {
    private static Logger log = LoggerFactory.getLogger(ContactMessages.class.getSimpleName());
    private ScreenManager parent;
    @FXML
    private HBox profile;
    @FXML
    private Label contactFirstName;
    @FXML
    private Label contactLastName;
    @FXML
    private VBox clientMessages;
    @FXML
    private VBox contactMessages;
    @FXML
    private TextField messageField;

    private ContactMessagesModel model;

    private void setUp() {
        // Adds first name and last name of contact
        setContactFirstName(model.getContactFirstName());
        setContactLastName(model.getContactLastName());
        // Request messages from user to his/her contact
        requestMessages(model.getClientUserName(),
                model.getContactUserName(),
                this::addClientMessages);
        // Request messages from his/her contact to user
        requestMessages(model.getContactUserName(),
                model.getClientUserName(),
                this::addContactMessages);

        messageField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER) && !messageField.getText().isEmpty()) {
                List<String> contacts = new ArrayList<>();
                contacts.add(model.getContactUserName());

                Message content = new Message(Util.user.getUserName(), messageField.getText(), contacts);
                sendMessage(content, () -> addClientMessage(messageField.getText()));
                messageField.clear();
            }
        });

        profile.setOnMouseClicked(event -> {
            ProfileModel model = ProfileModel.newProfileModel()
                    .userName(this.model.getContactUserName())
                    .build();
            parent.loadScreen(Screens.CONTACT_PROFILE.id, Screens.CONTACT_PROFILE.resource, model);
            parent.setScreen(Screens.CONTACT_PROFILE.id);
        });
    }

    private void setContactFirstName(String firstName) {
        contactFirstName.setText(firstName);
    }

    private void setContactLastName(String lastName) {
        contactLastName.setText(lastName);
    }

    private void addClientMessage(String message) {
        clientMessages.getChildren().addAll(new Label(message));
        contactMessages.getChildren().addAll(getEmptyLabel(1));
    }

    private void addClientMessages(List<Message> messages) {
        int size = messages.size();
        clientMessages.getChildren().addAll(coupleMessagesToLabels(messages));
        contactMessages.getChildren().addAll(getEmptyLabel(size));
    }

    private void addContactMessages(List<Message> messages) {
        int size = messages.size();
        contactMessages.getChildren().addAll(coupleMessagesToLabels(messages));
        clientMessages.getChildren().addAll(getEmptyLabel(size));
    }

    private List<Label> coupleMessagesToLabels(List<Message> messages) {
        return messages.stream().map(message -> new Label(message.getContent())).collect(Collectors.toList());
    }

    private List<Label> getEmptyLabel(int size) {
        List<Label> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new Label());
        }
        return list;
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
    public void provideModel(Object model) {
        this.model = (ContactMessagesModel) model;
        setUp();
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
