package com.siavash.messenger;

import java.util.List;

/**
 * Created by sia on 7/2/16.
 */
public class GroupMembers {
    private String groupId;
    private List<String > membersUserName;

    public GroupMembers(String groupId, List<String> membersUserName) {
        this.groupId = groupId;
        this.membersUserName = membersUserName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getMembersUserName() {
        return membersUserName;
    }

    public void setMembersUserName(List<String> membersUserName) {
        this.membersUserName = membersUserName;
    }
}
