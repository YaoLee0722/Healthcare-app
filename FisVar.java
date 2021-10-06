package com.example.careapp;

public class FisVar {
    private TriFunction _mf1,_mf2,_mf3;
    public FisVar(TriFunction mf1,TriFunction mf2,TriFunction mf3){
        _mf1=mf1;
        _mf2=mf2;
        _mf3=mf3;
    }

    public float GetMf1(float input){
        return _mf1.GetOutput(input);
    }

    public float GetMf2(float input){
        return _mf2.GetOutput(input);
    }

    public float GetMf3(float input){
        return _mf3.GetOutput(input);
    }
}
