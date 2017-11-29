package com.example.sahin.navigasyon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Ucakbileti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucakbileti);

        WebView webView=(WebView)findViewById(R.id.Wvucakbileti);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.turna.com/");
    }
}
