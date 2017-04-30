package com.example.ryan.qwiktix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SellTicketActivity extends BaseActivity {

    //private Button bLogOut;
    //private Button bEditProfile;
    private Button bChat;
    private TextView sEmail;
    private TextView sPayPalEmail;
    private TextView sFirstName;
    private TextView sLastName;
    private String sUid;
    private DatabaseReference mUserReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bChat = (Button) findViewById(R.id.otherChat);
        //bLogOut = (Button) findViewById(R.id.pLogOut);
        //bEditProfile = (Button) findViewById(R.id.pEditProfile);


        bChat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent ChatIntent = new Intent(SellTicketActivity.this,ChatActivity.class);
                startActivity(ChatIntent);
            }
        });

        Intent intent = getIntent();
        if(intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE") != null){
                sUid = intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE")[0];

                mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(sUid);
                sEmail = (TextView) findViewById(R.id.otherEmail);
                sPayPalEmail = (TextView) findViewById(R.id.otherPayPalEmail);
                sFirstName = (TextView) findViewById(R.id.otherFirstName);
                sLastName = (TextView) findViewById(R.id.otherLastName);
                mUserReference.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        sEmail.setText(user.getEmail());
                        sPayPalEmail.setText(user.getPayPalEmail());
                        sFirstName.setText(user.getFirstName());
                        sLastName.setText(user.getLastName());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        }

        }



    int getContentViewId()
    {
        return 0;
    }

    int getNavigationMenuItemId()
    {
        return R.layout.activity_sell_ticket;
    }
}