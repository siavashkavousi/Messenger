package com.siavash.messenger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
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
    private Stage primaryStage;
    private VBox rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Messenger");


        try {
            setUpRootLayout();
            showFirstPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpRootLayout() throws IOException {
        rootLayout = Util.loadFxmlObject(new FXMLLoader(), Constants.ROOT_LAYOUT_PATH);

        primaryStage.setScene(new Scene(rootLayout, 800, 600));
        primaryStage.show();
    }

    private void showFirstPage() throws IOException {
        HBox mainPage = Util.loadFxmlObject(new FXMLLoader(), Constants.FIRST_PAGE_PATH);
        rootLayout.getChildren().add(mainPage);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
