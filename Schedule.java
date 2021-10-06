package com.example.careapp;

public class Schedule {
    private String _time;
    private String _title;
    public Schedule(String time, String title){
        this._time = time;
        this._title = title;
    }
    public String GetTime(){
        return this._time;
    }

    public String GetTitle()
    {
        return this._title;
    }
}
