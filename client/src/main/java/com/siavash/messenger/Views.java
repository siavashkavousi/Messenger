package com.siavash.messenger;

/**
 * Created by sia on 7/1/16.
 */
public enum Views {
    CONTACT("contact_id", "view_contact"),
    CONTACT_MESSAGES("contact_messages_id", "view_contact_messages");

    public String id, resource;

    Views(String id, String resource) {
        this.id = id;
        this.resource = resource;
    }
}
