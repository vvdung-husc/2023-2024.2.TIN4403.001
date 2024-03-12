package com.example.myapplication.Object;

public class Button {
    private int ResourseId;
    private String Title;

    public Button(int resourseId, String title) {
        ResourseId = resourseId;
        Title = title;
    }

    public int getResourseId() {
        return ResourseId;
    }

    public void setResourseId(int resourseId) {
        ResourseId = resourseId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
