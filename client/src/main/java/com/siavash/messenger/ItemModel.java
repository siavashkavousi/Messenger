package com.siavash.messenger;

/**
 * Created by sia on 7/2/16.
 */
public class ItemModel {
    private String userName;
    private String name;

    public ItemModel(String userName, String name) {
        this.userName = userName;
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
