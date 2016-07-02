package com.siavash.messenger;

import com.siavash.messenger.views.ItemView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import retrofit2.Call;
import retrofit2.Callback;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sia on 6/26/16.
 */
public class Util {
    //    public static User user = null;
    public static User user = new User("sia", "123456", "siavash", "kavousi", "12");
    public static Contact currentContact = null;
    public static Group currentGroup = null;
    public static ItemView currentItemView = null;
    public static ObservableList<ItemView> itemViews = FXCollections.observableArrayList();
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

    public static void checkIfUserExists(String userName, Runnable postResultSuccess, Runnable postResultFailure) {
        MainApp.restApi.findUser(userName).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                User user = response.body();
                if (user != null) {
                    log.info("checkIfUserExists: findUser -> " + user.toString());
                    postResultSuccess.run();
                } else {
                    //// FIXME: 7/1/16 notify user not exist in our system
                    postResultFailure.run();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
