package com.example.ryan.qwiktix;

import android.content.Intent;
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
        super.onCreate(savedInstanceState);

        oEmail = (TextView) findViewById(R.id.oEmail);
        oPayPalEmail = (TextView) findViewById(R.id.oPayPalEmail);
        oFirstName = (TextView) findViewById(R.id.oFirstName);
        oLastName = (TextView) findViewById(R.id.oLastName);
        PayPalButton = (ImageButton) findViewById(R.id.pPayPalImageButton);
        ticketList = (ListView) findViewById(R.id.otherTicketList);

        PayPalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent PaypalIntent = new Intent(OtherProfileActivity.this,PaypalActivity.class);
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

        myAdapter = new FirebaseListAdapter<Ticket>(this,Ticket.class,R.layout.profile_ticket_display,
                getTickets()) {
            @Override
            protected void populateView(android.view.View v, Ticket model, int position) {
                TextView eventName = (TextView)v.findViewById(R.id.eventName);
                TextView price = (TextView)v.findViewById(R.id.price);
                TextView endDate = (TextView)v.findViewById(R.id.endDate);


//                Button messageSeller = (Button)v.findViewById(R.id.messageSellerBtn);
//                Button otherProfileButton = (Button)v.findViewById(R.id.otherProfileButton);
                //Set text
                if(model.getuID().equals(getUid())){
                    eventName.setText("EVENT: " + model.getEvent());
                    price.setText("PRICE: $" + Integer.toString(model.getPrice()));
                    endDate.setText("END DATE: " + model.getEndTime());

//                messageSeller.setTag(model);
//                otherProfileButton.setTag(model);
                }
                else{
                    eventName.setVisibility(View.GONE);
                    price.setVisibility(View.GONE);
                    endDate.setVisibility(View.GONE);

                }

            }


        };
        ticketList.setAdapter(myAdapter);





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
