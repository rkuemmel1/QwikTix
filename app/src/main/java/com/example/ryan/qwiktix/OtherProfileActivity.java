package com.example.ryan.qwiktix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class OtherProfileActivity extends BaseActivity {



    private Button oChat;
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

        oChat = (Button) findViewById(R.id.BChat);
        PayPalButton = (ImageButton) findViewById(R.id.pPayPalImageButton);

        PayPalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent PaypalIntent = new Intent(OtherProfileActivity.this,PaypalActivity.class);
                startActivity(PaypalIntent);
            }
        });



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
