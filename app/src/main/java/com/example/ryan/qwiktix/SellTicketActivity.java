package com.example.ryan.qwiktix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.ArrayAdapter;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by gmsotps on 4/16/2017.
 */

public class SellTicketActivity extends BaseActivity {


    int getContentViewId()
    {
        return R.layout.activity_add_ticket;
    }

    int getNavigationMenuItemId()
    {
        return R.id.action_add_ticket;
    }

}
