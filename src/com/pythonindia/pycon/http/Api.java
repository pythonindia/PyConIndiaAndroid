package com.pythonindia.pycon.http;

import org.apache.http.Header;
import org.json.JSONObject;
import org.json.JSONArray;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class Api {

	public static final String CONFERENCES_URL = "https://in.pycon.org/cfp/api/v1/conferences/";
	public static final String VENUES_URL = "https://in.pycon.org/cfp/api/v1/venues/";
	public static final String ROOMS_URL = "https://in.pycon.org/cfp/api/v1/rooms/?venue=1";
	public static final String SCHEDULLES_LIST = "https://in.pycon.org/cfp/api/v1/schedules/?conference=1";
	private ResponseHandler handler;
	public static enum UrlType {
		CONFERENCES_URL,
		VENUES_URL,
		ROOMS_URL,
		SCHEDULLES_LIST,
		OTHER
	}

	public Api(ResponseHandler handler) {
		this.handler = handler;
	}

	public void getSchedule() {
		get(CONFERENCES_URL, UrlType.CONFERENCES_URL);
	}

	public void getVenue() {
		get(VENUES_URL, UrlType.VENUES_URL);
	}

	public void getRooms() {
		get(ROOMS_URL, UrlType.ROOMS_URL);
	}

	public void getSchedulesList() {
		get(SCHEDULLES_LIST, UrlType.SCHEDULLES_LIST);
	}

	private void get(String url, final UrlType urlType) {
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.get(url, new JsonHttpResponseHandler(){
				@Override
	            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
	                handler.onSuccess(statusCode, response, urlType);
	            }
				@Override
	            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
					handler.onSuccess(statusCode, response, urlType);
	            }

				@Override
                public void onFailure(int statusCode, Header[] headers,
                        Throwable throwable, JSONObject response) {
                    super.onFailure(statusCode, headers, throwable, response);
                    Log.d("abhishek", "response fail");
                    handler.onFailure(statusCode, throwable, urlType, response);
                }
		});
	}
}
