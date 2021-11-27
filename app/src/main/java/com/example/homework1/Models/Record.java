package com.example.homework1.Models;

public class Record {

    private int distance = 0;
    private long date = 0;
    private int score = 0;
    private MyPosition myPosition;

    public Record() { }

    public Record(int distance, long date, int score) {
        this.distance = distance;
        this.date = date;
        this.score = score;

    }

    public int getDistance() {
        return distance;
    }

    public Record setDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public long getDate() {
        return date;
    }

    public Record setDate(long date) {
        this.date = date;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Record setScore(int score) {
        this.score = score;
        return this;
    }

    public MyPosition getMyPosition() {
        return myPosition;

    }

    public Record setMyPosition(MyPosition myPosition) {
        this.myPosition = myPosition;
        return this;
    }
}