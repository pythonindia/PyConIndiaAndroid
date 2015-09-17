package com.pyconindia.pycon.models;

public class TextQuestion extends FeedbackQuestion {

    public TextQuestion() {

    }

    @Override
    public String getType() {
        return FeedbackQuestion.TYPE_TEXT;
    }

}