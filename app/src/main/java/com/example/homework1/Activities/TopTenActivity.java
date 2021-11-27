package com.example.homework1.Activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework1.Callbacks.CallBackTop;
import com.example.homework1.Fragments.FragmentList;
import com.example.homework1.Fragments.FragmentMap;
import com.example.homework1.Models.TopTen;
import com.example.homework1.R;
import com.example.homework1.utils.SP;
import com.google.gson.Gson;

public class TopTenActivity extends AppCompatActivity {


    private FrameLayout topTen_LAY_list;
    private FrameLayout topTen_LAY_map;

    private FragmentList fragmentList;
    private FragmentMap fragmentMap;

    private TopTen topTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);

        topTen = null;
        String ttJson = SP.getInstance().getString(SP.KEY_TOP_TEN, "NA");
        if (!ttJson.equals("NA")) {
            topTen = new Gson().fromJson(ttJson, TopTen.class);
        }

        findViews();

        initViews();

    } // onCreate

    private void initViews() {
        fragmentList = new FragmentList(this, topTen);
        fragmentList.setCallBackTop(callBackTop);
        getSupportFragmentManager().beginTransaction().add(R.id.topTen_LAY_list, fragmentList).commit();

        fragmentMap = new FragmentMap(topTen);
        getSupportFragmentManager().beginTransaction().replace(R.id.topTen_LAY_map, fragmentMap).commit();


    }


    private void findViews() {
        topTen_LAY_list = findViewById(R.id.topTen_LAY_list);
        topTen_LAY_map = findViewById(R.id.topTen_LAY_map);
    }

    private CallBackTop callBackTop = new CallBackTop() {
        @Override
        public void zoomToMarker(double latitude, double longitude) {
            fragmentMap.zoomToMarker(latitude, longitude);
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
