package com.example.homework1.Models;

import android.media.MediaPlayer;
import android.util.Log;

import com.example.homework1.Interfaces.Constants;

public class GameManager implements Constants {

    private int numberOfHearts;
    private boolean[] roadsArr;
    private int currentPos;
    private int distance;
    private int coin;
    private int randomCoin;

    public GameManager() {
        this.numberOfHearts = NUMBER_OF_HEARTS;
        this.currentPos = THIRD_ROAD;
        this.roadsArr = new boolean[NUMBER_OF_ROADS];
        this.distance = 0;
        this.coin = 0;
        this.randomCoin = 0;
        randomSignOnRoads();
    }

    public int getNumberOfHearts() {
        return numberOfHearts;
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

    public boolean checkCrash(int roadIndex, MediaPlayer crashSound, MediaPlayer coinSound) {
        this.roadsArr[roadIndex] = false;
        if(currentPos == randomCoin && currentPos == roadIndex){
            this.coin++;
            coinSound.start();
            return false;
        }
        if (roadIndex == currentPos) {
            numberOfHearts--;
            crashSound.start();
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return numberOfHearts == 0;
    }
}
