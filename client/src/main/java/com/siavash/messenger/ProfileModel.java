package com.siavash.messenger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by sia on 7/1/16.
 */
public class ProfileModel {
    private StringProperty userName = new SimpleStringProperty();

    private ProfileModel(Builder builder) {
        setUserName(builder.userName);
    }

    public static Builder newProfileModel() {
        return new Builder();
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public static final class Builder {
        private String userName;

        private Builder() {
        }

        public ProfileModel build() {
            return new ProfileModel(this);
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }
    }
}
