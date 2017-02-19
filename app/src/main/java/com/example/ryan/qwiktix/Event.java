package com.example.ryan.qwiktix;

import java.util.Date;
import java.util.Scanner;

/**
 * Created by brade_000 on 2/18/2017.
 */

public class Event {

    public String dateTime;
    public String location;
    public String name;
    public String type;


    public Event (){

    }

    public String getDate(){
        return dateTime;
    }
    public void setDate(String dateTime)
    {
        this.dateTime = dateTime;
    }
    public String getLocation(){
        return location;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }


}
