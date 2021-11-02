package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

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
    }

    private void initViews() {
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shiftCarRight();
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shiftCarLeft();
            }
        });
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