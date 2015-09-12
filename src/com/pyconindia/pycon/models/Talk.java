package com.pyconindia.pycon.models;

public class Talk {

    private String title;
    private String description;
    private int audiNo;
    private boolean liked;
    private boolean feedbackGiven;

    public Talk(String title, String description, int audiNo, boolean liked, boolean feedbackGiven) {
        this.title = title;
        this.description = description;
        this.audiNo = audiNo;
        this.liked = liked;
        this.feedbackGiven = feedbackGiven;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAudiNo() {
        return audiNo;
    }

    public void setAudiNo(int audiNo) {
        this.audiNo = audiNo;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isFeedbackGiven() {
        return feedbackGiven;
    }

    public void setFeedbackGiven(boolean feedbackGiven) {
        this.feedbackGiven = feedbackGiven;
    }
}
