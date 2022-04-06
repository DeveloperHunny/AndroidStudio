package com.example.todoapplication

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isGone
import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class itemsetDialogClass(val context: Context, val todoList: ArrayList<TodoItem>, val todoAdapter: TodoAdapter){
    val dialogView = Dialog(context)
    var isModify = false
    lateinit var todoItem: TodoItem

    constructor(context: Context, todoList: ArrayList<TodoItem>, todoAdapter: TodoAdapter, todoItem:TodoItem) : this(context,todoList,todoAdapter) {
        isModify = true
        this.todoItem = todoItem
    }

    lateinit var titleEdit : EditText
    lateinit var contentEdit : EditText
    lateinit var dueDateText : TextView
    lateinit var  saveBtn : Button
    lateinit var  closeBtn : ImageButton
    lateinit var  timeBtn : Button
    lateinit var  deleteBtn : Button

    fun dateToString(date: Date): String{
        var timeText: String = ""
        if(date.hours >= 12){
            timeText = SimpleDateFormat("YYYY-MM-dd hh:mm").format(date) + " PM"
        }
        else{
            timeText = SimpleDateFormat("YYYY-MM-dd hh:mm").format(date) + " AM"
        }

        return timeText
    }

    fun showDialog(){
        dialogView.setContentView(R.layout.itemset_dialog)

        titleEdit = dialogView.findViewById(R.id.titleEdit)
        contentEdit = dialogView.findViewById(R.id.contentEdit)
        saveBtn = dialogView.findViewById(R.id.saveBtn)
        deleteBtn = dialogView.findViewById(R.id.deleteBtn)
        closeBtn = dialogView.findViewById(R.id.closeBtn)
        timeBtn = dialogView.findViewById(R.id.timeBtn)
        dueDateText = dialogView.findViewById(R.id.dueDateText)



        // 아이템 정보 수정할 때 보이는 View 객체 설정
        if(isModify){
            dueDateText.isGone = false
            deleteBtn.isGone = false

            titleEdit.setText(todoItem.title)
            contentEdit.setText(todoItem.content)
            dueDateText.text = dateToString(todoItem.dueTime)

        }
        else{ // 아이템을 처음 생성할 때 보이는 View 객체 설정
            dueDateText.isGone = true
            deleteBtn.isGone = true
            todoItem = TodoItem("None", "None")
        }

        closeBtn.setOnClickListener {
            dialogView.dismiss()
        }

        saveBtn.setOnClickListener {
            val title: String = titleEdit.text.toString()
            val content: String = contentEdit.text.toString()
            todoItem.title = title
            todoItem.content = content

            if(!isModify){
                todoList.add(todoItem)
            }

            //todoList.sortBy{data -> data.complete}
            todoAdapter.notifyItemInserted(todoList.size-1)
            dialogView.dismiss()
        }

        timeBtn.setOnClickListener {
            var now = System.currentTimeMillis()
            var date = Date(now)

            val calendarView = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                date.hours = hour
                date.minutes = minute

                todoItem.dueTime = date
                dueDateText.text = dateToString(date)
                if(dueDateText.isGone){
                    dueDateText.isGone = false
                }

            }

            var timePickerDialog = TimePickerDialog(context, timeSetListener, calendarView.get(Calendar.HOUR_OF_DAY), calendarView.get(Calendar.MINUTE), false)
            timePickerDialog.show()

        }

        dialogView.show()
    }


}