package com.ks.naotu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    WebView vweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        vweb = (WebView) findViewById(R.id.vweb);
        vweb.loadUrl("file:///android_asset/jsmind/index.html");
        WebSettings settings=vweb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setBuiltInZoomControls(false);
    }
}
