package com.example.homework1.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.homework1.Callbacks.CallBackTop;
import com.example.homework1.Models.Record;
import com.example.homework1.Models.RecordListAdapter;
import com.example.homework1.Models.TopTen;
import com.example.homework1.R;

public class FragmentList extends Fragment {

    private Context mContext;
    private TopTen topTen;
    private ListView topTen_LV_list;
    private CallBackTop callBackTop;

    public FragmentList(Context context, TopTen topTen) {
        mContext = context;
        this.topTen = topTen;
    }

    public void setCallBackTop(CallBackTop _callBack_top) {
        this.callBackTop = _callBack_top;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        findViews(view);

        initViews();

        return view;
    }

    private void initViews() {
        if (topTen != null) {
            RecordListAdapter adapter = new RecordListAdapter(mContext, R.layout.adapter_view_layout, topTen.getRecords());
            topTen_LV_list.setAdapter(adapter);
        }

        topTen_LV_list.setOnItemClickListener((parent, view, position, id) -> {
            Record record = (Record) parent.getItemAtPosition(position);
            if (callBackTop != null) {
                callBackTop.zoomToMarker(record.getMyPosition().getLatitude(), record.getMyPosition().getLongitude());
            }
        });

    }

    private void findViews(View view) {
        topTen_LV_list = view.findViewById(R.id.topTen_LV_list);
    }

}