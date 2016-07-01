package com.siavash.messenger.controllers;

import com.siavash.messenger.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    }

    public void setProfileEvent(Runnable e) {
        profile.setOnMouseClicked(event -> e.run());
    }

    public void setContactFirstName(String firstName) {
        contactFirstName.setText(firstName);
    }

    public void setContactLastName(String lastName) {
        contactLastName.setText(lastName);
    }

    public void addClientMessage(Message message) {
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        clientMessages.getChildren().addAll(coupleMessagesToLabels(messages));
        contactMessages.getChildren().addAll(getEmptyLabel(1));
    }

    public void addClientMessages(List<Message> messages) {
        int size = messages.size();
        clientMessages.getChildren().addAll(coupleMessagesToLabels(messages));
        contactMessages.getChildren().addAll(getEmptyLabel(size));
    }

    public void addContactMessages(List<Message> messages) {
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

    @Override
    public void provideModel(Object model) {
        this.model = (ContactMessagesModel) model;
        setUp();
    }

    @Override
    public void setParent(ScreenManager screen) {

    }
}
