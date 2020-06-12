package com.example.database;

public class ChatUser {
    String chat_table_name;
    String id;

    public ChatUser(){

    }

    public ChatUser(String chat_table_name, String id) {
        this.chat_table_name = chat_table_name;
        this.id = id;
    }

    public String getChat_table_name() {
        return chat_table_name;
    }

    public void setChat_table_name(String chat_table_name) {
        this.chat_table_name = chat_table_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
