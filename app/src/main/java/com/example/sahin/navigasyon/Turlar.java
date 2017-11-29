package com.example.sahin.navigasyon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Turlar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turlar);
        WebView webView=(WebView)findViewById(R.id.Wvturlar);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.etstur.com/Yurtdisi-Tatil-Turlari");
    }
}
