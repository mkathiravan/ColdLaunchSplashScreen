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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxJavaAndroidIntervalActivity extends AppCompatActivity {

    private static final String TAG = RxJavaAndroidIntervalActivity.class.getSimpleName();
    Button btn;
    TextView textView;
    private final CompositeDisposable disposables = new CompositeDisposable();


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

        disposables.add(getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(getObserver()));
    }

    private Observable<? extends Long> getObservable()
    {
        return Observable.interval(0,2, TimeUnit.SECONDS);
    }

    private DisposableObserver<Long> getObserver()
    {
        return new DisposableObserver<Long>() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear(); // clearing it : do not emit after destroy
    }

}
