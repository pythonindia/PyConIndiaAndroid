package com.pyconindia.pycon;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pyconindia.pycon.models.ChoiceQuestion;
import com.pyconindia.pycon.models.FeedbackChoice;
import com.pyconindia.pycon.models.FeedbackQuestion;
import com.pyconindia.pycon.models.FeedbackQuestionList;
import com.pyconindia.pycon.storage.ApplicationData;
import com.pythonindia.pycon.R;

public class FeedbackActivity extends BaseActivity {

    private ApplicationData data;
    private String type;

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

        data = new ApplicationData(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString("type");
            JSONObject feedbackObj = data.getFeedbackQuestions();
            FeedbackQuestionList list = new FeedbackQuestionList(type, feedbackObj);
            for(ChoiceQuestion question : list.getChoiceQuestions()) {
                LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.feedback_choice, null);
                LinearLayout choicesLayout = (LinearLayout) v.findViewById(R.id.choices);
                TextView title = (TextView) v.findViewById(R.id.title);
                title.setText(question.getTitle());
                RadioGroup group = new RadioGroup(this);
                for(FeedbackChoice choice : question.getChoices()) {
                    RadioButton b = new RadioButton(this);
                    b.setTextColor(getResources().getColor(android.R.color.black));
                    b.setText(choice.getTitle());
                    group.addView(b);
                }
                choicesLayout.addView(group);
                questionsLayout.addView(v);
            }
            for(FeedbackQuestion question : list.getTextQuestions()) {
                LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.feedback_choice, null);
                LinearLayout choicesLayout = (LinearLayout) v.findViewById(R.id.choices);
                TextView title = (TextView) v.findViewById(R.id.title);
                title.setText(question.getTitle());

                EditText text = (EditText) inflater.inflate(R.layout.text_question, null);
                choicesLayout.addView(text);
                questionsLayout.addView(v);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
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
        super.onBackPressed();

    }
}
