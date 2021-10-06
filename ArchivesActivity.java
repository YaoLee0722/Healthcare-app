package com.example.careapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ArchivesActivity extends AppCompatActivity {
    private EditText _name;
    private EditText _info;
    private EditText _allergens;
    private EditText _contact;
    private EditText _age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archives);
        _name = (EditText) findViewById(R.id.editText_name);
        _info = (EditText) findViewById(R.id.editText_info);
        _allergens = (EditText) findViewById(R.id.editText_allergens);
        _contact = (EditText) findViewById(R.id.editText_contact);
        _age = (EditText) findViewById(R.id.editText_age);
        load();
        //_info.setText(line);
    }

    public void Save(String input){
        FileOutputStream fout = null;
        BufferedWriter writer = null;
        try{
            fout = openFileOutput("archives", Context.MODE_PRIVATE);
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

    public void load(){
        FileInputStream fin = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            fin = openFileInput("archives");
            reader = new BufferedReader(new InputStreamReader(fin));
            String line = "";
            //while((line = reader.readLine())!=null)
                //content.append(line);
            if((line = reader.readLine())!=null)
                _name.setText(line);
            if((line = reader.readLine())!=null)
                _age.setText(line);
            if((line = reader.readLine())!=null)
                _info.setText(line);
            if((line = reader.readLine())!=null)
                _allergens.setText(line);
            if((line = reader.readLine())!=null)
                _contact.setText(line);
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
        String name = _name.getText().toString();
        String info = _info.getText().toString();
        String allergens = _allergens.getText().toString();
        String contact = _contact.getText().toString();
        String age = _age.getText().toString();
        Save(name+"\r\n"+age+"\r\n"+info+"\r\n"+allergens+"\r\n"+contact+"\r\n");
    }
}