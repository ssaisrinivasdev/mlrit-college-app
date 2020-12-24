package com.android.mlrit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class UserDetails extends AppCompatActivity {

    private EditText Hallticket, Name;
    private Button nextbtn;
    private TextView alerttext,contactAdmin,website;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Hallticket = findViewById(R.id.Hallticket);
        Name = findViewById(R.id.name);
        nextbtn = findViewById(R.id.nextbtn);
        alerttext = findViewById(R.id.alert);
        contactAdmin = findViewById(R.id.contactadmin);
        website = findViewById(R.id.clickhere);
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_website = new Intent(UserDetails.this,commonWebview.class);
                intent_website.putExtra("link","https://mlrinstitutions.ac.in/");
                startActivity(intent_website);
            }
        });
        contactAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_mail = new Intent(Intent.ACTION_SEND);
                intent_mail.putExtra(Intent.EXTRA_EMAIL, new String[]{"ssaisrinivas616@gmail.com"});
                intent_mail.setType("text/html");
                intent_mail.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent_mail, "Send mail"));
            }
        });



        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((Hallticket.getText().toString()).equals("") || (Hallticket.getText().toString()).length()!=10 || (Name.getText()).equals("")){
                    //Toast.makeText(UserDetails.this, "Hey", Toast.LENGTH_SHORT).show();
                    alerttext.setVisibility(View.VISIBLE);
                }else{
                    //Toast.makeText(UserDetails.this, "yyy", Toast.LENGTH_SHORT).show();
                    //alerttext.setVisibility(View.GONE);
                    new PrefManager(UserDetails.this).UpdateDetails((Hallticket.getText().toString()).toUpperCase(),Name.getText().toString());
                    Intent mainIntent = new Intent(UserDetails.this, Login.class);
                    UserDetails.this.startActivity(mainIntent);
                    UserDetails.this.finish();
                }
            }
        });


    }
}