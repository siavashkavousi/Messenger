package com.siavash.messenger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by sia on 7/2/16.
 */
public class GroupMessagesModel {
    private StringProperty creatorId = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty groupId = new SimpleStringProperty();

    private GroupMessagesModel(Builder builder) {
        setCreatorId(builder.creatorId);
        setName(builder.name);
        setGroupId(builder.groupId);
    }

    public static Builder newGroupMessagesModel() {
        return new Builder();
    }

    public String getCreatorId() {
        return creatorId.get();
    }

    public void setCreatorId(String creatorId) {
        this.creatorId.set(creatorId);
    }

    public StringProperty creatorIdProperty() {
        return creatorId;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getGroupId() {
        return groupId.get();
    }

    public void setGroupId(String groupId) {
        this.groupId.set(groupId);
    }

    public StringProperty groupIdProperty() {
        return groupId;
    }

    public static final class Builder {
        private String creatorId;
        private String name;
        private String groupId;

        private Builder() {
        }

        public GroupMessagesModel build() {
            return new GroupMessagesModel(this);
        }

        public Builder creatorId(String creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public Builder name(String title) {
            this.name = title;
            return this;
        }

        public Builder groupId(String groupId) {
            this.groupId = groupId;
            return this;
        }
    }
}
