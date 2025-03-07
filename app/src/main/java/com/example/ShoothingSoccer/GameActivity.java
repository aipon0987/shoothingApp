package com.example.ShoothingSoccer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.P.R;

public class GameActivity extends AppCompatActivity {

    private static final int DEFAULT_TIMER_VALUE = 30;
    private static final long TOTAL_TIME = DEFAULT_TIMER_VALUE * 1000;
    private static final long INTERVAL_TIME = 1000;

    public static Intent newIntent(Context context){
        return new Intent(context, GameActivity.class);
    }

    private TextView timerText;

    private ImageView soccer;
    private View tapView;
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

        soccer = findViewById(R.id.soccer);
        timerText = findViewById(R.id.timer_text);
        tapView = findViewById(R.id.renda);

        initTimer();
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }

    private void initTimer(){
        timerText.setText(String.valueOf(DEFAULT_TIMER_VALUE));
        timer = new CountDownTimer(TOTAL_TIME, INTERVAL_TIME) {
            @Override
            public void onTick(long millisUnitlFinished) {
                long second = millisUnitlFinished / 1000;
                timerText.setText(String.valueOf(second));
            }

            @Override
            public void onFinish() {
                tapView.setOnClickListener(null);
                Intent intent = ClearActivity
                        .newIntent(GameActivity.this, count);
                startActivity(intent);

            }
        };
    }

}