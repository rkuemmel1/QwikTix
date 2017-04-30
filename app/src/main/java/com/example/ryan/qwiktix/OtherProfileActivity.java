package com.example.ryan.qwiktix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OtherProfileActivity extends BaseActivity {



    private ImageButton PayPalButton;
    private TextView oEmail;
    private TextView oPayPalEmail;
    private TextView oFirstName;
    private TextView oLastName;
    private String oUid;
    private DatabaseReference mUserReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        oEmail = (TextView) findViewById(R.id.oEmail);
        oPayPalEmail = (TextView) findViewById(R.id.oPayPalEmail);
        oFirstName = (TextView) findViewById(R.id.oFirstName);
        oLastName = (TextView) findViewById(R.id.oLastName);
        PayPalButton = (ImageButton) findViewById(R.id.pPayPalImageButton);

        PayPalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent PaypalIntent = new Intent(OtherProfileActivity.this,PaypalActivity.class);
                startActivity(PaypalIntent);
            }
        });

        Intent intent = getIntent();
        if(intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE") != null){

            oUid = intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE")[0];

            mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(oUid);
            mUserReference.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    oEmail.setText(user.getEmail());
                    oPayPalEmail.setText(user.getPayPalEmail());
                    oFirstName.setText(user.getFirstName());
                    oLastName.setText(user.getLastName());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }



    }


    int getContentViewId()
    {
        return R.layout.activity_other_profile;
    }

    int getNavigationMenuItemId()
    {
        return 0;
    }

}
