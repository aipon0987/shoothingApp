package com.example;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
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

    private static final int DEFAULT_TIMER_VALUE = 33;
    private static final long TOTAL_TIME = DEFAULT_TIMER_VALUE * 1000;
    private static final long INTERVAL_TIME = 1000;

    public static Intent newIntent(Context context){
        return new Intent(context, GameActivity.class);
    }

    private FrameLayout mainLayout;
    private ImageView soccerBall;
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
        LayoutInflater inflater = LayoutInflater.from(this);
        View ballLayout = inflater.inflate(R.layout.soccer_ball_layout, null);
        soccerBall = ballLayout.findViewById(R.id.soccer_ball);


        initTimer();
        timer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int pxSize = dpToPx(80);
        int leftMargin = dpToPx(100);
        int topMargin = dpToPx(100);
        addSoccerBall(leftMargin, topMargin, pxSize);

    }

    private void addSoccerBall(int leftMargin, int topMargin, int ballSize){
        mainLayout.addView(soccerBall);
        ViewGroup.LayoutParams params = soccerBall.getLayoutParams();
        params.width = ballSize;
        params.height = ballSize;
        ((ViewGroup.MarginLayoutParams) params).leftMargin = leftMargin;
        ((ViewGroup.MarginLayoutParams) params).topMargin = topMargin;
        soccerBall.setLayoutParams(params);
    }

    private int dpToPx(int dp){
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
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
                Log.d("TAG", "OnTick" + second);

                boolean isCountDown = (second - 30) >= 0;
                if (isCountDown) {

                    if (second -30 == 0) {

                    }else  {

                    }

                } else {

                }

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