package com.pyconindia.pycon.view;

import android.content.Context;
import android.widget.RadioButton;

public class PRadioButton extends RadioButton {

    private int value;
    private int id;
    private int questionId;

    public PRadioButton(Context context, int id, int value, int questionId) {
        super(context);
        this.value = value;
        this.id = id;
        this.questionId = questionId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
