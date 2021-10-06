package com.example.careapp;

public class FuzzyLogic {
    private static float _age=0.0f,_recover=0.0f,_pedometer=0.0f,
            _sleep=0.0f,_calorie=0.0f,_heart_rate=0.0f,
            _blood_pressure=0.0f;
    private static float _reminder = 0;
    private static FisVar _f_age = new FisVar(new TriFunction(-5,20,45),
            new TriFunction(25,50,75),new TriFunction(55,80,100));
    private static FisVar _f_recover = new FisVar(new TriFunction(-5,0,5),
            new TriFunction(1,6,11),new TriFunction(7,12,19));
    private static FisVar _f_pedometer = new FisVar(new TriFunction(-8333,0,8333),
            new TriFunction(1667,10000,18333),new TriFunction(11667,20000,28333));
    private static FisVar _f_sleep = new FisVar(new TriFunction(-5,0,5),
            new TriFunction(1,6,11),new TriFunction(7,12,19));
    private static FisVar _f_calorie = new FisVar(new TriFunction(-666.7f,1000,2667),
            new TriFunction(1333,3000,4667),new TriFunction(3333,5000,6667));
    private static FisVar _f_heart_rate = new FisVar(new TriFunction(6.667f,40,73.33f),
            new TriFunction(46.67f,80,113.3f),new TriFunction(86.67f,120f,153.3f));
    private static FisVar _f_blood_pressure = new FisVar(new TriFunction(18.33f,60,101.7f),
            new TriFunction(68.33f,110,151.7f),new TriFunction(118.3f,160,201.7f));
    private static FisVar _f_reminder = new FisVar(new TriFunction(-41.67f,60,41.67f),
            new TriFunction(8.333f,50,91.67f),new TriFunction(58.33f,100,141.7f));

    public FuzzyLogic(float age,float recover,float pedometer,float sleep,
                      float calorie,float heart_rate,float blood_pressure){
        FuzzyLogic._age = age;
        FuzzyLogic._recover = recover;
        FuzzyLogic._pedometer = pedometer;
        FuzzyLogic._sleep = sleep;
        FuzzyLogic._calorie = calorie;
        FuzzyLogic._heart_rate = heart_rate;
        FuzzyLogic._blood_pressure = blood_pressure;
    }

    public static void Set(float age,float recover,float pedometer,float sleep,
                           float calorie,float heart_rate,float blood_pressure){
        FuzzyLogic._age = age;
        FuzzyLogic._recover = recover;
        FuzzyLogic._pedometer = pedometer;
        FuzzyLogic._sleep = sleep;
        FuzzyLogic._calorie = calorie;
        FuzzyLogic._heart_rate = heart_rate;
        FuzzyLogic._blood_pressure = blood_pressure;
    }

    public static void Rules(){
        float[] rule = new float[5];
        rule[0] = _f_reminder.GetMf1(_f_recover.GetMf3(_recover));
        rule[1] = _f_reminder.GetMf3(_f_sleep.GetMf1(_sleep));
        rule[2] = _f_reminder.GetMf3(_f_heart_rate.GetMf3(_heart_rate));
        rule[3] = _f_reminder.GetMf3(_f_calorie.GetMf3(_calorie));
        rule[4] = _f_reminder.GetMf3(Math.min(_f_pedometer.GetMf3(_pedometer), _f_recover.GetMf1(_recover)));
        _reminder = 0;
        for(int i = 0;i<5;++i)
            _reminder += rule[i];
        _reminder = _reminder/5;
    }

    //0-small  1-middle  2-large
    public static float GetReminder(){
        return _reminder;
    }
}
