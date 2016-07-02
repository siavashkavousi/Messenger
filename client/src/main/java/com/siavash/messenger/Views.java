package com.siavash.messenger;

/**
 * Created by sia on 7/1/16.
 */
public enum Views {
    CONTACT("contact_id", "view_contact"),
    ITEM("item_id", "view_item");

    public String id, resource;

    Views(String id, String resource) {
        this.id = id;
        this.resource = resource;
    }
}
