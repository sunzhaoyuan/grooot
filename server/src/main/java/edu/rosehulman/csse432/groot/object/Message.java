package edu.rosehulman.csse432.groot.object;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Message {

    @JsonIgnore
    private String key;

    private String chatRoom;
    private String sender;
    private String text;
    private Long timeStamp;

    public Message() {
    }

    public Message(String chatRoom, String sender, String text) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.text = text;
        this.timeStamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Message{" +
                "key='" + key + '\'' +
                ", chatRoom='" + chatRoom + '\'' +
                ", sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
