package com.example.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), ItemStartDragListener {

    lateinit var itemTouchHelper: ItemTouchHelper
    lateinit var todoAdapter: TodoAdapter
    lateinit var dialogView : itemsetDialogClass
    lateinit var todoList : ArrayList<TodoItem>
    lateinit var recyclerView: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //변수 선언 및 초기화
        val dbHelper = DbHelperClass(this)

        val addBtn = findViewById<Button>(R.id.addBtn)
        val moveBtn = findViewById<Button>(R.id.moveBtn)
        val configBtn = findViewById<Button>(R.id.configBtn)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        todoList = ArrayList<TodoItem>()
        todoAdapter = TodoAdapter(this, todoList,this)
        linearLayoutManager = LinearLayoutManager(this)

        todoAdapter.setOnItemClickListener(object: itemClickListener{
            override fun onClick(itemView: View, clickView: View, position: Int) {
                if(clickView != null){
                    when(clickView.id){
                        R.id.completeBtn->{
                            completeBtnOnClick(itemView,position)
                        }
                        else->{
                            Log.d("TEST", "else")
                        }
                    }
                }
            }
        })

        //recyclerView 설정
        recyclerView.apply {
            adapter = todoAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        }

        //ItemTouchHelper 설정
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(todoAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView) //RecyclerView에 ItemTouchHelper setting


        //todoList sort방법 설정정
        todoList.sortWith(Comparator{ data1, data2 ->
            data1.complete.compareTo(data2.complete)
        })

        //AddBtn 클릭 이벤트 설정
        addBtn.setOnClickListener {
            dialogView = itemsetDialogClass(this, todoList, todoAdapter)
            dialogView.showDialog()
        }

        //MoveBtn 클릭 이벤트 설정
        moveBtn.setOnClickListener{
            TODO("MOVE BTN FUNCTION SET")
        }

        //configBtn 클릭 이벤트 설정
        configBtn.setOnClickListener{
            TODO("CONFIG BTN FUNCTION SET")
        }

        //DB에서 데이터 읽어와서 보여주기
        GlobalScope.launch(Dispatchers.IO){
            var dbReader = dbHelper.readableDatabase
            //var todoItems : ArrayList<TodoItem> = ArrayList<TodoItem>()
            // Filter results WHERE "title" = 'My Title'
            val selection = null
            val selectionArgs = null

            // How you want the results sorted in the resulting Cursor
            val sortOrder = "id ASC"
            val projection = arrayOf(todoEntity.COLUMN_ID, todoEntity.COLUMN_TITLE, todoEntity.COLUMN_CONTENT, todoEntity.COLUMN_COMPLETE, todoEntity.COLUMN_DUETIME)
            val cursor = dbReader.query(
                "todo",   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
            )

            with(cursor) {
                while (moveToNext()) {
                    var todoItem : TodoItem
                    var id = getLong(0).toInt()
                    var title = getString(1)
                    var content = getString(2)
                    var complete = getLong(3).toInt()
                    var dueTime = SimpleDateFormat("YYYY-MM-dd HH:mm").parse(getString(4))

                    todoItem = TodoItem(id,title,content,complete  ,dueTime)
                    todoList.add(todoItem)
                }
            }
            cursor.close()

            todoAdapter.notifyDataSetChanged()
        }


    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    fun completeBtnOnClick(parentView:View, position:Int){
        var todoItem = todoList.get(position)

        if(todoItem.complete == 0){ //not complete -> complete
            todoItem.complete = 1;
            parentView.alpha = 0.5f
            if(position == linearLayoutManager.findFirstVisibleItemPosition()){ //현재 RecyclerView 일종의 버그로 0번째 index 이동 시 scroll 같이 움직임. - 이를 방지하기 위한 방법
                linearLayoutManager.scrollToPosition(position)
            }
            else{todoAdapter.notifyItemChanged(position,todoItem)}

            if(todoList.size != 1 && position != todoList.size -1){
                todoList.removeAt(position)
                todoList.add(todoItem) // todoList 맨 끝에 추가

                todoAdapter.notifyItemMoved(position, todoList.size-1) // Item 이동 알림
                //todoAdapter.notifyItemRangeChanged(position,todoList.size -1)
            }

        }
        else{ //complete -> not complete
            parentView.alpha = 1.0f
            todoItem.complete = 0
            todoAdapter.notifyItemChanged(position,todoItem)
            if(todoList.size != 1 || position != 0){
                var toIdx:Int = position
                for(idx : Int in position-1 downTo 0){
                    if(todoList.get(idx).complete == 0){
                        toIdx = idx+1
                        break;
                    }
                }
                if(toIdx != position){
                    todoList.removeAt(position)
                    todoList.add(toIdx, todoItem) // todoList toIdx 자리에 넣기
                    todoAdapter.notifyItemMoved(position, toIdx) // Item 이동 알림
                }

            }


        }

    }

}