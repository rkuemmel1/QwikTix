package com.example.ryan.qwiktix;

import java.util.Date;
import java.util.StringTokenizer;

public class ChatConversation {

    private String otherUser;
    private String otherUserfName;
    private String chatName;


    public ChatConversation(String chatName,String otherUser, String otherUserfName) {
        this.otherUser = otherUser;
        this.otherUserfName = otherUserfName;
        this.chatName = chatName;

    }

    public ChatConversation(){

    }
    public String getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(String otherUser) {
        this.otherUser = otherUser;
    }
    public String getOtherUserfName() {
        return otherUserfName;
    }

    public void setOtherUserfName(String otherUserfName) {
        this.otherUserfName = otherUserfName;
    }
    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }



}
