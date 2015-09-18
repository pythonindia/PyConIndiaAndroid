package com.pyconindia.pycon.http;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class Api {

	public static final String CONFERENCES_URL = "https://in.pycon.org/cfp/api/v1/conferences/";
	public static final String VENUES_URL = "https://in.pycon.org/cfp/api/v1/venues/";
	public static final String ROOMS_URL = "https://in.pycon.org/cfp/api/v1/rooms/?venue=1/";
	public static final String SCHEDULLES_LIST = "https://in.pycon.org/cfp/api/v1/schedules/?conference=1";
	public static final String DEVICE_VERIFRY_URL = "https://in.pycon.org/cfp/api/v1/devices/";
	public static final String FEEDBACK_QUESTION_URL = "https://in.pycon.org/cfp/api/v1/feedback_questions/?conference_id=1";
	public static final String FEEDBACK_SUBMIT_URL = "https://in.pycon.org/cfp/api/v1/feedback/";

	private ResponseHandler handler;
	private Context context;
	public static enum UrlType {
		CONFERENCES_URL,
		VENUES_URL,
		ROOMS_URL,
		SCHEDULLES_LIST,
		DEVICE_VERIFY,
		FEEDBACK_QUESTION_URL,
		FEEDBACK_SUBMIT_URL,
		OTHER
	}

	public Api(ResponseHandler handler, Context context) {
		this.handler = handler;
		this.context = context;
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

	public void verifyDevice(String uuid) {
	    JSONObject jsonParams = new JSONObject();
	    try {
            jsonParams.put("uuid", uuid);
            post(DEVICE_VERIFRY_URL, UrlType.DEVICE_VERIFY, jsonParams, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
	}

	public void submitFeedback(JSONObject params, String uuid) {
	    HashMap<String, String> map = new HashMap<String, String>();
	    map.put("Authorization", "Token: "+uuid);
	    Log.d("abhishek", uuid);
        post(FEEDBACK_SUBMIT_URL, UrlType.FEEDBACK_SUBMIT_URL, params, map);
    }

	public void getFeedbackQuestions() {
	    get(FEEDBACK_QUESTION_URL, UrlType.FEEDBACK_QUESTION_URL);
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
                    Log.d("abhishek", "response fail");
                    handler.onFailure(statusCode, throwable, urlType, response);
                }
		});
	}

	private void post(String url, final UrlType urlType, JSONObject jsonParams, HashMap<String, String> headers) {

	    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        StringEntity entity;
        try {
            entity = new StringEntity(jsonParams.toString());
            if(headers != null) {
                Iterator<Entry<String, String>> it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> map = it.next();
                    asyncHttpClient.addHeader(map.getKey(), map.getValue());
                    Log.d("abhishek", ""+map.getKey() +" - "+ map.getValue());
                }
            }

            asyncHttpClient.post(context, url, entity, "application/json", new JsonHttpResponseHandler(){
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
                        Log.d("abhishek", "response fail");
                        new Exception(throwable).printStackTrace();
                        handler.onFailure(statusCode, throwable, urlType, response);
                    }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

	}
}
