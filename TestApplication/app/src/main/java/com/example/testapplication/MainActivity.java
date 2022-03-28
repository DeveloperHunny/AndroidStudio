package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.os.Bundle;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext(Thread.currentThread().getName() + "\n: RxJava Observer Test");
            emitter.onComplete();
        });

        //observable.subscribe(observer); // main쓰레드에서 실행

        //다른 쓰레드에서 실행
        observable.subscribeOn(Schedulers.io()).subscribe(observer);


    }

    DisposableObserver<String> observer = new DisposableObserver<String>() {
        @Override
        public void onNext(@NonNull String s) {
            textView.setText(s);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.d("TEST1", "Observer Error....");
        }

        @Override
        public void onComplete() {
            Log.d("TEST1", "Observer Completed!");
        }
    };
}