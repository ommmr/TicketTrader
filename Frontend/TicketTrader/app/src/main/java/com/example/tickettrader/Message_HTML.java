package com.example.tickettrader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;


// This method is only used if we cannot get the message to work natively. It will load an HTML
// And uses the .JS file to open a socket.
public class Message_HTML extends AppCompatActivity {

    WebView webHtmlCss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message__html);

        webHtmlCss = (WebView) findViewById(R.id.web_view);

        WebSettings ws = webHtmlCss.getSettings();
        ws.setJavaScriptEnabled(true);

        webHtmlCss.loadUrl("file:///android_asset/index.html");


    }
}
