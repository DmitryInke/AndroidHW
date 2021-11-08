package com.example.homework1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.homework1.Constants.Constants;
import com.example.homework1.Models.GameManager;
import com.example.homework1.R;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ImageButton main_BTN_right;
    private ImageButton main_BTN_left;
    private Button main_BTN_newGame;

    private ImageView main_IMG_left_Car;
    private ImageView main_IMG_center_Car;
    private ImageView main_IMG_right_Car;
    private ImageView main_IMG_heart_1;
    private ImageView main_IMG_heart_2;
    private ImageView main_IMG_heart_3;
    private ImageView[] main_IMG_sign_arr;

    private LinearLayout main_LAY_car;

    private GameManager gameManager;

    private ScheduledFuture<?> scheduledFuture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        gameManager = new GameManager();
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
        scheduledFuture = new ScheduledThreadPoolExecutor(Constants.CORE_POOL_SIZE).scheduleWithFixedDelay(() -> runOnUiThread(this::moveSign),
                Constants.TIME_INTERVAL, Constants.DELAY, TimeUnit.MILLISECONDS);
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
        main_IMG_sign_arr = new ImageView[]{findViewById(R.id.main_IMG_sign_left), findViewById(R.id.main_IMG_sign_center),
                findViewById(R.id.main_IMG_sign_right)};


        main_LAY_car = findViewById(R.id.main_LAY_car);

        main_IMG_heart_1 = findViewById(R.id.main_IMG_heart_1);
        main_IMG_heart_2 = findViewById(R.id.main_IMG_heart_2);
        main_IMG_heart_3 = findViewById(R.id.main_IMG_heart_3);
        main_BTN_newGame = findViewById(R.id.main_BTN_newGame);
    }

    private void initViews() {
        main_BTN_right.setOnClickListener(v -> shiftCarRight());
        main_BTN_newGame.setOnClickListener(v -> newGame());
        main_BTN_left.setOnClickListener(v -> shiftCarLeft());
    }


    private void shiftCarLeft() {
        if (gameManager.shiftCarLeft()) {
            switch (gameManager.getCurrentPos()) {
                case Constants.LEFT_ROAD:
                    main_IMG_center_Car.setVisibility(View.INVISIBLE);
                    main_IMG_left_Car.setVisibility(View.VISIBLE);
                    break;
                case Constants.CENTER_ROAD:
                    main_IMG_right_Car.setVisibility(View.INVISIBLE);
                    main_IMG_center_Car.setVisibility(View.VISIBLE);
                    break;
                case Constants.RIGHT_ROAD:
                    break;
            }
        }
    }

    private void shiftCarRight() {
        if (gameManager.shiftCarRight()) {
            switch (gameManager.getCurrentPos()) {
                case Constants.LEFT_ROAD:
                    break;
                case Constants.CENTER_ROAD:
                    main_IMG_center_Car.setVisibility(View.VISIBLE);
                    main_IMG_left_Car.setVisibility(View.INVISIBLE);
                    break;
                case Constants.RIGHT_ROAD:
                    main_IMG_center_Car.setVisibility(View.INVISIBLE);
                    main_IMG_right_Car.setVisibility(View.VISIBLE);
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
        for (int i = 0; i < main_IMG_sign_arr.length; i++) {
            if (gameManager.getRoadsArr()[i]) {
                main_IMG_sign_arr[i].setY(main_IMG_sign_arr[i].getY() - (float) (Math.random() * Constants.HIGH_Y + Constants.LOW_Y));
                main_IMG_sign_arr[i].setVisibility(View.VISIBLE);
            }
        }
    }


    private void moveSign() {
        for (int i = 0; i < main_IMG_sign_arr.length; i++) {
            if (gameManager.getRoadsArr()[i]) {
                main_IMG_sign_arr[i].setY(main_IMG_sign_arr[i].getY() + Constants.DELTA_Y);
                checkCrash(main_IMG_sign_arr[i].getY() + main_IMG_sign_arr[i].getHeight(), main_LAY_car.getY(), i);
            }
        }
    }


    private void checkCrash(float signPositionY, float carPositionY, int roadIndex) {
        if (signPositionY > carPositionY) {
            main_IMG_sign_arr[roadIndex].setVisibility(View.INVISIBLE);
            main_IMG_sign_arr[roadIndex].setY(0);

            if (gameManager.checkCrash(roadIndex)) {

                toast(getString(R.string.toast_msg));
                vibrate();

                switch (gameManager.getNumberOfHearts()) {
                    case Constants.LEFT_ROAD:
                        main_IMG_heart_1.setVisibility(View.INVISIBLE);
                        gameOver();
                        break;
                    case Constants.CENTER_ROAD:
                        main_IMG_heart_2.setVisibility(View.INVISIBLE);
                        break;
                    case Constants.RIGHT_ROAD:
                        main_IMG_heart_3.setVisibility(View.INVISIBLE);
                        break;
                }
            }
            if (gameManager.isAllFalse())
                randomCreateSign();
        }
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(Constants.VIBRATE_TIME, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(Constants.VIBRATE_TIME);
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