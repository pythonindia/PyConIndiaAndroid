package com.pyconindia.pycon.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class FeedbackQuestionList {

    private ArrayList<ChoiceQuestion> choiceQuestions;
    private ArrayList<TextQuestion> textQuestions;
    private static final String TAG = "FeedbackQuestionList";
    private static final String TYPE_WORKSHOP = "Workshop";
    private static final String TYPE_TALK = "Talk";
    private static final String TEXT = "text";
    private static final String CHOICE = "choice";

    public FeedbackQuestionList(String type, JSONObject feedbackObj) {
        choiceQuestions = new ArrayList<ChoiceQuestion>();
        textQuestions = new ArrayList<TextQuestion>();
        parse(type, feedbackObj);
    }

    public void parse(String type, JSONObject feedbackObj) {
        if(feedbackObj.has(type)) {
            try {
                JSONObject root = feedbackObj.getJSONObject(type);
                JSONArray textArr = root.getJSONArray(CHOICE);
                for(int i = 0; i < textArr.length(); i++) {
                    JSONObject obj = textArr.getJSONObject(i);
                        ChoiceQuestion q = new ChoiceQuestion();
                        q.setRequired(obj.getBoolean(FeedbackQuestion.REQUIRED));
                        q.setTitle(obj.getString(FeedbackQuestion.TITLE));
                        ArrayList<FeedbackChoice> choices = null;
                        if(obj.has(FeedbackQuestion.CHOICES)) {
                            choices = new ArrayList<FeedbackChoice>();
                            JSONArray choicesArr = obj.getJSONArray(FeedbackQuestion.CHOICES);

                            for(int j = 0; j < choicesArr.length(); j++) {
                                JSONObject choiceObj = choicesArr.getJSONObject(j);

                                FeedbackChoice choice = new FeedbackChoice();
                                choice.setId(choiceObj.getInt(FeedbackChoice.ID));
                                choice.setTitle(choiceObj.getString(FeedbackChoice.TITLE));
                                choice.setValue(choiceObj.getInt(FeedbackChoice.VALUE));
                                choices.add(choice);
                            }
                            q.setChoices(choices);
                        }
                        choiceQuestions.add(q);

                }
                textArr = root.getJSONArray(TEXT);
                for(int i = 0; i < textArr.length(); i++) {
                    JSONObject obj = textArr.getJSONObject(i);
                    TextQuestion q = new TextQuestion();
                    q.setRequired(obj.getBoolean(FeedbackQuestion.REQUIRED));
                    q.setTitle(obj.getString(FeedbackQuestion.TITLE));
                    textQuestions.add(q);
                }
            } catch (JSONException e) {
                Log.d(TAG, ""+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ChoiceQuestion> getChoiceQuestions() {
        return choiceQuestions;
    }

    public ArrayList<TextQuestion> getTextQuestions() {
        return textQuestions;
    }
}
