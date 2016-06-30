package com.siavash.messenger;

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
        manager.loadScreen(Screens.CONTACT_MESSAGES.id, Screens.CONTACT_MESSAGES.resource);
        manager.loadScreen(Screens.FIRST_PAGE.id, Screens.FIRST_PAGE.resource);

        manager.setScreen(Screens.FIRST_PAGE.id);

        Group root = new Group();
        Parent menu;
        if ((menu = getMenu()) != null)
            root.getChildren().addAll(new VBox(menu, manager));
        else
            root.getChildren().addAll(manager);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private Parent getMenu() {
        try {
            return Util.loadFxmlObject(new FXMLLoader(), Util.getAbsolutePath(Screens.MENU.resource));
        } catch (IOException e) {
            log.info("getMenu: couldn't load menu resources -> " + e.getMessage());
        }
        return null;
    }

//    @Override
//    public void start(Stage primaryStage) {
//        this.primaryStage = primaryStage;
//        primaryStage.setTitle("Messenger");
//
//        try {
//            setUpRootLayout();
//            showFirstPage();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setUpRootLayout() throws IOException {
//        rootLayout = Util.loadFxmlObject(new FXMLLoader(), Constants.ROOT_LAYOUT_PATH);
//
//        primaryStage.setScene(new Scene(rootLayout, 800, 600));
//        primaryStage.show();
//    }

//    @Override
//    public void changeScene(SubScene from, SubScene to, boolean withAnimation) {
//    }

//    private void showFirstPage() throws IOException {
//        HBox mainPage = Util.loadFxmlObject(new FXMLLoader(), Constants.FIRST_PAGE_PATH);
//        rootLayout.getChildren().add(mainPage);
//    }
}
