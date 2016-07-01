package com.siavash.messenger.controllers;

import com.siavash.messenger.*;
import com.siavash.messenger.views.ContactView;
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
public class ContactProfile implements ParentProvider, ModelProvider {
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

    private ProfileModel model;

    private void setUp() {
        String contactUserName = model.getUserName();
        ContactView contact = findContactInfo(contactUserName);
        if (contact != null) {
            userName.setText(contactUserName);
            firstName.setText(contact.getContactFirstName());
            lastName.setText(contact.getContactLastName());

            sendMessage.setOnAction(event -> parent.setScreen(Screens.CONTACT_MESSAGES.id));

            unFriend.setOnAction(event -> deleteContact(contact));
        }
    }

    private void deleteContact(ContactView contactView) {
        Contact contact = new Contact(contactView.getClientUserName(),
                contactView.getContactUserName(),
                contactView.getContactFirstName(),
                contactView.getContactLastName());
        MainApp.restApi.deleteContact(contact).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                log.info("deleteContact: onResponse -> response status code: " + response.code());
                if (!Util.checkResponseMessage(response))
                    return;

                com.siavash.messenger.Response message = response.body();
                if (message != null && message.getMessage().equals(Constants.HTTP_ACCEPTED)) {
                    log.info("deleteContact: onResponse -> success: " + message);
                    Platform.runLater(() -> Util.contactViews.remove(contactView));
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

    private ContactView findContactInfo(String userName) {
        if (Util.contactViews.isEmpty())
            return null;
        for (ContactView view : Util.contactViews) {
            if (view.getContactUserName().equals(userName)) {
                return view;
            }
        }
        return null;
    }

    @Override
    public void provideModel(Object model) {
        this.model = (ProfileModel) model;
        setUp();
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
