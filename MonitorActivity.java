package com.example.careapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MonitorActivity extends AppCompatActivity {

    private EditText _sleep,_diet,_temp,_diastolic,_systolic,_heart_rate,_step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        _sleep = (EditText) findViewById(R.id.editText_sleep);
        _diet = (EditText) findViewById(R.id.editText_diet);
        _temp = (EditText) findViewById(R.id.editText_temp);
        _diastolic = (EditText) findViewById(R.id.editText_diastolic);
        _systolic = (EditText) findViewById(R.id.editText_systolic);
        _heart_rate = (EditText) findViewById(R.id.editText_heart_rate);
        _step = (EditText) findViewById(R.id.editText_step);
        Load();
    }

    public void Save(String input){
        FileOutputStream fout = null;
        BufferedWriter writer = null;
        try{
            fout = openFileOutput("monitor", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fout));
            writer.write(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(writer!=null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void Load(){
        FileInputStream fin = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            fin = openFileInput("monitor");
            reader = new BufferedReader(new InputStreamReader(fin));
            String line = "";
            //while((line = reader.readLine())!=null)
            //content.append(line);
            if((line = reader.readLine())!=null)
                _heart_rate.setText(line);
            if((line = reader.readLine())!=null)
                _diastolic.setText(line);
            if((line = reader.readLine())!=null)
                _systolic.setText(line);
            if((line = reader.readLine())!=null)
                _temp.setText(line);
            if((line = reader.readLine())!=null)
                _step.setText(line);
            if((line = reader.readLine())!=null)
                _sleep.setText(line);
            if((line = reader.readLine())!=null)
                _diet.setText(line);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //return content.toString();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        /**/
        String sleep = _sleep.getText().toString();
        String diet = _diet.getText().toString();
        String temp = _temp.getText().toString();
        String diastolic = _diastolic.getText().toString();
        String systolic = _systolic.getText().toString();
        String heart_rate = _heart_rate.getText().toString();
        String step = _step.getText().toString();
        Save(heart_rate+"\r\n"+diastolic+"\r\n"+systolic+"\r\n"+temp+"\r\n"+step+"\r\n"+sleep+"\r\n"+diet+"\r\n");
    }
}