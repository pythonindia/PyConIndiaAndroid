package com.pyconindia.pycon;

import java.io.IOException;

import org.markdown4j.Markdown4jProcessor;

import com.pyconindia.pycon.storage.ApplicationData;
import com.pythonindia.pycon.R;
import com.pythonindia.pycon.http.Api;

import android.os.Bundle;
import android.webkit.WebView;

public class DetailsActivity extends BaseActivity {

    private ApplicationData data;
    private String title;
    private String description;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        webview = (WebView) findViewById(R.id.description);

        data = new ApplicationData(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("title");
            description = extras.getString("description");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();
    }

    private void initComponents() {

//        PegDownProcessor pegDownProcessor = new PegDownProcessor((long)50000);
        String markdownSource = "#"+title +"\n\r\n\r"+ description;
        String data;
        try {
            data = new Markdown4jProcessor().process(markdownSource);
            webview.loadDataWithBaseURL(Api.SCHEDULLES_LIST, data, "text/html", "UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
