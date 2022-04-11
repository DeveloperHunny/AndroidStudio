package com.example.databaseexample

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHandler = DbHandler(this,"test.db",1,"testTable")

        val signUpBtn = findViewById<Button>(R.id.signUpBtn)
        val showBtn = findViewById<Button>(R.id.showBtn)
        val loginBtn = findViewById<Button>(R.id.loginBtn)

        val idEdit = findViewById<EditText>(R.id.idEdit)
        val passwordEdit = findViewById<EditText>(R.id.passwordEdit)
        val nameEdit = findViewById<EditText>(R.id.nameEdit)
        val showText: TextView = findViewById(R.id.showText)

        signUpBtn.setOnClickListener {
            var id = idEdit.text.toString()
            var password = passwordEdit.text.toString()
            var name = nameEdit.text.toString()

            dbHandler.insertData(User(id,password,name))
        }

        showBtn.setOnClickListener {
            var userList:ArrayList<User> = dbHandler.getAllData(showText)
        }

        loginBtn.setOnClickListener {
            var id = idEdit.text.toString()
            var password = passwordEdit.text.toString()

            var findUser = dbHandler.getData(User(id,password,"name"))

            Toast.makeText(this,findUser.name + "is found!", Toast.LENGTH_LONG).show()
        }



    }
}