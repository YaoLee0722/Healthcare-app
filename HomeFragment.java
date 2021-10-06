package com.example.careapp.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careapp.AdviceActivity;
import com.example.careapp.ArchivesActivity;
import com.example.careapp.DialogActivity;
import com.example.careapp.MonitorActivity;
import com.example.careapp.Notification;
import com.example.careapp.NotificationAdapter;
import com.example.careapp.R;
import com.example.careapp.databinding.FragmentHomeBinding;
import com.example.careapp.databinding.FragmentHomeBinding;
import com.example.careapp.ui.dashboard.DashboardFragment;

import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private List<Notification> _notification_list = new ArrayList<>();
    private Button _btn_login;
    private ImageButton _img_btn_call;
    private String _aid_call;
    private Float _sleep,_diet,_temp,_diastolic,_systolic,_heart_rate,_step;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        _btn_login = (Button) root.findViewById(R.id.button_login);
        _btn_login.setOnClickListener(new HomeFragment.ButtonListener());
        _img_btn_call = (ImageButton)root.findViewById(R.id.imageButton_aid_call);
        _img_btn_call.setOnClickListener(new HomeFragment.ButtonListener());
        SetNotice();
        RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.recycler_home);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        NotificationAdapter adapter = new NotificationAdapter(_notification_list);
        recyclerView.setAdapter(adapter);
        String user = LoadUser();
        SetUser(user);
        return root;
    }

    private void SetNotice(){
        _notification_list.clear();
        LoadMonitor();
        /*
        Notification review = new Notification("5 days to go before the reexamination",
                R.drawable.ic_baseline_priority_high_24);
        _notification_list.add(review);*/
        int notice_cnt = 0;
        if(_temp.floatValue()>37.2){
            Notification temp = new Notification("High body temperature", R.drawable.ic_baseline_add_alert_24);
            _notification_list.add(temp);
            notice_cnt++;
        }
        if(_sleep.intValue()<7){
            Notification sleep = new Notification("Lack of sleep", R.drawable.ic_baseline_priority_high_24);
            _notification_list.add(sleep);
            notice_cnt++;
        }
        if(_diet.intValue()>2300){
            Notification diet = new Notification("Too many calories", R.drawable.ic_baseline_priority_high_24);
            _notification_list.add(diet);
            notice_cnt++;
        }
        else if(_diet.intValue()<1800){
            Notification diet = new Notification("Lack of calories", R.drawable.ic_baseline_priority_high_24);
            _notification_list.add(diet);
            notice_cnt++;
        }
        if((_diastolic.intValue()<70)||(_diastolic.intValue()>140)||(_systolic.intValue()<70)||(_systolic.intValue()>140)){
            Notification pressure = new Notification("Abnormal blood pressure", R.drawable.ic_baseline_add_alert_24);
            _notification_list.add(pressure);
            notice_cnt++;
        }

        if(_heart_rate.intValue()<60){
            Notification heart_rate = new Notification("The heart rate is too slow", R.drawable.ic_baseline_add_alert_24);
            _notification_list.add(heart_rate);
            notice_cnt++;
        }
        else if(_heart_rate.intValue()>160){
            Notification heart_rate = new Notification("The heart rate is too fast", R.drawable.ic_baseline_add_alert_24);
            _notification_list.add(heart_rate);
            notice_cnt++;
        }

        if(_step.intValue()<6000){
            Notification heart_rate = new Notification("Lack of exercise", R.drawable.ic_baseline_priority_high_24);
            _notification_list.add(heart_rate);
            notice_cnt++;
        }
        else if(_heart_rate.intValue()>30000){
            Notification heart_rate = new Notification("Need to rest.", R.drawable.ic_baseline_priority_high_24);
            _notification_list.add(heart_rate);
            notice_cnt++;
        }
        if(notice_cnt==0){
            Notification good = new Notification("Good habits!", R.drawable.ic_baseline_stars_24);
            _notification_list.add(good);
        }
    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_login:
                    Intent intent_archives= new Intent(getActivity(), ArchivesActivity.class);
                    startActivity(intent_archives);
                    break;
                case R.id.imageButton_aid_call:
                    Intent intent_call= new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+_aid_call));
                    intent_call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent_call);
                    break;
            }
        }
    }

    public void SetUser(String user){
        _btn_login.setText(user);
    }

    public String LoadUser(){
        FileInputStream fin = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            fin = getActivity().openFileInput("archives");
            reader = new BufferedReader(new InputStreamReader(fin));
            String line = reader.readLine();
            if(line!=null)
                content.append("Hello, ");
            else
                content.append("register/login");
            content.append(line);
            for(int i = 0;i<4;++i)
                line = reader.readLine();
            if(line!=null)
                _aid_call = line;
            else
                _aid_call = "911";
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
            else{
                content.append("register/login");
                _aid_call = "911";
            }
        }
        return content.toString();
    }

    public void LoadMonitor(){
        FileInputStream fin = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            fin = getActivity().openFileInput("monitor");
            reader = new BufferedReader(new InputStreamReader(fin));
            String line = "";
            //while((line = reader.readLine())!=null)
            //content.append(line);
            if((line = reader.readLine())!=null)
                _heart_rate = Float.valueOf(line);
            if((line = reader.readLine())!=null)
                _diastolic = Float.valueOf(line);
            if((line = reader.readLine())!=null)
                _systolic = Float.valueOf(line);
            if((line = reader.readLine())!=null)
                _temp = Float.valueOf(line);
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
        //return content.toString();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}