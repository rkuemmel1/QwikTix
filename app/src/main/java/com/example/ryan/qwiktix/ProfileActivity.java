package com.example.ryan.qwiktix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends BaseActivity {

    private Button bLogOut;
    private TextView pEmail;
    private TextView pPayPalEmail;
    private TextView pFirstName;
    private TextView pLastName;
    private String pUid;
    private DatabaseReference mUserReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bLogOut = (Button) findViewById(R.id.hLogOut);

        bLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                signOut();

            }
        });

        pUid = getmAuth().getCurrentUser().getUid();

        mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(pUid);
        pEmail = (TextView) findViewById(R.id.pEmail);
        pPayPalEmail = (TextView) findViewById(R.id.pPayPalEmail);
        pFirstName = (TextView) findViewById(R.id.pFirstName);
        pLastName = (TextView) findViewById(R.id.pLastName);

        mUserReference.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                pEmail.setText(user.getEmail());
                pPayPalEmail.setText(user.getPayPalEmail());
                pFirstName.setText(user.getFirstName());
                pLastName.setText(user.getLastName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }






    int getContentViewId()
    {
        return R.layout.activity_profile;
    }

    int getNavigationMenuItemId()
    {
        return R.id.action_profile;
    }
}