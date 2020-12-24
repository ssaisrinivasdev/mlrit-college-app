package com.android.mlrit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class userProfile extends AppCompatActivity {

    TextView logout,reportabug,name,roll,batch;
    String batchstr,halltick;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        logout = findViewById(R.id.profile_logout);
        reportabug = findViewById(R.id.bug);
        back = findViewById(R.id.back);
        name =findViewById(R.id.std_name);
        roll = findViewById(R.id.std_roll);


        name.setText(new PrefManager(this).returnDetails("Name"));
        roll.setText(new PrefManager(this).returnDetails("HallTicket"));
        halltick = new PrefManager(this).returnDetails("HallTicket");





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(userProfile.this)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new PrefManager(userProfile.this).Logout();
                                Intent mainIntent = new Intent(userProfile.this, UserDetails.class);
                                userProfile.this.startActivity(mainIntent);
                                userProfile.this.finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        reportabug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceName = android.os.Build.MODEL; // returns model name
                String deviceManufacturer = android.os.Build.MANUFACTURER; // returns manufacturer
                Intent intent_mail = new Intent(Intent.ACTION_SEND);
                intent_mail.putExtra(Intent.EXTRA_EMAIL, new String[]{"ssaisrinivas616@gmail.com"});
                intent_mail.putExtra(Intent.EXTRA_SUBJECT, "MLRIT Application : Mobile Model: "+deviceName+" ");
                intent_mail.putExtra(Intent.EXTRA_TEXT, "Issue:\n ScreenShots:");
                intent_mail.setType("text/html");
                intent_mail.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent_mail, "Send mail"));

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userProfile.super.onBackPressed();
            }
        });


    }
}