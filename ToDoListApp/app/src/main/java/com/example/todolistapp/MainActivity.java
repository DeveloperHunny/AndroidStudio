package com.example.todolistapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<todoItem> array_todo;
    private ListView todoListView;
    private Button addBtn;
    private TodoListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //변수 초기화
        todoListView = (ListView) findViewById(R.id.todoListView);
        addBtn = (Button) findViewById(R.id.addBtn);
        array_todo = new ArrayList<todoItem>();
        mAdapter = new TodoListAdapter(this,array_todo);

        //Adapter 연결
        todoListView.setAdapter(mAdapter);

        //OnClickEvent 연결
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClick_AddBtn();
            }
        });

    }
    public void OnClick_ModifyBtn(){
        Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
    }

    public void OnClick_AddBtn(){
        View dialogCustomView = getLayoutInflater().inflate(R.layout.dialog_create_todo,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Make TodoItem");
        builder.setView(dialogCustomView);

        AlertDialog dialogView = builder.create();

        Button createBtn = dialogCustomView.findViewById(R.id.createBtn);
        EditText editText_title = dialogCustomView.findViewById(R.id.editText_title);
        EditText editText_content = dialogCustomView.findViewById(R.id.editText_content);

        dialogView.show();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString();
                String content = editText_content.getText().toString();

                array_todo.add(new todoItem(title,content));
                mAdapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this,"Todo Item Create",Toast.LENGTH_SHORT).show();
                dialogView.dismiss();
            }
        });
    }
}