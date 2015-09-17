package com.pyconindia.pycon.models;

import java.util.ArrayList;

public class ChoiceQuestion extends FeedbackQuestion {

    private ArrayList<FeedbackChoice> choices;
    public ChoiceQuestion() {
        setChoices(new ArrayList<FeedbackChoice>());
    }

    @Override
    public String getType() {
        return FeedbackQuestion.TYPE_CHOICE;
    }


    public ArrayList<FeedbackChoice> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<FeedbackChoice> choices) {
        this.choices = choices;
    }
}
