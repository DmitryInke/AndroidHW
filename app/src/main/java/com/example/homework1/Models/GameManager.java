package com.example.homework1.Models;

import com.example.homework1.Interfaces.Constants;
import com.example.homework1.R;
import com.example.homework1.utils.MusicPlayer;

import java.util.ArrayList;

public class GameManager implements Constants {

    private int numberOfHearts;
    private boolean[] roadsArr;
    private int currentPos;
    private int distance;
    private int coin;
    private int randomCoin;
    private int currentTopPos;
    private TopTen topTen;
    private MyPosition myPosition;
    private float currentZ;
    private MusicPlayer mp;
    private boolean flag;

    public GameManager() {

    }

    public GameManager(TopTen topTen, MyPosition thePosition, float currentZ, MusicPlayer mp) {
        this.numberOfHearts = NUMBER_OF_HEARTS;
        this.currentPos = THIRD_ROAD;
        this.roadsArr = new boolean[NUMBER_OF_ROADS];
        this.distance = 0;
        this.coin = 0;
        this.randomCoin = 0;
        this.currentTopPos = -1;
        this.mp = mp;
        this.currentZ = currentZ;
        this.myPosition = thePosition;
        this.topTen = topTen;
        this.flag = true;
        if (this.topTen == null) {
            this.topTen = new TopTen();
        }
        randomSignOnRoads();
    }

    public int getNumberOfHearts() {
        return numberOfHearts;
    }

    public TopTen getTopTen() {
        return topTen;
    }

    public void setTopTen(TopTen topTen) {
        this.topTen = topTen;
    }

    public boolean isFlag() {
        return flag;
    }

    public GameManager setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public int getCurrentTopPos() {
        return currentTopPos;
    }

    public GameManager setCurrentTopPos(int currentTopPos) {
        this.currentTopPos = currentTopPos;
        return this;
    }

    public GameManager setNumberOfHearts(int numberOfHearts) {
        this.numberOfHearts = numberOfHearts;
        return this;
    }

    public boolean[] getRoadsArr() {
        return roadsArr;
    }

    public GameManager setRoadsArr(boolean[] roadsArr) {
        this.roadsArr = roadsArr;
        return this;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public float getCurrentZ() {
        return currentZ;
    }

    public GameManager setCurrentZ(float currentZ) {
        this.currentZ = currentZ;
        return this;
    }

    public int getDistance() {
        return distance;
    }

    public GameManager setDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public GameManager setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
        return this;
    }

    public int getCoin() {
        return coin;
    }

    public GameManager setCoin(int coin) {
        this.coin = coin;
        return this;
    }

    public int getRandomCoin() {
        return randomCoin;
    }

    public GameManager setRandomCoin(int randomCoin) {
        this.randomCoin = randomCoin;
        return this;
    }

    public void addToTopTen() {
        setFlag(true);
        if (topTen.getRecords().isEmpty()) { // TopTen list is empty
            addRecordToTopTen( 0);
            setCurrentTopPos(1);
        } else { // TopTen list is not empty
            ArrayList<Record> records = topTen.getRecords();
            int gameDistance = getDistance();
            int i = 0;
            if (gameDistance > records.get(records.size() - 1).getDistance() || records.size() < TopTen.MAX_IN_LIST) {
                do {
                    int currentDistance = records.get(i).getDistance();
                    if (gameDistance > currentDistance && i < TopTen.MAX_IN_LIST) {
                        if (records.size() == TopTen.MAX_IN_LIST) { // if list if full remove last
                            records.remove(records.size() - 1);
                        }
                        addRecordToTopTen(i);
                        setCurrentTopPos(i+1);
                        setFlag(false);
                    }
                    if (i == records.size() - 1 && i < TopTen.MAX_IN_LIST) { // winner distance is the lowest, and there is room in the list
                        addRecordToTopTen(i + 1);
                        setCurrentTopPos(i+2);
                        setFlag(false);
                    }
                    i++;
                } while (i < records.size() && isFlag());
            }
        }

    }

    private void addRecordToTopTen(int index) {
                Record record = new Record()
                .setDate(System.currentTimeMillis())
                .setDistance(getDistance())
                .setScore(getCoin())
                .setMyPosition(myPosition);

        topTen.getRecords().add(index, record);

    }

    public void randomSignOnRoads() {
        int randomSign = !isGameOver() ? (int) (Math.random() * NUMBER_OF_ROADS) : ERROR;
        randomCoin =  (int) (Math.random() * NUMBER_OF_ROADS);
        while (randomSign == randomCoin)
            randomCoin =  (int) (Math.random() * NUMBER_OF_ROADS);

        for (int i = 0; i < roadsArr.length; i++) {
            roadsArr[i] = randomSign != i;
        }

    }

    public boolean shiftCarLeft() {
        if (!isGameOver() && this.currentPos > FIRST_ROAD) {
            this.currentPos--;
            return true;
        }
        return false;
    }

    public boolean shiftCarRight() {
        if (!isGameOver() && this.currentPos < FIFTH_ROAD) {
            this.currentPos++;
            return true;
        }
        return false;
    }

    public boolean isAllFalse() {
        for (int i = 0; i < this.roadsArr.length; i++) {
            if (roadsArr[i])
                return false;
        }
        return true;
    }

    public boolean checkCrash(int roadIndex) {
        this.roadsArr[roadIndex] = false;
        if(currentPos == randomCoin && currentPos == roadIndex){
            this.coin++;
            mp.playSound(R.raw.coin_pick);
            return false;
        }
        if (roadIndex == currentPos) {
            numberOfHearts--;
            mp.playSound(R.raw.car_crash);
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return numberOfHearts == 0;
    }
}
