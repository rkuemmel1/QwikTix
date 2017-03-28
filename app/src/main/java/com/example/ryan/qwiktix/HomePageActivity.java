package com.example.ryan.qwiktix;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;



public class HomePageActivity extends AppCompatActivity {

//    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//    private TextView uEmail;
//    private TextView uFirstName;
//    private TextView uLastName;
    private Button bLogOut;
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setupBottomNavigationView();

        Firebase.setAndroidContext(this);
//
//        uEmail = (TextView) findViewById(R.id.user_email);
//        uFirstName = (TextView) findViewById(R.id.user_first_name);
//        uLastName = (TextView) findViewById(R.id.user_last_name);
        mAuth = FirebaseAuth.getInstance();
        bLogOut = (Button) findViewById(R.id.hLogOut);

        bLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                mAuth.signOut();

            }
        });


        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            Intent loginIntent = new Intent(HomePageActivity.this,LoginActivity.class);
            HomePageActivity.this.startActivity(loginIntent);
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    Intent loginIntent = new Intent(HomePageActivity.this,LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        };


    }

    private void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        break;
                    case R.id.action_search:
                        Intent searchIntent = new Intent(HomePageActivity.this,SearchActivity.class);
                        startActivity(searchIntent);
                        break;
                    case R.id.action_add_ticket:
                        break;
                    case R.id.action_trends:
                        break;
                    case R.id.action_profile:
                        break;
                }
                return true;
            }
        });
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

        mAuth.addAuthStateListener(mAuthListener);


//        ValueEventListener userListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot userSnapshot : dataSnapshot.child("users").getChildren())
//                {
//                    User user = userSnapshot.getValue(User.class);
//
//                    uEmail.setText(user.getEmail());
//                    uFirstName.setText(user.getFirstName());
//                    uLastName.setText(user.getLastName());
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        mDatabase.addValueEventListener(userListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
