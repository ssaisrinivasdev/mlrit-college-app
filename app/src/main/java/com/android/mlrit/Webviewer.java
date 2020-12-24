package com.android.mlrit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.platforminfo.GlobalLibraryVersionRegistrar;

public class Webviewer extends AppCompatActivity {

    public WebView webView;
    public String alllinks,HallTicket,password_exam,currentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webviewer);

        HallTicket = new PrefManager(this).returnUserDetails("HallTicket");
        password_exam = new PrefManager(this).returnUserDetails("Password");

        Toast.makeText(Webviewer.this, "Loading...", Toast.LENGTH_LONG).show();

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipelayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.Aqua,R.color.Blue,R.color.Violet);
        swipeRefreshLayout.setRefreshing(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(currentUrl);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        final Dialog dialog = new Dialog(Webviewer.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.no_internet);
        dialog.setCancelable(true);
        Button button = dialog.findViewById(R.id.ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Webviewer.super.onBackPressed();
            }
        });


        webView = findViewById(R.id.mWebView);
        alllinks = getIntent().getStringExtra("link");
        String url = "https://exams.mlrinstitutions.ac.in/Login.aspx";
        currentUrl = "https://exams.mlrinstitutions.ac.in/Login.aspx";
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false);
        final  String js = "javascript:document.getElementById('lnkStudent').click();"+"document.getElementById('txtUserId').value='"+HallTicket+"';"+"document.getElementById('txtPwd').value='"+
                password_exam+"';" +"document.getElementById('btnLogin').click()";
        webView.getSettings().setBuiltInZoomControls(true);

        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, final String url){
                    view.evaluateJavascript(js , new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            if(url.equals("https://exams.mlrinstitutions.ac.in/StudentLogin/MainStud.aspx")){
                                webView.loadUrl(alllinks);
                            }
                            if(url.equals(alllinks)){
                                webView.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                            currentUrl = url;
                        }
                    });
                }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                swipeRefreshLayout.setRefreshing(false);
                dialog.show();
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}