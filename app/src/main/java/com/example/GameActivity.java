package com.example;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

public class GameActivity extends AppCompatActivity {

    private static final int DEFAULT_TIMER_VALUE = 30;
    private static final long TOTAL_TIME = DEFAULT_TIMER_VALUE * 1000;
    private static final long INTERVAL_TIME = 1000;

    public static Intent newIntent(Context context){
        return new Intent(context, GameActivity.class);
    }

    private FrameLayout mainLayout;
    private CountDownTimer timer;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainLayout = findViewById(R.id.main);

        initTimer();
        timer.start();
        addBall();
    }

    private void addBall(){
        int screenWidth = 0;
        int screenHeight = 0;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
            Display disp = wm.getDefaultDisplay();
            Point realSize = new Point();
            disp.getRealSize(realSize);
            screenWidth = realSize.x;
            screenHeight = realSize.y;
        } else {
            WindowMetrics windowMetrics = this.getWindowManager().getCurrentWindowMetrics();
            screenWidth = windowMetrics.getBounds().width();
            screenHeight = windowMetrics.getBounds().height();
        }

        Log.d("TAG", "screenWidth: " + screenWidth + ", screenHeight: " + screenHeight);

        ImageView ballImage = new ImageView(this);
        ballImage.setImageResource(R.drawable.soccer_ball);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.leftMargin = 200;
        params.topMargin = 300;
        mainLayout.addView(ballImage, params);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }

    private void initTimer(){
        timer = new CountDownTimer(TOTAL_TIME, INTERVAL_TIME) {
            @Override
            public void onTick(long millisUnitlFinished) {
                long second = millisUnitlFinished / 1000;
            }

            @Override
            public void onFinish() {
                Intent intent = ClearActivity
                        .newIntent(GameActivity.this, count);
                startActivity(intent);

            }
        };
    }
}