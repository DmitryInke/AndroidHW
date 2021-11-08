package com.example.homework1.Models;

import com.example.homework1.Interfaces.Constants;

public class GameManager implements Constants {

    private int numberOfHearts;
    private boolean[] roadsArr;
    private int currentPos;

    public GameManager() {
        this.numberOfHearts = NUMBER_OF_HEARTS;
        this.currentPos = CENTER_ROAD;
        this.roadsArr = new boolean[NUMBER_OF_ROADS];
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

    public GameManager setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
        return this;
    }

    public void randomSignOnRoads() {
        int randomSign = !isGameOver() ? (int) (Math.random() * NUMBER_OF_ROADS) : ERROR;
        for (int i = 0; i < roadsArr.length; i++) {
            roadsArr[i] = randomSign != i;
        }
    }

    public boolean shiftCarLeft() {
        if (!isGameOver() && this.currentPos > LEFT_ROAD) {
            this.currentPos--;
            return true;
        }
        return false;
    }

    public boolean shiftCarRight() {
        if (!isGameOver() && this.currentPos < RIGHT_ROAD) {
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
        if (roadIndex == currentPos) {
            numberOfHearts--;
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return numberOfHearts == 0;
    }
}
