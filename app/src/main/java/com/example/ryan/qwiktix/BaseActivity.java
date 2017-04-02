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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;

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
            } else if (itemId == R.id.action_trends) {
                startActivity(new Intent(this, TrendsActivity.class));
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


    public void signOut(){
            mAuth.signOut();
    }


    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();

}