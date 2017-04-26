package com.example.ryan.qwiktix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.ArrayAdapter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";

    FirebaseListAdapter<Ticket> myAdapter;

    private EditText Search;

    private String pUid;
    private String pUserEmail;

    private DatabaseReference mDatabase;

    private AutoCompleteTextView actv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Search = (EditText) findViewById(R.id.Search);

        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        ListView ticketList = (ListView)findViewById(R.id.searchList);


        myAdapter = new FirebaseListAdapter<Ticket>(this,Ticket.class,R.layout.ticket_display,
                getTickets()) {
            @Override
            protected void populateView(android.view.View v, Ticket model, int position) {
                mDatabase.child("events").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
                        for (com.google.firebase.database.DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                            //Get the suggestion by childing the key of the string you want to get.
                            String suggestion = suggestionSnapshot.child("name").getValue(String.class);
                            //Add the retrieved string to the list
                            autoComplete.add(suggestion);

                            if(model.getEvent().contains(Search.getText().toString())) {
                                TextView eventName = (TextView) v.findViewById(R.id.eventName);
                                TextView price = (TextView) v.findViewById(R.id.price);
                                TextView endDate = (TextView) v.findViewById(R.id.endDate);
                                //Set text
                                String eventText = "EVENT: " + model.getEvent();
                                String priceText = "PRICE: $" + Integer.toString(model.getPrice());
                                String endDateText = "END DATE " + model.getEndTime();

                                eventName.setText(eventText);
                                price.setText(priceText);
                                endDate.setText(endDateText);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        };

        ticketList.setAdapter(myAdapter);

        //Child the root before all the push() keys are found and add a ValueEventListener()
        mDatabase.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
                for (com.google.firebase.database.DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                    //Get the suggestion by childing the key of the string you want to get.
                    String suggestion = suggestionSnapshot.child("name").getValue(String.class);
                    //Add the retrieved string to the list
                    autoComplete.add(suggestion);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        AutoCompleteTextView ACTV = (AutoCompleteTextView) Search;
        ACTV.setAdapter(autoComplete);



    }

    int getContentViewId()
    {
        return R.layout.activity_search;
    }

    int getNavigationMenuItemId()
    {
        return R.id.action_search;
    }

}