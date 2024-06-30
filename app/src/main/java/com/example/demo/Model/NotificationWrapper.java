package com.example.demo.Model;

public class NotificationWrapper {
    private final String notification;
    private final long timestamp;

    public NotificationWrapper(String notification, long timestamp) {
        this.notification = notification;
        this.timestamp = timestamp;
    }
    public String getNotification(){
        return notification;
    }
    public long getTimestamp(){
        return timestamp;
    }
}
