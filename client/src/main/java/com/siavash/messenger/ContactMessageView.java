package com.siavash.messenger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sia on 6/27/16.
 */
public class ContactMessageView extends HBox {
    private static Logger log = LoggerFactory.getLogger(ContactMessageView.class.getSimpleName());
    @FXML
    private VBox clientMessages;
    @FXML
    private VBox contactMessages;

    public ContactMessageView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setRoot(this);
            loader.setController(this);

            loader.load(Util.getInputStream(Constants.CONTACT_MESSAGES_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
