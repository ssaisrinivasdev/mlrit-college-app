package com.android.mlrit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    public Button  nextbtn;
    public TextView HallTicket,Name,alerttext,backtouser,notstd;
    public WebView std_webview,exam_portal;
    public  int num=0;
    private static final long SPLASH_DISPLAY_LENGTH = 15000;
    private ProgressBar loading;
    private EditText password_exam;
    public String examportal;
    public View view;
    CheckBox checkBox;
    TextInputLayout textInputLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);

        Name= findViewById(R.id.username);
        HallTicket = findViewById(R.id.userhallticket);
        nextbtn = findViewById(R.id.nextlogginbutton);
        alerttext = findViewById(R.id.alert);
        exam_portal = findViewById(R.id.exam_portal);
        loading = findViewById(R.id.loading);
        view = findViewById(R.id.view);
        password_exam = findViewById(R.id.password_std_login);
        backtouser = findViewById(R.id.backtouser);
        checkBox = findViewById(R.id.checkbox_roll_password);
        textInputLayout = findViewById(R.id.pass);
        notstd = findViewById(R.id.notstd);

        Toast.makeText(this, "Please enter your password carefully..", Toast.LENGTH_LONG).show();


        examportal = "https://exams.mlrinstitutions.ac.in/Login.aspx";


        backtouser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(Login.this, UserDetails.class);
                Login.this.startActivity(mainIntent);
                Login.this.finish();
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()) {
                    password_exam.setInputType(1);
                    password_exam.setText(new PrefManager(Login.this).returnDetails("HallTicket"));
                }
            }
        });


        Name.setText(new PrefManager(this).returnDetails("Name"));
        HallTicket.setText(new PrefManager(this).returnDetails("HallTicket"));
        notstd.setText("Not "+new PrefManager(this).returnDetails("HallTicket")+"?");

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "Logging in..", Toast.LENGTH_SHORT).show();
                num=0;
                alerttext.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);

                if((password_exam.getText().toString()).equals("")){
                    alerttext.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }
                else{
                    final String js = "javascript:document.getElementById('lnkStudent').click();"+"document.getElementById('txtUserId').value='"+HallTicket.getText()+"';"+"document.getElementById('txtPwd').value='"+
                            password_exam.getText().toString()+"';" +"document.getElementById('btnLogin').click()";
                    Toast.makeText(Login.this, "Please Wait\nThis may take sometime", Toast.LENGTH_LONG).show();
                    exam_portal.loadUrl(examportal);
                    exam_portal.getSettings().setJavaScriptEnabled(true);
                    exam_portal.setWebViewClient(new WebViewClient(){
                        public void onPageFinished( WebView view, String url){
                            if(num<6){

                                view.evaluateJavascript(js , new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String s) {
                                        num++;
                                        //Toast.makeText(Login.this, "num : "+num, Toast.LENGTH_SHORT).show();
                                    }

                                });
                            }
                            else{
                                num =-1;
                            }
                        }

                    });

                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            loading.setVisibility(View.GONE);
                            view.setVisibility(View.VISIBLE);
                            if(num>=0&&num<3){
                                Toast.makeText(Login.this, "Server must be busy\nTry again later", Toast.LENGTH_LONG).show();
                            }
                            else if(num==-1){
                                num=0;
                                alerttext.setVisibility(View.VISIBLE);
                            }else if(num>=3&&num<=4){
                                new PrefManager(Login.this).UpdateUserdetails(HallTicket.getText().toString(),password_exam.getText().toString());
                                alerttext.setVisibility(View.GONE);
                                Intent mainIntent = new Intent(Login.this, MainActivity.class);
                                Login.this.startActivity(mainIntent);
                                Login.this.finish();
                            }
                        }
                    }, SPLASH_DISPLAY_LENGTH);

                }
            }

        });




    }
}