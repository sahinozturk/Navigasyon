package com.example.sahin.navigasyon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class OtelBul extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otel_bul);
        WebView webView=(WebView)findViewById(R.id.Wvotelbul);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.trivago.com.tr/");
    }
}
