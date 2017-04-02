package com.example.ryan.qwiktix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTicketActivity extends AppCompatActivity {

    private static final String TAG = "AddTicketActivity";

    private EditText tEvent;
    private EditText tEventDate;
    private EditText tPrice;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        tEvent = (EditText) findViewById(R.id.tEvent);
        tEventDate = (EditText) findViewById(R.id.tEventDate);
        tPrice = (EditText) findViewById(R.id.tPrice);
        final Button tSubmitButton = (Button) findViewById(R.id.tSubmitButton);

        tSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTicket();
            }
        });

    }


    private void addTicket() {

        String event = tEvent.getText().toString().trim();
        String eventDate = tEventDate.getText().toString().trim();
        String price = tPrice.getText().toString().trim();
        String user = "sydney-garcia@uiowa.edu";
        String date = "06/03/2017";
        String ticketIDString = event.concat(user).concat(date).replace(" ", "").replace("/", "").replace(":", "").replace("-", "").replace("@", "").replace(".","").toLowerCase();

        if (!TextUtils.isEmpty(price) && !TextUtils.isEmpty(eventDate) && !TextUtils.isEmpty(event)) {
            Ticket ticket = new Ticket(event, Integer.parseInt(price), date, eventDate, user);
            mDatabase.child("tickets").child(ticketIDString).setValue(ticket);
        }

        Toast.makeText(AddTicketActivity.this, "ticket added",
                Toast.LENGTH_SHORT).show();

        Intent addTicketIntent = new Intent(AddTicketActivity.this,HomePageActivity.class);
        addTicketIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(addTicketIntent);

    }

}
