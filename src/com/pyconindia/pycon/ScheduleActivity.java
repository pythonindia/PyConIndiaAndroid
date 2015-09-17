package com.pyconindia.pycon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pyconindia.pycon.adapters.ScheduleListAdapter;
import com.pyconindia.pycon.models.ScheduleItem;
import com.pyconindia.pycon.models.Talk;
import com.pyconindia.pycon.storage.ApplicationData;
import com.pyconindia.pycon.view.SlidingTabLayout;
import com.pythonindia.pycon.R;

public class ScheduleActivity extends BaseActivity {

    private ApplicationData data;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private ListView scheduleListView;
    private ArrayList<ScheduleItem> scheduleList;
    private ScheduleListAdapter<ScheduleItem> adapter;

    public static final String[] DAY = {
        "2015-10-01", "2015-10-02", "2015-10-03"
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PyCon");
        getSupportActionBar().setIcon(R.drawable.footerlogo);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        data = new ApplicationData(ScheduleActivity.this);

        scheduleList = new ArrayList<ScheduleItem>();

        ArrayList<ListView> lists = new ArrayList<ListView>();
        ListView listview1 = new ListView(this);
        ListView listview2 = new ListView(this);
        ListView listview3 = new ListView(this);
        lists.add(listview1);
        lists.add(listview2);
        lists.add(listview3);

        mViewPager.setAdapter(new SamplePagerAdapter(lists));
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(android.R.color.white);
            }
        });
        scheduleList = getScheduleList(1);
        listview1.setAdapter(new ScheduleListAdapter<ScheduleItem>(ScheduleActivity.this, R.layout.pager_item, scheduleList));
        scheduleList = getScheduleList(0);
        listview2.setAdapter(new ScheduleListAdapter<ScheduleItem>(ScheduleActivity.this, R.layout.pager_item, scheduleList));
        scheduleList = getScheduleList(2);
        listview3.setAdapter(new ScheduleListAdapter<ScheduleItem>(ScheduleActivity.this, R.layout.pager_item, scheduleList));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public ArrayList<ScheduleItem> getScheduleList(int dayNo) {
        JSONObject scObject = data.getScheduleList();
        ArrayList<ScheduleItem> scheduleList = new ArrayList<ScheduleItem>();
        JSONObject likes = data.getScheduleLikes();
        JSONObject feedbackObj = data.getScheduleFeedback();

        try {
            JSONObject dayObject = scObject.has(DAY[dayNo]) ? scObject.getJSONObject(DAY[dayNo]) : new JSONObject();

            Iterator<String> iter2 = dayObject.keys();
            while (iter2.hasNext()) {
                String time = iter2.next();
                JSONArray scheduleListArr = dayObject.getJSONArray(time);
                String startTime = "", endTime = "", name, description;
                int roomId, sessionId;
                boolean like, feedback;
                ArrayList<Talk> talkList = new ArrayList<Talk>();
                for(int i = 0; i < scheduleListArr.length(); i++) {

                    JSONObject talkObj = scheduleListArr.getJSONObject(i);
                    name = talkObj.getString("name");
                    startTime = talkObj.getString("start_time");
                    endTime = talkObj.getString("end_time");

                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                    Date date =  (Date) formatter.parse(startTime);
                    startTime = new SimpleDateFormat("hh:mm a").format(date);

                    formatter = new SimpleDateFormat("HH:mm:ss");
                    date =  (Date) formatter.parse(endTime);
                    endTime = new SimpleDateFormat("hh:mm a").format(date);

                    sessionId = talkObj.getJSONObject("session").getInt("id");
                    like = likes.has(""+sessionId) ? likes.getBoolean(""+sessionId) : false;
                    feedback = feedbackObj.has(""+sessionId) ? feedbackObj.getBoolean(""+sessionId) : false;
                    description = talkObj.getJSONObject("session").getString("description");
                    roomId = talkObj.getInt("room_id");
                    Talk talk = new Talk(sessionId, name.toUpperCase(), description, roomId, like, feedback);
                    talkList.add(talk);
                }
                Collections.sort(talkList);
                ScheduleItem item = new ScheduleItem(startTime, endTime, talkList);
                scheduleList.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return scheduleList;
    }

	class SamplePagerAdapter extends PagerAdapter {

	    ArrayList<ListView> pages;

	    public SamplePagerAdapter(ArrayList<ListView> pages) {
	        super();
	        this.pages = pages;
        }


        @Override
        public int getCount() {
            return 3;
        }



        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "DAY " + (position + 1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ListView page = pages.get(position);
            container.addView(page);
            return page;
        }



        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
}

