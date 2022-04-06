package com.example.todoapplication

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import java.security.AccessControlContext

interface ItemStartDragListener{
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}

class TodoAdapter (val context: Context, val todoList: ArrayList<TodoItem>, val dragListener: ItemStartDragListener) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>(), ItemTouchHelperListener{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflateView = LayoutInflater.from(context).inflate(R.layout.todoitem,parent,false)
        return TodoAdapter.ViewHolder(inflateView,todoList)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    class ViewHolder(private var itemView:View, private var todoList: ArrayList<TodoItem>) : RecyclerView.ViewHolder(itemView){
        val titleText:TextView = itemView.findViewById(R.id.titleText)
        val completeBtn:CircleImageView = itemView.findViewById(R.id.completeBtn)
        fun bind(todoItem:TodoItem){
            viewSet(todoItem)

            //completeBtn Click Event 설정
            //preCondition - todoList complete로 정렬되어 있어야 함.
            completeBtn.setOnClickListener {
                TODO("completeBtn Click event 설정")
            }


        }

        fun viewSet(todoItem:TodoItem){
            titleText.setText(todoItem.title)
            if(!todoItem.complete){ //not complete item = alpha = 1.0
                itemView.alpha = 1.0f
            }
            else{ //complete item - alpha = 0.5
                itemView.alpha = 0.5f
            }
        }

    }

    override fun onItemMoved(fromIdx: Int, toIdx: Int) {
        val fromItem = todoList.removeAt(fromIdx)
        todoList.add(toIdx, fromItem)
    }

    override fun onItemSwiped(position: Int) {
        todoList.removeAt(position)
        this.notifyItemRemoved(position)
    }


}