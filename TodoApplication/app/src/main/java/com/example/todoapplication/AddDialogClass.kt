package com.example.todoapplication

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText

class AddDialogClass(context: Context, todoList: ArrayList<TodoItem>, todoAdapter: TodoAdapter){

    val dialogView = Dialog(context)
    val todoList = todoList
    val todoAdapter = todoAdapter

    lateinit var titleEdit : EditText
    lateinit var contentEdit : EditText
    lateinit var  addBtn : Button
    lateinit var  closeBtn : Button
    lateinit var  timeBtn : Button

    fun showDialog(){
        dialogView.setContentView(R.layout.add_dialog)

        titleEdit = dialogView.findViewById<EditText>(R.id.titleEdit)
        contentEdit = dialogView.findViewById<EditText>(R.id.contentEdit)
        addBtn = dialogView.findViewById<Button>(R.id.addBtn)
        closeBtn = dialogView.findViewById<Button>(R.id.closeBtn)


        closeBtn.setOnClickListener {
            dialogView.dismiss()
        }

        addBtn.setOnClickListener {
            val title: String = titleEdit.text.toString()
            val content: String = contentEdit.text.toString()
            var todoItem = TodoItem(title,content,"2020-00-10")

            todoList.add(todoItem)
            todoAdapter.notifyDataSetChanged()
            dialogView.dismiss()
        }

        timeBtn.setOnClickListener {

        }

        dialogView.show()
    }


}