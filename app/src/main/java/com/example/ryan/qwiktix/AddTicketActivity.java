package com.example.ryan.qwiktix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.ArrayAdapter;
import java.text.DateFormat;
import java.util.Date;

public class AddTicketActivity extends BaseActivity {

    private static final String TAG = "AddTicketActivity";

    private EditText tEvent;
    private EditText tEventDate;
    private EditText tPrice;
    private EditText tEventTime;

    private String pUid;
    private String pUserEmail;

    private DatabaseReference mDatabase;

    private AutoCompleteTextView actv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_ticket);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        pUid = getmAuth().getCurrentUser().getUid();
        pUserEmail = getmAuth().getCurrentUser().getEmail();

        tEvent = (EditText) findViewById(R.id.tEvent);
        tEventDate = (EditText) findViewById(R.id.tEventDate);
        tPrice = (EditText) findViewById(R.id.tPrice);
        tEventTime = (EditText) findViewById(R.id.tEventTime);
        final Button tSubmitButton = (Button) findViewById(R.id.tSubmitButton);

        tSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTicket();
            }
        });

        actv = (AutoCompleteTextView) findViewById(R.id.tEvent);
        String[] events = {"BTS Wings Tour", "Iowa State Basketball Game", "Purdue Track Meet"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,events);
        actv.setAdapter(adapter);

    }


    private void addTicket() {

        String event = tEvent.getText().toString().trim();
        String eventDate = tEventDate.getText().toString().trim().concat(" ").concat(tEventTime.getText().toString().trim());
        String price = tPrice.getText().toString().trim();
        String userEmail = pUserEmail;
        String user = pUid;

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        if (!TextUtils.isEmpty(price) && !TextUtils.isEmpty(eventDate) && !TextUtils.isEmpty(event)) {
            Ticket ticket = new Ticket(event, Integer.parseInt(price), currentDateTimeString, eventDate, userEmail, user);
            mDatabase.child("tickets").push().setValue(ticket);
        }

        Toast.makeText(AddTicketActivity.this, "ticket added",
                Toast.LENGTH_SHORT).show();

        Intent addTicketIntent = new Intent(AddTicketActivity.this,HomePageActivity.class);
        addTicketIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(addTicketIntent);

    }



    int getContentViewId()
    {
        return R.layout.activity_add_ticket;
    }

    int getNavigationMenuItemId()
    {
        return R.id.action_add_ticket;
    }

}