package com.example.ryan.qwiktix;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends BaseActivity {

    private Button bGoBack;
    private Button bChangeInfo;
    private Button bChangePass;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editPayPalEmail;
    private EditText editOldPassword;
    private EditText editNewPassword;
    private EditText editNewPassword2;
    private String pUid;
    private DatabaseReference mUserReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_profile);

        //bGoBack = (Button) findViewById(R.id.bGoBack);
        bChangeInfo = (Button) findViewById(R.id.bchangeInfo);
        bChangePass = (Button) findViewById(R.id.bChangePass);
        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editPayPalEmail = (EditText) findViewById(R.id.editPayPalEmail);
        editOldPassword = (EditText) findViewById(R.id.editOldPass);
        editNewPassword = (EditText) findViewById(R.id.editNewPass);
        editNewPassword2 = (EditText) findViewById(R.id.editNewPass2);

        pUid = getUid();

        mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(pUid);

        mUserReference.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                editFirstName.setText(user.getFirstName());
                editLastName.setText(user.getLastName());
                editPayPalEmail.setText(user.getPayPalEmail());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        bGoBack.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                Intent profileIntent = new Intent(EditProfileActivity.this,ProfileActivity.class);
//                startActivity(profileIntent);
//                finish();
//
//            }
//        });

        bChangeInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeInfo();

            }
        });

        bChangePass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changePass();
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



    }

    private void changeInfo(){

        //taken from http://stackoverflow.com/questions/4841228/after-type-in-edittext-how-to-make-keyboard-disappear
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editPayPalEmail.getWindowToken(), 0);

        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String payPalEmail = editPayPalEmail.getText().toString().trim();

        if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(payPalEmail))
        {

            if(!payPalEmail.contains("@"))
            {
                Toast.makeText(EditProfileActivity.this, "need valid email",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                mUserReference.child("firstName").setValue(firstName);
                mUserReference.child("lastName").setValue(editLastName.getText().toString().trim());
                mUserReference.child("payPalEmail").setValue(editPayPalEmail.getText().toString().trim());

                Toast.makeText(EditProfileActivity.this, "successfully changed info",
                        Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(EditProfileActivity.this, "can't have blank fields",
                    Toast.LENGTH_SHORT).show();
        }



    }


    private void changePass(){

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editNewPassword2.getWindowToken(), 0);

        String oldPass = editOldPassword.getText().toString().trim();
        String newPass = editNewPassword.getText().toString().trim();
        String newPass2 = editNewPassword2.getText().toString().trim();

        if(!TextUtils.isEmpty(oldPass) && !TextUtils.isEmpty(newPass) && !TextUtils.isEmpty(newPass2)){

            if(!newPass.equals(newPass2))
            {
                Toast.makeText(EditProfileActivity.this, "Passwords must match",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                FirebaseUser currentUser = getmAuth().getCurrentUser();

                AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(),oldPass);

                currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            currentUser.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(EditProfileActivity.this, "Successful password change",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(EditProfileActivity.this, "Password update failed",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(EditProfileActivity.this, "incorrect old password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }
        else{
            Toast.makeText(EditProfileActivity.this, "can't have blank fields",
                    Toast.LENGTH_SHORT).show();
        }


    }




    int getContentViewId()
    {
        return R.layout.activity_edit_profile;
    }

    int getNavigationMenuItemId()
    {
        return 0;
    }

}