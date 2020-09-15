package com.project.daylight;

public class DateSet {
    String title;
    int targetDate;
    String cartegory;
    String gift;
    String place;
    int alarm;
    String memo;
    boolean sign = false;   //중요한거

    public DateSet(String title, int targetDate, String cartegory, String gift, String place, int alarm, String memo, boolean sign){
        this.title = title;
        this.targetDate = targetDate;
        this.cartegory = cartegory;
        this.gift = gift;
        this.place = place;
        this.alarm = alarm;
        this.memo = memo;
        this.sign = sign;
    }
}
