package com.siavash.messenger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        ObservableList<ContactView> contacts = FXCollections.observableArrayList(
                new ContactView(new Image("/images/contact.png"), new Contact("sia", "arr", "Siavash", "Kavousi")),
                new ContactView(new Image("/images/contact.png"), new Contact("kir", "kos", "kirri", "kirabaadi"))
        );
        messagedContacts.setItems(contacts);

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
                    Call<List<Message>> request = MainApp.restApi.message(oldValue.getReceiverUserName(), oldValue.getSenderUserName());
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
                    // Request messages from his/her contact to user
                    request = MainApp.restApi.message(oldValue.getSenderUserName(), oldValue.getReceiverUserName());
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
                    messageArea.getChildren().add(new ContactMessageView());
                });
    }
}
