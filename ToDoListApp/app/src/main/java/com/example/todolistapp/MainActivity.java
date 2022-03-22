package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<todoItem> array_todo;
    private ListView todoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //데이터 생성
        array_todo = new ArrayList<todoItem>();
        array_todo.add(new todoItem("TEST1", "CONTENT1"));
        array_todo.add(new todoItem("TEST2", "CONTENT1"));
        array_todo.add(new todoItem("TEST3", "CONTENT1"));
        array_todo.add(new todoItem("TEST4", "CONTENT1"));
        array_todo.add(new todoItem("TEST5", "CONTENT1"));
        array_todo.add(new todoItem("TEST6", "CONTENT1"));
        array_todo.add(new todoItem("TEST7", "CONTENT1"));
        array_todo.add(new todoItem("TEST8", "CONTENT1"));

        todoListView = (ListView) findViewById(R.id.todoListView);
        final TodoListAdapter myAdapter = new TodoListAdapter(this,array_todo);

        todoListView.setAdapter(myAdapter);

        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),
                        myAdapter.getItem(i).getContent(),
                        Toast.LENGTH_LONG).show();
            }
        });



    }
}