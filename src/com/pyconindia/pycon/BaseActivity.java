package com.pyconindia.pycon;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pyconindia.pycon.http.ResponseHandler;
import com.pyconindia.pycon.http.Api.UrlType;
import com.pythonindia.pycon.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BaseActivity extends AppCompatActivity implements ResponseHandler {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onSuccess(int statusCode, JSONObject response, UrlType urlType) {

	}

	@Override
	public void onSuccess(int statusCode, JSONArray response, UrlType urlType) {

	}

	@Override
	public void onFailure(int statusCode, String resonseString, Throwable e, UrlType urlType) {

	}

    @Override
    public void onFailure(int statusCode, Throwable e, UrlType urlType,
            JSONObject response) {
        Log.d("abhishek", "response fail");
    }
}
