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



public class HomePageActivity extends BaseActivity {

   // private Button bLogOut;
    //private static final String TAG = "MainActivity";
//    private FirebaseAuth mAuth;
//
//    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Firebase.setAndroidContext(this);
//
//        mAuth = FirebaseAuth.getInstance();
//        bLogOut = (Button) findViewById(R.id.hLogOut);
//
//        bLogOut.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                mAuth.signOut();
//
//            }
//        });
//
//
//        if(FirebaseAuth.getInstance().getCurrentUser() == null)
//        {
//            Intent loginIntent = new Intent(HomePageActivity.this,LoginActivity.class);
//            HomePageActivity.this.startActivity(loginIntent);
//        }
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if(firebaseAuth.getCurrentUser() == null)
//                {
//                    Intent loginIntent = new Intent(HomePageActivity.this,LoginActivity.class);
//                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(loginIntent);
//                }
//            }
//        };


    }

    @Override
    public void onStart(){
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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
