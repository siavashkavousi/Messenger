package com.siavash.messenger;

/**
 * Created by sia on 7/2/16.
 */
public class Request {
    private String message;

    public Request(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
