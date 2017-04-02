package com.example.ryan.qwiktix;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.core.view.View;
import com.firebase.ui.database.FirebaseListAdapter;

/*
    Modified from https://firebaseui.com/docs/android/index.html in relation with the
    FirebaseListAdapter


 */

public class HomePageActivity extends BaseActivity {

    Firebase mRef;
    FirebaseListAdapter<Ticket> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mRef = new Firebase("https://qwiktix.firebaseio.com");

        myAdapter = new FirebaseListAdapter<Ticket>(this,Ticket.class,android.R.layout.simple_list_item_1,mRef) {
            @Override
            protected void populateView(View view, Ticket t, int i) {
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setText(t.getEvent());
            }
        };
        final ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(myAdapter);
/*
        Button addBtn = (Button) findViewById(R.id.add_button);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.push().setValue("test123");
            }
        });
    }*/


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
