package com.example.homework1.Models;

import java.util.ArrayList;

public class TopTen {

    public final static int MAX_IN_LIST = 10;

    private ArrayList<Record> records = new ArrayList<>();

    public TopTen() { }

    public TopTen(ArrayList<Record> records) {
        this.records = records;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public TopTen setRecords(ArrayList<Record> records) {
        this.records = records;
        return this;
    }

}