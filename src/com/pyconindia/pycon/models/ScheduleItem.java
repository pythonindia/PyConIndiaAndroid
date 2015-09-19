package com.pyconindia.pycon.models;

import java.util.ArrayList;

public class ScheduleItem {

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


}
