package com.project.daylight;

import java.util.ArrayList;

public class UserInfo {
    public int UID;
    public int birthday;
    public String name;
    public String sex;
    public int notificationSet;
    public int theme;
    public ArrayList<DateSet> dateSets;

    private UserInfo(){}

    private static class UserInfoHolder{
        public static final UserInfo instance = new UserInfo();
    }
    public static UserInfo getUser(){
        return UserInfoHolder.instance;
    }
}