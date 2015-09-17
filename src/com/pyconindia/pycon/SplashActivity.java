package com.pyconindia.pycon;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.pyconindia.pycon.storage.ApplicationData;
import com.pyconindia.pycon.storage.DeviceUuidFactory;
import com.pyconindia.pycon.storage.Installation;
import com.pythonindia.pycon.R;
import com.pythonindia.pycon.http.Api;
import com.pythonindia.pycon.http.Api.UrlType;

public class SplashActivity extends BaseActivity {

	private ApplicationData data;
	private int p = 0;
	private static final String TAG = "SplashActivity";
	private static int NUMBER_OF_API_CALLS = 2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Api api = new Api(this, this);
		data = new ApplicationData(this);
        JSONObject scheduleList = data.getScheduleList(); //TODO: Remove this after testing
        JSONObject feedbackObj = data.getFeedbackQuestions();

        if(data.isDeviceVerified()) {
//            if(scheduleList.length() > 0 && feedbackObj.has("Workshop")) { //TODO: Remove this after testing
//                startScheduleActivity();
//            } else {
                api.getFeedbackQuestions();
                api.getSchedulesList();
    //            api.getRooms(); // We don't need this for the time being
//            }
        } else {
            String uuid = Installation.id(this);
            api.verifyDevice(uuid);
            api.getFeedbackQuestions();
            api.getSchedulesList();
        }
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
		} else if(urlType == UrlType.FEEDBACK_QUESTION_URL) {
            data.setFeedbackQuestions(response.toString());
            progress();
        }
	}

	@Override
	public void onSuccess(int statusCode, JSONArray response, UrlType urlType) {
//		data.setRooms(response); // Rooms not called
//		progress();
	}

	@Override
    public void onFailure(int statusCode, Throwable e, UrlType urlType,
            JSONObject response) {
	    Log.d(TAG, new Exception(e).getMessage());
	    if(urlType == UrlType.DEVICE_VERIFY) {
            Toast.makeText(this, "Device Verification Failed", Toast.LENGTH_SHORT).show();
            finish();
        } else if(urlType == UrlType.SCHEDULLES_LIST) {
            JSONObject scheduleList = data.getScheduleList();
            if(scheduleList != null) {
                Toast.makeText(this, "Showing old data", Toast.LENGTH_SHORT).show();
                progress();
            } else {
                showNoNetworkActivity();
            }
        } else if(urlType == UrlType.FEEDBACK_QUESTION_URL) {
            JSONObject feedbackObj = data.getFeedbackQuestions();
            if(feedbackObj != null) {
                Log.d(TAG, "Found old feedback Show em!!");
                progress();
            } else {
                showNoNetworkActivity();
            }
        }
    }

	private void progress() {
		p++;
		if(p == NUMBER_OF_API_CALLS) {
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

