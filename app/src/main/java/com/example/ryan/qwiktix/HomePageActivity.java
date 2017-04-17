package com.example.ryan.qwiktix;

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
        //final Button messageButton = (Button)findViewById(R.id.messageSellerBtn);
        //final TextView seller = (TextView) findViewById(R.id.seller); //assuming seller field is added
        final TextView event= (TextView) findViewById(R.id.eventName);

        ListView ticketList = (ListView)findViewById(R.id.homeList);

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
        /*messageButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                //ChatConversation newConvo = new ChatConversation(event,seller,);


                //ChatMessage messageToSend = new ChatMessage(input.getText().toString(),mAuth.getCurrentUser().getEmail());
                //Read the input field and push a new instance
                //of ChatMessage to the Firebase database
              //  getMessages().child(conversation).push().setValue(messageToSend);
                // Clear the input
              //  input.setText("");
            }
        });*/

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
