package com.example.todolistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoListAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<todoItem> array_todo;
    LayoutInflater mLayoutInfater;

    public TodoListAdapter(Context mContext, ArrayList<todoItem> array_todo){
        this.mContext = mContext;
        this.array_todo = array_todo;
        mLayoutInfater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount(){
        return array_todo.size();
    }

    @Override
    public todoItem getItem(int position){
        return array_todo.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = mLayoutInfater.inflate(R.layout.todoitem, null);

        TextView titleView = (TextView) view.findViewById(R.id.titleView);
        TextView contentView = (TextView) view.findViewById(R.id.contentView);
        Button modifyBtn = (Button) view.findViewById(R.id.modifyBtn);

        titleView.setText(array_todo.get(position).getTitle());
        contentView.setText(array_todo.get(position).getContent());

        return view;

    }









}
