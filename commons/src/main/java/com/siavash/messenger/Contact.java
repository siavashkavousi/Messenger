package com.siavash.messenger;

/**
 * Created by sia on 6/26/16.
 */
public class Contact {
    private String clientUserName;
    private String contactUserName;
    private String firstName;
    private String lastName;

    public Contact(String clientUserName, String contactUserName, String firstName, String lastName) {
        this.clientUserName = clientUserName;
        this.contactUserName = contactUserName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
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
                "clientUserName='" + clientUserName + '\'' +
                ", contactUserName='" + contactUserName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
