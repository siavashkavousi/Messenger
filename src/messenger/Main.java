package messenger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private VBox rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Messenger");

        setUpRootLayout();
        showMainPage();
    }

    private void setUpRootLayout() {
        try {
            rootLayout = FXMLLoader.load(getClass().getResource("root_layout.fxml"));

            primaryStage.setScene(new Scene(rootLayout, 800, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMainPage(){
        try {
            SplitPane mainPage = FXMLLoader.load(getClass().getResource("main_page.fxml"));

            rootLayout.getChildren().addAll(mainPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
