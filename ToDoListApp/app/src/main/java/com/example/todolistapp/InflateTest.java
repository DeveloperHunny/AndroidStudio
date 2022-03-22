package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class InflateTest extends AppCompatActivity {

    private LinearLayout container;
    private Button inflateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflate_test);

        container = (LinearLayout) findViewById(R.id.container);
        inflateBtn = (Button) findViewById(R.id.inflateBtn);

        LayoutInflater inflateObj = LayoutInflater.from(this);

        inflateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                inflateObj.inflate(R.layout.inflateitem,container,true);
            }
        });




    }

}