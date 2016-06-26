package com.siavash.messenger;

import javafx.fxml.FXMLLoader;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created by sia on 6/26/16.
 */
public class Util {
    private static Logger logger = Logger.getLogger(Util.class.getSimpleName());

    public static <T> T loadFxmlObject(FXMLLoader loader, String path) throws IOException {
        return loader.load(new FileInputStream(new File(path)));
    }
}
