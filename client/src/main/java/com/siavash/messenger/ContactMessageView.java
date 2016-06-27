package com.siavash.messenger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sia on 6/27/16.
 */
public class ContactMessageView extends HBox {
    @FXML
    private VBox receivedMessages;
    @FXML
    private VBox sentMessages;

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

    public void addReceivedMessages(List<Message> messages) {
        receivedMessages.getChildren().addAll(coupleMessagesToLabels(messages));
    }

    public void addSentMessages(List<Message> messages) {
        sentMessages.getChildren().addAll(coupleMessagesToLabels(messages));
    }

    private List<Label> coupleMessagesToLabels(List<Message> messages) {
        return messages.stream().map(message -> new Label(message.getContent())).collect(Collectors.toList());
    }
}
