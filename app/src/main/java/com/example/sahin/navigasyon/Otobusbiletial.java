package com.example.sahin.navigasyon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Otobusbiletial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otobusbiletial);
        WebView webView=(WebView)findViewById(R.id.Wvotobus);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.obilet.com/");
    }
}
