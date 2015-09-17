package com.pyconindia.pycon;

import java.io.IOException;

import org.markdown4j.Markdown4jProcessor;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.pyconindia.pycon.storage.ApplicationData;
import com.pythonindia.pycon.R;
import com.pythonindia.pycon.http.Api;

public class DetailsActivity extends BaseActivity {

    private ApplicationData data;
    private String title;
    private String description;
    private WebView webview;
    private RelativeLayout feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        webview = (WebView) findViewById(R.id.description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalis");
        getSupportActionBar().setIcon(R.drawable.footerlogo);

        data = new ApplicationData(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("title");
            description = extras.getString("description");
        }

        feedback = (RelativeLayout) findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startFeedbackActivity();
            }
        });
    }

    private void startFeedbackActivity() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();
    }

    private void initComponents() {

        String markdownSource = "###"+title +"\n\r\n\r"+ description + "";
        String data;
        try {
            data = new Markdown4jProcessor().process(markdownSource);
            webview.loadDataWithBaseURL(Api.SCHEDULLES_LIST, data, "text/html", "UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
