package com.siavash.messenger.views;

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
 * Created by sia on 7/2/16.
 */
public class ItemView extends HBox {
    private String itemId;
    @FXML
    private ImageView image;
    @FXML
    private ImageView type;
    @FXML
    private Label name;

    private String itemType;

    public ItemView(String itemId, Image image, Image type, String name, String itemType) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setRoot(this);
            loader.setController(this);

            loader.load(Util.getInputStream(Util.getAbsolutePath(Views.ITEM.resource)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setItemId(itemId);
        setImage(image);
        setType(type);
        setName(name);
        setItemType(itemType);
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Image getImage() {
        return image.getImage();
    }

    public void setImage(Image image) {
        this.image.setImage(image);
    }

    public Image getType() {
        return type.getImage();
    }

    public void setType(Image type) {
        this.type.setImage(type);
    }

    public String getName() {
        return name.getText();
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
