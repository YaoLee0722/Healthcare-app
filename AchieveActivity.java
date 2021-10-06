package com.example.careapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AchieveActivity extends AppCompatActivity {

    private Float _sleep,_step,_diet;
    private Float _sleep_plan,_step_plan,_diet_plan;
    private List<Notification> _achievement_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);
        SetAchieve();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_achieve);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        AchieveAdapter adapter = new AchieveAdapter(_achievement_list);
        recyclerView.setAdapter(adapter);
    }

    public void Load(){
        FileInputStream fin = null;
        BufferedReader reader = null;
        try{
            fin = openFileInput("plan");
            reader = new BufferedReader(new InputStreamReader(fin));
            String line = "";
            if((line = reader.readLine())!=null)
                _step_plan = Float.valueOf(line);
            if((line = reader.readLine())!=null)
                _sleep_plan = Float.valueOf(line);
            if((line = reader.readLine())!=null)
                _diet_plan = Float.valueOf(line);
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

        try{
            fin = openFileInput("monitor");
            reader = new BufferedReader(new InputStreamReader(fin));
            String line = "";
            for(int i = 0;i<4;++i)
                line = reader.readLine();
            if((line = reader.readLine())!=null)
                _step = Float.valueOf(line);
            if((line = reader.readLine())!=null)
                _sleep = Float.valueOf(line);
            if((line = reader.readLine())!=null)
                _diet = Float.valueOf(line);
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
    }

    private void SetAchieve(){
        _achievement_list.clear();
        Load();
        int achievement_cnt = 0;
        if(_sleep.floatValue()>_sleep_plan.floatValue()){
            Notification sleep = new Notification("Sleep Achievement!", R.mipmap.ic_stars);
            _achievement_list.add(sleep);
            achievement_cnt++;
        }
        if(_step.floatValue()>_step_plan.floatValue()){
            Notification step = new Notification("Step Achievement!", R.mipmap.ic_stars);
            _achievement_list.add(step);
            achievement_cnt++;
        }
        if(_diet.floatValue()<_diet_plan.floatValue()){
            Notification diet = new Notification("Diet Achievement!", R.mipmap.ic_stars);
            _achievement_list.add(diet);
            achievement_cnt++;
        }
        if(achievement_cnt==0){
            Notification good = new Notification("No Achievement!", R.mipmap.ic_clear);
            _achievement_list.add(good);
        }
    }
}