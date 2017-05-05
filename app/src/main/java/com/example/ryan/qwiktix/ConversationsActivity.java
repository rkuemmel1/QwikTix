package com.example.ryan.qwiktix;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.core.view.View;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

/*
    Modified from https://firebaseui.com/docs/android/index.html in relation with the
    FirebaseListAdapter


 */

public class ConversationsActivity extends BaseActivity {

    FirebaseListAdapter<ChatConversation> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ListView convoList = (ListView)findViewById(R.id.convoList);

        myAdapter = new FirebaseListAdapter<ChatConversation>(this,ChatConversation.class,R.layout.conversation,
                getConversations()) {
            @Override
            protected void populateView(android.view.View v, ChatConversation model, int position) {
                TextView otherUserEmail = (TextView)v.findViewById(R.id.otherUserName);
                TextView lastMessage = (TextView)v.findViewById(R.id.lastMessage);

                lastMessage.setText(model.getLastMessageText());
                lastMessage.setTag(model);
                otherUserEmail.setText(model.getOtherUserName());
                otherUserEmail.setTag(model);

            }


        };
         convoList.setAdapter(myAdapter);


    }
    public void messageThemButton(android.view.View v){


        ChatConversation selectedConvo = (ChatConversation) v.getTag();
        String sellerUid = selectedConvo.getOtherUser();
        String sellerEmail = selectedConvo.getOtherUserName();

        Intent ChatIntent = new Intent(ConversationsActivity.this,ChatActivity.class);

        ChatIntent.putExtra("com.example.ryan.qwiktix.MESSAGE",new String[]{sellerUid,sellerEmail,"true","true"} );


        startActivity(ChatIntent);


    }




    int getContentViewId()
    {
        return R.layout.activity_viewconversations;
    }

    int getNavigationMenuItemId()
    {
        return R.id.action_chat;
    }

}
