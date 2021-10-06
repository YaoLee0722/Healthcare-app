package com.example.careapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<Notification> _notification_list;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView _icon;
        TextView _msg;
        public ViewHolder(View view){
            super(view);
            _icon = (ImageView) view.findViewById(R.id.imageView_notice_icon);
            _msg = (TextView) view.findViewById(R.id.textview_notice_msg);
        }
    }

    public NotificationAdapter(List<Notification> list){
        this._notification_list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_notice,parent,false);
        ViewHolder holder = new ViewHolder(view);
        //holder._msg.setTextSize(30);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int index){
        Notification notification = _notification_list.get(index);
        holder._icon.setImageResource(notification.GetId());
        holder._msg.setText(notification.GetMsg());
    }

    @Override
    public int getItemCount(){
        return _notification_list.size();
    }
}
