package com.example.ryan.qwiktix;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

import static com.example.ryan.qwiktix.R.id.spinner;

public class AddTicketActivity extends BaseActivity {

    private static final String TAG = "AddTicketActivity";

   // private EditText tEvent;
    private EditText tEventDate;
    private EditText tPrice;
    private EditText tExtra;

    private String pUid;
    private String pUserEmail;

    private DatabaseReference mDatabase;
    private Spinner tSpinner;

    //private AutoCompleteTextView actv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_ticket);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        pUid = getUid();
        pUserEmail = getEmail();

        tSpinner = (Spinner) findViewById(R.id.tSpinner);
        tEventDate = (EditText) findViewById(R.id.tEventDateTime);
        tPrice = (EditText) findViewById(R.id.tPrice);
        tExtra = (EditText) findViewById(R.id.tExtra);
        final Button tSubmitButton = (Button) findViewById(R.id.tSubmitButton);

        tSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTicket();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mDatabase.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
                for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                    //Get the suggestion by childing the key of the string you want to get.
                    String suggestion = suggestionSnapshot.getKey();
                    //Add the retrieved string to the list
                    adapter.add(suggestion);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //adapter.add("Event Name");

        tSpinner.setAdapter(adapter);
        tSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                String event = tSpinner.getSelectedItem().toString();
                mDatabase.child("events").child(event).child("date_time").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        tEventDate.setText(dataSnapshot.getValue(String.class));
                        tEventDate.setEnabled(false);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing, just another required interface callback
            }

        });
        //tSpinner.setSelection(adapter.getCount()); //display hint

    }



    private void addTicket() {

        String event = tSpinner.getSelectedItem().toString();//tEvent.getText().toString().trim();
        String eventDate = tEventDate.getText().toString().trim();//.concat(" ").concat(tEventTime.getText().toString().trim());
        String price = tPrice.getText().toString().trim();
        String userEmail = pUserEmail;
        String user = pUid;
        String extrainfo = tExtra.getText().toString().trim();

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        if (!TextUtils.isEmpty(price) && !TextUtils.isEmpty(eventDate) && !TextUtils.isEmpty(event) && !TextUtils.isEmpty(extrainfo)) {
            Ticket ticket = new Ticket(event, Integer.parseInt(price), currentDateTimeString, eventDate, userEmail, user,extrainfo);
            mDatabase.child("tickets").push().setValue(ticket);

            Toast.makeText(AddTicketActivity.this, "ticket added",
                    Toast.LENGTH_SHORT).show();

            Intent addTicketIntent = new Intent(AddTicketActivity.this,HomePageActivity.class);
            addTicketIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(addTicketIntent);
        }

        else
        {
            Toast.makeText(AddTicketActivity.this, "please fill all fields", Toast.LENGTH_SHORT).show();
        }

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