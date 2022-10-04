package com.triton.johnson_tap_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.triton.johnson_tap_app.activity.Daily_Collection_DetailsActivity;
import com.triton.johnson_tap_app.activity.MainActivity;
import com.triton.johnsonapp.R;

public class Change_PasswordActivity extends AppCompatActivity {

    ImageView iv_back;
    Button submit;
    EditText old_pass,new_pass;
    String s_old_pass, s_new_pass;
    AlertDialog alertDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_change_password);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        submit = (Button) findViewById(R.id.submit);
        old_pass = (EditText)findViewById(R.id.old_pass);
        new_pass = (EditText)findViewById(R.id.new_pass);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Change_PasswordActivity.this, Main_Menu_ServicesActivity.class);
                startActivity(send);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_old_pass = old_pass.getText().toString();
                s_new_pass = new_pass.getText().toString();

                if (s_old_pass.equals("") || s_new_pass.equals("")){

                    alertDialog = new AlertDialog.Builder(Change_PasswordActivity.this)

                            .setMessage("Please Fill the All Values")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    alertDialog.dismiss();
                                }
                            })
                            .show();
                }
                else{

                    Toast.makeText(getApplicationContext(), "Your Old Password is : " + s_old_pass + "\n" + "Your New Password is :" + s_new_pass, Toast.LENGTH_LONG).show();
                    Intent send = new Intent(Change_PasswordActivity.this, Main_Menu_ServicesActivity.class);
                    startActivity(send);
                }

            }
        });
    }
}