package com.example.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //변수 선언 및 초기화
        val listView = findViewById<ListView>(R.id.listView)
        val addBtn = findViewById<Button>(R.id.addBtn)
        var todoList = ArrayList<TodoItem>()
        val todoAdapter = TodoAdapter(this, todoList)
        val dialogView = AddDialogClass(this,todoList,todoAdapter)

        listView.adapter = todoAdapter


        //AddBtn 클릭 이벤트 설정
        addBtn.setOnClickListener {
            dialogView.showDialog()
//            var newTodoItem = TodoItem("Test", "test","2020-00-00")
//            todoList.add(newTodoItem)
//
//            TodoAdapter.notifyDataSetChanged()
        }



    }
}