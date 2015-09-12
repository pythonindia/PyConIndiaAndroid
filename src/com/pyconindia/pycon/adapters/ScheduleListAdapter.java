package com.pyconindia.pycon.adapters;

import java.util.ArrayList;

import com.pyconindia.pycon.models.ScheduleItem;
import com.pyconindia.pycon.models.Talk;
import com.pythonindia.pycon.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScheduleListAdapter<T extends ScheduleItem> extends ArrayAdapter<ScheduleItem> {

    private ArrayList<ScheduleItem> items;

    public ScheduleListAdapter(Context context, int resource, ArrayList<ScheduleItem> items) {
        super(context, resource, items);
        this.items = items;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.schedule_list_item, null);
        }

        LinearLayout talkList = (LinearLayout) v.findViewById(R.id.talkList);
        talkList.removeAllViews();
        ScheduleItem item = items.get(position);
        ArrayList<Talk> talks = item.getTalkList();
        View talkView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        talkView = inflater.inflate(R.layout.talk_item, null);
        for(int i = 0; i < talks.size(); i++) {
            talkView = inflater.inflate(R.layout.talk_item, null);
            Talk talk = talks.get(i);
            TextView titleText = (TextView) talkView.findViewById(R.id.title);
            TextView descText = (TextView) talkView.findViewById(R.id.desc);
            ImageView feedbackImage = (ImageView) talkView.findViewById(R.id.feedback);
            ImageView likedImage = (ImageView) talkView.findViewById(R.id.like);

            if(talk.isFeedbackGiven()) {
                feedbackImage.setImageResource(R.drawable.pycon_feedback_active);
            }

            if(talk.isLiked()) {
                likedImage.setImageResource(R.drawable.pycon_favorite_active);
            }


            titleText.setText(talk.getTitle());
            descText.setText(talk.getDescription());
            talkList.addView(talkView);
        }

        return v;

    }


}
