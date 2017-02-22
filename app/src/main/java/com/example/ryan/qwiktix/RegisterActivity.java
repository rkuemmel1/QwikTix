package com.example.ryan.qwiktix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText rFirstName = (EditText) findViewById(R.id.rFirstName);
        final EditText rLastName = (EditText) findViewById(R.id.rLastName);
        final EditText rPassword = (EditText) findViewById(R.id.rPassword);
        final EditText rPasswordConfirm = (EditText) findViewById(R.id.rPasswordConfirm);
        final EditText rEmail = (EditText) findViewById(R.id.rEmail);
        final Button rSubmitButton = (Button) findViewById(R.id.rSubmitButton);
    }
}
