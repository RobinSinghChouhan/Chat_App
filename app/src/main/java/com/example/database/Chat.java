package com.example.database;

public class Chat {
    String id;
    String from;
    String chat;

    public Chat(){

    }

    public Chat(String id, String from, String chat) {
        this.id = id;
        this.from = from;
        this.chat = chat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
