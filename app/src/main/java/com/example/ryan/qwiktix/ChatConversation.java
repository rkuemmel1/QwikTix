package com.example.ryan.qwiktix;

import java.util.Date;
import java.util.StringTokenizer;

public class ChatConversation {

    private String title;
    private String seller;
    private String requester;


    public ChatConversation(String title, String seller, String requester) {
        this.title= title;
        this.seller = seller;


    }

    public ChatConversation(){

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }
}

