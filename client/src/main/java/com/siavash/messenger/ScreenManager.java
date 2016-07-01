package com.siavash.messenger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by sia on 6/30/16.
 */
public class ScreenManager extends StackPane {
    private static Logger log = Logger.getLogger(ScreenManager.class.getSimpleName());
    private Map<String, Node> screens = new HashMap<>();

    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public Node getScreen(String name) {
        return screens.get(name);
    }

    public void loadScreen(String name, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent layout = Util.loadFxmlObject(loader, Util.getAbsolutePath(resource));
            ParentProvider provider = loader.getController();
            provider.setParent(this);
            addScreen(name, layout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadScreen(String name, String resource, Object model) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent layout = Util.loadFxmlObject(loader, Util.getAbsolutePath(resource));
            ParentProvider provider1 = loader.getController();
            provider1.setParent(this);
            ModelProvider provider2 = loader.getController();
            provider2.provideModel(model);
            addScreen(name, layout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScreen(String name) {
        if (screens.get(name) != null) {
            DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000), t -> {
                            // Remove the displayed screen
                            getChildren().remove(0);
                            // Add the screen
                            getChildren().add(0, screens.get(name));
                            Timeline fadeIn = new Timeline(
                                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                    new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
                            fadeIn.play();
                        }, new KeyValue(opacity, 0.0)));
                fade.play();
            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(2000), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
        } else {
            log.info("setScreen: screen hasn't been loaded");
        }
    }

    public void unloadScreen(String name) {
        screens.remove(name);
    }
}
