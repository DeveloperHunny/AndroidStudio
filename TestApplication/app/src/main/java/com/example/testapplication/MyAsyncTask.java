package com.example.testapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<String,Integer,Boolean> {

    ProgressBar progressBar;
    TextView progressTextView;
    Context mContext;

    int value = 0;
    private Boolean threadStatus;

    public MyAsyncTask(ProgressBar progressBar, TextView progressTextView, Context mContext){
        this.progressTextView = progressTextView;
        this.progressBar = progressBar;
        this.mContext = mContext;
        value = 0;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setProgress(value);
        progressTextView.setText("0");
        threadStatus = true;
        Log.d("test", "onPreExecute");

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        Log.d("test", "onPostExecute");
        super.onPostExecute(aBoolean);
        progressBar.setProgress(100);
        Toast.makeText(mContext, "완료!!", Toast.LENGTH_LONG ).show();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        while (threadStatus) {
            value++;
            if (value >= 100) {
                break;
            } else {
                publishProgress(value);
            }
            try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        };
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0].intValue());
        progressTextView.setText(values[0].toString());
    }



    @Override
    protected void onCancelled(Boolean aBoolean) {
        progressBar.setProgress(0);
        value = 0;
        Toast.makeText(mContext, "취소되었습니다.", Toast.LENGTH_LONG ).show();
    }
}
