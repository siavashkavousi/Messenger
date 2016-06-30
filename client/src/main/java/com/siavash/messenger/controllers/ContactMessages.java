package com.siavash.messenger.controllers;

import com.siavash.messenger.Message;
import com.siavash.messenger.ParentProvider;
import com.siavash.messenger.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sia on 6/27/16.
 */
public class ContactMessages implements ParentProvider {
    private static Logger log = LoggerFactory.getLogger(ContactMessages.class.getSimpleName());
    @FXML
    private Label contactFirstName;
    @FXML
    private Label contactLastName;
    @FXML
    private VBox clientMessages;
    @FXML
    private VBox contactMessages;

    @FXML
    private void initialize(){

    }

    public void addClientMessages(List<Message> messages) {
        clientMessages.getChildren().addAll(coupleMessagesToLabels(messages));
    }

    public void addContactMessages(List<Message> messages) {
        contactMessages.getChildren().addAll(coupleMessagesToLabels(messages));
    }

    private List<Label> coupleMessagesToLabels(List<Message> messages) {
        return messages.stream().map(message -> new Label(message.getContent())).collect(Collectors.toList());
    }

    @Override
    public void setParent(ScreenManager screen) {

    }
}
