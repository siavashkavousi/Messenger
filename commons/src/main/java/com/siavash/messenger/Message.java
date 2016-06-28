package com.siavash.messenger;

import java.util.List;

/**
 * Created by sia on 6/26/16.
 */
public class Message {
    private String client;
    private String content;
    private List<String> contacts;

    public Message(String client, String content, List<String> contacts) {
        this.client = client;
        this.content = content;
        this.contacts = contacts;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "Message{" +
                "client='" + client + '\'' +
                ", content='" + content + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
