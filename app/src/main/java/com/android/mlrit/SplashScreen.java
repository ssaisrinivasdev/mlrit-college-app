package com.android.mlrit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(!new PrefManager(this).FirstStatus()){
            new PrefManager(this).UpdateFirststatus("no");
        }


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(new PrefManager(SplashScreen.this).CheckDetails()) {
                    Intent mainIntent = new Intent(SplashScreen.this, UserDetails.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
                else if(new PrefManager(SplashScreen.this).CheckUser()){
                    Intent mainIntent = new Intent(SplashScreen.this, Login.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
                else{
                    Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
        
    }
}