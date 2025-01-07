package com.example.SCM.helper;

public class Message {

    private String content;
    private MessageType type;

    public Message() {
        this.type = MessageType.blue; // Set default value for `type`
    }

    public Message(String content, MessageType type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
