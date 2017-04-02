package com.example.ryan.qwiktix;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.core.view.View;
import com.firebase.ui.FirebaseListAdapter;
/*
    Modified from https://firebaseui.com/docs/android/index.html in relation with the
    FirebaseListAdapter


 */

public class HomePageActivity extends BaseActivity {

    Firebase mRef;
    FirebaseListAdapter<Ticket> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);
        mRef = new Firebase("http://qwiktix.firebaseio.com");
        mAdapter = new FirebaseListAdapter<Ticket>(this,Ticket.class,android.R.layout.two_line_list_item,mRef)
        {
            protected void populateView(View view, Ticket ticket){
                ((TextView)view.findViewByID(android.R.id.text1)).setText(ticket.getEvent());
                ((TextView)view.findViewByID(android.R.id.text2)).setText(ticket.getEndTime());
            }
        };
        ListView
    }

    int getContentViewId()
    {
        return R.layout.activity_home_page;
    }

    int getNavigationMenuItemId()
    {
        return R.id.action_home;
    }

}
