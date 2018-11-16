package com.example.kabali.coldlaunchsplashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RxJavaAndroidActivity extends AppCompatActivity {


    Button rxJavaObservable, disposal, observableMap, observabletimer, observableInterval;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_main_layout);

        rxJavaObservable = (Button)findViewById(R.id.observable_btn);
        disposal = (Button)findViewById(R.id.disposal_btn);
        observableMap = (Button)findViewById(R.id.observable_mapbtn);
        observabletimer = (Button)findViewById(R.id.observable_timerbtn);
        observableInterval = (Button)findViewById(R.id.observable_intervalbtn);
        rxJavaObservable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RxJavaAndroidActivity.this,RxJavaAndroidObservableActivity.class);
                startActivity(intent);
            }
        });

        disposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RxJavaAndroidActivity.this,RxJavaAndroidDisposalActivity.class);
                startActivity(intent);
            }
        });

        observableMap.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent intent = new Intent(RxJavaAndroidActivity.this,RxJavaAndroidMapActivity.class);
                                                 startActivity(intent);
                                             }
                                         }

        );

        observabletimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RxJavaAndroidActivity.this,RxJavaAndroidTimerActivity.class);
                startActivity(intent);
            }
        });

        observableInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RxJavaAndroidActivity.this,RxJavaAndroidIntervalActivity.class);
                startActivity(intent);

            }
        });
    }
}
