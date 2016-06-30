package com.siavash.messenger;

/**
 * Created by sia on 6/30/16.
 */
public enum Screens {
    ADD_CONTACT("add_contact_id", "add_contact_layout"),
    CONTACT_LIST("contact_list_id", "contact_list_layout"),
    CONTACT_MESSAGES("contact_messages_id", "contact_messages_layout"),
    FIRST_PAGE("first_page_id", "first_page_layout"),
    SIGN_IN("sign_in", "sign_in_layout"),
    SIGN_UP("sign_up", "sign_up_layout"),
    MENU("menu_id", "menu_layout");

    public String id, resource;

    Screens(String id, String resource) {
        this.id = id;
        this.resource = resource;
    }
}
