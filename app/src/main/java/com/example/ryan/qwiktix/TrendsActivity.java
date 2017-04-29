package com.example.ryan.qwiktix;


public class TrendsActivity extends BaseActivity {



// #Create Batch Payout
// The asynchronous payout mode (sync_mode=false, which is the default) enables a maximum of 500 individual payouts to be specified in one API call.
// Exceeding 500 payouts in one call returns an HTTP response message with status code 400 (Bad Request).

// API used: POST /v1/payments/payouts

    private static final long serialVersionUID = 1L;

    int getContentViewId()
    {
        return R.layout.activity_trends;
    }

    int getNavigationMenuItemId()
    {
        return R.id.action_trends;
    }



 
}