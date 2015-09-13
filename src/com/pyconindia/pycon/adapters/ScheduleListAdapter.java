package com.pyconindia.pycon.adapters;

import java.util.ArrayList;

import com.pyconindia.pycon.models.ScheduleItem;
import com.pyconindia.pycon.models.Talk;
import com.pyconindia.pycon.storage.ApplicationData;
import com.pythonindia.pycon.R;

import android.content.Context;
import android.graphics.Typeface;
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
    private Typeface typeFace;
    private ApplicationData data;
    public ScheduleListAdapter(Context context, int resource, ArrayList<ScheduleItem> items) {
        super(context, resource, items);
        this.items = items;
        typeFace = Typeface.createFromAsset(context.getAssets(),"Helvetica.ttf");
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
        timeView.setTypeface(typeFace, Typeface.BOLD);
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
            TextView titleText = (TextView) talkView.findViewById(R.id.title);
            titleText.setTypeface(typeFace, Typeface.BOLD);
            TextView descText = (TextView) talkView.findViewById(R.id.desc);
            descText.setTypeface(typeFace);
            ImageView feedbackImage = (ImageView) talkView.findViewById(R.id.feedback);
            final ImageView likedImage = (ImageView) talkView.findViewById(R.id.like);
            ImageView audiImg = (ImageView) talkView.findViewById(R.id.audiImg);

            if(talk.getAudiNo() == 1) {
                audiImg.setBackgroundResource(R.drawable.pycon_audi_1);
            } else if(talk.getAudiNo() == 2) {
                audiImg.setBackgroundResource(R.drawable.pycon_audi_2);
            } else {
                audiImg.setBackgroundResource(R.drawable.pycon_audi_3);
            }

            if(talk.isFeedbackGiven()) {
                feedbackImage.setImageResource(R.drawable.pycon_feedback_active);
            }

            if(talk.isLiked()) {
                likedImage.setImageResource(R.drawable.pycon_favorite_active);
            }

            likedImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    talk.setLiked(!talk.isLiked());
                    data.setTalkLike(""+talk.getSessionId(), talk.isLiked());

                    if(talk.isLiked()) {
                        likedImage.setImageResource(R.drawable.pycon_favorite_active);
                    } else {
                        likedImage.setImageResource(R.drawable.pycon_favorite);
                    }
                }
            });

            titleText.setText(talk.getTitle());
            descText.setText(talk.getDescription());
            talkList.addView(talkView);
        }

        return v;

    }


}
