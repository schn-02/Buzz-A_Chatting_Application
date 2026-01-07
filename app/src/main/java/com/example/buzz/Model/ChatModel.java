package com.example.buzz.Model;

public class ChatModel {
    public String senderUId; // Renamed to lowercase
    public String message;
    public Long timestamp;

    public ChatModel(String senderUId, String message, Long timestamp) {
        this.senderUId = senderUId; // No change needed here
        this.message = message;
        this.timestamp = timestamp;
    }

    public ChatModel() {
        super();
    }

    public String getSenderUId() {
        return senderUId; // Updated to match the new field name
    }

    public void setSenderUId(String senderUId) {
        this.senderUId = senderUId; // Updated to match the new field name
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
