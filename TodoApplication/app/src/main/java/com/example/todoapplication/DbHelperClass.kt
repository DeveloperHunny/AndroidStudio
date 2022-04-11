package com.example.todoapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object todoEntity {
    // Table contents are grouped together in an anonymous object.
    const val COLUMN_ID = "id"
    const val COLUMN_TITLE = "title"
    const val COLUMN_CONTENT = "content"
    const val COLUMN_COMPLETE = "complete"
    const val COLUMN_DUETIME = "dueTime"
}

class DbHelperClass(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "TodoList.db"
        const val SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS todo (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT," +
                    "content TEXT," +
                    "complete INTEGER," +
                    "dueTime TEXT)"
        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS todo"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(SQL_CREATE_TABLE)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL(SQL_DELETE_ENTRIES)
        }
        onCreate(db)
    }

}



class DbAccessObject(val context: Context, val tableName: String,val adapter: TodoAdapter){
    val dbHelper = DbHelperClass(context)

    val projection = arrayOf(todoEntity.COLUMN_ID, todoEntity.COLUMN_TITLE, todoEntity.COLUMN_CONTENT, todoEntity.COLUMN_COMPLETE, todoEntity.COLUMN_DUETIME)

    fun insertData(todoItem: TodoItem): Int{
        var dbWriter = dbHelper.writableDatabase

        val values = ContentValues().apply{
            put("title",todoItem.title)
            put("content", todoItem.content)
            put("complete", todoItem.complete)
            put("dueTime", SimpleDateFormat("YYYY-MM-dd HH:mm").format(todoItem.dueTime))
        }

        var RowId = dbWriter.insert("todo",null,values)
        return RowId.toInt()
    }

    fun deleteDate(todoItem: TodoItem){
        var dbWriter = dbHelper.writableDatabase
    }

    fun updateData(todoItem: TodoItem){
        var dbWriter = dbHelper.writableDatabase
    }

    fun getAllData() : ArrayList<TodoItem>{
        var dbReader = dbHelper.readableDatabase
        var todoItems : ArrayList<TodoItem> = ArrayList<TodoItem>()
        // Filter results WHERE "title" = 'My Title'
        val selection = null
        val selectionArgs = null

        // How you want the results sorted in the resulting Cursor
        val sortOrder = "id ASC"

        val cursor = dbReader.query(
            tableName,   // The table to query
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
                todoItems.add(todoItem)
            }
        }
        cursor.close()


        return todoItems
    }

    fun getData(todoItem: TodoItem){
        var dbReader = dbHelper.readableDatabase
    }


}

