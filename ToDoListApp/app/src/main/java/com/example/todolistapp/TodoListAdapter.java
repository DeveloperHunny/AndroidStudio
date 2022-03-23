package com.example.todolistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
        View view = mLayoutInfater.inflate(R.layout.todoitem,null);

        TextView titleView = (TextView) view.findViewById(R.id.titleView);
        TextView contentView = (TextView) view.findViewById(R.id.contentView);
        Button modifyBtn = (Button) view.findViewById(R.id.modifyBtn);

        titleView.setText(array_todo.get(position).getTitle());
        contentView.setText(array_todo.get(position).getContent());


        //Button Event 설정
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View dialogCustomView = View.inflate(mContext,R.layout.dialog_modify_todo,null);

                builder.setView(dialogCustomView);
                builder.setTitle("Modify Todo Item");
                AlertDialog dialogView = builder.create();

                dialogView.show();

                Button dialog_modifyBtn = (Button) dialogView.findViewById(R.id.modifyBtn);
                dialog_modifyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText editText_title = (EditText) dialogView.findViewById(R.id.editText_title);
                        EditText editText_content = (EditText) dialogView.findViewById(R.id.editText_content);

                        String re_title = editText_title.getText().toString();
                        String re_content = editText_content.getText().toString();

                        todoItem todoItem = getItem(position);
                        todoItem.setTitle(re_title);
                        todoItem.setContent(re_content);

                        TodoListAdapter.this.notifyDataSetChanged();

                        Toast.makeText(mContext,"Modify Success",Toast.LENGTH_SHORT).show();
                        dialogView.dismiss();
                    }
                });

            }
        });

        return view;

    }









}
