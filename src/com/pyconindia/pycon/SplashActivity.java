package com.pyconindia.pycon;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.pyconindia.pycon.storage.ApplicationData;
import com.pyconindia.pycon.storage.DeviceUuidFactory;
import com.pythonindia.pycon.R;
import com.pythonindia.pycon.http.Api;
import com.pythonindia.pycon.http.Api.UrlType;

public class SplashActivity extends BaseActivity {

	private ApplicationData data;
	private int p = 0;
	boolean failed;
	private static final String TAG = "SplashActivity";
	private static final int NUMBER_OF_API_CALLS = 2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Api api = new Api(this, this);
		data = new ApplicationData(this);
        JSONObject scheduleList = data.getScheduleList();

        if(data.isDeviceVerified() && scheduleList != null) {
            startScheduleActivity();
        } else {
            DeviceUuidFactory factory = new DeviceUuidFactory(this);
            String uuid = factory.getDeviceUuid().toString();
            api.getSchedulesList();
//            api.getRooms(); // We don't need this for the time being
            api.verifyDevice(uuid);
        }
        failed = false;
	}

	@Override
	public void onSuccess(int statusCode, JSONObject response, UrlType urlType) {
		if(urlType == UrlType.DEVICE_VERIFY) {
		    if(response.has("uuid")) {
    		    String verified = "1";
    		    data.setDeviceVerified(verified);
    		    progress();
		    }
		} else if(urlType == UrlType.SCHEDULLES_LIST) {
		    data.setSchedulesList(response);
	        progress();
		}
	}

	@Override
	public void onSuccess(int statusCode, JSONArray response, UrlType urlType) {
		data.setRooms(response);
		progress();
	}

	@Override
    public void onFailure(int statusCode, Throwable e, UrlType urlType,
            JSONObject response) {
	    if(urlType == UrlType.DEVICE_VERIFY) {
            Toast.makeText(this, "Device Verification Failed", Toast.LENGTH_SHORT).show();
            finish();
        } else if(urlType == UrlType.SCHEDULLES_LIST) {
            Toast.makeText(this, "Showing old data", Toast.LENGTH_SHORT).show();
        }
    }

	private void progress() {
		p++;
		if(p == NUMBER_OF_API_CALLS) {
		    startScheduleActivity();
		}
		if(p != NUMBER_OF_API_CALLS) {
            JSONArray rooms = data.getRooms();
            JSONObject scheduleList = data.getScheduleList();
            if(rooms != null && scheduleList != null) {
                startScheduleActivity();
            } else {
                showNoNetworkActivity();
            }
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

