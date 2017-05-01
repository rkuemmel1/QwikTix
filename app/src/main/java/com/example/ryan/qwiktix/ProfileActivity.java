package com.example.ryan.qwiktix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends BaseActivity {

    private Button bLogOut;
    private Button bEditProfile;
    private Button bChat;
    //private ImageButton PayPalButton;
    private TextView pEmail;
    private TextView pPayPalEmail;
    private TextView pFirstName;
    private TextView pLastName;
    private String pUid;
    private DatabaseReference mUserReference;

    private ListView ticketList;
    private FirebaseListAdapter<Ticket> myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bChat = (Button) findViewById(R.id.BChat);
        bLogOut = (Button) findViewById(R.id.pLogOut);
        bEditProfile = (Button) findViewById(R.id.pEditProfile);
        ticketList = (ListView) findViewById(R.id.ticketList);
        //PayPalButton = (ImageButton) findViewById(R.id.pPayPalImageButton);

        bLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                signOut();

            }
        });

        bEditProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent editProfileIntent = new Intent(ProfileActivity.this,EditProfileActivity.class);
                startActivity(editProfileIntent);
            }
        });

//        bChat.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent ChatIntent = new Intent(ProfileActivity.this,ChatActivity.class);
//                startActivity(ChatIntent);
//            }
//        });



        pUid = getmAuth().getCurrentUser().getUid();

        mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(pUid);
        pEmail = (TextView) findViewById(R.id.pEmail);
        pPayPalEmail = (TextView) findViewById(R.id.pPayPalEmail);
        pFirstName = (TextView) findViewById(R.id.pFirstName);
        pLastName = (TextView) findViewById(R.id.pLastName);
        mUserReference.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                pEmail.setText(user.getEmail());
                pPayPalEmail.setText(user.getPayPalEmail());
                pFirstName.setText(user.getFirstName());
                pLastName.setText(user.getLastName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myAdapter = new FirebaseListAdapter<Ticket>(this,Ticket.class,R.layout.profile_ticket_display,
                getTickets()) {
            @Override
            protected void populateView(android.view.View v, Ticket model, int position) {
                TextView eventName = (TextView)v.findViewById(R.id.eventName);
                TextView price = (TextView)v.findViewById(R.id.price);
                TextView endDate = (TextView)v.findViewById(R.id.endDate);
                Button ticketAvailable = (Button)v.findViewById(R.id.ticketAvailableButton);
                Button ticketPending = (Button)v.findViewById(R.id.ticketPendingButton);
                Button ticketSold = (Button)v.findViewById(R.id.ticketSoldButton);

//                Button messageSeller = (Button)v.findViewById(R.id.messageSellerBtn);
//                Button otherProfileButton = (Button)v.findViewById(R.id.otherProfileButton);
                //Set text
                if(model.getuID().equals(getUid())){
                    eventName.setText("EVENT: " + model.getEvent());
                    price.setText("PRICE: $" + Integer.toString(model.getPrice()));
                    endDate.setText("END DATE: " + model.getEndTime());
                    ticketAvailable.setTag(position);
                    ticketPending.setTag(position);
                    ticketSold.setTag(position);
//                messageSeller.setTag(model);
//                otherProfileButton.setTag(model);
                }
                else{
                    eventName.setVisibility(View.GONE);
                    price.setVisibility(View.GONE);
                    endDate.setVisibility(View.GONE);
                    ticketAvailable.setVisibility(View.GONE);
                    ticketPending.setVisibility(View.GONE);
                    ticketSold.setVisibility(View.GONE);
                }

            }


        };
        ticketList.setAdapter(myAdapter);



    }



    public void ticketAvailable(android.view.View v) {

            int selectedTicker = (int)v.getTag();
            String ticketString = myAdapter.getRef(selectedTicker).getKey();

            DatabaseReference ticketReference = FirebaseDatabase.getInstance().getReference().child("tickets").child(ticketString).child("status");
            ticketReference.setValue("Available");


            Toast.makeText(ProfileActivity.this, "Changed ticket to available", Toast.LENGTH_SHORT).show();

    }

    public void ticketPending(android.view.View v) {

        int selectedTicker = (int)v.getTag();
        String ticketString = myAdapter.getRef(selectedTicker).getKey();

        DatabaseReference ticketReference = FirebaseDatabase.getInstance().getReference().child("tickets").child(ticketString).child("status");
        ticketReference.setValue("Pending");

        Toast.makeText(ProfileActivity.this, "Changed ticket to pending", Toast.LENGTH_SHORT).show();
    }

    public void ticketSold(android.view.View v) {

        int selectedTicker = (int)v.getTag();
        String ticketString = myAdapter.getRef(selectedTicker).getKey();

        DatabaseReference ticketReference = FirebaseDatabase.getInstance().getReference().child("tickets").child(ticketString).child("status");
        ticketReference.setValue("Sold");

        Toast.makeText(ProfileActivity.this, "Changed ticket to sold", Toast.LENGTH_SHORT).show();
    }






    int getContentViewId()
    {
        return R.layout.activity_profile;
    }

    int getNavigationMenuItemId()
    {
        return R.id.action_profile;
    }
}
