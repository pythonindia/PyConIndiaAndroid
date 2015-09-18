package com.pyconindia.pycon.models;

public class Talk implements Comparable<Talk> {

    private String title;
    private String description;
    private int audiNo;
    private boolean liked;
    private String type;
    private boolean feedbackGiven;
    private int id;
    private String markdown;

    public Talk(int id, String title, String description, String type, int audiNo, boolean liked, boolean feedbackGiven) {
        this.id = id;
        this.title = title;
        this.markdown = description;
        description = description.replaceAll("\r\n\r\n", " ");
        description = description.replaceAll("#", "");
        description = description.replace("*", " ");
        this.description = description;
        this.audiNo = audiNo;
        this.liked = liked;
        this.feedbackGiven = feedbackGiven;
        this.type = type;
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

    @Override
    public int compareTo(Talk another) {
        return this.audiNo - another.audiNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
