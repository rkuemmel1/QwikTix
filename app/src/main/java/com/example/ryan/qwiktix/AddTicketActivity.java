package com.example.ryan.qwiktix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddTicketActivity extends BaseActivity {

    int getContentViewId()
    {
        return R.layout.activity_add_ticket;
    }

    int getNavigationMenuItemId()
    {
        return R.id.action_add_ticket;
    }
}
