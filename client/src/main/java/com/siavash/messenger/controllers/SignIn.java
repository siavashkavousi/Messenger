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
 * Created by sia on 6/30/16.
 */
public class SignIn implements ParentProvider {
    private static Logger log = LoggerFactory.getLogger(SignIn.class.getSimpleName());
    private ScreenManager parent;

    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private Button signIn;
    @FXML
    private Button signUp;

    @FXML
    private void initialize() {
        signIn.setOnAction(event -> signIn(userName.getText(), password.getText()
                , () -> parent.setScreen(Screens.FIRST_PAGE.id)));
        signUp.setOnAction(event -> parent.setScreen(Screens.SIGN_UP.id));
    }

    private void signIn(String userName, String password, Runnable postResult) {
        MainApp.restApi.signIn(userName, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user != null) {
                    log.info("signIn: findUser -> " + user.toString());
                    postResult.run();
                } else {
                    //// FIXME: 6/30/16 not found notification should be shown
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
