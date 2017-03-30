package com.example.ryan.qwiktix;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTicketActivity extends BaseActivity {

    private static final String TAG = "AddTicketActivity";

    int getContentViewId()
    {
        return R.layout.activity_add_ticket;
    }

    int getNavigationMenuItemId()
    {
        return R.id.action_add_ticket;
    }

    private EditText tEvent;
    private EditText tPrice;
    private EditText tEventDate;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String pUid;
    private String pEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        tEvent = (EditText) findViewById(R.id.tEvent);
        tPrice = (EditText) findViewById(R.id.tPrice);
        tEventDate = (EditText) findViewById(R.id.tEventDate);
        final Button rSubmitButton = (Button) findViewById(R.id.tSubmitButton);

        rSubmitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                addTicket();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };



    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }
    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void addTicket() {

        pUid = getmAuth().getCurrentUser().getUid();
        pEmail = findViewById(R.id.pEmail).toString().trim();

        String event = tEvent.getText().toString().trim();
        String price = tPrice.getText().toString().trim();
        String endDate = tEventDate.getText().toString().trim();

        if (!TextUtils.isEmpty(event) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(endDate)) {

            Ticket ticket = new Ticket(event, Integer.parseInt(price), "now", endDate, pEmail);

            mDatabase.child("tickets").child(pEmail).setValue(ticket);

            Intent loginIntent = new Intent(AddTicketActivity.this, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
        }

    }

}


