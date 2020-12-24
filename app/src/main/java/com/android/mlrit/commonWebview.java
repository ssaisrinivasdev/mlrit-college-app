package com.android.mlrit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class commonWebview extends AppCompatActivity {

    WebView webView;
    String link,currentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_webview);

        webView = findViewById(R.id.cWebView);

        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        final Dialog dialog = new Dialog(commonWebview.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.no_internet);
        dialog.setCancelable(true);
        Button button = dialog.findViewById(R.id.ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonWebview.super.onBackPressed();
            }
        });


        link = getIntent().getStringExtra("link");

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipelayoutweb);
        swipeRefreshLayout.setColorSchemeResources(R.color.Aqua,R.color.Blue,R.color.Violet,R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(true);

        currentUrl = link;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(currentUrl);
            }
        });
        webView.loadUrl(link);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, final String url){
                swipeRefreshLayout.setRefreshing(false);
                currentUrl = url;
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                swipeRefreshLayout.setRefreshing(false);
                dialog.show();
            }
        });
        webView.getSettings().setBuiltInZoomControls(true);

    }
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else
            super.onBackPressed();
    }
}