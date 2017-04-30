package com.example.ryan.qwiktix;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by lukeb on 3/31/2017.
 */

public class ChatActivity extends BaseActivity {

    FirebaseListAdapter conversationAdapter;
    Spinner spinner;
    String conversation ="testconvo";
    ListView listOfMessages;
    String uid;
    String newConvoUid;
    String email;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //final String[] otherUsername = new String[1];
        //setContentView(R.layout.activity_chat);
        uid =getmAuth().getCurrentUser().getUid();
        email = getmAuth().getCurrentUser().getEmail();
        final EditText input = (EditText) findViewById(R.id.input);
        final FloatingActionButton sendButton = (FloatingActionButton)findViewById(R.id.fab);
        Intent intent = getIntent();
        if(intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE") != null) {
             //uid of user you just started a conversation with
            newConvoUid = intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE")[0];
            String otherUserName = intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE")[1];

            //getUsers().child(newConvoUid);
            String newChatName = uid+newConvoUid;
            //otherUserName = otherUsername[0];
            String otherUser = newConvoUid;
            ChatConversation theirConvoToStart = new ChatConversation(newChatName,getmAuth().getCurrentUser().getUid(),email);
            ChatConversation convoToStart = new ChatConversation(newChatName,otherUser,otherUserName);

            ChatMessage welcomeMessage = new ChatMessage("New Chat","admin");

            getMessages().child(newChatName).push().setValue(welcomeMessage);
            getUsers().child(otherUser).child("convos").push().setValue(theirConvoToStart);

            getConversations().push().setValue(convoToStart);

            conversation = newChatName;
            spinner = (Spinner) findViewById(R.id.spinner);
            spinner.setSelection(0);
            getIntent().removeExtra("com.example.ryan.qwiktix.MESSAGE");
        }
        // Load chat room contents
        displayChatMessages();
        displayConversations();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMessage messageToSend = new ChatMessage(input.getText().toString(),mAuth.getCurrentUser().getEmail());
                //Read the input field and push a new instance
                //of ChatMessage to the Firebase database
                getMessages().child(conversation).push().setValue(messageToSend);
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
                listOfMessages.setAdapter(null);
                ChatConversation chat = (ChatConversation) spinner.getSelectedItem();
                String text = chat.getChatName();
                Log.e("Verbose","SPINNER INFO"+text);
                conversation = text;
                //displayConversations();
                displayChatMessages();


            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }



    public void displayChatMessages(){
        listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        listOfMessages.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        FirebaseListAdapter messageAdapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, getMessages().child(conversation)) {

            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);
                if(model.getMessageUser().toString().equals(email) ){
                    messageUser.setTextColor(Color.BLUE);
                }
                else{
                    messageUser.setTextColor(Color.RED);
                }
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
        conversationAdapter = new FirebaseListAdapter<ChatConversation>(this, ChatConversation.class,R.layout.conversation, getConversations()){
            @Override
            protected void populateView(View v, ChatConversation model,int position){
                TextView convotext= (TextView)v.findViewById(R.id.convo);
                convotext.setText(model.getOtherUserName());
            }

        };
        // attaching data adapter to spinner
        spinner.setAdapter(conversationAdapter);

    }
/*
    public void createConvo(String otherUser, String otherUserName, String chatName) {
        ChatConversation theirConvoToStart = new ChatConversation(chatName,getmAuth().getCurrentUser().getUid(),mAuth.getCurrentUser().getEmail());
        ChatConversation convoToStart = new ChatConversation(chatName,otherUser,otherUserName);

        ChatMessage welcomeMessage = new ChatMessage("New Chat","admin");

        getMessages().child(chatName).push().setValue(welcomeMessage);
        getUsers().child(otherUser).child("convos").push().setValue(theirConvoToStart);

        getConversations().push().setValue(convoToStart);




    }
*/



    @Override
    public int getNavigationMenuItemId(){
        return 0;
    }

    @Override
    public int getContentViewId(){
        return R.layout.activity_chat;
    }
}
