package com.example.ryan.qwiktix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;


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