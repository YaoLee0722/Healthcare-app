package com.example.careapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.careapp.ui.notifications.NotificationsFragment;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class NewNoticeActivity extends AppCompatActivity {

    private TextView _date;
    private EditText _location,_time,_description,_title;
    private ImageButton _btn_check,_btn_clear;
    private DatabaseHelper _db_helper;
    private SQLiteDatabase db;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_notice);
        _location=(EditText)findViewById(R.id.editText_location);
        _time=(EditText)findViewById(R.id.editText_time);
        _description=(EditText)findViewById(R.id.editText_description);
        _title=(EditText)findViewById(R.id.editText_title);
        _btn_check=(ImageButton)findViewById(R.id.imageButton_check_notice);
        _btn_check.setOnClickListener(new NewNoticeActivity.ButtonListener());
        _btn_clear=(ImageButton)findViewById(R.id.imageButton_clear_notice);
        _btn_clear.setOnClickListener(new NewNoticeActivity.ButtonListener());
        _date = (TextView) findViewById(R.id.textView_date);
        Intent intent = getIntent();
        date = intent.getStringExtra("year")+"/"+intent.getStringExtra("month")
                +"/"+intent.getStringExtra("day");
        _date.setText(date);
        _db_helper = new DatabaseHelper(this, "Schedule.db",null,1);
        db = _db_helper.getWritableDatabase();
    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageButton_check_notice:
                    ContentValues values = new ContentValues();
                    values.put("date",_date.getText().toString());
                    values.put("time",_time.getText().toString());
                    values.put("location",_location.getText().toString());
                    values.put("title",_title.getText().toString());
                    values.put("description",_description.getText().toString());
                    db.insert("Schedule",null,values);
                    Save(_date.getText().toString()+"\r\n"
                            +_time.getText().toString()+"\r\n"
                            +_location.getText().toString()+"\r\n"
                            +_title.getText().toString()+"\r\n"
                            +_description.getText().toString()+"\r\n");
                    NewNoticeActivity.this.finish();
                    break;
                case R.id.imageButton_clear_notice:
                    NewNoticeActivity.this.finish();
                    break;
            }
        }
    }

    public void Save(String input){
        FileOutputStream fout = null;
        BufferedWriter writer = null;
        try{
            fout = openFileOutput("schedule", Context.MODE_PRIVATE);
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
}