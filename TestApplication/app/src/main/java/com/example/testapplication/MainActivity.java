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
    private Button clickBtn;
    private Button finishBtn;


    private static int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        clickBtn = (Button) findViewById(R.id.clickBtn);
        finishBtn = (Button) findViewById(R.id.finishBtn);
        number = 1;

        String[] source = {"First", "Second", "Third"};
        Observable.fromArray(source)
                .observeOn(Schedulers.single())
                .subscribe( data -> {
                    System.out.println("Observe On : "+Thread.currentThread().getName()+" | "+"value : "+data);
                });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Observable<Integer> observable = Observable.create(emitter -> {
            clickBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG",Thread.currentThread().getName());
                    emitter.onNext(number);
                    number += 1;
                }
            });

            finishBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emitter.onComplete();
                }
            });
        });

        //observable.subscribe(observer); // main쓰레드에서 실행

        //다른 쓰레드에서 실행
        observable.subscribeOn(Schedulers.io()).subscribe(observer);


    }

    DisposableObserver<Integer> observer = new DisposableObserver<Integer>() {
        @Override
        public void onNext(@NonNull Integer number) {
            textView.setText(number.toString());
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