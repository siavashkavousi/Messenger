package com.siavash.messenger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by sia on 6/30/16.
 */
public class LoginView extends BorderPane {
    private static Logger log = LoggerFactory.getLogger(LoginView.class.getSimpleName());
    @FXML
    private Label userName;
    @FXML
    private Label password;
    @FXML
    private Button login;
    @FXML
    private Button cancel;

    public LoginView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setRoot(this);
            loader.setController(this);

            loader.load(Util.getInputStream(Constants.LOGIN_VIEW_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void checkIfUserExists() {
        MainApp.restApi.findUser(userName.getText()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                log.info("checkIfUserExists: findUser -> " + user.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
