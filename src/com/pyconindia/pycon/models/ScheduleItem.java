package com.pyconindia.pycon.models;

import java.util.ArrayList;

public class ScheduleItem {

    private ArrayList<Talk> talkList;
    private String startTime;
    private String endTime;

    public ScheduleItem(String startTime, String endTime, ArrayList<Talk> talkList) {
        this.startTime = startTime;
        this.setEndTime(endTime);
        this.setTalkList(talkList);
    }

    public String getTime() {
        return startTime;
    }

    public void setTime(String time) {
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
}
