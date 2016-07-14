package com.seg3525_project.pdfviewer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;


/**
 * Created by karim on 7/13/2016.
 */
public class MyPdfViewActivity extends Activity {

    private WebView webView1;


    //	inside this goes our pdf viewer, just a toy for this test. Requires  more work to make it production ready
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.loadUrl("http://www.lssu.edu/blackboard/documents/SampleSyllabus.pdf");

    }



}