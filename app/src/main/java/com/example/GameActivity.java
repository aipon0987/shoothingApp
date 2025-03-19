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
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private static final int DEFAULT_TIMER_VALUE = 33;
    private static final long TOTAL_TIME = DEFAULT_TIMER_VALUE * 1000;
    private static final long INTERVAL_TIME = 1000;
    private static final int BALL_SIZE = 80;

    public static Intent newIntent(Context context){
        return new Intent(context, GameActivity.class);
    }

    private FrameLayout mainLayout;
    private ImageView soccerBall;
    private FrameLayout countDouwnLayout;
    private TextView countDownTextView;
    private CountDownTimer timer;
    private int count = 0;
    private int screenWidth = 0 ;
    private int screenHeight = 0;
    private int maxXPosition = 0;
    private int maxYPosition = 0;

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
        countDouwnLayout = findViewById(R.id.count_down_layout);
        countDownTextView = findViewById(R.id.count_down_text);

        //画面サイズを取得する
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = this.getWindowManager().getCurrentWindowMetrics();

            screenWidth = windowMetrics.getBounds().width();
            screenHeight = windowMetrics.getBounds().height();
        } else {
            WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display disp = windowManager.getDefaultDisplay();

            Point realSize = new Point();
            disp.getRealSize(realSize);
            screenWidth = realSize.x;
            screenHeight = realSize.y;
        }
        Log.d("TAG", "screenWidth: " + screenWidth + " screenHeight: " + screenHeight);

        //サッカーボールが表示できる範囲を計算する
        int  ballSize = dpToPx(BALL_SIZE);
        maxXPosition = screenWidth - ballSize;
        maxYPosition = (screenHeight - dpToPx(51)) - ballSize;

        LayoutInflater inflater = LayoutInflater.from(this);
        View ballLayout = inflater.inflate(R.layout.soccer_ball_layout, null);
        soccerBall = ballLayout.findViewById(R.id.soccer_ball);

        soccerBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                removeSoccerBall();

                //もう一度ボールをランダムに生成する
                Random random = new Random();
                int leftMargin = random.nextInt(maxXPosition);
                int topMargin = random.nextInt(maxYPosition);
                addSoccerBall(leftMargin, topMargin);
            }
        });


        initTimer();
        timer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Random random = new Random();
        int leftMargin = random.nextInt(maxXPosition);
        int topMargin = random.nextInt(maxYPosition);
        addSoccerBall(leftMargin, topMargin);

    }

    private void addSoccerBall(int leftMargin, int topMargin){
        int ballSize = dpToPx(BALL_SIZE);
        mainLayout.addView(soccerBall);
        ViewGroup.LayoutParams params = soccerBall.getLayoutParams();
        params.width = ballSize;
        params.height = ballSize;
        ((ViewGroup.MarginLayoutParams) params).leftMargin = leftMargin;
        ((ViewGroup.MarginLayoutParams) params).topMargin = topMargin;
        soccerBall.setLayoutParams(params);
    }

    private void removeSoccerBall(){
        mainLayout.removeView(soccerBall);

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
                if (second -30 == 0) {

                    countDownTextView.setText("スタート");

                }else  if (second - 30 >= 0){

                } else {

                }

            }

            @Override
            public void onFinish() {
                //タップ数の合計を次画面に渡す
                Intent intent = ClearActivity.newIntent(GameActivity.this, count);
                startActivity(intent);

            }
        };
    }

}