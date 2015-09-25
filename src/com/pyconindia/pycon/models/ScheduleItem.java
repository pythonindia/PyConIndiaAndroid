package com.pyconindia.pycon.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

public class ScheduleItem implements Comparable<ScheduleItem> {

    private ArrayList<Talk> talkList;
    private String startTime;
    private String endTime;
    private String eventDate;

    public ScheduleItem(String eventDate, String startTime, String endTime, ArrayList<Talk> talkList) {
        this.startTime = startTime;
        this.setEndTime(endTime);
        this.setTalkList(talkList);
        this.setEventDate(eventDate);
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStarTime(String time) {
        this.startTime = time;
    }

    public ArrayList<Talk> getTalkList() {
        return talkList;
    }

    public void setTalkList(ArrayList<Talk> talkList) {
        this.talkList = talkList;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public int compareTo(ScheduleItem another) {

        SimpleDateFormat parser = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");

        try {
            Date st1 = parser.parse(this.getStartTime());
            st1 = displayFormat.parse(st1.getHours() + ":" + st1.getMinutes() + " "+this.getStartTime().substring(this.getStartTime().length()-2));
            Date st2 = parser.parse(another.getStartTime());
            st2 = displayFormat.parse(st2.getHours() + ":" + st2.getMinutes() + " "+another.getStartTime().substring(another.getStartTime().length()-2));
            if (st1.getTime() < st2.getTime()) {
                return -1;
            } else {
                return 1;
            }
        } catch (ParseException e) {
            Log.e("abhishek", "Parser exception in shcedule item");
            Log.e("abhishek", "error", e.getCause());
            return 1;
        }
    }


}
