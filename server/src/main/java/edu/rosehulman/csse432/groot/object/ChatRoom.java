package edu.rosehulman.csse432.groot.object;

import org.codehaus.jackson.annotate.JsonIgnore;

public class ChatRoom implements Comparable<ChatRoom> {
    public static final String ROOM_NAME = "roomName"; // for FB path

    @JsonIgnore
    public String key;

    public String clientName;
    public String hostName;
    public String roomName;
    public boolean waiting;
    public Long timestamp;

    public ChatRoom() {
    }

    public ChatRoom(String clientName, String hostName, String roomName, boolean isWaiting) {
        this.clientName = clientName;
        this.hostName = hostName;
        this.roomName = roomName;
        this.waiting = isWaiting;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getClientName() {
        return clientName;
    }

    public String getHostName() {
        return hostName;
    }

    public String getRoomName() {
        return roomName;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "clientName='" + clientName + '\'' +
                ", hostName='" + hostName + '\'' +
                ", roomName='" + roomName + '\'' +
                ", waiting=" + waiting +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int compareTo(ChatRoom o) {
        return timestamp.compareTo(o.timestamp);
    }
}
