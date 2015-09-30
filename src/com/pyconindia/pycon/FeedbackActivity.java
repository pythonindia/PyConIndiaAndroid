package com.pyconindia.pycon;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pyconindia.pycon.http.Api;
import com.pyconindia.pycon.http.Api.UrlType;
import com.pyconindia.pycon.models.ChoiceQuestion;
import com.pyconindia.pycon.models.FeedbackChoice;
import com.pyconindia.pycon.models.FeedbackQuestionList;
import com.pyconindia.pycon.models.TextQuestion;
import com.pyconindia.pycon.storage.ApplicationData;
import com.pyconindia.pycon.view.PRadioButton;

public class FeedbackActivity extends BaseActivity {

    private ApplicationData data;
    private String type;
    private int id;
    private Api api;
    private ProgressDialog progress;
    private HashMap<Integer, EditText> textMap = new HashMap<Integer, EditText>();
    private HashMap<Integer, RadioButton> radioMap = new HashMap<Integer, RadioButton>();
    private FeedbackQuestionList list;
    private static final String TAG = "FeedbackActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setIcon(R.drawable.footerlogo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayout questionsLayout = (LinearLayout) findViewById(R.id.feedback);
        api = new Api(this, this);
        data = new ApplicationData(this);
        progress = new ProgressDialog(this);
        progress.setCancelable(true);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("Sending Feedback...");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString("type");
            id = extras.getInt("id");
            JSONObject feedbackObj = data.getFeedbackQuestions();
            list = new FeedbackQuestionList(type, feedbackObj);
            int i = 0;
            for(ChoiceQuestion question : list.getChoiceQuestions()) {
                LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.feedback_choice, null);
                LinearLayout choicesLayout = (LinearLayout) v.findViewById(R.id.choices);
                TextView title = (TextView) v.findViewById(R.id.title);
                String titleText = question.getTitle();
                titleText += question.isRequired() ? " *" : "";
                title.setText(titleText);
                RadioGroup group = new RadioGroup(this);
                group.setId(i);
                for(FeedbackChoice choice : question.getChoices()) {
                    final PRadioButton b = new PRadioButton(this, choice.getId(), choice.getValue(), question.getId());
                    b.setTextColor(getResources().getColor(android.R.color.black));
                    b.setText(choice.getTitle());
                    b.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            clearRadio();
                            radioMap.put(b.getQuestionId(), b);
                        }
                    });
                    group.addView(b);
                }
                choicesLayout.addView(group);
                questionsLayout.addView(v);
                i++;
            }
            float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (150 * scale + 0.5f);

            for(TextQuestion question : list.getTextQuestions()) {
                LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.feedback_choice, null);
                LinearLayout choicesLayout = (LinearLayout) v.findViewById(R.id.choices);
                TextView title = (TextView) v.findViewById(R.id.title);
                title.setText(question.getTitle());

                EditText text = (EditText) inflater.inflate(R.layout.text_question, null);
                textMap.put(question.getId(), text);
                text.setHeight(pixels);
                choicesLayout.addView(text);
                questionsLayout.addView(v);
            }
        }

        RelativeLayout feedback = (RelativeLayout) findViewById(R.id.feedbackLayout);
        feedback.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(validate()) {
                    try {
                        JSONObject params = new JSONObject();

                        JSONArray textArr = new JSONArray();
                        JSONArray choiceArr = new JSONArray();
                        Iterator<Entry<Integer, EditText>> it = textMap.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<Integer, EditText> pair = it.next();
                            String text = pair.getValue().getText().toString().trim();
                            if(!"".equals(text)) {
                                JSONObject textObj = new JSONObject();
                                textObj.put("id", pair.getKey());
                                textObj.put("text", pair.getValue().getText().toString());
                                textArr.put(textObj);
                            }
                        }

                        Iterator<Entry<Integer, RadioButton>>  it2 = radioMap.entrySet().iterator();
                        while (it2.hasNext()) {
                            Map.Entry<Integer, RadioButton> pair = it2.next();

                            JSONObject textObj = new JSONObject();
                            textObj.put("id", pair.getKey());
                            textObj.put("value_id", pair.getValue().getId());
                            choiceArr.put(textObj);
                        }

                        params.put("schedule_item_id", id);
                        params.put("choices", choiceArr);
                        params.put("text", textArr);
                        api.submitFeedback(params, data.getDeviceUUID());
                        progress.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });
    }

    private void clearRadio() {
        Iterator<Entry<Integer, RadioButton>>  it2 = radioMap.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<Integer, RadioButton> pair = it2.next();

            pair.getValue().setChecked(false);
        }
    }

    private boolean validate() {
        boolean pass = true;
        for(ChoiceQuestion question : list.getChoiceQuestions()) {
            if(question.isRequired()) {
                if(radioMap.containsKey(question.getId())) {
                    int id = radioMap.get(question.getId()).getId();
                    if(id == -1) {
                        Toast.makeText(this, "Choice Question is compulsary to answer.", Toast.LENGTH_SHORT).show();
                        pass = false;
                    }
                } else {
                    Toast.makeText(this, "Choice Question is compulsary to answer.", Toast.LENGTH_SHORT).show();
                    pass = false;
                }
            }
        }
        for(TextQuestion question : list.getTextQuestions()) {
            if(question.isRequired()) {
                EditText text = textMap.get(question.getId());
                if(text.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "Textual Question is compulsary to answer.", Toast.LENGTH_SHORT).show();
                    pass = false;
                }
            }
        }
        return pass;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(!super.onOptionsItemSelected(item)) {
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();
    }

    private void initComponents() {


    }

    @Override
    public void onBackPressed() {
        progress.dismiss();
        super.onBackPressed();
    }

    @Override
    public void onSuccess(int statusCode, JSONObject response, UrlType urlType) {
        progress.dismiss();
        if(statusCode == 201) {
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            data.setFeedbackGiven(""+id, true);
            onBackPressed();
        } else {
            Toast.makeText(this, "Something's not right", Toast.LENGTH_SHORT).show();
            Log.d(TAG, ""+response); //WTF??
        }
    }

    @Override
    public void onFailure(int statusCode, Throwable e, UrlType urlType,
            JSONObject response) {
        progress.dismiss();


        try {
            if(response != null && response.has("error")) {
                Log.d(TAG, response.toString());
                data.setFeedbackGiven(""+id, true);
                Toast.makeText(this, response.getString("error"), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No Network", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e1) {
            Log.e(TAG, new Exception(e1).toString());
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }


    }
}
