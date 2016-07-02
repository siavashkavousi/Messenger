package com.siavash.messenger;

import com.siavash.messenger.rest.RestApi;
import com.siavash.messenger.rest.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by sia on 6/26/16.
 */
public class MainApp extends Application {
    public static RestApi restApi = Service.createService(RestApi.class, "http://127.0.0.1:8100");
    private static Logger log = Logger.getLogger(MainApp.class.getSimpleName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ScreenManager manager = new ScreenManager();
        manager.loadScreen(Screens.SIGN_UP.id, Screens.SIGN_UP.resource);
        manager.loadScreen(Screens.SIGN_IN.id, Screens.SIGN_IN.resource);

//        manager.setScreen(Screens.SIGN_IN.id);
        manager.loadScreen(Screens.FIRST_PAGE.id, Screens.FIRST_PAGE.resource);
        manager.loadScreen(Screens.PROFILE.id, Screens.PROFILE.resource);
        manager.setScreen(Screens.FIRST_PAGE.id);

        Group root = new Group();
        Parent menu;
        if ((menu = getMenu(manager)) != null)
            root.getChildren().addAll(new VBox(menu, manager));
        else
            root.getChildren().addAll(manager);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private Parent getMenu(ScreenManager manager) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent layout = Util.loadFxmlObject(loader, Util.getAbsolutePath(Screens.MENU.resource));
            ParentProvider provider = loader.getController();
            provider.setParent(manager);
            return layout;
        } catch (IOException e) {
            log.info("getMenu: couldn't load menu resources -> " + e.getMessage());
        }
        return null;
    }
}
