package com.siavash.messenger.controllers;

import com.siavash.messenger.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by sia on 7/1/16.
 */
public class ContactProfile implements ParentProvider {
    private static Logger log = LoggerFactory.getLogger(ContactProfile.class.getSimpleName());
    private ScreenManager parent;
    @FXML
    private Label userName;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Button unFriend;
    @FXML
    private Button sendMessage;
    @FXML
    private Button report;

    @FXML
    private void initialize(){
        setUp();
    }

    private void setUp() {
        if (Util.currentContact != null) {
            userName.setText(Util.currentContact.getContactUserName());
            firstName.setText(Util.currentContact.getFirstName());
            lastName.setText(Util.currentContact.getLastName());

            sendMessage.setOnAction(event -> parent.setScreen(Screens.CONTACT_MESSAGES.id));

            unFriend.setOnAction(event -> deleteContact());

            report.setOnAction(event -> reportUser(Util.currentContact.getContactUserName()));
        }
    }

    private void deleteContact() {
        MainApp.restApi.deleteContact(Util.currentContact).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                log.info("deleteContact: onResponse -> response status code: " + response.code());
                if (!Util.checkResponseMessage(response))
                    return;

                com.siavash.messenger.Response message = response.body();
                if (message != null && message.getMessage().equals(Constants.HTTP_ACCEPTED)) {
                    log.info("deleteContact: onResponse -> success: " + message);
                    Platform.runLater(() -> Util.itemViews.remove(Util.currentItemView));
                } else {
                    log.info("deleteContact: onResponse -> failure: " + message);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                log.info("deleteContact: onFailure -> something happened!");
            }
        });
    }

    private void reportUser(String userName) {
        MainApp.restApi.reportUser(new Request(userName)).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                log.info("reportUser: onResponse -> response status code: " + response.code());
                if (!Util.checkResponseMessage(response))
                    return;

                com.siavash.messenger.Response message = response.body();
                if (message != null && message.getMessage().equals(Constants.HTTP_ACCEPTED)) {
                    log.info("reportUser: onResponse -> success: " + message);
                    //// FIXME: 7/2/16 notification needed
                } else {
                    log.info("reportUser: onResponse -> failure: " + message);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                log.info("reportUser: onFailure -> something happened!");
            }
        });
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
