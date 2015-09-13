package com.pyconindia.pycon.storage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationData {

	private Context context;
	private static String PYCON_2015 = "PYCON_2015";
	private static String SCHEDULES_LIST = "SCHEDULES_LIST";
	private static String ROOMS_LIST = "ROOMS_LIST";
	private static String LIKED_LIST = "LIKED_LIST";
	private static String FEEDBACK_LIST = "FEEDBACK_LIST";

	public ApplicationData(Context context) {
		this.context = context;
	}

	public void setSchedulesList(JSONObject list) {
		setString(SCHEDULES_LIST, list.toString());
	}

	public void setRooms(JSONArray list) {
		setString(ROOMS_LIST, list.toString());
	}

	public JSONObject getScheduleList() {
		String s = getString(SCHEDULES_LIST);
		try {
			return s == null ? null : new JSONObject(s);
		} catch (JSONException e) {
			return null;
		}
	}

	public JSONArray getRooms() {
		String s = getString(SCHEDULES_LIST);
		try {
			return s == null ? null : new JSONArray(s);
		} catch (JSONException e) {
			return null;
		}
	}

	private void setString(String key, String value) {
		SharedPreferences sharedPref = context.getSharedPreferences(PYCON_2015, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(key, value);
		editor.apply();
	}

	private String getString(String key) {
		SharedPreferences sharedPref = context.getSharedPreferences(PYCON_2015, Context.MODE_PRIVATE);
		return sharedPref.getString(key, null);
	}

	public void setTalkLike(String key, boolean value) {
	    JSONObject obj = getScheduleLikes();
	    try {
            obj.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
	    setScheduleLikes(obj);
	}

	public void setScheduleLikes(JSONObject likeObj) {
	    setString(LIKED_LIST, likeObj.toString());
	}

	public JSONObject getScheduleLikes() {
	    String s = getString(LIKED_LIST);
        try {
            return s == null ? new JSONObject() : new JSONObject(s);
        } catch (JSONException e) {
            return new JSONObject();
        }
	}

	public void setScheduleFeedback(JSONObject likeObj) {
        setString(FEEDBACK_LIST, likeObj.toString());
    }

    public JSONObject getScheduleFeedback() {
        String s = getString(FEEDBACK_LIST);
        try {
            return s == null ? new JSONObject() : new JSONObject(s);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }
}
