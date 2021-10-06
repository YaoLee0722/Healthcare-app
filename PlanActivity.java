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

public class PlanActivity extends AppCompatActivity {

    private EditText _sleep,_step,_diet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        _sleep = (EditText) findViewById(R.id.editText_sleep_plan);
        _step = (EditText) findViewById(R.id.editText_step_plan);
        _diet = (EditText) findViewById(R.id.editText_diet_plan);
        Load();
    }

    public void Save(String input){
        FileOutputStream fout = null;
        BufferedWriter writer = null;
        try{
            fout = openFileOutput("plan", Context.MODE_PRIVATE);
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
        //StringBuilder content = new StringBuilder();
        try{
            fin = openFileInput("plan");
            reader = new BufferedReader(new InputStreamReader(fin));
            String line = "";
            //while((line = reader.readLine())!=null)
            //content.append(line);
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
        String step = _step.getText().toString();
        String sleep = _sleep.getText().toString();
        String diet = _diet.getText().toString();
        Save(step+"\r\n"+sleep+"\r\n"+diet+"\r\n");
    }
}