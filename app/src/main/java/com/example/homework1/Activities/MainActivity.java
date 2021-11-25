package com.example.homework1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homework1.Interfaces.Constants;
import com.example.homework1.Models.GameManager;
import com.example.homework1.R;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements Constants {

    private ImageButton main_BTN_right;
    private ImageButton main_BTN_left;
    private Button main_BTN_newGame;

    private ImageView main_IMG_first_Car;
    private ImageView main_IMG_second_Car;
    private ImageView main_IMG_third_Car;
    private ImageView main_IMG_fourth_Car;
    private ImageView main_IMG_fifth_Car;
    private ImageView main_IMG_heart_1;
    private ImageView main_IMG_heart_2;
    private ImageView main_IMG_heart_3;
    private ImageView[] main_IMG_sign_arr;

    private TextView main_Text_distance_counter;
    private TextView main_Text_count_coins;


    private LinearLayout main_LAY_car;

    private GameManager gameManager;

    private ScheduledFuture<?> scheduledFuture;

    private Handler timerHandler = new Handler();
    private long startTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        gameManager = new GameManager();
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(distanceCounter, 0);
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

    private Runnable distanceCounter = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            gameManager.setDistance((int) (millis / TIME_MILLIS_DELAY)-OFFSET_TIME) ;
            if(gameManager.getDistance() >0)
                main_Text_distance_counter.setText(gameManager.getDistance() + " m");
            timerHandler.postDelayed(this, TIME_MILLIS_DELAY);
        }
    };

    private void start() {
        randomCreateSign();
        scheduledFuture = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE).scheduleWithFixedDelay(() -> runOnUiThread(this::moveSign),
                TIME_INTERVAL, DELAY, TimeUnit.MILLISECONDS);

    }

    private void stop() {
        scheduledFuture.cancel(false);
        timerHandler.removeCallbacks(distanceCounter);
    }

    private void findViews() {
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_IMG_first_Car = findViewById(R.id.main_IMG_first_Car);
        main_IMG_second_Car = findViewById(R.id.main_IMG_second_Car);
        main_IMG_third_Car = findViewById(R.id.main_IMG_third_Car);
        main_IMG_fourth_Car = findViewById(R.id.main_IMG_fourth_Car);
        main_IMG_fifth_Car = findViewById(R.id.main_IMG_fifth_Car);
        main_IMG_sign_arr = new ImageView[]{findViewById(R.id.main_IMG_sign_first), findViewById(R.id.main_IMG_sign_second),
                findViewById(R.id.main_IMG_sign_third), findViewById(R.id.main_IMG_sign_fourth), findViewById(R.id.main_IMG_sign_fifth)};

        main_LAY_car = findViewById(R.id.main_LAY_car);

        main_IMG_heart_1 = findViewById(R.id.main_IMG_heart_1);
        main_IMG_heart_2 = findViewById(R.id.main_IMG_heart_2);
        main_IMG_heart_3 = findViewById(R.id.main_IMG_heart_3);
        main_BTN_newGame = findViewById(R.id.main_BTN_newGame);

        main_Text_distance_counter = findViewById(R.id.main_Text_distance_counter);
        main_Text_count_coins = findViewById(R.id.main_Text_count_coins);
    }

    private void initViews() {
        main_BTN_right.setOnClickListener(v -> shiftCarRight());
        main_BTN_newGame.setOnClickListener(v -> newGame());
        main_BTN_left.setOnClickListener(v -> shiftCarLeft());
    }


    private void shiftCarLeft() {
        if (gameManager.shiftCarLeft()) {
            switch (gameManager.getCurrentPos()) {
                case FIRST_ROAD:
                    main_IMG_second_Car.setVisibility(View.INVISIBLE);
                    main_IMG_first_Car.setVisibility(View.VISIBLE);
                    break;
                case SECOND_ROAD:
                    main_IMG_third_Car.setVisibility(View.INVISIBLE);
                    main_IMG_second_Car.setVisibility(View.VISIBLE);
                    break;
                case THIRD_ROAD:
                    main_IMG_fourth_Car.setVisibility(View.INVISIBLE);
                    main_IMG_third_Car.setVisibility(View.VISIBLE);
                    break;
                case FOURTH_ROAD:
                    main_IMG_fifth_Car.setVisibility(View.INVISIBLE);
                    main_IMG_fourth_Car.setVisibility(View.VISIBLE);
                    break;
                case FIFTH_ROAD:
                    break;
            }
        }
    }

    private void shiftCarRight() {
        if (gameManager.shiftCarRight()) {
            switch (gameManager.getCurrentPos()) {
                case FIRST_ROAD:
                    break;
                case SECOND_ROAD:
                    main_IMG_second_Car.setVisibility(View.VISIBLE);
                    main_IMG_first_Car.setVisibility(View.INVISIBLE);
                    break;
                case THIRD_ROAD:
                    main_IMG_second_Car.setVisibility(View.INVISIBLE);
                    main_IMG_third_Car.setVisibility(View.VISIBLE);
                    break;
                case FOURTH_ROAD:
                    main_IMG_third_Car.setVisibility(View.INVISIBLE);
                    main_IMG_fourth_Car.setVisibility(View.VISIBLE);
                    break;
                case FIFTH_ROAD:
                    main_IMG_fourth_Car.setVisibility(View.INVISIBLE);
                    main_IMG_fifth_Car.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private void newGame() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void randomCreateSign() {
        gameManager.randomSignOnRoads();
        main_IMG_sign_arr[gameManager.getRandomCoin()].setImageResource(R.drawable.coins);
        for (int i = 0; i < main_IMG_sign_arr.length; i++) {
            if (gameManager.getRoadsArr()[i]) {
                main_IMG_sign_arr[i].setY(main_IMG_sign_arr[i].getY() - (float) (Math.random() * HIGH_Y + LOW_Y));
                main_IMG_sign_arr[i].setVisibility(View.VISIBLE);
            }
        }
    }


    private void moveSign() {
        for (int i = 0; i < main_IMG_sign_arr.length; i++) {
            if (gameManager.getRoadsArr()[i]) {
                main_IMG_sign_arr[i].setY(main_IMG_sign_arr[i].getY() + DELTA_Y);
                checkCrash(main_IMG_sign_arr[i].getY() + main_IMG_sign_arr[i].getHeight(), main_LAY_car.getY(), i);
            }
        }
    }

    private void checkCrash(float signPositionY, float carPositionY, int roadIndex) {
        if (signPositionY > carPositionY) {
            main_IMG_sign_arr[roadIndex].setVisibility(View.INVISIBLE);
            main_IMG_sign_arr[roadIndex].setY(0);
            main_IMG_sign_arr[roadIndex].setImageResource(R.drawable.road_sign);

            if (gameManager.checkCrash(roadIndex)) {

                toast(getString(R.string.toast_msg));
                vibrate();

                switch (gameManager.getNumberOfHearts()) {
                    case FIRST_HEALTH:
                        main_IMG_heart_1.setVisibility(View.INVISIBLE);
                        gameOver();
                        break;
                    case SECOND_HEALTH:
                        main_IMG_heart_2.setVisibility(View.INVISIBLE);
                        break;
                    case THIRD_HEALTH:
                        main_IMG_heart_3.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            main_Text_count_coins.setText(gameManager.getCoin() + "");
            if (gameManager.isAllFalse())

                randomCreateSign();
        }
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(VIBRATE_TIME, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(VIBRATE_TIME);
        }
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void gameOver() {
        main_BTN_newGame.setVisibility(View.VISIBLE);
        stop();
    }

}