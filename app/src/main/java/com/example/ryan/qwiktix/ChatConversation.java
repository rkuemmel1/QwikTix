package com.example.ryan.qwiktix;

import java.util.Date;
import java.util.StringTokenizer;

public class ChatConversation {

    private String otherUser;
    private String otherUserName;
    private String chatName;
    private String lastMessageText;



    public ChatConversation(String chatName, String otherUser, String otherUserName, String lastMessageText) {
        this.otherUser = otherUser;
        this.otherUserName = otherUserName;
        this.chatName = chatName;
        this.lastMessageText = lastMessageText;

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
    public String getLastMessageText() {
        return lastMessageText;
    }

    public void setLastMessageText(String lastMessageText) {
        this.lastMessageText = lastMessageText;
    }


}
