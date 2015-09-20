package com.pyconindia.pycon.storage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pyconindia.pycon.models.ScheduleItem;
import com.pyconindia.pycon.models.Talk;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationData {

	private Context context;
	private static String PYCON_2015 = "PYCON_2015";
	private static String SCHEDULES_LIST = "SCHEDULES_LIST";
	private static String ROOMS_LIST = "ROOMS_LIST";
	private static String LIKED_LIST = "LIKED_LIST";
	private static String FEEDBACK_LIST = "FEEDBACK_LIST";
	private static String DEVICE_VERIFIED = "DEVICE_VERIFIED";
	private static String FEEDBACK_QUESTIONS = "FEEDBACK_QUESTIONS";
	private static String CURRENT_TALK = "CURRENT_TALK";

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
		return getJSONObjectFromKey(SCHEDULES_LIST);
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

	public void setFeedbackGiven(String key, boolean value) {
	    JSONObject obj = getScheduleFeedback();
        try {
            obj.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setScheduleFeedback(obj);
	}

	public void setScheduleLikes(JSONObject likeObj) {
	    setString(LIKED_LIST, likeObj.toString());
	}

	public JSONObject getScheduleLikes() {
        return getJSONObjectFromKey(LIKED_LIST);
	}

	public void setScheduleFeedback(JSONObject feedbackObj) {
        setString(FEEDBACK_LIST, feedbackObj.toString());
    }

    public JSONObject getScheduleFeedback() {
        return getJSONObjectFromKey(FEEDBACK_LIST);
    }

    public boolean isDeviceVerified() {
        String s = getString(DEVICE_VERIFIED);
        if(s != null) {
            return true;
        }
        return false;
    }

    public String getDeviceUUID() {
        return getString(DEVICE_VERIFIED);
    }

    public void setDeviceVerified(String verified) {
        setString(DEVICE_VERIFIED, verified);
    }

    public void setFeedbackQuestions(String feedback) {
        setString(FEEDBACK_QUESTIONS, feedback);
    }

    public JSONObject getFeedbackQuestions() {
        return getJSONObjectFromKey(FEEDBACK_QUESTIONS);
    }

    private JSONObject getJSONObjectFromKey(String key) {
        String s = getString(key);
        try {
            return s == null ? new JSONObject() : new JSONObject(s);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    public void setCurrentTalk(Talk talk) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("title", talk.getTitle());
            obj.put("markdown", talk.getMarkdown());
            obj.put("id", talk.getId());
            obj.put("room_id", talk.getAudiNo());
            obj.put("event_date", talk.getEventDate());
            if(talk.getAuthor() != "") {
                obj.put("author", talk.getAuthor());
            }
            if(talk.getSpeakerInfo() != "") {
                obj.put("speaker_info", talk.getSpeakerInfo());
            }
            if(talk.getSection() != "") {
                obj.put("section", talk.getSection());
            }
            if(talk.getStartTime() != "") {
                obj.put("start_time", talk.getStartTime());
            }
            if(talk.getEndTime() != "") {
                obj.put("end_time", talk.getEndTime());
            }
            if(talk.getPrerequisites() != "") {
                obj.put("prerequisites", talk.getPrerequisites());
            }
            if(talk.getType() != "") {
                obj.put("type", talk.getType());
            }
            if(talk.getTargetAudience() != -1) {
                obj.put("target_audience", talk.getTargetAudience());
            }
            if(talk.getContentUrls() != "") {
                obj.put("content_urls", talk.getContentUrls());
            }
            setString(CURRENT_TALK, obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Talk getCurrentTalk() {
        Talk talk = null;
        try {
        JSONObject talkObj = getJSONObjectFromKey(CURRENT_TALK);
        if(talkObj.has("title")) {
            String name, markdown, type, eventDate = "";
            int roomId, talkId;
            boolean like = false, feedback = false;
            talkId = talkObj.getInt("id");
            markdown = talkObj.getString("markdown");
            roomId = talkObj.getInt("room_id");
            type = talkObj.getString("type");
            name = talkObj.getString("title");
            eventDate = talkObj.getString("event_date");
            talk = new Talk(talkId, name, markdown, type, eventDate, roomId, like, feedback);

            if(talkObj.has("content_urls")) {
                talk.setContentUrls(talkObj.getString("content_urls"));
            }
            if(talkObj.has("speaker_info")) {
                talk.setSpeakerInfo(talkObj.getString("speaker_info"));
            }
            if(talkObj.has("target_audience")) {
                talk.setTargetAudience(talkObj.getInt("target_audience"));
            }
            if(talkObj.has("prerequisites")) {
                talk.setPrerequisites(talkObj.getString("prerequisites"));
            }
            if(talkObj.has("section")) {
                talk.setSection(talkObj.getString("section"));
            }
            if(talkObj.has("author")) {
                talk.setAuthor(talkObj.getString("author"));
            }
            if(talkObj.has("section")) {
                talk.setSection(talkObj.getString("section"));
            }
            if(talkObj.has("start_time")) {
                talk.setStartTime(talkObj.getString("start_time"));
            }
            if(talkObj.has("end_time")) {
                talk.setEndTime(talkObj.getString("end_time"));
            }
        }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return talk;
    }
}
