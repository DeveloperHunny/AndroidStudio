package com.example.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
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
        lateinit var dialogView : itemsetDialogClass

        listView.adapter = todoAdapter


        //AddBtn 클릭 이벤트 설정
        addBtn.setOnClickListener {
            dialogView = itemsetDialogClass(this, todoList, todoAdapter)
            dialogView.showDialog()
        }


        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position)
            //var todoItem: TodoItem = todoList.get(position)
            Log.d("dblog", "Click Item")
            //dialogView = itemsetDialogClass(this,todoList,todoAdapter,todoItem)
            //dialogView.showDialog()
        }




    }
}