package com.pyconindia.pycon.models;

public class TextQuestion extends FeedbackQuestion {

    private int id;

    public TextQuestion() {

    }

    @Override
    public String getType() {
        return FeedbackQuestion.TYPE_TEXT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}