package com.siavash.messenger.controllers;

import com.siavash.messenger.*;
import com.siavash.messenger.views.ItemView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by sia on 6/26/16.
 */
public class FirstPage implements ParentProvider {
    private static Logger log = LoggerFactory.getLogger(FirstPage.class.getSimpleName());
    private ScreenManager parent;
    @FXML
    private TextField searchField;
    @FXML
    private Button createGroup;
    @FXML
    private Button createChannel;
    @FXML
    private ListView<ItemView> itemList;
    @FXML
    private AnchorPane messageArea;

    private ScreenManager messagesManager = new ScreenManager();

    @FXML
    private void initialize() {
        messageArea.getChildren().add(messagesManager);

        searchField.setOnKeyPressed(event -> {
            itemList.getItems().clear();
            if (event.getCode().equals(KeyCode.ENTER)) {
                if (!searchField.getText().isEmpty()) {
//                    findSpecificContact(searchField.getText(),
//                            contacts -> Platform.runLater(() -> itemList.setItems(coupleItemsToViews(contacts))));
                } else {
                    requestContacts(contacts -> Util.itemViews.addAll(coupleItemsToViews(contacts, "contact")));
                    requestGroups(groups -> Util.itemViews.addAll(coupleItemsToViews(groups, "group")));
                }
            }
        });

        createGroup.setOnAction(event -> {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Create Group");

            ButtonType createGroupType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(createGroupType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField groupId = new TextField();
            groupId.setPromptText("Group Id");
            TextField groupName = new TextField();
            groupName.setPromptText("Group Name");

            grid.add(new Label("Group Id:"), 0, 0);
            grid.add(groupId, 1, 0);
            grid.add(new Label("Group Name:"), 0, 1);
            grid.add(groupName, 1, 1);

            Node createGroupButton = dialog.getDialogPane().lookupButton(createGroupType);
            createGroupButton.setDisable(true);

            groupId.textProperty().addListener((observable, oldValue, newValue) -> {
                createGroupButton.setDisable(newValue.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(grid);
            Platform.runLater(groupId::requestFocus);

            dialog.setResultConverter(param -> {
                if (param == createGroupType)
                    return new Pair<>(groupId.getText(), groupName.getText());
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();
            result.ifPresent(pair -> {
                Group group = new Group(pair.getKey(),
                        Util.user.getUserName(),
                        pair.getValue());
                Map<String, String> map = new HashMap<>();
                map.put(groupId.getText(), groupName.getText());
                Util.itemViews.addAll(coupleItemsToViews(map, "group"));
                createGroup(group, dialog::close);
            });
        });

        createChannel.setOnAction(event -> {

        });

        itemList.setItems(Util.itemViews);
        requestContacts(contacts -> Util.itemViews.addAll(coupleItemsToViews(contacts, "contact")));
        requestGroups(groups -> Util.itemViews.addAll(coupleItemsToViews(groups, "group")));

        itemList.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, contactView) -> {
                    Util.currentItemView = contactView;

                    if (contactView.getItemType().equals("contact")) {
                        messagesManager.loadScreen(Screens.CONTACT_MESSAGES.id,
                                Screens.CONTACT_MESSAGES.resource);
                        messagesManager.setScreen(Screens.CONTACT_MESSAGES.id);
                    } else if (contactView.getItemType().equals("group")) {
                        messagesManager.loadScreen(Screens.GROUP_MESSAGES.id,
                                Screens.GROUP_MESSAGES.resource);
                        messagesManager.setScreen(Screens.GROUP_MESSAGES.id);
                    }
                });
    }

    private void requestContacts(Consumer<Map<String, String>> postResult) {
        Call<List<Contact>> request = MainApp.restApi.contacts(Util.user.getUserName());
        request.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> contacts = response.body();
                if (contacts != null && !contacts.isEmpty()) {
                    Map<String, String> result = new HashMap<>();
                    for (Contact contact : contacts)
                        result.put(contact.getContactUserName(), contact.getFirstName() + " " + contact.getLastName());
                    postResult.accept(result);
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
            }
        });
    }

    private void requestGroups(Consumer<Map<String, String>> postResult) {
        Call<List<Group>> request = MainApp.restApi.groups(Util.user.getUserName());
        request.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                List<Group> groups = response.body();
                if (groups != null && !groups.isEmpty()) {
                    Map<String, String> result = new HashMap<>();
                    for (Group group : groups)
                        result.put(group.getGroupId(), group.getName());
                    postResult.accept(result);
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {

            }
        });
    }

    private ObservableList<ItemView> coupleItemsToViews(Map<String, String> items, String type) {
        Image image = new Image("/images/contact.png");
        Image typeImage = null;
        switch (type) {
            case "contact":
                typeImage = new Image("/images/contact_icon.png");
                break;
            case "group":
                typeImage = new Image("/images/group_icon.png");
                break;
            case "channel":
                typeImage = new Image("/images/channel_icon.png");
                break;
        }
        Image finalTypeImage = typeImage;

        List<ItemView> itemViews = items.entrySet()
                .stream()
                .map(item -> new ItemView(item.getKey(), image, finalTypeImage, item.getValue(), type))
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(itemViews);
    }

//    private void findSpecificContact(String name, Consumer<List<Contact>> postResult) {
//        MainApp.restApi.findContacts(Util.user.getUserName(), name).enqueue(new Callback<List<Contact>>() {
//            @Override
//            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
//                List<Contact> contacts = response.body();
//                postResult.accept(contacts);
//            }
//
//            @Override
//            public void onFailure(Call<List<Contact>> call, Throwable t) {
//
//            }
//        });
//    }

    private void createGroup(Group group, Runnable postResult) {
        MainApp.restApi.createGroup(group).enqueue(new Callback<com.siavash.messenger.Response>() {
            @Override
            public void onResponse(Call<com.siavash.messenger.Response> call, retrofit2.Response<com.siavash.messenger.Response> response) {
                log.info("createGroup: onResponse -> response status code: " + response.code());
                if (!Util.checkResponseMessage(response))
                    return;

                com.siavash.messenger.Response message = response.body();
                if (message != null && message.getMessage().equals(Constants.HTTP_ACCEPTED)) {
                    log.info("createGroup: onResponse -> success: " + message);
                    postResult.run();
                } else {
                    log.info("createGroup: onResponse -> failure: " + message);
                }
            }

            @Override
            public void onFailure(Call<com.siavash.messenger.Response> call, Throwable t) {
                log.info("createGroup: onFailure -> something happened!");
            }
        });
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
