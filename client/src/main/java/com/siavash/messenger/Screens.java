package com.siavash.messenger;

/**
 * Created by sia on 6/30/16.
 */
public enum Screens {
    ADD_CONTACT("add_contact_id", "layout_add_contact"),
    CONTACT_LIST("contact_list_id", "layout_contact_list"),
    FIRST_PAGE("first_page_id", "layout_first_page"),
    SIGN_IN("sign_in", "layout_sign_in"),
    SIGN_UP("sign_up", "layout_sign_up"),
    CONTACT_MESSAGES("contact_messages_id", "layout_contact_messages"),
    MENU("menu_id", "layout_menu"),
    PROFILE("profile_id", "layout_profile"),
    EDIT_PROFILE("edit_profile_id", "layout_edit_profile"),
    CONTACT_PROFILE("contact_profile_id", "layout_contact_profile"),
    GROUP_MESSAGES("group_messages_id", "layout_group_messages"),
    GROUP_PROFILE("group_profile_id", "layout_group_profile"),
    ADD_GROUP("add_group_id", "layout_add_group");

    public String id, resource;

    Screens(String id, String resource) {
        this.id = id;
        this.resource = resource;
    }
}
