package com.pyconindia.pycon;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.markdown4j.Markdown4jProcessor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pyconindia.pycon.http.Api;
import com.pyconindia.pycon.models.Talk;
import com.pyconindia.pycon.storage.ApplicationData;

public class DetailsActivity extends BaseActivity {

    private ApplicationData data;
    private WebView webview;
    private ImageButton feedbackBtn;
    private String type;
    private Talk talk;
    private static final long SEVEN_DAYS = 7 * 24 * 60 * 60 * 1000;
    private static final String[] TARGET_AUDIENCE = {"Beginner", "Intermediate", "Advanced"};

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

        feedbackBtn = (ImageButton) findViewById(R.id.feedback);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isFeedbackEnabled()) {
                    startFeedbackActivity();
                } else {
                    Toast.makeText(DetailsActivity.this, "Feedback can be given after session starts", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isFeedbackEnabled() {
        boolean enabled = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault());
        try {
            Date startDate = sdf.parse(talk.getEventDate() + " " + talk.getStartTime());
            Date endDate = sdf.parse(talk.getEventDate() + " " + talk.getEndTime());
            Date now = new Date();

            if(now.getTime() < startDate.getTime()) {
                enabled = false;
            }
            if(now.getTime() > SEVEN_DAYS + endDate.getTime()) {
                enabled = false;
            }
        } catch (ParseException e) {
            e.printStackTrace(); // Big Trouble!!
        }
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
        String markdownSource = "###"+talk.getTitle() + newline + newline;

        if(talk.getAuthor().trim() != "") {
           markdownSource += "<i><b>by: </b>"+ talk.getAuthor() + "</i>" + newline + newline;
        }
        if(talk.getSpeakerInfo() != "") {
            markdownSource += "<i><b>Speaker Info: </b></i>" + newline + talk.getSpeakerInfo() + newline + newline;
        }
        if(talk.getSection() != "") {
            markdownSource += "<i><b>Section: </b>"+ talk.getSection() + "</i>" + newline + newline;
        }
        if(talk.getEventDate() != "") {
            markdownSource += "<i><b>Event Date: </b>"+ talk.getEventDate() + "</i>" + newline + newline;
        }
        if(talk.getStartTime() != "") {
            markdownSource += "<i><b>Start Time: </b>" +talk.getStartTime() + "</i>" + newline + newline;
        }
        if(talk.getEndTime() != "") {
            markdownSource += "<i><b>End Time: </b>" +talk.getEndTime() + newline + newline;
        }
        if(talk.getPrerequisites() != "") {
            markdownSource += "<i><b> Prerequisites: </b>" + "</i>"+ newline +talk.getPrerequisites() + newline + newline;
        }
        if(talk.getType() != "") {
            markdownSource += "<i><b> Type: </b>" +talk.getType() + "</i>" + newline + newline;
        }
        if(talk.getTargetAudience() > 0 && talk.getTargetAudience() < 4) {
            markdownSource += "<i><b> Target Audience: </b>" +TARGET_AUDIENCE[talk.getTargetAudience()-1] + "</i>" + newline + newline;
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
            if(!isFeedbackEnabled()) {
                feedbackBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_gray));
            } else {
                feedbackBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
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
