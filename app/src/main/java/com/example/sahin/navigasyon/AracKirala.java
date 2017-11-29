package com.example.sahin.navigasyon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class AracKirala extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arac_kirala);
        WebView webView=(WebView)findViewById(R.id.Wvarackiral);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.rentalcars.com/tr/");
    }
}
