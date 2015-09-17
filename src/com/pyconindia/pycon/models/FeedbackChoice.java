package com.pyconindia.pycon.models;

public class FeedbackChoice {

    private int id;
    private int value;
    private String title;
    public static final String TITLE = "title";
    public static final String ID = "id";
    public static final String VALUE = "value";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
