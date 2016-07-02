package com.siavash.messenger.controllers;

import com.siavash.messenger.ParentProvider;
import com.siavash.messenger.ScreenManager;
import com.siavash.messenger.Screens;
import com.siavash.messenger.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sia on 7/2/16.
 */
public class GroupProfile implements ParentProvider {
    private static Logger log = LoggerFactory.getLogger(GroupProfile.class.getSimpleName());
    private ScreenManager parent;
    @FXML
    private Label groupId;
    @FXML
    private Label groupName;
    @FXML
    private Label creator;
    @FXML
    private Button leave;
    @FXML
    private Button sendMessage;
    @FXML
    private Button report;

    @FXML
    private void initialize() {
        setUp();
    }

    private void setUp() {
        if (Util.currentContact != null) {
            groupId.setText(Util.currentGroup.getGroupId());
            groupName.setText(Util.currentGroup.getName());
            creator.setText(Util.currentGroup.getCreatorId());

            sendMessage.setOnAction(event -> parent.setScreen(Screens.GROUP_MESSAGES.id));
        }
    }

    @Override
    public void setParent(ScreenManager screen) {
        parent = screen;
    }
}
