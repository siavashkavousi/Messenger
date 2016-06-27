package com.siavash.messenger;

/**
 * Created by sia on 6/26/16.
 */
public class Contact {
    private String receiverUserName;
    private String senderUserName;
    private String firstName;
    private String lastName;

    public Contact(String receiverUserName, String senderUserName, String firstName, String lastName) {
        this.receiverUserName = receiverUserName;
        this.senderUserName = senderUserName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getReceiverUserName() {
        return receiverUserName;
    }

    public void setReceiverUserName(String receiverUserName) {
        this.receiverUserName = receiverUserName;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
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
                "receiverUserName='" + receiverUserName + '\'' +
                ", senderUserName='" + senderUserName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
