package com.example.ryan.qwiktix;

import java.util.Date;
import java.util.StringTokenizer;

public class ChatConversation {

    private String otherUser;
    private String otherUserName;
    private String chatName;


    public ChatConversation(String chatName,String otherUser, String otherUserName) {
        this.otherUser = otherUser;
        this.otherUserName = otherUserName;
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
    public String getOtherUserName() {
        return otherUserName;
    }

    public void setOtherUserName(String otherUserName) {
        this.otherUserName = otherUserName;
    }
    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }



}
