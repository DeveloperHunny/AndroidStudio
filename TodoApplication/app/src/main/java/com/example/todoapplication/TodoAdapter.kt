package com.example.todoapplication

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView




class TodoAdapter (private val context: Context, val todoList: ArrayList<TodoItem>, private val listener: ItemStartDragListener ) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>(), ItemTouchHelperListener{

    private lateinit var itemClickListener: itemClickListener

    fun setOnItemClickListener(itemClickListener: itemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflateView = LayoutInflater.from(context).inflate(R.layout.todoitem,parent,false)
        return TodoAdapter.ViewHolder(inflateView,todoList,listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this,todoList,position)
        holder.itemView.findViewById<CircleImageView>(R.id.completeBtn).setOnClickListener{
            itemClickListener.onClick(holder.itemView,it,holder.adapterPosition)
        }
        Log.d("TEST", "data binding")
//        holder.itemView.findViewById<Button>(R.id.completeBtn).setOnClickListener{
//            itemClickListener.onClick(it,position)
//        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    class ViewHolder(itemView:View, private var todoList: ArrayList<TodoItem>, listener: ItemStartDragListener ) : RecyclerView.ViewHolder(itemView){
        val titleText:TextView = itemView.findViewById(R.id.titleText)
        val completeBtn:CircleImageView = itemView.findViewById(R.id.completeBtn)
        val hamburgerBtn: ImageView = itemView.findViewById(R.id.hamburgerBtn)

        init{
            hamburgerBtn.setOnTouchListener{v,event ->
                if(event.action == MotionEvent.ACTION_DOWN){
                    Log.d("TEST","TOUCH EVENT")
                    listener.onStartDrag(this)
                }
                false
            }
        }


        fun bind(todoAdapter: TodoAdapter, todoList: ArrayList<TodoItem>,position: Int){
            var todoItem = todoList[position]
            Log.d("TEST1",position.toString())
            InitializeView(todoItem)

        }

        fun InitializeView(todoItem:TodoItem){
            titleText.setText(todoItem.title)
            if(todoItem.complete == 0){ //not complete item = alpha = 1.0
                itemView.alpha = 1.0f
            }
            else{ //complete item - alpha = 0.5
                itemView.alpha = 0.5f
            }
        }

    }


    override fun onItemMoved(fromIdx: Int, toIdx: Int) {
        val fromItem = todoList.removeAt(fromIdx) //????????? ?????? ?????? ??? ??????
        todoList.add(toIdx, fromItem) //????????? ????????? ?????? ??????
        this.notifyItemMoved(fromIdx,toIdx) //?????? ?????? ??????
    }

    override fun onItemSwiped(position: Int) {
        todoList.removeAt(position) //?????? ??????
        this.notifyItemRemoved(position) //?????? ?????? ??????
    }



}
