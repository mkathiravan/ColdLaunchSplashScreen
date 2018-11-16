package com.example.kabali.coldlaunchsplashscreen;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class RxJavaAndroidDisposalActivity extends AppCompatActivity{

    private TextView disposalItemsView;
    private static final String TAG = RxJavaAndroidDisposalActivity.class.getSimpleName();
    private CompositeDisposable disposable = new CompositeDisposable();
    private Button btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.observable_layout);
        btn = (Button)findViewById(R.id.btn);

        disposalItemsView = (TextView) findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSomeTask();
            }
        });

        }

        void doSomeTask() {

        disposable.add(sampleObservable()

          .subscribeOn(Schedulers.io())

          .observeOn(AndroidSchedulers.mainThread())

          .subscribeWith(new DisposableObserver<String>()
          {

              @Override
              public void onNext(String s) {

                  disposalItemsView.append(" onNext : value : " + s);
                  disposalItemsView.append("\n");
                  Log.d(TAG, " onNext value : " + s);

              }

              @Override
              public void onError(Throwable e) {
                  disposalItemsView.append(" onError : " + e.getMessage());
                  disposalItemsView.append("\n");
                  Log.d(TAG, " onError : " + e.getMessage());
              }

              @Override
              public void onComplete() {

                  disposalItemsView.append(" onComplete");
                  disposalItemsView.append("\n");
                  Log.d(TAG, " onComplete");

              }
          }));

    }

    static Observable<String> sampleObservable()
    {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                SystemClock.sleep(2000);
                return Observable.just("Zidane", "Ronaldo", "Beckham", "Henry", "Buffon");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
