package com.siavash.messenger;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sia on 6/26/16.
 */
public class Util {
    public static User user = null;
    public static List<Contact> contacts = null;
    private static Logger log = Logger.getLogger(Util.class.getSimpleName());

    public static InputStream getInputStream(String path) throws FileNotFoundException {
        return new FileInputStream(new File(path));
    }

    public static <T> T loadFxmlObject(FXMLLoader loader, String path) throws IOException {
        return loader.load(getInputStream(path));
    }

    public static String getAbsolutePath(String resource) {
        return Constants.RESOURCES_PATH + resource + ".fxml";
    }

    public static <T> void addItemListToListView(ListView<T> listView, List<T> data) {
        listView.setItems(FXCollections.observableArrayList(data));
    }

    public static <T> boolean checkResponseMessage(retrofit2.Response<T> response) {
        if (!response.isSuccessful()) {
            try {
                log.info(response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
}
