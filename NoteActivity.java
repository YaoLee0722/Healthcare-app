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

public class NoteActivity extends AppCompatActivity {

    private EditText _note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        _note = (EditText) findViewById(R.id.editText_note);
        Load();
    }

    public void Save(String input){
        FileOutputStream fout = null;
        BufferedWriter writer = null;
        try{
            fout = openFileOutput("note", Context.MODE_PRIVATE);
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
            fin = openFileInput("note");
            reader = new BufferedReader(new InputStreamReader(fin));
            String line = "";
            //while((line = reader.readLine())!=null)
            //content.append(line);
            if((line = reader.readLine())!=null)
                _note.setText(line);
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
        String note = _note.getText().toString();
        Save(note+"\r\n");
    }
}