package com.example.ryan.qwiktix;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";

    private EditText Search;
    private ListView searchList;
    private RadioButton userRadio;
    private RadioButton eventRadio;

    private DatabaseReference mDatabase;

    //ArrayList<String> strings = new ArrayList<String>();

    FirebaseListAdapter<Ticket> myEventAdapter;
    FirebaseListAdapter<User> myUserAdapter;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        mDatabase = FirebaseDatabase.getInstance().getReference();

        Search = (EditText) findViewById(R.id.Search);
        searchList = (ListView) findViewById(R.id.searchList);

        userRadio = (RadioButton) findViewById(R.id.radio_user);
        eventRadio = (RadioButton) findViewById(R.id.radio_event);

        Search.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

        searchEventAdapter();
        searchList.setAdapter(myEventAdapter);

//        mDatabase.child("tickets").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String ticket = snapshot.child("event").getValue(String.class) + " " + snapshot.child("endTime").getValue(String.class);
//                    strings.add(ticket);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError firebaseError) {
//
//            }
//        });

        //adapter = new ArrayAdapter<String>(SearchActivity.this, R.layout.search_results, R.id.results, strings);


        //searchList.setAdapter(myAdapter);





        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    int getContentViewId() {
        return R.layout.activity_search;
    }

    int getNavigationMenuItemId() {
        return R.id.action_search;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_user:
                if (checked)
                    searchUserAdapter();
                    searchList.setAdapter(myUserAdapter);
                    Search.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            searchUserAdapter();
                            searchList.setAdapter(myUserAdapter);
                            //SearchActivity.this.myAdapter.getItem(1);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                    break;
            case R.id.radio_event:
                if (checked)
                    searchEventAdapter();
                    searchList.setAdapter(myEventAdapter);
                    Search.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            searchEventAdapter();
                            searchList.setAdapter(myEventAdapter);
                            //SearchActivity.this.myAdapter.getItem(1);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                    break;
        }
    }

    public void searchEventAdapter(){
        myEventAdapter = new FirebaseListAdapter<Ticket>(this,Ticket.class,R.layout.ticket_display_search,
                getTickets()) {
            @Override
            protected void populateView(android.view.View v, Ticket model, int position) {
                Typeface typeface=Typeface.createFromAsset(getAssets(), "Fonts/Sports.ttf");
                Typeface subtitle=Typeface.createFromAsset(getAssets(), "Fonts/sports2.ttf");

                TextView eventName = (TextView) v.findViewById(R.id.eventName);
                TextView price = (TextView) v.findViewById(R.id.price);
                TextView endDate = (TextView) v.findViewById(R.id.endDate);
                TextView user = (TextView) v.findViewById(R.id.user);

//                ImageButton messageSeller = (ImageButton) v.findViewById(R.id.messageSellerBtn);
//                messageSeller.setBackgroundDrawable(null);
//                ImageButton otherProfileButton = (ImageButton) v.findViewById(R.id.otherProfileButton);
//                otherProfileButton.setBackgroundDrawable(null);
                ImageButton goToSingleTicketButton = (ImageButton)v.findViewById(R.id.singleTicketButton);
                goToSingleTicketButton.setBackgroundDrawable(null);

                eventName.setVisibility(View.VISIBLE);
                price.setVisibility(View.VISIBLE);
                endDate.setVisibility(View.VISIBLE);
                user.setVisibility(View.VISIBLE);
//                messageSeller.setVisibility(View.VISIBLE);
//                otherProfileButton.setVisibility(View.VISIBLE);

                if(model.getEvent().toLowerCase().contains(Search.getText().toString().toLowerCase()) && !model.getStatus().contains("Sold")) {
                    //Set text
                    eventName.setText(model.getEvent());
                    eventName.setTypeface(typeface);
                    price.setText("PRICE: $" + Integer.toString(model.getPrice()));
                    price.setTypeface(subtitle);
                    endDate.setText("END DATE: " + model.getEndTime());
                    endDate.setTypeface(subtitle);
                    user.setText("USER: " + model.getUserEmail());
                    user.setTypeface(subtitle);
//                    messageSeller.setTag(model);
//                    otherProfileButton.setTag(model);
                    goToSingleTicketButton.setTag(position);

                }

                else
                {

                    eventName.setVisibility(View.GONE);
                    price.setVisibility(View.GONE);
                    endDate.setVisibility(View.GONE);
                    user.setVisibility(View.GONE);
                    goToSingleTicketButton.setVisibility(View.GONE);
//                    messageSeller.setVisibility(View.GONE);
//                    otherProfileButton.setVisibility(View.GONE);

                }

            }

        };
        //searchList.setAdapter(myAdapter);

    }

    public void searchUserAdapter(){
        myUserAdapter = new FirebaseListAdapter<User>(this,User.class,R.layout.user_display_search,
                getUsers()) {
            @Override
            protected void populateView(android.view.View v, User model, int position) {
                String info[] = new String[2];
                Typeface typeface=Typeface.createFromAsset(getAssets(), "Fonts/Sports.ttf");

                TextView userName = (TextView) v.findViewById(R.id.userName);
                TextView userEmail = (TextView) v.findViewById(R.id.userEmail);

                ImageButton messageSeller = (ImageButton) v.findViewById(R.id.messageSellerBtn);
                messageSeller.setBackgroundDrawable(null);
                ImageButton otherProfileButton = (ImageButton) v.findViewById(R.id.otherProfileButton);
                otherProfileButton.setBackgroundDrawable(null);

                userName.setVisibility(View.VISIBLE);
                userEmail.setVisibility(View.VISIBLE);

                messageSeller.setVisibility(View.VISIBLE);
                otherProfileButton.setVisibility(View.VISIBLE);

                if(model.getFirstName().toLowerCase().contains(Search.getText().toString().toLowerCase()) || model.getLastName().contains(Search.getText().toString().toLowerCase()) ||  model.getEmail().contains(Search.getText().toString().toLowerCase())) {
                    //Set text
                    userName.setText(model.getFirstName() + " " + model.getLastName());
                    userName.setTypeface(typeface);
                    userEmail.setText(model.getEmail());
                    userEmail.setTypeface(typeface);
                    info[0] = Integer.toString(position);
                    info[1] = model.getEmail();
                    messageSeller.setTag(info);
                    otherProfileButton.setTag(info);

                }

                else
                {

                    userName.setVisibility(View.GONE);
                    userEmail.setVisibility(View.GONE);
                    messageSeller.setVisibility(View.GONE);
                    otherProfileButton.setVisibility(View.GONE);

                }

            }

        };
        //searchList.setAdapter(myAdapter);

    }

    public void goToOtherProfile(android.view.View v){

        //RelativeLayout vwParentRow = (RelativeLayout) v.getParent();

        Ticket selectedTicket = (Ticket)v.getTag();
        String sellerUid = selectedTicket.getuID();
        String sellerEmail = selectedTicket.getUserEmail();

        Intent otherProfileIntent = new Intent(SearchActivity.this,OtherProfileActivity.class);

        otherProfileIntent.putExtra("com.example.ryan.qwiktix.MESSAGE",new String[]{sellerUid,sellerEmail} );

        startActivity(otherProfileIntent);


    }

    public void goToOtherProfileUser(android.view.View v){

        //RelativeLayout vwParentRow = (RelativeLayout) v.getParent();

        String info[] = (String[])v.getTag();
        int position = Integer.parseInt(info[0]);
        String userEmail = info[1];

        String userID = myUserAdapter.getRef(position).getKey().toString();


        Intent otherProfileIntent = new Intent(SearchActivity.this,OtherProfileActivity.class);

        otherProfileIntent.putExtra("com.example.ryan.qwiktix.MESSAGE",new String[]{userID, userEmail} );

        startActivity(otherProfileIntent);


    }

    public void messageSellerButton(android.view.View v){
        String sellerUid;
        String sellerEmail;

        Ticket selectedTicket = (Ticket) v.getTag();
        sellerUid = selectedTicket.getuID();
        sellerEmail = selectedTicket.getUserEmail();

        Intent ChatIntent = new Intent(SearchActivity.this,ChatActivity.class);

        ChatIntent.putExtra("com.example.ryan.qwiktix.MESSAGE",new String[]{sellerUid,sellerEmail} );
        //ChatIntent.putExtra("com.example.ryan.qwiktix.SELLEREMAIL",sellerEmail);

        startActivity(ChatIntent);


    }

    public void messageSellerButtonUser(android.view.View v){

        String info[] = (String[])v.getTag();
        int position = Integer.parseInt(info[0]);
        String userEmail = info[1];


        String userID = myUserAdapter.getRef(position).getKey().toString();
        //DatabaseReference userEmail = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("email");

        Intent ChatIntent = new Intent(SearchActivity.this,ChatActivity.class);

        ChatIntent.putExtra("com.example.ryan.qwiktix.MESSAGE",new String[]{userID,userEmail} );
        //ChatIntent.putExtra("com.example.ryan.qwiktix.SELLEREMAIL",sellerEmail);

        startActivity(ChatIntent);


    }
    public void goToSingleTicket(android.view.View v){

        int selectedTicket = (int)v.getTag();

        String ticketId = myEventAdapter.getRef(selectedTicket).getKey();

        Intent singleTicketIntent = new Intent(SearchActivity.this,SingleTicketActivity.class);

        singleTicketIntent.putExtra("com.example.ryan.qwiktix.MESSAGE",new String[] {ticketId});

        startActivity(singleTicketIntent);


    }

}
