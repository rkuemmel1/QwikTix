package com.example.ryan.qwiktix;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.FirebaseError;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";

    FirebaseListAdapter<Ticket> myAdapter;

    private EditText Search;
    private ListView listviewforresults;

    private String pUid;
    private String pUserEmail;

    private DatabaseReference mDatabase;

    private AutoCompleteTextView actv;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Search = (EditText) findViewById(R.id.Search);

        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        ListView ticketList = (ListView)findViewById(R.id.searchList);
        listviewforresults=(ListView)findViewById(R.id.searchList);


        //Child the root before all the push() keys are found and add a ValueEventListener()
        mDatabase.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
                for (com.google.firebase.database.DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()) {
                    //Get the suggestion by childing the key of the string you want to get.
                    String suggestion = suggestionSnapshot.child("name").getValue(String.class);
                    //Add the retrieved string to the list
                    autoComplete.add(suggestion);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        AutoCompleteTextView ACTV = (AutoCompleteTextView) Search;
        ACTV.setAdapter(autoComplete);


        ListView listView = (ListView) findViewById(R.id.searchList);
        ArrayList<String> strings = new ArrayList<String>();
        ArrayList<String> tickets = new ArrayList<String>();

        mDatabase.child("tickets").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    strings.add(snapshot.child("event").getValue(String.class));
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1,
                        strings);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });

        ArrayAdapter adapter = new ArrayAdapter<String>(SearchActivity.this,R.layout.search_results,R.id.results,strings);
        listviewforresults.setAdapter(adapter);

        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                SearchActivity.this.adapter.getFilter().filter(s);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    int getContentViewId()
    {
        return R.layout.activity_search;
    }

    int getNavigationMenuItemId()
    {
        return R.id.action_search;
    }

}