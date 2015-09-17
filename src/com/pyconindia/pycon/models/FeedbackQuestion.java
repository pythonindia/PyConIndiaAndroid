package com.pyconindia.pycon.models;

public abstract class FeedbackQuestion {

    private boolean required;
    private String title;
    public static final String TYPE_CHOICE = "choice";
    public static final String TYPE_TEXT = "text";
    public static final String REQUIRED = "is_required";
    public static final String TITLE = "title";
    public static final String ID = "id";
    public static final String CHOICES = "allowed_choices";


    public FeedbackQuestion() {

    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public abstract String getType();

}
