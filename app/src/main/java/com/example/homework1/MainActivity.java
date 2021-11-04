package com.example.homework1;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private final int TIME_INTERVAL = 2000;
    private final int DELAY = 1;

    private ImageButton main_BTN_right;
    private ImageButton main_BTN_left;

    private ImageView main_IMG_left_Car;
    private ImageView main_IMG_center_Car;
    private ImageView main_IMG_right_Car;
    private ImageView main_IMG_sign_left;
    private ImageView main_IMG_sign_center;
    private ImageView main_IMG_sign_right;
    private ImageView main_IMG_heart_1;
    private ImageView main_IMG_heart_2;
    private ImageView main_IMG_heart_3;
    private TextView main_TXT_gameOver;
    private LinearLayout main_LAY_car;

    private GameManager gameManager;

    private ScheduledFuture<?> scheduledFuture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameManager = new GameManager();
        findViews();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        start();
    }


    @Override
    protected void onStop() {
        super.onStop();
        stop();

    }

    private void start() {
        randomCreateSign();
        scheduledFuture = new ScheduledThreadPoolExecutor(5).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        randomMove();
                    }
                });
            }
        }, TIME_INTERVAL, DELAY, TimeUnit.MILLISECONDS);
    }

    private void stop() {
        scheduledFuture.cancel(false);
    }

    private void findViews() {
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_IMG_left_Car = findViewById(R.id.main_IMG_left_Car);
        main_IMG_center_Car = findViewById(R.id.main_IMG_center_Car);
        main_IMG_right_Car = findViewById(R.id.main_IMG_right_Car);
        main_IMG_sign_left = findViewById(R.id.main_IMG_sign_left);
        main_IMG_sign_center = findViewById(R.id.main_IMG_sign_center);
        main_IMG_sign_right = findViewById(R.id.main_IMG_sign_right);
        main_LAY_car = findViewById(R.id.main_LAY_car);
        main_IMG_heart_1 = findViewById(R.id.main_IMG_heart_1);
        main_IMG_heart_2 = findViewById(R.id.main_IMG_heart_2);
        main_IMG_heart_3 = findViewById(R.id.main_IMG_heart_3);
        main_TXT_gameOver = findViewById(R.id.main_TXT_gameOver);
    }

    private void initViews() {
        main_BTN_right.setOnClickListener(v -> shiftCarRight());

        main_BTN_left.setOnClickListener(v -> shiftCarLeft());
    }

    private void shiftCarLeft() {
        if (gameManager.isGameOver())
            return;
        switch (gameManager.getCurrentPos()) {
            case CENTER:
                main_IMG_center_Car.setVisibility(View.INVISIBLE);
                main_IMG_left_Car.setVisibility(View.VISIBLE);
                gameManager.setCurrentPos(GameManager.CarPosition.LEFT);
                break;
            case RIGHT:
                main_IMG_right_Car.setVisibility(View.INVISIBLE);
                main_IMG_center_Car.setVisibility(View.VISIBLE);
                gameManager.setCurrentPos(GameManager.CarPosition.CENTER);
                break;
            case LEFT:
                break;
        }
    }

    private void shiftCarRight() {
        if (gameManager.isGameOver())
            return;
        switch (gameManager.getCurrentPos()) {
            case CENTER:
                main_IMG_center_Car.setVisibility(View.INVISIBLE);
                main_IMG_right_Car.setVisibility(View.VISIBLE);
                gameManager.setCurrentPos(GameManager.CarPosition.RIGHT);
                break;
            case RIGHT:
                break;
            case LEFT:
                main_IMG_center_Car.setVisibility(View.VISIBLE);
                main_IMG_left_Car.setVisibility(View.INVISIBLE);
                gameManager.setCurrentPos(GameManager.CarPosition.CENTER);
                break;
        }
    }

    private void randomCreateSign() {
        gameManager.generateSignNumber();
        switch (gameManager.getRandomSignNumber()) {
            case 0:
                main_IMG_sign_left.setVisibility(View.VISIBLE);
                break;
            case 1:
                main_IMG_sign_center.setVisibility(View.VISIBLE);
                break;
            case 2:
                main_IMG_sign_right.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void randomMove() {
        switch (gameManager.getRandomSignNumber()) {
            case 0:
                moveSign(main_IMG_sign_left);
                break;
            case 1:
                moveSign(main_IMG_sign_center);
                break;
            case 2:
                moveSign(main_IMG_sign_right);
                break;
        }

    }

    private void moveSign(ImageView imgView) {
        imgView.setY(imgView.getY() + 1);
        if (main_LAY_car.getY() < imgView.getY() + imgView.getHeight()) {
            imgView.setVisibility(View.INVISIBLE);
            imgView.setY(0);
            checkCrash();

        }
    }

    private void checkCrash() {
        if (gameManager.checkCrash()) {
            if (gameManager.getNumberOfHearts() != 0) {
                toast(getString(R.string.toast_msg));
                vibrate();
            }
            switch (gameManager.getNumberOfHearts()) {
                case 0:
                    main_IMG_heart_1.setVisibility(View.INVISIBLE);
                    gameover();
                    break;
                case 1:
                    main_IMG_heart_2.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    main_IMG_heart_3.setVisibility(View.INVISIBLE);
                    break;
            }

        }
        randomCreateSign();

    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void gameover() {
        main_TXT_gameOver.setVisibility(View.VISIBLE);
        stop();
    }

}