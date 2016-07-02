package com.siavash.messenger.controllers;

import com.siavash.messenger.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by sia on 7/2/16.
 */
public class GroupMessages implements ParentProvider {
    private static Logger log = LoggerFactory.getLogger(GroupMessages.class.getSimpleName());
    private ScreenManager parent;
    @FXML
    private HBox groupProfile;
    @FXML
    private Label groupId;
    @FXML
    private Label groupName;
    @FXML
    private VBox clientMessages;
    @FXML
    private VBox contactsMessages;
    @FXML
    private TextField messageField;

    private GroupMembers groupMembers = null;

    @FXML
    private void initialize() {
        setUp();
    }

    private void setUp() {
        String groupId = Util.currentItemView.getItemId();
        String name = Util.currentItemView.getName();

        setGroupId(groupId);
        setGroupName(name);

        requestMessages(
                Util.user.getUserName(),
                groupId,
                false,
                this::addClientMessages);

        requestMessages(
                Util.user.getUserName(),
                groupId,
                true,
                this::addContactMessages);

        requestGroupMembers(
                groupId,
                members -> groupMembers = members);

        messageField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER) && !messageField.getText().isEmpty()) {
                if (groupMembers == null)
                    return;

                Message content = new Message(Util.user.getUserName(),
                        messageField.getText(),
                        groupMembers.getMembersUserName());
                sendMessage(content, () -> addClientMessage(messageField.getText()));
                messageField.clear();
            }
        });

        groupProfile.setOnMouseClicked(event -> {
            parent.loadScreen(Screens.CONTACT_PROFILE.id, Screens.CONTACT_PROFILE.resource);
            parent.setScreen(Screens.CONTACT_PROFILE.id);
        });
    }

    private void setGroupId(String groupId) {
        this.groupId.setText(groupId);
    }

    private void setGroupName(String groupName) {
        this.groupName.setText(groupName);
    }

    private void addClientMessage(String message) {
        clientMessages.getChildren().addAll(new Label(message));
        contactsMessages.getChildren().addAll(getEmptyLabel(1));
    }

    private void addClientMessages(List<Message> messages) {
        int size = messages.size();
        clientMessages.getChildren().addAll(coupleMessagesToLabels(messages));
        contactsMessages.getChildren().addAll(getEmptyLabel(size));
    }

    private void addContactMessages(List<Message> messages) {
        int size = messages.size();
        contactsMessages.getChildren().addAll(coupleMessagesToLabels(messages));
        clientMessages.getChildren().addAll(getEmptyLabel(size));
    }

    private List<Label> coupleMessagesToLabels(List<Message> messages) {
        return messages.stream().map(message -> new Label(message.getContent())).collect(Collectors.toList());
    }

    private List<Label> getEmptyLabel(int size) {
        List<Label> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new Label());
        }
        return list;
    }

    private void requestMessages(String senderUserName,
                                 String groupId,
                                 boolean exceptSender,
                                 Consumer<List<Message>> postResult) {
        Call<List<Message>> request = MainApp.restApi.groupMessages(senderUserName, groupId, exceptSender);
        request.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
                Platform.runLater(() -> postResult.accept(messages));
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    private void requestGroupMembers(String groupId, Consumer<GroupMembers> postResult) {
        Call<GroupMembers> request = MainApp.restApi.groupMembers(groupId);
        request.enqueue(new Callback<GroupMembers>() {
            @Override
            public void onResponse(Call<GroupMembers> call, Response<GroupMembers> response) {
                GroupMembers members = response.body();
                if (members != null) {
                    postResult.accept(members);
                }
            }

            @Override
            public void onFailure(Call<GroupMembers> call, Throwable t) {

            }
        });
    }

    private void sendMessage(Message message, Runnable postResult) {
        MainApp.restApi.addMessage(message).enqueue(new Callback<com.siavash.messenger.Response>() {
            @Override
            public void onResponse(Call<com.siavash.messenger.Response> call, Response<com.siavash.messenger.Response> response) {
                log.info("sendMessage: onResponse -> response status code: " + response.code());
                if (!Util.checkResponseMessage(response))
                    return;

                com.siavash.messenger.Response message = response.body();
                if (message != null && message.getMessage().equals(Constants.HTTP_ACCEPTED)) {
                    log.info("sendMessage: onResponse -> success: " + message);
                    Platform.runLater(postResult);
                } else {
                    log.info("sendMessage: onResponse -> failure: " + message);
                }
            }

            @Override
            public void onFailure(Call<com.siavash.messenger.Response> call, Throwable t) {

            }
        });
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
