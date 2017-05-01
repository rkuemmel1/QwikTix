package com.example.ryan.qwiktix;

/*
 Taken and modified from https://github.com/ddekanski/BottomNavigationViewBetweenActivities/

 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    private String pUid;
    private DatabaseReference mUserReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());


        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);

        Firebase.setAndroidContext(this);

        mAuth = FirebaseAuth.getInstance();


        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            Intent loginIntent = new Intent(this,LoginActivity.class);
            this.startActivity(loginIntent);
        }
        else {
            pUid = getmAuth().getCurrentUser().getUid();

        }
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    Intent loginIntent = new Intent(BaseActivity.this,LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
        mAuth.addAuthStateListener(mAuthListener);
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_home) {
                startActivity(new Intent(this, HomePageActivity.class));
            } else if (itemId == R.id.action_search) {
                startActivity(new Intent(this, SearchActivity.class));
            } else if (itemId == R.id.action_add_ticket) {
                startActivity(new Intent(this, AddTicketActivity.class));
            } else if (itemId == R.id.action_chat) {
                startActivity(new Intent(this, ChatActivity.class));
            } else if (itemId == R.id.action_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
            }
            finish();
        }, 20);
        return true;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
            //THIS SHOULD BE FIXED ANOTHER WAY BECAUSE THERE ARE PROBABLY UNDERLYING PROBLEMS
            else{
                item.setChecked(false);
            }
        }
    }

    protected DatabaseReference getTickets(){
        return FirebaseDatabase.getInstance().getReference().child("tickets");
    }


    protected FirebaseAuth getmAuth(){
        return mAuth;
    }

    protected String getUid(){
        return mAuth.getCurrentUser().getUid();
    }
    protected String getEmail(){
        return mAuth.getCurrentUser().getEmail();
    }

    public void signOut(){
            mAuth.signOut();
    }
    protected DatabaseReference getMessages(){
        return FirebaseDatabase.getInstance().getReference().child("conversations");
    }
    protected DatabaseReference getConversations(){

        mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(pUid);
        return mUserReference.getRef().child("convos");
    }

    protected DatabaseReference getUsers(){
        mUserReference = FirebaseDatabase.getInstance().getReference().child("users");
        return mUserReference;
    }

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();

}