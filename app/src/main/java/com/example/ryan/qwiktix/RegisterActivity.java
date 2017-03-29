package com.example.ryan.qwiktix;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private EditText rFirstName;
    private EditText rLastName;
    private EditText rPassword;
    private EditText rPasswordConfirm;
    private EditText rEmail;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        rFirstName = (EditText) findViewById(R.id.rFirstName);
        rLastName = (EditText) findViewById(R.id.rLastName);
        rPassword = (EditText) findViewById(R.id.rPassword);
        rPasswordConfirm = (EditText) findViewById(R.id.rPasswordConfirm);
        rEmail = (EditText) findViewById(R.id.rEmail);
        final Button rSubmitButton = (Button) findViewById(R.id.rSubmitButton);

        rSubmitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                registerUser();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // email sent


                                        // after email is sent just logout the user and finish this activity
                                        FirebaseAuth.getInstance().signOut();
                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        Toast.makeText(RegisterActivity.this, "verification email sent",
                                                Toast.LENGTH_SHORT).show();
                                        //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                    else
                                    {
                                        // email not sent, so display message and restart the activity or do whatever you wish to do

                                        //restart this activity
                                        overridePendingTransition(0, 0);
                                        finish();
                                        overridePendingTransition(0, 0);
                                        startActivity(getIntent());

                                    }
                                }
                            });
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };



    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }
    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void registerUser() {

        String firstName = rFirstName.getText().toString().trim();
        String lastName = rLastName.getText().toString().trim();
        String password = rPassword.getText().toString().trim();
        String passwordConfirm = rPasswordConfirm.getText().toString().trim();
        String email = rEmail.getText().toString().trim();

        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm) && !TextUtils.isEmpty(email)) {

            if (!password.equals(passwordConfirm)) {
                Toast.makeText(RegisterActivity.this, "Passwords must match",
                        Toast.LENGTH_SHORT).show();
            } else if (!email.contains("@uiowa.edu")) {
                Toast.makeText(RegisterActivity.this, "email needs @uiowa.edu",
                        Toast.LENGTH_SHORT).show();
            } else {
                 mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Unable to create user",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(loginIntent);



                                }


                            }
                        });

            }



        }

    }

}
