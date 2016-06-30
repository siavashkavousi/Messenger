package com.siavash.messenger;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created by sia on 6/26/16.
 */
public class Util {
    private static Logger logger = Logger.getLogger(Util.class.getSimpleName());

    public static InputStream getInputStream(String path) throws FileNotFoundException {
        return new FileInputStream(new File(path));
    }

    public static <T> T loadFxmlObject(FXMLLoader loader, String path) throws IOException {
        return loader.load(getInputStream(path));
    }

    public static String getAbsolutePath(String resource){
        return Constants.RESOURCES_PATH + resource + ".fxml";
    }

    public static void changeSceneWithTransition(Scene from, Scene to, int width, int height){
        // Create snapshots with the last state of the scenes
        Parent fromRoot = from.getRoot();
        Parent toRoot = to.getRoot();
        WritableImage wi = new WritableImage(width, height);
        Image image1 = fromRoot.snapshot(new SnapshotParameters(), wi);
        ImageView imageView1 = new ImageView(image1);
        wi = new WritableImage(width, height);
        Image image2 = toRoot.snapshot(new SnapshotParameters(), wi);
        ImageView imageView2 = new ImageView(image2);
        // Create new pane with both images
        imageView1.setTranslateX(0);
        imageView2.setTranslateX(width);
        StackPane pane = new StackPane(imageView1, imageView2);
        pane.setPrefSize(width, height);
        // Replace fromRoot with new pane
        fromRoot.getChildrenUnmodifiable().setAll(pane);
        // Create transition
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(imageView2.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t->{
            // remove pane and restore scene 1
//            fromRoot.getChildrenUnmodifiable().setAll()
//            root1.getChildren().setAll(rectangle1);
            // set scene 2
//            primaryStage.setScene();
        });
        timeline.play();
    }
}
