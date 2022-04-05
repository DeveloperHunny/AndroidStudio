package com.example.todoapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import java.security.AccessControlContext

class TodoAdapter (val context: Context, val todoList: ArrayList<TodoItem>) : BaseAdapter() {

    override fun getCount(): Int {
        return todoList.size
    }

    override fun getItem(position: Int): Any {
        return todoList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        //변수 선언 및 초기화
        val todoItemView: View = LayoutInflater.from(context).inflate(R.layout.todoitem, null)

        val deleteBtn = todoItemView.findViewById<Button>(R.id.deleteBtn)
        val titleText = todoItemView.findViewById<TextView>(R.id.titleText)

        //Array Item 가져오기
        val todoItem = todoList.get(position)

        //Inflate View 설정
        titleText.setText(todoItem.title)

        //deleteBtn Click Event설정
        deleteBtn.setOnClickListener {
            todoList.removeAt(position)
            this.notifyDataSetChanged()
        }

        //item Click Event 설정
        todoItemView.setOnClickListener {
            val itemsetDialog = itemsetDialogClass(context,todoList,this,todoItem)
            itemsetDialog.showDialog()
        }


        return todoItemView
    }


}