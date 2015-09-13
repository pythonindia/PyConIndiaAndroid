package com.pyconindia.pycon;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.pyconindia.pycon.storage.ApplicationData;
import com.pythonindia.pycon.R;
import com.pythonindia.pycon.http.Api;
import com.pythonindia.pycon.http.Api.UrlType;

public class SplashActivity extends BaseActivity {

	private ApplicationData data;
	private int p = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Api api = new Api(this);
		data = new ApplicationData(this);
        JSONObject scheduleList = data.getScheduleList();
        if(scheduleList != null) {
            startScheduleActivity();
        } else {
            api.getSchedulesList();
            api.getRooms();
        }

	}

	@Override
	public void onSuccess(int statusCode, JSONObject response, UrlType urlType) {
		data.setSchedulesList(response);
		progress();

	}

	@Override
	public void onSuccess(int statusCode, JSONArray response, UrlType urlType) {
		data.setRooms(response);
		progress();
	}

	@Override
    public void onFailure(int statusCode, Throwable e, UrlType urlType,
            JSONObject response) {
	    p++;
	    if(p == 2) {
    	    JSONArray rooms = data.getRooms();
            JSONObject scheduleList = data.getScheduleList();
            if(rooms != null && scheduleList != null) {
                startScheduleActivity();
            } else {
                showNoNetworkActivity();
            }
	    }
    }

	private void progress() {
		p++;
		if(p == 2) {
		    startScheduleActivity();
		}
	}

	private void startScheduleActivity() {
	    Intent intent = new Intent(SplashActivity.this, ScheduleActivity.class);
        startActivity(intent);
        finish();
	}

	private void showNoNetworkActivity() {
	    Intent intent = new Intent(SplashActivity.this, NoNetworkActivity.class);
        startActivity(intent);
        finish();
	}

	@Override
    public void onBackPressed() {

    }
}

