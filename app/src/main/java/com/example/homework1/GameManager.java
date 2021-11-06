package com.example.homework1;

public class GameManager {
    public enum CarPosition {
        LEFT,
        CENTER,
        RIGHT
    }

    private int numberOfHearts;
    private int randomSignNumber;

    private CarPosition currentPos;

    public GameManager() {
        this.numberOfHearts = 3;
        this.currentPos = CarPosition.CENTER;
        generateSignNumber();
    }

    public int getNumberOfHearts() {
        return numberOfHearts;
    }

    public GameManager setNumberOfHearts(int numberOfHearts) {
        this.numberOfHearts = numberOfHearts;
        return this;
    }

    public int getRandomSignNumber() {
        return randomSignNumber;
    }

    public GameManager setRandomSignNumber(int randomSignNumber) {
        this.randomSignNumber = randomSignNumber;
        return this;
    }

    public void generateSignNumber() {
        this.randomSignNumber = !isGameOver() ? (int) (Math.random() * 3) : -1;
    }

    public CarPosition getCurrentPos() {
        return currentPos;
    }

    public GameManager setCurrentPos(CarPosition currentPos) {
        this.currentPos = currentPos;
        return this;
    }

    public boolean checkCrash() {
        if (CarPosition.values()[randomSignNumber] != currentPos) {
            numberOfHearts--;
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return numberOfHearts == 0;
    }
}
