package com.example.databaseexample

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.TextView

object userEntity{
    const val ID = "id"
    const val PASSWORD = "password"
    const val NAME = "name"
}

class DbHandler(val context: Context, val dbName: String, val dbVersion: Int, val tableName: String) : SQLiteOpenHelper(context, dbName, null, dbVersion) {

    private val SQL_CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "${userEntity.ID} TEXT PRIMARY KEY," +
                "${userEntity.PASSWORD} TEXT," +
                "${userEntity.NAME} TEXT)"

    private val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + tableName


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_TABLE)
        onCreate(db)
    }

    fun insertData(user: User){
        val db = this.writableDatabase

        val values = ContentValues().apply{
            put(userEntity.ID, user.id)
            put(userEntity.PASSWORD, user.password)
            put(userEntity.NAME, user.name)
        }

        db.insert(tableName, null, values)
    }

    fun getData(user: User): User{
        val db = this.readableDatabase

        val projection = arrayOf(userEntity.ID, userEntity.PASSWORD, userEntity.NAME)
        val selection = "${userEntity.ID} = ? and ${userEntity.PASSWORD} = ?"
        val selectionArgs = arrayOf(user.id, user.password)

        val cursor = db.query(
            tableName,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            "1"
        )

        var id: String = ""
        var password: String =""
        var name: String = "None"

        if(cursor.count != 0){
            cursor.moveToFirst()
            id = cursor.getString(0)
            password = cursor.getString(1)
            name = cursor.getString(2)
        }


        return User(id,password,name)

    }

    fun getAllData(textView: TextView): ArrayList<User>{
        val db = this.readableDatabase

        val projection = arrayOf(userEntity.ID, userEntity.PASSWORD, userEntity.NAME)
        val selection = null
        val selectionArgs = null

        val cursor = db.query(
            tableName,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var result: ArrayList<User> = ArrayList()
        with(cursor){
            while(moveToNext()){
                var tmpUser: User
                var id = cursor.getString(0)
                var password = cursor.getString(1)
                var name = cursor.getString(2)

                tmpUser = User(id,password,name)
                result.add(tmpUser)
            }
        }

        textView.text = ""
        result.forEach{
            textView.text = textView.text.toString() + it.name
        }
        return result
    }

    fun deleteData(user: User): Int{
        var db = this.writableDatabase

        val selection = "${userEntity.ID} = ?"
        val selectionArgs = arrayOf(user.id)

        var deleteRowNum = db.delete(tableName, selection, selectionArgs)

        return deleteRowNum
    }

    fun updateData(user: User): Int{
        val db = this.writableDatabase

        val selection = "${userEntity.ID} = ?"
        val selectionArgs = arrayOf(user.id)

        val values = ContentValues().apply {
            put(userEntity.ID,user.id)
            put(userEntity.PASSWORD, user.password)
            put(userEntity.NAME, user.name)
        }

        var updateRowNum = db.update(tableName,values,selection,selectionArgs)

        return updateRowNum
    }

}


