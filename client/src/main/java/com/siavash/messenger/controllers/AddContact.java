package com.siavash.messenger.controllers;

import com.siavash.messenger.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sia on 6/29/16.
 */
public class AddContact implements ParentProvider {
    private static Logger log = LoggerFactory.getLogger(AddContact.class.getSimpleName());
    private ScreenManager parent;
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

    @FXML
    public void initialize() {
        create.setOnAction(event ->
                checkIfUserExists(() -> sendContactInfo(() -> log.info("done")),
                        () -> {
                            log.info("user does not exist in our system");
                            parent.setScreen(Screens.CONTACT_LIST.id);
                        }));
    }

    private void checkIfUserExists(Runnable postResultSuccess, Runnable postResultFailure) {
        MainApp.restApi.findUser(userName.getText()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user != null) {
                    log.info("checkIfUserExists: findUser -> " + user.toString());
                    postResultSuccess.run();
                } else {
                    //// FIXME: 7/1/16 notify user not exist in our system
                    postResultFailure.run();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void sendContactInfo(Runnable postResult) {
        MainApp.restApi.addContact(new Contact(Util.user.getUserName(),
                userName.getText(), firstName.getText(), lastName.getText()))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //// FIXME: 6/29/16 should be replaced with notification!
                        postResult.run();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
