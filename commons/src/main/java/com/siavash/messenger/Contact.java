package com.siavash.messenger;

/**
 * Created by sia on 6/26/16.
 */
public class Contact extends User {
    private String parentUserName;

    public Contact(String parentUserName, String userName, String firstName, String lastName, String phoneNumber) {
        super(userName, firstName, lastName, phoneNumber);
        this.parentUserName = parentUserName;
    }

    public String getParentUserName() {
        return parentUserName;
    }

    public void setParentUserName(String parentUserName) {
        this.parentUserName = parentUserName;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "parentUserName='" + parentUserName + '\'' +
                '}';
    }
}
