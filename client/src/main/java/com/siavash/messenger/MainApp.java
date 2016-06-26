package com.siavash.messenger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by sia on 6/26/16.
 */
public class MainApp extends Application {
    private static Logger logger = Logger.getLogger(MainApp.class.getSimpleName());
    private Stage primaryStage;
    private VBox rootLayout;
    private FXMLLoader loader = new FXMLLoader();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Messenger");

        setUpRootLayout();
        showMainPage();
    }

    private void setUpRootLayout() {
        try {
            rootLayout = Util.loadFxmlObject(loader, Constants.ROOT_LAYOUT_PATH);

            primaryStage.setScene(new Scene(rootLayout, 800, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMainPage() {
        try {
            SplitPane mainPage = Util.loadFxmlObject(loader, Constants.MAIN_PAGE_PATH);

            rootLayout.getChildren().addAll(mainPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
