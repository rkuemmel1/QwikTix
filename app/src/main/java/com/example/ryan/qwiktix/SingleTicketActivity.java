package com.example.ryan.qwiktix;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleTicketActivity extends BaseActivity {



    private TextView tEventName;
    private TextView tEventTime;
    private TextView tPrice;
    private TextView tTimePosted;
    private TextView tStatus;
    private TextView tSellersEmail;
    private TextView tExtraInfo;
    private Ticket myTicket;
    private ImageButton mbuy;
    private ImageButton mMessage;

    private DatabaseReference mticketReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        tEventName = (TextView) findViewById(R.id.tEventName);
        tEventTime = (TextView) findViewById(R.id.tEventTime);
        tPrice = (TextView) findViewById(R.id.tPrice);
        tTimePosted = (TextView) findViewById(R.id.tTimePosted);
        tStatus = (TextView) findViewById(R.id.tStatus);
        tSellersEmail = (TextView) findViewById(R.id.tSellersEmail);
        tExtraInfo = (TextView) findViewById(R.id.tExtraInfo);
        mbuy = (ImageButton) findViewById(R.id.otherProfileButton);
        mbuy.setBackgroundDrawable(null);
        mMessage = (ImageButton) findViewById(R.id.messageSellerBtn);
        mMessage.setBackgroundDrawable(null);


        Typeface title=Typeface.createFromAsset(getAssets(), "Fonts/Sports.ttf");
        Typeface subtitle=Typeface.createFromAsset(getAssets(), "Fonts/sports2.ttf");
        Intent intent = getIntent();
        if(intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE") != null) {

            String ticketId = intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE")[0];
            mticketReference = FirebaseDatabase.getInstance().getReference().child("tickets").child(ticketId);


            mticketReference.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Ticket ticket = dataSnapshot.getValue(Ticket.class);

                    myTicket = ticket;

                    tEventName.setText(ticket.getEvent());
                    tEventName.setTypeface(title);
                    tEventTime.setText(ticket.getEndTime());
                    tEventName.setTypeface(subtitle);
                    tPrice.setText(Integer.toString(ticket.getPrice()));
                    tPrice.setTypeface(subtitle);
                    tTimePosted.setText(ticket.getTimePosted());
                    tTimePosted.setTypeface(subtitle);
                    tStatus.setText(ticket.getStatus());
                    tStatus.setTypeface(subtitle);
                    tSellersEmail.setText(ticket.getUserEmail());
                    tSellersEmail.setTypeface(subtitle);
                    tExtraInfo.setText(ticket.getExtraInfo());
                    tExtraInfo.setTypeface(subtitle);



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }


        public void messageSellerButton(android.view.View v){

        String sellerUid = myTicket.getuID();
        String sellerEmail = myTicket.getUserEmail();

        Intent ChatIntent = new Intent(SingleTicketActivity.this,ChatActivity.class);

        ChatIntent.putExtra("com.example.ryan.qwiktix.MESSAGE",new String[]{sellerUid,sellerEmail} );
        //ChatIntent.putExtra("com.example.ryan.qwiktix.SELLEREMAIL",sellerEmail);

        startActivity(ChatIntent);


    }

    public void goToOtherProfile(android.view.View v){



        String sellerUid = myTicket.getuID();
        String sellerEmail = myTicket.getUserEmail();

        Intent otherProfileIntent = new Intent(SingleTicketActivity.this,OtherProfileActivity.class);

        otherProfileIntent.putExtra("com.example.ryan.qwiktix.MESSAGE",new String[]{sellerUid,sellerEmail} );

        startActivity(otherProfileIntent);


    }




    int getContentViewId() {
        return R.layout.activity_single_ticket;
    }

    int getNavigationMenuItemId() {
        return 0;
    }
}
