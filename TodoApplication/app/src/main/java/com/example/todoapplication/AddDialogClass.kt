package com.example.todoapplication

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window

class AddDialogClass(context: Context){

    val dialogView = Dialog(context)

    fun showDialog(){
        dialogView.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogView.setContentView(R.layout.add_dialog)
        dialogView.show()
    }


}