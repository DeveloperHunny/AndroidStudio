package com.example.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ItemStartDragListener {

    lateinit var itemTouchHelper: ItemTouchHelper
    lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //변수 선언 및 초기화
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val addBtn = findViewById<Button>(R.id.addBtn)
        var todoList = ArrayList<TodoItem>()

        todoAdapter = TodoAdapter(this, todoList,this)
        lateinit var dialogView : itemsetDialogClass
        recyclerView.adapter = todoAdapter
        recyclerView.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        }

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(todoAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)


        //todoList sort방법 설정정
        todoList.sortWith(Comparator{ data1, data2 ->
            data1.complete.compareTo(data2.complete)
        })

        //AddBtn 클릭 이벤트 설정
        addBtn.setOnClickListener {
            dialogView = itemsetDialogClass(this, todoList, todoAdapter)
            dialogView.showDialog()
        }

    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }
}