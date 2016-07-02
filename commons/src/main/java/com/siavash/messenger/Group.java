package com.siavash.messenger;

/**
 * Created by sia on 7/2/16.
 */
public class Group {
    private String groupId;
    private String creatorId;
    private String name;

    public Group(String groupId, String creatorId, String name) {
        this.groupId = groupId;
        this.creatorId = creatorId;
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
