package com.siavash.messenger;

import java.util.List;

/**
 * Created by sia on 6/26/16.
 */
public class Message {
    private String sender;
    private String content;
    private List<String> receivers;

    public Message(String sender, String content, List<String> receivers) {
        this.sender = sender;
        this.content = content;
        this.receivers = receivers;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                ", receivers=" + receivers +
                '}';
    }
}
