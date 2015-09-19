package com.pyconindia.pycon.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pyconindia.pycon.DetailsActivity;
import com.pyconindia.pycon.models.ScheduleItem;
import com.pyconindia.pycon.models.Talk;
import com.pyconindia.pycon.storage.ApplicationData;
import com.pyconindia.pycon.utils.Alarm;
import com.pythonindia.pycon.R;

public class ScheduleListAdapter<T extends ScheduleItem> extends ArrayAdapter<ScheduleItem> {

    private ArrayList<ScheduleItem> items;
//    private Typeface typeFace;
    private ApplicationData data;
    private Context context;

    public ScheduleListAdapter(Context context, int resource, ArrayList<ScheduleItem> items) {
        super(context, resource, items);
        this.items = items;
        this.context = context;
//        typeFace = Typeface.createFromAsset(context.getAssets(),"Helvetica.ttf");
        data = new ApplicationData(context);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.schedule_list_item, null);
        }
        ScheduleItem item = items.get(position);

        TextView timeView = (TextView) v.findViewById(R.id.timeView);
        timeView.setTypeface(null, Typeface.BOLD);

        timeView.setText(item.getStartTime() + "\n" + item.getEndTime());

        LinearLayout talkList = (LinearLayout) v.findViewById(R.id.talkList);
        talkList.removeAllViews();

        ArrayList<Talk> talks = item.getTalkList();
        View talkView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        talkView = inflater.inflate(R.layout.talk_item, null);
        for(int i = 0; i < talks.size(); i++) {
            talkView = inflater.inflate(R.layout.talk_item, null);
            final Talk talk = talks.get(i);
            TextView audiView = (TextView) talkView.findViewById(R.id.audiText);
            audiView.setTypeface(null, Typeface.BOLD);
            TextView titleText = (TextView) talkView.findViewById(R.id.title);
            titleText.setTypeface(null, Typeface.BOLD);
            TextView descText = (TextView) talkView.findViewById(R.id.desc);
//            descText.setTypeface(typeFace);
            ImageView feedbackImage = (ImageView) talkView.findViewById(R.id.feedback);
            final ImageView likedImage = (ImageView) talkView.findViewById(R.id.like);
            ImageView audiImg = (ImageView) talkView.findViewById(R.id.audiImg);

            if(talk.getAudiNo() == 1) {
                audiImg.setBackgroundResource(R.drawable.pycon_audi_1);
            } else if(talk.getAudiNo() == 2) {
                audiImg.setBackgroundResource(R.drawable.pycon_audi_2);
            } else if(talk.getAudiNo() == 3) {
                audiImg.setBackgroundResource(R.drawable.pycon_audi_3);
            } else {
                audiImg.setBackgroundResource(R.drawable.pycon_audi_4);
            }

            if(talk.isFeedbackGiven()) {
                feedbackImage.setImageResource(R.drawable.pycon_feedback_active);
            }

            if(talk.isLiked()) {
                likedImage.setImageResource(R.drawable.pycon_favorite_active);
            }

            final String date = item.getEventDate() +" "+ item.getStartTime();
            likedImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    talk.setLiked(!talk.isLiked());
                    data.setTalkLike(""+talk.getId(), talk.isLiked());

                    if(talk.isLiked()) {

                        Alarm alarm = new Alarm(context, talk.getId(), date, talk.getType()+" is about to start", talk.getTitle().toLowerCase());
                        alarm.create();
                        likedImage.setImageResource(R.drawable.pycon_favorite_active);
                    } else {
                        Alarm alarm = new Alarm(context, talk.getId(), date, talk.getType()+" is about to start", talk.getTitle().toLowerCase());
                        alarm.cancel();
                        likedImage.setImageResource(R.drawable.pycon_favorite_active);
                        likedImage.setImageResource(R.drawable.pycon_favorite);
                    }
                }
            });

            titleText.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startDetailsActivity(talk.getTitle(), talk.getMarkdown(), talk.getType(), talk.getId(), talk.isFeedbackGiven());
                }
            });

            descText.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startDetailsActivity(talk.getTitle(), talk.getMarkdown(), talk.getType(), talk.getId(), talk.isFeedbackGiven());
                }
            });

            titleText.setText(talk.getTitle());
            descText.setText(talk.getDescription());
            talkList.addView(talkView);
        }

        return v;

    }


    private void startDetailsActivity(String title, String description, String type, int id, boolean feedbackGiven) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("type", type);
        intent.putExtra("feedback", feedbackGiven);
        context.startActivity(intent);
    }

}
