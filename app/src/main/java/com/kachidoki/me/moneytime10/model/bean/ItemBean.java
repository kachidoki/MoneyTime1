package com.kachidoki.me.moneytime10.model.bean;

/**
 * Created by Frank on 15/8/17.
 */
public class ItemBean {
    private String describe;
    private int year;
    private int month;
    private int day;
    private float startTime;
    private float endTime;
    private int weekDay;
    private String color;
    private int weekOfYear;


    public ItemBean(int year,int month,int day,int weekDay,int weekOfYear,float startTime,float endTime,String describe,String color){
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekDay = weekDay;
        this.describe = describe;
        this.year = year;
        this.month = month;
        this.day = day;
        this.color = color;
        this.weekOfYear = weekOfYear;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }
}
