package com.seg3525_project.pdfviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

public class PDFActivity extends AppCompatActivity {

    private WebView pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pdf = (WebView) findViewById(R.id.pdf);
        pdf.getSettings().setJavaScriptEnabled(true);
        pdf.loadUrl("http://www.lssu.edu/blackboard/documents/SampleSyllabus.pdf");

    }

}
