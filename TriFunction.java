package com.example.careapp;

public class TriFunction {
    private float _p1,_p2,_p3;

    public TriFunction(float p1,float p2,float p3){
        this._p1 = p1;
        this._p2 = p2;
        this._p3 = p3;
    }

    public float GetOutput(final float input){
        if(input<_p1||input>_p3)
            return 0;
        else if(input<_p2){
            return (_p2-_p1)*(input-_p1);
        }
        else
            return (_p3-input)/(_p3-_p2);
    }
}
