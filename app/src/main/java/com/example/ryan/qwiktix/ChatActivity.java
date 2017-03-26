package com.example.ryan.qwiktix;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by lukeb on 3/20/2017.
 */

public class ChatActivity extends AppCompatActivity  {
    FirebaseListAdapter messageAdapter;
    FirebaseListAdapter conversationAdapter;
    Spinner spinner;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    final String conversation ="testconvo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mAuth = FirebaseAuth.getInstance();


        final EditText input = (EditText) findViewById(R.id.input);
        final FloatingActionButton sendButton = (FloatingActionButton)findViewById(R.id.fab);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Load chat room contents
        displayChatMessages();
        displayConversations();




        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMessage messageToSend = new ChatMessage(input.getText().toString(),mAuth.getCurrentUser().getEmail());
                 //Read the input field and push a new instance
                 //of ChatMessage to the Firebase database
                mDatabase.child("conversations").child(conversation).push().setValue(messageToSend);
                // Clear the input
                input.setText("");
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

    }



    public void displayChatMessages(){
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        messageAdapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, mDatabase.child("conversations").child(conversation)) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(messageAdapter);
    }
    public void displayConversations(){
        spinner = (Spinner) findViewById(R.id.spinner);
        conversationAdapter = new FirebaseListAdapter<String>(this, String.class,R.layout.conversation, mDatabase.child("listofconvos")){
            @Override
            protected void populateView(View v, String model,int position){
                TextView convotext= (TextView)v.findViewById(R.id.convo);

                convotext.setText(model);
            }
        };
        // attaching data adapter to spinner
        spinner.setAdapter(conversationAdapter);

    }

}
