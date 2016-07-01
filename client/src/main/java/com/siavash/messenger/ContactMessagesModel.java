package com.siavash.messenger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by sia on 7/1/16.
 */
public class ContactMessagesModel {
    private StringProperty clientUserName = new SimpleStringProperty();
    private StringProperty contactUserName = new SimpleStringProperty();
    private StringProperty contactFirstName = new SimpleStringProperty();
    private StringProperty contactLastName = new SimpleStringProperty();

    private ContactMessagesModel(Builder builder) {
        setClientUserName(builder.clientUserName);
        setContactUserName(builder.contactUserName);
        setContactFirstName(builder.contactFirstName);
        setContactLastName(builder.contactLastName);
    }

    public static Builder newContactMessagesModel() {
        return new Builder();
    }

    public String getClientUserName() {
        return clientUserName.get();
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName.set(clientUserName);
    }

    public StringProperty clientUserNameProperty() {
        return clientUserName;
    }

    public String getContactUserName() {
        return contactUserName.get();
    }

    public void setContactUserName(String contactUserName) {
        this.contactUserName.set(contactUserName);
    }

    public StringProperty contactUserNameProperty() {
        return contactUserName;
    }

    public String getContactFirstName() {
        return contactFirstName.get();
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName.set(contactFirstName);
    }

    public StringProperty contactFirstNameProperty() {
        return contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName.get();
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName.set(contactLastName);
    }

    public StringProperty contactLastNameProperty() {
        return contactLastName;
    }

    public static final class Builder {
        private String clientUserName;
        private String contactUserName;
        private String contactFirstName;
        private String contactLastName;

        private Builder() {
        }

        public ContactMessagesModel build() {
            return new ContactMessagesModel(this);
        }

        public Builder clientUserName(String clientUserName) {
            this.clientUserName = clientUserName;
            return this;
        }

        public Builder contactUserName(String contactUserName) {
            this.contactUserName = contactUserName;
            return this;
        }

        public Builder contactFirstName(String contactFirstName) {
            this.contactFirstName = contactFirstName;
            return this;
        }

        public Builder contactLastName(String contactLastName) {
            this.contactLastName = contactLastName;
            return this;
        }
    }
}
