package com.siavash.messenger.controllers;

import com.siavash.messenger.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by sia on 7/1/16.
 */
public class EditProfile implements ParentProvider {
    private static Logger log = LoggerFactory.getLogger(EditProfile.class.getSimpleName());
    private ScreenManager parent;
    @FXML
    private Label userName;
    @FXML
    private TextField password;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private Button back;
    @FXML
    private Button apply;

    @FXML
    private void initialize() {
        setUp();
    }

    private void setUp() {
        if (Util.user != null) {
            User user = Util.user;
            userName.setText(user.getUserName());
            password.setText(user.getPassword());
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            phoneNumber.setText(user.getPhoneNumber());

            apply.setOnAction(event -> {
                if (checkChanges(Util.user))
                    updateUser(() -> parent.setScreen(Screens.PROFILE.id));
            });

            back.setOnAction(event -> parent.setScreen(Screens.PROFILE.id));
        }
    }

    private void updateUser(Runnable postResult) {
        User user = new User(userName.getText(),
                password.getText(),
                firstName.getText(),
                lastName.getText(),
                phoneNumber.getText());
        MainApp.restApi.updateUser(user).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                log.info("updateUser: onResponse -> response status code: " + response.code());
                if (!Util.checkResponseMessage(response))
                    return;

                com.siavash.messenger.Response message = response.body();
                if (message != null && message.getMessage().equals(Constants.HTTP_ACCEPTED)) {
                    log.info("updateUser: onResponse -> success: " + message);
                    Util.user = user;
                    parent.loadScreen(Screens.FIRST_PAGE.id, Screens.FIRST_PAGE.resource);
                    parent.loadScreen(Screens.PROFILE.id, Screens.PROFILE.resource);
                    postResult.run();
                } else {
                    log.info("updateUser: onResponse -> failure: " + message);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                log.info("updateUser: onFailure -> something happened!");
            }
        });
    }

    private boolean checkChanges(User user) {
        if (!password.getText().equals(user.getPassword()))
            return true;
        if (!firstName.getText().equals(user.getFirstName()))
            return true;
        if (!lastName.getText().equals(user.getLastName()))
            return true;
        if (!phoneNumber.getText().equals(user.getPhoneNumber()))
            return true;
        return false;
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
