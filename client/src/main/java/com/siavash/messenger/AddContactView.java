package com.siavash.messenger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by sia on 6/29/16.
 */
public class AddContactView extends VBox {
    private static Logger log = LoggerFactory.getLogger(AddContactView.class.getSimpleName());
    @FXML
    private TextField userName;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private Button cancel;
    @FXML
    private Button create;

    public AddContactView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setRoot(this);
            loader.setController(this);

            loader.load(Util.getInputStream(Constants.ADD_CONTACT_VIEW_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        create.setOnAction(event -> checkIfUserExists());
    }

    private void checkIfUserExists() {
        MainApp.restApi.user(userName.getText()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                log.info("checkIfUserExists: user -> " + user.toString());
                if (user != null) sendContactInfo();
                //// FIXME: 6/29/16 add contact stage should be closed after creation
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void sendContactInfo() {
        MainApp.restApi
                .addContact(new Contact("sia", userName.getText(), firstName.getText(), lastName.getText()))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //// FIXME: 6/29/16 should be replaced with notification!
                        System.out.println("done done done");
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }
}
