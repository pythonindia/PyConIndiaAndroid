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
    private String speakerInfo;
    private String speakerLinks;
    private String author;
    private String section;
    private String contentUrls;
    private String prerequisites;
    private int targetAudience;
    private String startTime;
    private String endTime;

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
        setAuthor("");
        setSection("");
        setContentUrls("");
        setPrerequisites("");
        setTargetAudience(-1);
        setStartTime("");
        setEndTime("");
        setSpeakerInfo("");
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

    public String getSpeakerInfo() {
        return speakerInfo;
    }

    public void setSpeakerInfo(String speakerInfo) {
        this.speakerInfo = speakerInfo;
    }

    public String getSpeakerLinks() {
        return speakerLinks;
    }

    public void setSpeakerLinks(String speakerLinks) {
        this.speakerLinks = speakerLinks;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getContentUrls() {
        return contentUrls;
    }

    public void setContentUrls(String contentUrls) {
        this.contentUrls = contentUrls;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public int getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(int targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
