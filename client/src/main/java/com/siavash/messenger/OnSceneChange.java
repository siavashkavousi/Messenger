package com.siavash.messenger;

import javafx.scene.Group;
import javafx.scene.SubScene;

/**
 * Created by sia on 6/30/16.
 */
public interface OnSceneChange {
    void changeScene(Group from, Group to, boolean withAnimation);
}
