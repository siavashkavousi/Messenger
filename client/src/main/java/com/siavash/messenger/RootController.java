package com.siavash.messenger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

/**
 * Created by sia on 6/27/16.
 */
public class RootController {
    @FXML
    private Label settings;
    @FXML
    private Label contacts;
    @FXML
    private Label about;

    @FXML
    private void initialize() {
        settings.setOnMouseClicked(event -> {

        });

        contacts.setOnMouseClicked(event -> {
            Call<List<Contact>> request = MainApp.restApi.contacts("sia");
            request.enqueue(new Callback<List<Contact>>() {
                @Override
                public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                    List<Contact> contacts = response.body();
                    Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            VBox contactsLayout = Util.loadFxmlObject(loader, Constants.CONTACTS_LAYOUT);

                            ContactsController controller = loader.getController();
                            controller.addContacts(contacts);

                            Stage contactsStage = new Stage();
                            contactsStage.setScene(new Scene(contactsLayout));
                            contactsStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<Contact>> call, Throwable t) {

                }
            });
        });

        about.setOnMouseClicked(event -> {

        });
    }
}
