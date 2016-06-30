package com.siavash.messenger.views;

import com.siavash.messenger.Message;
import com.siavash.messenger.Util;
import com.siavash.messenger.Views;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sia on 6/27/16.
 */
public class ContactMessages extends VBox {
    private static Logger log = LoggerFactory.getLogger(ContactMessages.class.getSimpleName());
    @FXML
    private Label contactFirstName;
    @FXML
    private Label contactLastName;
    @FXML
    private VBox clientMessages;
    @FXML
    private VBox contactMessages;

    public ContactMessages() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setRoot(this);
            loader.setController(this);

            loader.load(Util.getInputStream(Util.getAbsolutePath(Views.CONTACT_MESSAGES.resource)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setContactFirstName(String firstName){
        contactFirstName.setText(firstName);
    }

    public void setContactLastName(String lastName){
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
}
