package com.example.ryan.qwiktix;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
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
        //final Button messageButton = (Button)findViewById(R.id.messageSellerBtn);
        //final TextView seller = (TextView) findViewById(R.id.seller); //assuming seller field is added
        //final TextView event= (TextView) findViewById(R.id.eventName);

        ListView ticketList = (ListView)findViewById(R.id.homeList);

        myAdapter = new FirebaseListAdapter<Ticket>(this,Ticket.class,R.layout.ticket_display,
                getTickets()) {
            @Override
            protected void populateView(android.view.View v, Ticket model, int position) {
                TextView eventName = (TextView)v.findViewById(R.id.eventName);
                TextView price = (TextView)v.findViewById(R.id.price);
                TextView endDate = (TextView)v.findViewById(R.id.endDate);
                Button messageSeller = (Button)v.findViewById(R.id.messageSellerBtn);
                Button otherProfileButton = (Button)v.findViewById(R.id.otherProfileButton);
                //Set text
                eventName.setText("EVENT: " + model.getEvent());
                price.setText("PRICE: $" + Integer.toString(model.getPrice()));
                endDate.setText("END DATE: " + model.getEndTime());
                messageSeller.setTag(model);
                otherProfileButton.setTag(model);
            }


        };
        ticketList.setAdapter(myAdapter);


    }
    public void messageSellerButton(android.view.View v){

        LinearLayout vwParentRow = (LinearLayout)v.getParent();
        int c = Color.CYAN;
        vwParentRow.setBackgroundColor(c);

        Ticket selectedTicket= (Ticket)v.getTag();
        String sellerUid = selectedTicket.getuID();
        String sellerEmail = selectedTicket.getUserEmail();

        Intent ChatIntent = new Intent(HomePageActivity.this,ChatActivity.class);

        ChatIntent.putExtra("com.example.ryan.qwiktix.MESSAGE",new String[]{sellerUid,sellerEmail} );
        //ChatIntent.putExtra("com.example.ryan.qwiktix.SELLEREMAIL",sellerEmail);

        startActivity(ChatIntent);


    }

    public void goToOtherProfile(android.view.View v){

        LinearLayout vwParentRow = (LinearLayout)v.getParent();
        int c = Color.GREEN;
        vwParentRow.setBackgroundColor(c);

        Ticket selectedTicket= (Ticket)v.getTag();
        String sellerUid = selectedTicket.getuID();
        String sellerEmail = selectedTicket.getUserEmail();

        Intent otherProfileIntent = new Intent(HomePageActivity.this,OtherProfileActivity.class);

        otherProfileIntent.putExtra("com.example.ryan.qwiktix.MESSAGE",new String[]{sellerUid,sellerEmail} );

        startActivity(otherProfileIntent);


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
