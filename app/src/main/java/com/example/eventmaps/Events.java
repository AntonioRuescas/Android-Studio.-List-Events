package com.example.eventmaps;

public class Events {

    private String event;
    private String site;
    private String date;
    private String time;
    private boolean check;


    public Events(String event, String site, String date, String time) {
        this.event = event;
        this.site = site;
        this.date = date;
        this.time = time;
        this.check = false;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setHour(String hour) {
        this.time = time;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
