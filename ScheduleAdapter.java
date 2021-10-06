package com.example.careapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<Schedule> _schedule_list;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView _time;
        TextView _title;
        public ViewHolder(View view){
            super(view);
            _time = (TextView) view.findViewById(R.id.textview_sch_time);
            _title = (TextView) view.findViewById(R.id.textview_sch_title);
            _time.setTextSize(25);
            _title.setTextSize(25);
        }
    }

    public ScheduleAdapter(List<Schedule> list){
        this._schedule_list = list;
    }

    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int type){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_item,parent,false);
        ScheduleAdapter.ViewHolder holder = new ScheduleAdapter.ViewHolder(view);
        //holder._msg.setTextSize(30);
        return holder;
    }

    @Override
    public void onBindViewHolder(ScheduleAdapter.ViewHolder holder, int index){
        Schedule schedule = _schedule_list.get(index);
        holder._time.setText(schedule.GetTime());
        holder._title.setText(schedule.GetTitle());
    }

    @Override
    public int getItemCount(){
        return _schedule_list.size();
    }

}
