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

import java.io.IOException;

/**
 * Created by sia on 6/30/16.
 */
public class SignUp implements ParentProvider {
    private static Logger log = LoggerFactory.getLogger(SignUp.class.getSimpleName());
    private ScreenManager parent;
    @FXML
    private TextField userName;
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
    private Button signUp;

    @FXML
    private void initialize() {
        back.setOnAction(event -> parent.setScreen(Screens.SIGN_IN.id));

        //// FIXME: 6/30/16 notification for try again
        signUp.setOnAction(event ->
                signUp(() -> parent.setScreen(Screens.FIRST_PAGE.id), () -> log.info("try again!!!")));
    }

    private void signUp(Runnable postResultSuccess, Runnable postResultFailure) {
        User user = new User(userName.getText(), password.getText(), firstName.getText(),
                lastName.getText(), phoneNumber.getText());
        MainApp.restApi.signUp(user).enqueue(new Callback<com.siavash.messenger.Response>() {
            @Override
            public void onResponse(Call<com.siavash.messenger.Response> call, Response<com.siavash.messenger.Response> response) {
                log.info("signUp: onResponse -> response status code: " + response.code());
                if(!Util.checkResponseMessage(response))
                    return;

                com.siavash.messenger.Response message = response.body();
                if (message != null && message.getMessage().equals(Constants.HTTP_ACCEPTED)) {
                    log.info("signUp: onResponse -> success: " + message);
                    Util.user = user;
                    parent.loadScreen(Screens.FIRST_PAGE.id, Screens.FIRST_PAGE.resource);
                    postResultSuccess.run();
                } else {
                    log.info("signUp: onResponse -> failure: " + message);
                    postResultFailure.run();
                }
            }

            @Override
            public void onFailure(Call<com.siavash.messenger.Response> call, Throwable t) {
                log.info("signUp: onFailure -> something happened!");
            }
        });
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
