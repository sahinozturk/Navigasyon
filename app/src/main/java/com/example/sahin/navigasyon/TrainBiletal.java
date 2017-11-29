package com.example.sahin.navigasyon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class TrainBiletal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_biletal);
        WebView webView=(WebView)findViewById(R.id.Wvtrenbilet);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.biletall.com/tren-bileti");
    }
}
