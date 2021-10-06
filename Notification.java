package com.example.careapp;

public class Notification {
    private String _message;
    private int _icon_id;
    public Notification(String msg, int id){
        this._message = msg;
        this._icon_id = id;
    }
    public String GetMsg(){
        return this._message;
    }

    public int GetId()
    {
        return this._icon_id;
    }
}
