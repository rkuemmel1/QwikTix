package com.example.ryan.qwiktix;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by lukeb on 3/31/2017.
 */

public class ChatActivity extends BaseActivity {

    FirebaseListAdapter conversationAdapter;
    //Spinner spinner;
    String conversation ="testconvo";
    ListView listOfMessages;
    String uid;
    String otherUserUid;
    String email;
    int switchedConvos = 0;
    int spinnerPosition = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // spinner = (Spinner) findViewById(R.id.spinner);
        //final String[] otherUsername = new String[1];
        //setContentView(R.layout.activity_chat);
        uid =getmAuth().getCurrentUser().getUid();
        email = getmAuth().getCurrentUser().getEmail();
        final EditText input = (EditText) findViewById(R.id.input);
        final FloatingActionButton sendButton = (FloatingActionButton)findViewById(R.id.fab);
        Intent intent = getIntent();
        if(intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE") != null) {
            //uid of user you just started a conversation with
            otherUserUid = intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE")[0];
            String otherUserName = intent.getStringArrayExtra("com.example.ryan.qwiktix.MESSAGE")[1];

            //getUsers().child(otherUserUid);
            String newChatName = uid+ otherUserUid;
            //otherUserName = otherUsername[0];
            String otherUser = otherUserUid;
            ChatConversation theirConvoToStart = new ChatConversation(newChatName,getmAuth().getCurrentUser().getUid(),email,"New Chat");
            ChatConversation convoToStart = new ChatConversation(newChatName,otherUser,otherUserName,"New Chat");
            conversation = newChatName;
            ChatMessage welcomeMessage = new ChatMessage("New Chat","admin");
            getMessages().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(!snapshot.child(newChatName).exists() ){
                        getMessages().child(newChatName).push().setValue(welcomeMessage);
                        getUsers().child(otherUser).child("convos").child(newChatName).setValue(theirConvoToStart);

                        getConversations().child(newChatName).setValue(convoToStart);
                    }
                }
                @Override
                public void onCancelled(DatabaseError e){

                }
            });

            //displayConversations();


            displayChatMessages();


            getIntent().removeExtra("com.example.ryan.qwiktix.MESSAGE");
        }
        // Load chat room contents

        displayChatMessages();



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMessage messageToSend = new ChatMessage(input.getText().toString(),mAuth.getCurrentUser().getEmail());
                //Read the input field and push a new instance
                //of ChatMessage to the Firebase database
                getMessages().child(conversation).push().setValue(messageToSend);
                getConversations().child(conversation).child("lastMessageText").setValue("You:   "+input.getText().toString());
                getUsers().child(otherUserUid).child("convos").child(conversation).child("lastMessageTest").setValue("them:   "+input.getText().toString());
                // Clear the input
                input.setText("");
            }
        });
        /*
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item

                listOfMessages.setAdapter(null);
                ChatConversation chat = (ChatConversation) spinner.getSelectedItem();
                String text = chat.getChatName();

                if(switchedConvos != 0) {
                    conversation = text;

                }
                else{

                    switchedConvos = 1;
                }
                Toast.makeText(ChatActivity.this,"switched chat item selected"+ conversation, Toast.LENGTH_LONG).show();
                //displayConversations();
                displayChatMessages();


            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });*/

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
    /*
    public void displayConversations(){

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
    */
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
        return -1;
    }

    @Override
    public int getContentViewId(){
        return R.layout.activity_chat;
    }
}
