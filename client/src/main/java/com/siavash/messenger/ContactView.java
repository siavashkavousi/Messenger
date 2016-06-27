package com.siavash.messenger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Created by sia on 6/27/16.
 */
public class ContactView extends HBox {
    @FXML
    private ImageView contactImage;
    @FXML
    private Label contactFirstName;
    @FXML
    private Label contactLastName;

    private String senderUserName;
    private String receiverUserName;

    public ContactView(Image image, Contact contact) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setRoot(this);
            loader.setController(this);

            loader.load(Util.getInputStream(Constants.CONTACT_VIEW_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setImage(image);
        setContactFirstName(contact.getFirstName());
        setContactLastName(contact.getLastName());
        setSenderUserName(contact.getSenderUserName());
        setReceiverUserName(contact.getReceiverUserName());
    }

    public Image getImage() {
        return contactImage.getImage();
    }

    public void setImage(Image image) {
        contactImage.setImage(image);
    }

    public String getContactFirstName() {
        return contactFirstName.getText();
    }

    public void setContactFirstName(String name) {
        contactFirstName.setText(name);
    }

    public String getContactLastName() {
        return contactLastName.getText();
    }

    public void setContactLastName(String name) {
        contactLastName.setText(name);
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getReceiverUserName() {
        return receiverUserName;
    }

    public void setReceiverUserName(String receiverUserName) {
        this.receiverUserName = receiverUserName;
    }
}
