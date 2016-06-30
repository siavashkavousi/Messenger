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
                signUp(()->parent.setScreen(Screens.FIRST_PAGE.id), ()-> log.info("try again!!!")));
    }

    private void signUp(Runnable postResultSuccess, Runnable postResultFailure) {
        MainApp.restApi.signUp(new User(userName.getText(), password.getText(), firstName.getText(),
                lastName.getText(), phoneNumber.getText())).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String message = response.body();
                if (message != null && message.equals(Constants.SUCCESS))
                    postResultSuccess.run();
                else
                    postResultFailure.run();
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
