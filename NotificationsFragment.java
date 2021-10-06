package com.example.careapp.ui.notifications;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
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
import com.example.careapp.DatabaseHelper;
import com.example.careapp.DialogActivity;
import com.example.careapp.MonitorActivity;
import com.example.careapp.NewNoticeActivity;
import com.example.careapp.Notification;
import com.example.careapp.NotificationAdapter;
import com.example.careapp.R;
import com.example.careapp.Schedule;
import com.example.careapp.ScheduleAdapter;
import com.example.careapp.databinding.FragmentNotificationsBinding;
import com.example.careapp.databinding.FragmentNotificationsBinding;
import com.example.careapp.ui.dashboard.DashboardFragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private Button _btn_add_notice,_btn_delete_notice;
    private CalendarView calendarview;
    private int _year, _month, _dayOfMonth;
    private List<Schedule> _schedule_list = new ArrayList<>();
    private DatabaseHelper _db_helper;
    private SQLiteDatabase db;
    private String date,_title,_time;
    private int sch_cnt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sch_cnt = 0;
        _btn_add_notice=(Button)root.findViewById(R.id.button_add_notice);
        _btn_add_notice.setOnClickListener(new NotificationsFragment.ButtonListener());
        _btn_delete_notice=(Button)root.findViewById(R.id.button_delete_notice);
        _btn_delete_notice.setOnClickListener(new NotificationsFragment.ButtonListener());
        calendarview = (CalendarView) root.findViewById(R.id.calendarView);
        Calendar selected = Calendar.getInstance();
        selected.setTimeInMillis(calendarview.getDate());
        _year = selected.get(Calendar.YEAR);
        _month = selected.get(Calendar.MONTH);
        _dayOfMonth = selected.get(Calendar.DAY_OF_MONTH);
        date = _year+"/"+_month+"/"+_dayOfMonth;
        _db_helper = new DatabaseHelper(root.getContext(), "Schedule.db",null,1);
        db = _db_helper.getWritableDatabase();
        Cursor cursor = db.query("Schedule",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String value_date = cursor.getString(cursor.getColumnIndex("date"));
                if(date==value_date){
                    _title = cursor.getString(cursor.getColumnIndex("title"));
                    _time = cursor.getString(cursor.getColumnIndex("time"));
                    sch_cnt = 1;
                    break;
                }
            }while(cursor.moveToNext());
        }
        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                _year = year;
                _month = month;
                _dayOfMonth = dayOfMonth;
                date = _year+"/"+_month+"/"+_dayOfMonth;
                Cursor cursor = db.query("Schedule",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String value_date = cursor.getString(cursor.getColumnIndex("date"));
                        if(date==value_date){
                            _title = cursor.getString(cursor.getColumnIndex("title"));
                            _time = cursor.getString(cursor.getColumnIndex("time"));
                            sch_cnt = 1;
                            break;
                        }
                    }while(cursor.moveToNext());
                }
            }
        });
        Load();
        SetSchedule();
        RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.recycler_notice);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ScheduleAdapter adapter = new ScheduleAdapter(_schedule_list);
        recyclerView.setAdapter(adapter);
        return root;
    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_add_notice:
                    Intent intent_add_notice= new Intent(getActivity(), NewNoticeActivity.class);
                    intent_add_notice.putExtra("year",String.valueOf(_year));
                    intent_add_notice.putExtra("month",String.valueOf(_month+1));
                    intent_add_notice.putExtra("day",String.valueOf(_dayOfMonth));
                    startActivity(intent_add_notice);
                    break;
                case R.id.button_delete_notice:
                    sch_cnt = 0;
                    db.delete("Schedule","date = ?",new String[]{date});
                    SetSchedule();
                    break;
            }
        }
    }

    private void SetSchedule(){
        _schedule_list.clear();

        if(sch_cnt==0){
            Schedule good = new Schedule("Today", "No schedule");
            _schedule_list.add(good);
        }
        else{
            Schedule sch = new Schedule(_time, _title);
            _schedule_list.add(sch);
        }
    }

    public void Load(){
        FileInputStream fin = null;
        BufferedReader reader = null;
        //StringBuilder content = new StringBuilder();
        try{
            fin = getActivity().openFileInput("schedule");
            reader = new BufferedReader(new InputStreamReader(fin));
            String line = "";
            //while((line = reader.readLine())!=null)
            //content.append(line);
            line = reader.readLine();
            if((line = reader.readLine())!=null){
                _time=line;
                sch_cnt++;
            }
            line = reader.readLine();
            if((line = reader.readLine())!=null)
                _title=line;
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