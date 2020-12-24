package com.android.mlrit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;


    public TextView stdname,roll;
    public String newslink,alllinks;
    private GridLayout gridLayout;
    private CardView meCardView;
    public WebView news;
    Dialog dialog;
    public CardView  clgwebsite,aboutme,holidays,contactus,tutionfee,regularfee,supplyfee,midmarks,internalmarks,overallmarks,overallmarkssemwise,semhallticket,internalhallticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stdname = findViewById(R.id.name);
        roll = findViewById(R.id.roll);
        news = findViewById(R.id.news);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        textView=findViewById(R.id.textView);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.no_internet);
        dialog.setCancelable(true);
        Button button = dialog.findViewById(R.id.ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        checkInternet();


        init();





        stdname.setText(new PrefManager(MainActivity.this).returnDetails("Name"));
        roll.setText(new PrefManager(this).returnDetails("HallTicket"));
        news.setBackgroundColor(getResources().getColor(R.color.lightbackground));


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        toolbar.setNavigationIcon(R.drawable.menu_icon);

        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.background));


        FirebaseDatabase.getInstance().getReference("newslink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newslink = snapshot.getValue().toString();
                news.loadData(newslink, "text/html", "UTF-8");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo data = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(!wifi.isConnected()&&!data.isConnected()){
            dialog.show();
            Toast.makeText(this, "Make Sure You Have Internet!..", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    private void init(){

        clgwebsite = findViewById(R.id.clgwebsite);
        aboutme = findViewById(R.id.aboutme);
        holidays = findViewById(R.id.holidays);
        contactus = findViewById(R.id.contactus);
        tutionfee = findViewById(R.id.tutionfee);
        regularfee = findViewById(R.id.regularfee);
        supplyfee = findViewById(R.id.supplyfee);
        midmarks = findViewById(R.id.midmarks);
        internalmarks = findViewById(R.id.internalmarks);
        overallmarks = findViewById(R.id.overallmarks);
        overallmarkssemwise = findViewById(R.id.overallmarkssemwise);
        semhallticket = findViewById(R.id.semhallticket);
        internalhallticket = findViewById(R.id.internalhallticket);


        clgwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_website = new Intent(MainActivity.this,commonWebview.class);
                intent_website.putExtra("link","https://mlrinstitutions.ac.in/");
                startActivity(intent_website);
            }
        });
        aboutme.setOnClickListener(this);
        holidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_portal = new Intent(MainActivity.this,commonWebview.class);
                intent_portal.putExtra("link","http://103.15.62.229/results/mlrit-login/");
                startActivity(intent_portal);
            }
        });
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_portal = new Intent(MainActivity.this,commonWebview.class);
                intent_portal.putExtra("link","https://mlrinstitutions.ac.in/Contactus");
                startActivity(intent_portal);
            }
        });
        tutionfee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "We Recommend To Pay Through College Website", Toast.LENGTH_LONG).show();
                String uri = "https://m.p-y.tm/mlrit_sms";
                Intent intent_paytm = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent_paytm);
            }
        });
        regularfee.setOnClickListener(this);
        supplyfee.setOnClickListener(this);
        midmarks.setOnClickListener(this);
        internalmarks.setOnClickListener(this);
        overallmarkssemwise.setOnClickListener(this);
        overallmarks.setOnClickListener(this);
        semhallticket.setOnClickListener(this);
        internalhallticket.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aboutme:
                alllinks = "https://exams.mlrinstitutions.ac.in/StudentLogin/Student/StudentInformation.aspx";
                break;
            case R.id.regularfee:
                alllinks = "https://exams.mlrinstitutions.ac.in/StudentLogin/Student/ExamFeePaymentReg.aspx";
                break;
            case R.id.supplyfee:
                alllinks = "https://exams.mlrinstitutions.ac.in/StudentLogin/Student/ExamFeePaymentSupply.aspx";
                break;
            case R.id.midmarks:
                alllinks = "https://exams.mlrinstitutions.ac.in/StudentLogin/Student/MidMarks.aspx";
                break;
            case R.id.internalmarks:
                alllinks = "https://exams.mlrinstitutions.ac.in/StudentLogin/Student/InternalMarks.aspx";
                break;
            case R.id.overallmarks:
                alllinks = "https://exams.mlrinstitutions.ac.in/StudentLogin/Student/overallMarks.aspx";
                break;
            case R.id.overallmarkssemwise:
                alllinks = "https://exams.mlrinstitutions.ac.in/StudentLogin/Student/OverallMarksSemwise.aspx";
                break;
            case R.id.semhallticket:
                alllinks = "https://exams.mlrinstitutions.ac.in/StudentLogin/Student/StudentHallTicketDownloading.aspx";
                break;
            case R.id.internalhallticket:
                alllinks = "https://exams.mlrinstitutions.ac.in/StudentLogin/Student/StudentHallTicketDownloadingInternal.aspx";
                break;

        }
        Intent intent = new Intent(MainActivity.this, Webviewer.class);
        intent.putExtra("link",alllinks);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_website:
                Intent intent_website = new Intent(MainActivity.this,commonWebview.class);
                intent_website.putExtra("link","https://mlrinstitutions.ac.in/");
                startActivity(intent_website);
                break;
            case R.id.nav_portal:
                Intent intent_portal = new Intent(MainActivity.this,commonWebview.class);
                intent_portal.putExtra("link","http://103.15.62.229/results/mlrit-login/");
                startActivity(intent_portal);
                break;
            case R.id.nav_profile:
                Intent intent_profile = new Intent(MainActivity.this,userProfile.class);
                startActivity(intent_profile);
                break;
            case R.id.nav_map:
                String uri = "https://goo.gl/maps/QzGjpacQJgE2k1BJ6";
                Intent intent_map = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent_map);
                break;
            case R.id.nav_admin:
                String deviceName = android.os.Build.MODEL; // returns model name
                String deviceManufacturer = android.os.Build.MANUFACTURER; // returns manufacturer
                Intent intent_mail = new Intent(Intent.ACTION_SEND);
                intent_mail.putExtra(Intent.EXTRA_EMAIL, new String[]{"ssaisrinivas616@gmail.com"});
                intent_mail.putExtra(Intent.EXTRA_SUBJECT, "MLRIT Application : Mobile Model: "+deviceName+" ");
                intent_mail.putExtra(Intent.EXTRA_TEXT, "Issue:");
                intent_mail.setType("text/html");
                intent_mail.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent_mail, "Send mail"));
                break;
            case R.id.nav_logout:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new PrefManager(MainActivity.this).Logout();
                                Intent mainIntent = new Intent(MainActivity.this, UserDetails.class);
                                MainActivity.this.startActivity(mainIntent);
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}