package com.jnu.student.adapter;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.R;
import com.jnu.student.data.EventItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<EventItem> eventList;

    String currentDate;
    public BillAdapter(List<EventItem> eventList) {
        this.eventList = eventList;
        currentDate = "t";
        for (int i = 0; i < eventList.size(); ++i) {
            EventItem event = eventList.get(i);
            if(!currentDate.equals(event.getTime().substring(0, 10))) {
                currentDate = event.getTime().substring(0, 10);
                eventList.add(i, new EventItem("", 0, currentDate));
                ++i;
            }
        }
    }

    public int getItemViewType(int position) {
        if(eventList.get(position).getEventName().equals("")) return 1;
        return 0;
    }

    public int getItemCount(){return eventList.size();}

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch(viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
                return new EventViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);
                return new GroupViewHolder(view);
            default:
                return null;
        }
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        EventItem event = eventList.get(position);
        if(holder instanceof EventViewHolder) {
            EventViewHolder eHolder = (EventViewHolder) holder;
            eHolder.name.setText(event.getEventName());
            int value = event.getEventValue();
            if (value >= 0) {
                eHolder.value.setText("+" + value);
                eHolder.value.setTextColor(Color.rgb(0, 0, 255));
            } else {
                eHolder.value.setText("" + value);          // 本身有负号，不用再添加
                eHolder.value.setTextColor(Color.rgb(255, 0, 0));
            }
            eHolder.time.setText(event.getTime().substring(11));
        }
        else if(holder instanceof GroupViewHolder) {
            GroupViewHolder gHolder = (GroupViewHolder) holder;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {date = dateFormat.parse(event.getTime());}
            catch (ParseException e) {e.printStackTrace();}
            gHolder.time.setText(event.getTime()+" - 星期"+"日一二三四五六".charAt(date.getDay()));
            gHolder.layout.setBackgroundColor(0xfff0f0f0);
        }
    }

    private static class EventViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView value;
        TextView time;
        public EventViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.event_name);
            value = itemView.findViewById(R.id.event_value);
            time = itemView.findViewById(R.id.event_time);
        }
    }

    private static class GroupViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        LinearLayout layout;
        public GroupViewHolder(@NonNull View itemView){
            super(itemView);
            time = itemView.findViewById(R.id.text_view_bill_time);
            layout = itemView.findViewById(R.id.layout_time);
        }
    }
}
