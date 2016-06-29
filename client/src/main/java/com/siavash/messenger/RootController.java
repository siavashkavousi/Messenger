package com.siavash.messenger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private Stage secondaryStage = new Stage();

    @FXML
    private void initialize() {
        settings.setOnMouseClicked(event -> {

        });

        contacts.setOnMouseClicked(event -> {
            Call<List<Contact>> request = MainApp.restApi.contacts("sia");
            request.enqueue(new Callback<List<Contact>>() {
                @Override
                public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                    Platform.runLater(() -> {
                        List<Contact> contacts = response.body();
                        ContactListView view = new ContactListView(secondaryStage);
                        secondaryStage.setScene(new Scene(view));
                        secondaryStage.show();
                        view.addContacts(contacts);
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
