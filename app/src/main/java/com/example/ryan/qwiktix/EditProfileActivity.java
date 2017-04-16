package com.example.ryan.qwiktix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        bGoBack = (Button) findViewById(R.id.bGoBack);
        bChangeInfo = (Button) findViewById(R.id.bchangeInfo);
        bChangePass = (Button) findViewById(R.id.bChangePass);
        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editPayPalEmail = (EditText) findViewById(R.id.editPayPalEmail);
        editOldPassword = (EditText) findViewById(R.id.editOldPass);
        editNewPassword = (EditText) findViewById(R.id.editNewPass);
        editNewPassword2 = (EditText) findViewById(R.id.editNewPass2);

        bGoBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                finish();

            }
        });

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



    }


    private void changeInfo(){

    }


    private void changePass(){

    }




    int getContentViewId()
    {
        return R.layout.activity_profile;
    }

    int getNavigationMenuItemId()
    {
        return R.id.action_profile;
    }

}
