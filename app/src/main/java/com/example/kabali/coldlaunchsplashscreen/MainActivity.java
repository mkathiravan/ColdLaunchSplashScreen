package com.example.kabali.coldlaunchsplashscreen;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kabali.coldlaunchsplashscreen.pagination.PaginationActivity;

public class MainActivity extends AppCompatActivity {

    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;
    private boolean animationStarted = false;
    Button roomDatabase,rxJavaAndroid, androidPagination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.StartAppTheme);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roomDatabase = (Button)findViewById(R.id.room_btn);

        rxJavaAndroid = (Button)findViewById(R.id.btn_rx);

        androidPagination = (Button)findViewById(R.id.btn_pagination);

        roomDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                room_database_activity();
            }
        });

        rxJavaAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rxJavaAndroidActivity();
            }
        });

        androidPagination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPaginationActivity();
            }
        });
    }

    private void callPaginationActivity() {

        Intent intent = new Intent(MainActivity.this,PaginationActivity.class);
        startActivity(intent);
    }

    private void rxJavaAndroidActivity() {

        Intent intent = new Intent(MainActivity.this,RxJavaAndroidActivity.class);
        startActivity(intent);
    }

    private void room_database_activity() {

        Intent intent = new Intent(MainActivity.this,RoomDatabaseActivity.class);
        startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus || animationStarted) {
            return;
        }

        animate();

        super.onWindowFocusChanged(hasFocus);
    }


    private void animate() {
        ImageView logoImageView = (ImageView) findViewById(R.id.img_logo);
        ViewGroup container = (ViewGroup) findViewById(R.id.container);

        ViewCompat.animate(logoImageView)
                .translationY(-250)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (!(v instanceof Button)) {
                viewAnimator = ViewCompat.animate(v)
                        .translationY(50).alpha(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(1000);
            } else {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(500);
            }

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }
    }



}
