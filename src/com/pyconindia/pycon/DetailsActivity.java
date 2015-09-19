package com.pyconindia.pycon;

import java.io.IOException;

import org.markdown4j.Markdown4jProcessor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.pyconindia.pycon.http.Api;
import com.pyconindia.pycon.storage.ApplicationData;
import com.pythonindia.pycon.R;

public class DetailsActivity extends BaseActivity {

    private ApplicationData data;
    private String title;
    private String description;
    private WebView webview;
    private RelativeLayout feedbackLayout;
    private String type;
    private int id;
    private boolean feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        webview = (WebView) findViewById(R.id.description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalis");
        getSupportActionBar().setIcon(R.drawable.footerlogo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data = new ApplicationData(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            title = extras.getString("title");
            description = extras.getString("description");
            type = extras.getString("type");
            feedback = extras.getBoolean("feedback");
        }

        feedbackLayout = (RelativeLayout) findViewById(R.id.feedback);
        feedbackLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startFeedbackActivity();
            }
        });

        if((type.equals("Workshop") || type.equals("Talk") ) && !feedback) {
            feedbackLayout.setVisibility(View.VISIBLE);
        } else {
            feedbackLayout.setVisibility(View.GONE);
        }
    }

    private void startFeedbackActivity() {
        Intent intent = new Intent(DetailsActivity.this, FeedbackActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(!super.onOptionsItemSelected(item)) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
