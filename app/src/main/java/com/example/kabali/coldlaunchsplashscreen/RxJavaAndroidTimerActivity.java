package com.example.kabali.coldlaunchsplashscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaAndroidTimerActivity extends AppCompatActivity{

    private static final String TAG = RxJavaAndroidTimerActivity.class.getSimpleName();
    Button btn;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.observable_layout);
        textView = (TextView)findViewById(R.id.textView);
        btn = (Button)findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doSomeTask();
            }
        });
    }

    private void doSomeTask() {

        getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver());
    }

    private Observable<? extends Long> getObservable()
    {
        return Observable.timer(2, TimeUnit.SECONDS);
    }

    private Observer<Long> getObserver()
    {
        return new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());

            }

            @Override
            public void onNext(Long aLong) {

                textView.append(" onNext : value : " + aLong);
                textView.append("\n");
                Log.d(TAG, " onNext : value : " + aLong);

            }

            @Override
            public void onError(Throwable e) {

                textView.append(" onError : " + e.getMessage());
                textView.append("\n");
                Log.d(TAG, " onError : " + e.getMessage());

            }

            @Override
            public void onComplete() {

                textView.append(" onComplete");
                textView.append("\n");
                Log.d(TAG, " onComplete");

            }
        };
    }
}
