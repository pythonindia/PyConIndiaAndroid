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

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.pyconindia.pycon.adapters.ScheduleListAdapter;
import com.pyconindia.pycon.http.Api;
import com.pyconindia.pycon.http.Api.UrlType;
import com.pyconindia.pycon.models.ScheduleItem;
import com.pyconindia.pycon.models.Talk;
import com.pyconindia.pycon.storage.ApplicationData;
import com.pyconindia.pycon.view.SlidingTabLayout;

public class ScheduleActivity extends BaseActivity {

    private ApplicationData data;
    private Api api;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private ArrayList<ScheduleItem> scheduleList;
    private ProgressDialog progress;

    public static final String[] DAY = {
        "2015-10-02", "2015-10-03", "2015-10-04"
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(this.getString(R.string.app_name));
        getSupportActionBar().setIcon(R.drawable.footerlogo);
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage(this.getText(R.string.schedule_refresh_loading_text));
	}

	private void initComponents() {
	    mViewPager = (ViewPager) findViewById(R.id.pager);
        data = new ApplicationData(ScheduleActivity.this);
        api = new Api(this, this);
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
        scheduleList = getScheduleList(0);
        listview1.setAdapter(new ScheduleListAdapter<ScheduleItem>(ScheduleActivity.this, R.layout.pager_item, scheduleList));
        scheduleList = getScheduleList(1);
        listview2.setAdapter(new ScheduleListAdapter<ScheduleItem>(ScheduleActivity.this, R.layout.pager_item, scheduleList));
        scheduleList = getScheduleList(2);
        listview3.setAdapter(new ScheduleListAdapter<ScheduleItem>(ScheduleActivity.this, R.layout.pager_item, scheduleList));
        int pageNo = ((BaseApplication) this.getApplication()).getPageNumber();
        mViewPager.setCurrentItem(pageNo, true);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		progress.dismiss();
		((BaseApplication) this.getApplication()).setPageNumber(mViewPager.getCurrentItem());
	}

	@Override
	protected void onResume() {
		super.onResume();
		initComponents();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

	public boolean onOptionsItemSelected(MenuItem item) {
	   if(!super.onOptionsItemSelected(item)) {
           switch (item.getItemId()) {

           case R.id.refresh:
               progress.show();
               api.getSchedulesList();
               break;
               default:
                   break;
           }
	   }
       return true;
	}

	@Override
    public void onSuccess(int statusCode, JSONObject response, UrlType urlType) {
        if(urlType == UrlType.SCHEDULLES_LIST) {
            progress.dismiss();
            data.setSchedulesList(response);
            initComponents();
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(int statusCode, Throwable e, UrlType urlType,
            JSONObject response) {
        progress.dismiss();
        if(urlType == UrlType.SCHEDULLES_LIST) {
            JSONObject scheduleList = data.getScheduleList();
            if(scheduleList != null) {
                Toast.makeText(this, "No network showing old data", Toast.LENGTH_SHORT).show();
                initComponents();
            } else {
                Toast.makeText(this, "Exiting: No network or old data found", Toast.LENGTH_SHORT).show(); //WTF?? Deleted old data
                finish(); // Die!!!
            }
        }
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
                String startTime = "", endTime = "", name, description, type, eventDate = "";
                int roomId, talkId;
                boolean like, feedback;
                ArrayList<Talk> talkList = new ArrayList<Talk>();
                for(int i = 0; i < scheduleListArr.length(); i++) {

                    JSONObject talkObj = scheduleListArr.getJSONObject(i);
                    JSONObject sessionObj = talkObj.getJSONObject("session");
                    name = talkObj.getString("name");
                    startTime = talkObj.getString("start_time");
                    endTime = talkObj.getString("end_time");
                    eventDate = talkObj.getString("event_date");
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                    Date date =  (Date) formatter.parse(startTime);
                    startTime = new SimpleDateFormat("hh:mm a").format(date);

                    formatter = new SimpleDateFormat("HH:mm:ss");
                    date =  (Date) formatter.parse(endTime);
                    endTime = new SimpleDateFormat("hh:mm a").format(date);

                    talkId = talkObj.getInt("id");
                    like = likes.has(""+talkId) ? likes.getBoolean(""+talkId) : false;
                    feedback = feedbackObj.has(""+talkId) ? feedbackObj.getBoolean(""+talkId) : false;
                    description = sessionObj.getString("description");
                    roomId = talkObj.getInt("room_id");
                    type = talkObj.getString("type");
                    Talk talk = new Talk(talkId, name.toUpperCase(), description, type, eventDate, roomId, like, feedback);

                    if(sessionObj.has("content_urls")) {
                        talk.setContentUrls(sessionObj.getString("content_urls"));
                    }
                    if(sessionObj.has("speaker_info")) {
                        talk.setSpeakerInfo(sessionObj.getString("speaker_info"));
                    }
                    if(sessionObj.has("target_audience")) {
                        talk.setTargetAudience(sessionObj.getInt("target_audience"));
                    }
                    if(sessionObj.has("prerequisites")) {
                        talk.setPrerequisites(sessionObj.getString("prerequisites"));
                    }
                    if(sessionObj.has("section")) {
                        talk.setSection(sessionObj.getString("section"));
                    }
                    if(sessionObj.has("author")) {
                        talk.setAuthor(sessionObj.getString("author"));
                    }
                    if(sessionObj.has("section")) {
                        talk.setSection(sessionObj.getString("section"));
                    }
                    if(talkObj.has("start_time")) {
                        talk.setStartTime(startTime);
                    }
                    if(talkObj.has("end_time")) {
                        talk.setEndTime(endTime);
                    }
                    talkList.add(talk);
                }
                Collections.sort(talkList);
                ScheduleItem item = new ScheduleItem(eventDate, startTime, endTime, talkList);
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

