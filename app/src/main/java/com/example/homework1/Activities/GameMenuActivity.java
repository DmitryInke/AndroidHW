package com.example.homework1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework1.R;
import com.google.android.material.button.MaterialButton;

public class GameMenuActivity extends AppCompatActivity {

    private MaterialButton menu_BTN_accelerometer;
    private MaterialButton menu_BTN_light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menu_BTN_accelerometer = findViewById(R.id.menu_BTN_accelerometer);
        menu_BTN_light = findViewById(R.id.menu_BTN_light);
        menu_BTN_accelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame("ACC");
            }
        });
        menu_BTN_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame("LIGHT");
            }
        });

    }

    private void startGame(String sensor) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.SENSOR_TYPE,sensor);
        bundle.putString(MainActivity.NAME,"sensor");
        intent.putExtra("bundle",bundle);
        startActivity(intent);
        finish();
    }
}
