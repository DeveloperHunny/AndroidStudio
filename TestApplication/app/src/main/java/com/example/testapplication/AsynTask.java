package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AsynTask extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button cancleBtn;
    private Button startBtn;
    private MyAsyncTask myAsyncTask;
    private TextView progressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asyn_task);

        progressBar = (ProgressBar) findViewById(R.id.progressBarView);
        cancleBtn = (Button) findViewById(R.id.cancleBtn);
        startBtn = (Button) findViewById(R.id.startBtn);
        progressTextView = (TextView) findViewById(R.id.progressTextView);

        myAsyncTask = new MyAsyncTask(progressBar,progressTextView, AsynTask.this);

        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAsyncTask.cancel(true);
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAsyncTask.execute();
            }
        });
    }
}