package com.example.todoapplication

import java.text.SimpleDateFormat
import java.util.*

class TodoItem {
    var title : String = ""
    var content: String = ""
    var dueTime: String = "0000-00-00"
    var completed: Boolean = false

    constructor(title:String, content:String, dueTime:String){
        this.title = title;
        this.content = content;
        this.dueTime = dueTime;
    }



}