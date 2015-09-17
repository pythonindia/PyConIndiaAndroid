package com.pyconindia.pycon;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.pyconindia.pycon.storage.ApplicationData;
import com.pythonindia.pycon.R;

public class FeedbackActivity extends BaseActivity {

    private ApplicationData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setIcon(R.drawable.footerlogo);
        data = new ApplicationData(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();
    }

    private void initComponents() {


    }


}
