package com.example.ryan.qwiktix;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;



public class HomePageActivity extends AppCompatActivity {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private TextView uEmail;
    private TextView uFirstName;
    private TextView uLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Firebase.setAndroidContext(this);

        uEmail = (TextView) findViewById(R.id.user_email);
        uFirstName = (TextView) findViewById(R.id.user_first_name);
        uLastName = (TextView) findViewById(R.id.user_last_name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_navigation_main, menu); //
        return true;
    }

    @Override
    public void onStart(){
        super.onStart();


        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot : dataSnapshot.child("users").getChildren())
                {
                    User user = userSnapshot.getValue(User.class);

                    uEmail.setText(user.getEmail());
                    uFirstName.setText(user.getFirstName());
                    uLastName.setText(user.getLastName());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(userListener);
    }


}
