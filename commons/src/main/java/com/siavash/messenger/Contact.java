package com.siavash.messenger;

/**
 * Created by sia on 6/26/16.
 */
public class Contact {
    private String parentUserName;
    private String contactUserName;
    private String firstName;
    private String lastName;

    public Contact(String parentUserName, String contactUserName, String firstName, String lastName) {
        this.parentUserName = parentUserName;
        this.contactUserName = contactUserName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getParentUserName() {
        return parentUserName;
    }

    public void setParentUserName(String parentUserName) {
        this.parentUserName = parentUserName;
    }

    public String getContactUserName() {
        return contactUserName;
    }

    public void setContactUserName(String contactUserName) {
        this.contactUserName = contactUserName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "parentUserName='" + parentUserName + '\'' +
                ", contactUserName='" + contactUserName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
