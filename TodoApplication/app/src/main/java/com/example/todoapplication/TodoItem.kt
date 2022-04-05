package com.example.todoapplication

import java.text.SimpleDateFormat
import java.util.*

class TodoItem {
    var title : String = ""
    var content: String = ""
    var dueTime: Date = Date()
    var completed: Boolean = false

    constructor(title:String, content:String){
        this.title = title;
        this.content = content;
    }



}