package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ezcats.ezkapal.R;

public class WebViewActivity extends AppCompatActivity {


    WebView webView;
    WebSettings webSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webViewID);
        webSettings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient());
        String kode = getIntent().getStringExtra("ticket_file");

        webView.loadUrl("http://10.0.2.2:8000/pdf/check/"+kode);
    }
}