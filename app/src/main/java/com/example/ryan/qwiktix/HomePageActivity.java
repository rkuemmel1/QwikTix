package com.example.ryan.qwiktix;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.core.view.View;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

/*
    Modified from https://firebaseui.com/docs/android/index.html in relation with the
    FirebaseListAdapter


 */

public class HomePageActivity extends BaseActivity {

    FirebaseListAdapter<Ticket> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView ticketList = (ListView)findViewById(R.id.homeList);
        Button sellingButton = (Button)findViewById(R.id.sellButton);
        Button chattingButton = (Button)findViewById(R.id.chatButton);

        myAdapter = new FirebaseListAdapter<Ticket>(this,Ticket.class,R.layout.ticket_display,
                getTickets()) {
            @Override
            protected void populateView(android.view.View v, Ticket model, int position) {
                TextView eventName = (TextView)v.findViewById(R.id.eventName);
                TextView price = (TextView)v.findViewById(R.id.price);
                TextView endDate = (TextView)v.findViewById(R.id.endDate);
                //Set text
                eventName.setText("EVENT: " + model.getEvent());
                price.setText("PRICE: $" + Integer.toString(model.getPrice()));
                endDate.setText("END DATE: " + model.getEndTime());
            }
        };
        ticketList.setAdapter(myAdapter);

        //sell button: goes to seller's profile
         sellingButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(HomePageActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        //chat button: chats with seller
        chattingButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(HomePageActivity.this,ChatActivity.class);
                startActivity(intent);
            }
        });

    }

    int getContentViewId()
    {
        return R.layout.activity_home_page;
    }

    int getNavigationMenuItemId()
    {
        return R.id.action_home;
    }

}
