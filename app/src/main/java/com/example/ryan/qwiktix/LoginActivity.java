package com.example.ryan.qwiktix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText lEmail = (EditText) findViewById(R.id.lEmail);
        final EditText lPassword =(EditText) findViewById(R.id.lPassword);
        final Button lLoginButton = (Button) findViewById(R.id.lLoginButton);
        final TextView lRegisterLink = (TextView) findViewById(R.id.lRegisterLink);

        lRegisterLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

    }
}
