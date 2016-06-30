package com.siavash.messenger.views;

import com.siavash.messenger.Contact;
import com.siavash.messenger.Util;
import com.siavash.messenger.Views;
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

    private String clientUserName;
    private String contactUserName;

    public ContactView(Image image, Contact contact) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setRoot(this);
            loader.setController(this);

            loader.load(Util.getInputStream(Util.getAbsolutePath(Views.CONTACT.resource)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setImage(image);
        setContactFirstName(contact.getFirstName());
        setContactLastName(contact.getLastName());
        setClientUserName(contact.getClientUserName());
        setContactUserName(contact.getContactUserName());
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

    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
    }

    public String getContactUserName() {
        return contactUserName;
    }

    public void setContactUserName(String contactUserName) {
        this.contactUserName = contactUserName;
    }
}
