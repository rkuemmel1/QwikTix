package com.example.ryan.qwiktix;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OtherProfileActivity extends BaseActivity {



    private ImageButton PayPalButton;
    private TextView oEmail;
    private TextView oPayPalEmail;
    private TextView oFirstName;
    private TextView oLastName;
    private String oUid;
    private DatabaseReference mUserReference;

    private ListView ticketList;
    private FirebaseListAdapter<Ticket> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Typeface title=Typeface.createFromAsset(getAssets(), "Fonts/Sports.ttf");
        Typeface subtitle=Typeface.createFromAsset(getAssets(), "Fonts/sports2.ttf");
        super.onCreate(savedInstanceState);

        oEmail = (TextView) findViewById(R.id.oEmail);
        oEmail.setTypeface(subtitle);
        oPayPalEmail = (TextView) findViewById(R.id.oPayPalEmail);
        oPayPalEmail.setTypeface(subtitle);
        oFirstName = (TextView) findViewById(R.id.oFirstName);
        oFirstName.setTypeface(title);
        oLastName = (TextView) findViewById(R.id.oLastName);
        oLastName.setTypeface(title);
        PayPalButton = (ImageButton) findViewById(R.id.pPayPalImageButton);
        PayPalButton.setBackgroundDrawable(null);
        ticketList = (ListView) findViewById(R.id.otherTicketList);

        PayPalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri = Uri.parse("https://www.paypal.com/myaccount/transfer/send");
                Intent PaypalIntent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(PaypalIntent);
            }
        });

        Intent intent = getIntent();
        if(intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE") != null){

            oUid = intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE")[0];

            mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(oUid);
            mUserReference.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    oEmail.setText(user.getEmail());
                    oPayPalEmail.setText(user.getPayPalEmail());
                    oFirstName.setText(user.getFirstName());
                    oLastName.setText(user.getLastName());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        myAdapter = new FirebaseListAdapter<Ticket>(this,Ticket.class,R.layout.other_profile_ticket_display,
                getTickets()) {
            @Override
            protected void populateView(android.view.View v, Ticket model, int position) {
                TextView eventName = (TextView)v.findViewById(R.id.eventName);
                TextView price = (TextView)v.findViewById(R.id.price);
                TextView endDate = (TextView)v.findViewById(R.id.endDate);
                ImageButton goToSingleTicketButton = (ImageButton)v.findViewById(R.id.singleTicketButton);
                goToSingleTicketButton.setBackgroundDrawable(null);
                eventName.setTypeface(subtitle);
                price.setTypeface(subtitle);
                endDate.setTypeface(subtitle);

//                Button messageSeller = (Button)v.findViewById(R.id.messageSellerBtn);
//                Button otherProfileButton = (Button)v.findViewById(R.id.otherProfileButton);
                //Set text
                if(model.getuID().equals(oUid)){
                    eventName.setText("EVENT: " + model.getEvent());
                    price.setText("PRICE: $" + Integer.toString(model.getPrice()));
                    endDate.setText("END DATE: " + model.getEndTime());
                    goToSingleTicketButton.setTag(position);
//                messageSeller.setTag(model);
//                otherProfileButton.setTag(model);
                }
                else{
                    eventName.setVisibility(View.GONE);
                    price.setVisibility(View.GONE);
                    endDate.setVisibility(View.GONE);
                    goToSingleTicketButton.setVisibility(android.view.View.GONE);

                }

            }


        };
        ticketList.setAdapter(myAdapter);





    }

    public void goToSingleTicket(android.view.View v){

        int selectedTicket = (int)v.getTag();

        String ticketId = myAdapter.getRef(selectedTicket).getKey();

        Intent singleTicketIntent = new Intent(OtherProfileActivity.this,SingleTicketActivity.class);

        singleTicketIntent.putExtra("com.example.ryan.qwiktix.MESSAGE",new String[] {ticketId});

        startActivity(singleTicketIntent);


    }


    int getContentViewId()
    {
        return R.layout.activity_other_profile;
    }

    int getNavigationMenuItemId()
    {
        return 0;
    }

}
