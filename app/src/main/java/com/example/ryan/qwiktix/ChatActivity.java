package com.example.ryan.qwiktix;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;

/**
 * Created by lukeb on 3/31/2017.
 */

public class ChatActivity extends BaseActivity {
    FirebaseListAdapter messageAdapter;
    FirebaseListAdapter conversationAdapter;
    Spinner spinner;
    final String conversation ="testconvo";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_chat);

        final EditText input = (EditText) findViewById(R.id.input);
        final FloatingActionButton sendButton = (FloatingActionButton)findViewById(R.id.fab);

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
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

    }



    public void displayChatMessages(){
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        messageAdapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, getMessages().child(conversation)) {
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
        conversationAdapter = new FirebaseListAdapter<ChatConversation>(this, ChatConversation.class,R.layout.conversation, getConversations()){
            @Override
            protected void populateView(View v, ChatConversation model,int position){
                TextView convotext= (TextView)v.findViewById(R.id.convo);

                convotext.setText(model.getTitle());
            }
        };
        // attaching data adapter to spinner
        spinner.setAdapter(conversationAdapter);

    }





    @Override
    public int getNavigationMenuItemId(){
        return 0;
    }

    @Override
    public int getContentViewId(){
        return R.layout.activity_chat;
    }
}
