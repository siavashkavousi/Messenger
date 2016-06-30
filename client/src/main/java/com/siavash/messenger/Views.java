package com.siavash.messenger;

/**
 * Created by sia on 7/1/16.
 */
public enum Views {
    CONTACT("contact_id", "contact_view"),
    CONTACT_MESSAGES("contact_messages_id", "contact_messages_view");

    public String id, resource;

    Views(String id, String resource) {
        this.id = id;
        this.resource = resource;
    }
}
