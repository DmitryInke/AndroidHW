package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    public enum CarPosition {
        LEFT,
        CENTER,
        RIGHT
    }

    private CarPosition currentPos = CarPosition.CENTER;
    private ImageButton rightArrow;
    private ImageButton leftArrow;
    private ImageView leftCar;
    private ImageView centerCar;
    private ImageView rightCar;
    private ImageView main_IMG_background_left_road;
    private ImageView main_IMG_background_center_road;
    private ImageView main_IMG_background_right_road;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
    }

    private void findViews() {
        rightArrow = findViewById(R.id.rightButton);
        leftArrow = findViewById(R.id.leftButton);
        leftCar = findViewById(R.id.leftCar);
        centerCar = findViewById(R.id.centerCar);
        rightCar = findViewById(R.id.rightCar);
        main_IMG_background_left_road = findViewById(R.id.main_IMG_background_left_road);
        main_IMG_background_center_road = findViewById(R.id.main_IMG_background_center_road);
        main_IMG_background_right_road = findViewById(R.id.main_IMG_background_right_road);
    }

    private void initViews() {
        Glide.with(this).load(R.drawable.city_street_img).centerCrop().into(main_IMG_background_left_road);
        Glide.with(this).load(R.drawable.city_street_img).centerCrop().into(main_IMG_background_center_road);
        Glide.with(this).load(R.drawable.city_street_img).centerCrop().into(main_IMG_background_right_road);
        rightArrow.setOnClickListener(v -> shiftCarRight());

        leftArrow.setOnClickListener(v -> shiftCarLeft());
    }

    private void shiftCarLeft() {
        switch (currentPos) {
            case CENTER:
                centerCar.setVisibility(View.INVISIBLE);
                leftCar.setVisibility(View.VISIBLE);
                currentPos = CarPosition.LEFT;
                break;
            case RIGHT:
                rightCar.setVisibility(View.INVISIBLE);
                centerCar.setVisibility(View.VISIBLE);
                currentPos = CarPosition.CENTER;
                break;
            case LEFT:
                break;
        }
    }

    private void shiftCarRight() {
        switch (currentPos) {
            case CENTER:
                centerCar.setVisibility(View.INVISIBLE);
                rightCar.setVisibility(View.VISIBLE);
                currentPos = CarPosition.RIGHT;
                break;
            case RIGHT:
                break;
            case LEFT:
                centerCar.setVisibility(View.VISIBLE);
                leftCar.setVisibility(View.INVISIBLE);
                currentPos = CarPosition.CENTER;
                break;
        }
    }


}