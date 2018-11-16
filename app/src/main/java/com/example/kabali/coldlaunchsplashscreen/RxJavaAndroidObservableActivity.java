package com.example.kabali.coldlaunchsplashscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RxJavaAndroidObservableActivity extends AppCompatActivity {

    private static final String TAG = RxJavaAndroidObservableActivity.class.getSimpleName();
    private TextView nameView;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.observable_layout);

        btn = findViewById(R.id.btn);
        nameView = findViewById(R.id.textView);

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

    private Observable<String> getObservable()
    {
        return Observable.just("Chelsea","Manchester United");
    }

    private Observer<String> getObserver()
    {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

                Log.d(TAG,"onSubscribe "+d.isDisposed());

            }

            @Override
            public void onNext(String s) {

                nameView.append(" onNext : value : " + s);
                nameView.append("\n");
                Log.d(TAG, " onNext : value : " + s);

            }

            @Override
            public void onError(Throwable e) {

                nameView.append(" onError : " + e.getMessage());
                nameView.append("\n");
                Log.d(TAG, " onError : " + e.getMessage());

            }

            @Override
            public void onComplete() {
                nameView.append(" onComplete");
                nameView.append("\n");
                Log.d(TAG, " onComplete");

            }
        };
    }

}
