package com.pythonindia.pycon.http;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pythonindia.pycon.http.Api.UrlType;

public interface ResponseHandler {

	public void onSuccess(int statusCode, JSONObject response, UrlType urlType);
	public void onSuccess(int statusCode, JSONArray response, UrlType urlType);
	public void onFailure(int statusCode, String resonseString, Throwable e, UrlType urlType);
	public void onFailure(int statusCode, Throwable e, UrlType urlType, JSONObject response);
}
