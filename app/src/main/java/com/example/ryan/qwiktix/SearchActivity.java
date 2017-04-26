package com.example.ryan.qwiktix;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";

    EditText Search;
    private ListView searchList;

    private DatabaseReference mDatabase;

    ArrayList<String> strings = new ArrayList<String>();

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Search = (EditText) findViewById(R.id.Search);
        searchList = (ListView) findViewById(R.id.searchList);

        mDatabase.child("tickets").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String ticket = snapshot.child("event").getValue(String.class) + " " + snapshot.child("endTime").getValue(String.class);
                    strings.add(ticket);
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1,
                        strings);
                searchList.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });

        adapter = new ArrayAdapter<String>(SearchActivity.this,R.layout.search_results,R.id.results,strings);
        searchList.setAdapter(adapter);

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