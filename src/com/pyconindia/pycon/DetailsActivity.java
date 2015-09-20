package com.pyconindia.pycon;

import java.io.IOException;

import org.markdown4j.Markdown4jProcessor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.pyconindia.pycon.http.Api;
import com.pyconindia.pycon.models.Talk;
import com.pyconindia.pycon.storage.ApplicationData;

public class DetailsActivity extends BaseActivity {

    private ApplicationData data;
//    private String title;
//    private String description;
    private WebView webview;
    private ImageButton feedbackBtn;
    private String type;
//    private int id;
    private boolean feedback;
    private boolean feedbackEnabled;
    private Talk talk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        webview = (WebView) findViewById(R.id.description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getText(R.string.details_ctivity_title));
        getSupportActionBar().setIcon(R.drawable.footerlogo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data = new ApplicationData(this);

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            id = extras.getInt("id");
//            title = extras.getString("title");
//            description = extras.getString("description");
//            type = extras.getString("type");
//            feedback = extras.getBoolean("feedback");
//        }

        feedbackBtn = (ImageButton) findViewById(R.id.feedback);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startFeedbackActivity();
            }
        });




    }

    private boolean isFeedbackEnabled() {
        boolean enabled = false;
        return enabled;
    }

    private void startFeedbackActivity() {
        Intent intent = new Intent(DetailsActivity.this, FeedbackActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", talk.getId());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();
    }

    private String generateDetailMarkdown() {
        String newline = "\n\r";
        String markdownSource = "##"+talk.getTitle() + newline + newline;

        if(talk.getAuthor() != "") {
           markdownSource += "### Author: "+ newline +talk.getAuthor() + newline + newline;
        }
        if(talk.getSpeakerInfo() != "") {
            markdownSource += "### Speaker Info: "+ newline +talk.getSpeakerInfo() + newline + newline;
        }
        if(talk.getSection() != "") {
            markdownSource += "###Section: "+ newline +talk.getSection() + newline + newline;
        }
        if(talk.getStartTime() != "") {
            markdownSource += "###Start Time: "+ newline +talk.getStartTime() + newline + newline;
        }
        if(talk.getEndTime() != "") {
            markdownSource += "### End Time: "+ newline +talk.getEndTime() + newline + newline;
        }
        if(talk.getPrerequisites() != "") {
            markdownSource += "### Prerequisites: "+ newline +talk.getPrerequisites() + newline + newline;
        }
        if(talk.getType() != "") {
            markdownSource += "### Type: "+ newline +talk.getType() + newline + newline;
        }
        if(talk.getTargetAudience() != -1) {
            markdownSource += "### Target Audience: "+ newline +talk.getTargetAudience() + newline + newline;
        }
        if(talk.getContentUrls() != "") {
            markdownSource += "### Content Urls: "+ newline +talk.getContentUrls() + newline + newline;
        }
        if(talk.getDescription() != "") {
            markdownSource += "### Description: " + newline + newline + talk.getMarkdown();
        }
        return markdownSource;
    }

    private void initComponents() {
        talk = data.getCurrentTalk();
        if(talk == null) { // Whaaat!!
            Intent intent = new Intent(DetailsActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        } else {
            type = talk.getType();
            if((type.equals("Workshop") || type.equals("Talk") ) && !talk.isFeedbackGiven()) {
                feedbackBtn.setVisibility(View.VISIBLE);
            } else {
                feedbackBtn.setVisibility(View.GONE);
            }
            String markdownSource = generateDetailMarkdown();
            String data;
            try {
                data = new Markdown4jProcessor().process(markdownSource);
                webview.loadDataWithBaseURL(Api.SCHEDULLES_LIST, data, "text/html", "UTF-8", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
