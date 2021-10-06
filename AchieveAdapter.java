package com.example.careapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AchieveAdapter extends RecyclerView.Adapter<AchieveAdapter.ViewHolder> {
    private List<Notification> _achievement_list;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView _icon;
        TextView _msg;
        public ViewHolder(View view){
            super(view);
            _icon = (ImageView) view.findViewById(R.id.imageView_notice_icon);
            _msg = (TextView) view.findViewById(R.id.textview_notice_msg);
            _msg.setTextSize(30);
        }
    }

    public AchieveAdapter(List<Notification> list){
        this._achievement_list = list;
    }

    @Override
    public AchieveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int type){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.achievement_item,parent,false);
        AchieveAdapter.ViewHolder holder = new AchieveAdapter.ViewHolder(view);
        //holder._msg.setTextSize(30);
        return holder;
    }

    @Override
    public void onBindViewHolder(AchieveAdapter.ViewHolder holder, int index){
        Notification notification = _achievement_list.get(index);
        holder._icon.setImageResource(notification.GetId());
        holder._msg.setText(notification.GetMsg());
    }

    @Override
    public int getItemCount(){
        return _achievement_list.size();
    }
}
