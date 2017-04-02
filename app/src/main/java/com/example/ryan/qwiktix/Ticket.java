package com.example.ryan.qwiktix;

import java.util.Date;

/**
 * Created by brade_000 on 2/18/2017.
 *
 * will probably want to take in:
 *  - seat location
 *  - number of seats
 *  - time of event
 *  - any additional information field
 */

public class Ticket {

    public String endTime;
    public String event;
    public int price;
    public String status;
    public String timePosted;
    public String userEmail;
    public String uID;

    public Ticket(String event, int price,String timePosted, String endTime, String userEmail, String uID){
        this.event = event;
        this.price = price;
        this.timePosted = timePosted;
        this.endTime = endTime;
        this.userEmail = userEmail;
        this.uID = uID;
        status = "pending";
        //timePosted = "3/30/2017";
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(String timePosted) {
        this.timePosted = timePosted;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }
}
