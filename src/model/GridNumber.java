package model;

import java.util.Arrays;
import java.util.Random;

public class GridNumber {
    private final int X_COUNT;   //row
    private final int Y_COUNT;   //column

    private int[][] numbers;     //boards  4*4

    private int[][] tempNumbers;

    static Random random = new Random();

    public GridNumber(int xCount, int yCount) {
        this.X_COUNT = xCount;
        this.Y_COUNT = yCount;
        this.numbers = new int[this.X_COUNT][this.Y_COUNT];
        this.tempNumbers = new int[this.X_COUNT][this.Y_COUNT];

        //call method
        this.fakeInitialNumbers();   //need to create a spawn
    }

    //
    public void randomNextTile() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                //only spawn must come out form the tile having 0 value
                if (numbers[i][j] == 0) {
                    numbers[i][j] = random.nextInt(10) < 9 ? 2 : 4; // 90% chance to get 2
                }
            }
        }
    }

    public void fakeInitialNumbers() {

        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                int powerNumber = random.nextInt(4);
                if(powerNumber == 0){
                    powerNumber++;
                }
                numbers[i][j] = random.nextInt(2) == 0 ? (int) Math.pow(2, powerNumber) : 0;
            }

        }
    }

    public void initialNumbers() {
        int randFirstRow = random.nextInt(4);
        int randFirstCol = random.nextInt(4);
        int randSecondRow = random.nextInt(4);
        int randSecondCol = random.nextInt(4);

        //second location must not equal to first location
        while (randSecondRow == randFirstRow && randSecondCol == randFirstCol) {
            randSecondRow = random.nextInt(4);
            randSecondCol = random.nextInt(4);
        }

        //create random two tiles(2 and 4) in first and second location
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                if (randFirstRow == i && randFirstCol == j) {
                    numbers[i][j] = 2;
                }
                if (randSecondRow == i && randSecondCol == j) {
                    numbers[i][j] = 4;
                }
            }

        }


    }

    //restart
    public void restart(){
        System.out.println("Restart run");
        for (int[] number : numbers) {
            Arrays.fill(number, 0);
        }
//        initialNumbers();
        fakeInitialNumbers();
        printNumber();
    }

    //todo: finish the method of four direction moving.
    public void moveRight() {
        storeTempArray();
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                if (j + 1 == numbers.length) {
                    break;
                } else {
                    numbers[i][j + 1] += numbers[i][j];
                    numbers[i][j] = 0;
                }
            }
        }
        printNumber();

        printTempNumber();
    }

    public void moveLeft() {
        storeTempArray();

        for (int i = 0; i < numbers.length; i++) {
            for (int j = numbers.length - 1; j >= 0; j--) {
                if (j - 1 < 0) {
                    break;
                } else {
                    numbers[i][j - 1] += numbers[i][j];
                    numbers[i][j] = 0;
                }
            }
        }
        printNumber();
        printTempNumber();

    }

    public void moveUp() {
        storeTempArray();

        for (int i = 0; i < numbers.length; i++) {
            for (int j = numbers.length - 1; j >= 0; j--) {
                if (j - 1 < 0) {
                    break;
                } else {
                    numbers[j - 1][i] += numbers[j][i];
                    numbers[j][i] = 0;
                }
            }
            // numbers[0][i] += numbers[1][i];
            //numbers[1][i] = 0;
        }
        printNumber();

    }

    public void moveDown() {
        storeTempArray();

        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                if (j + 1 == numbers.length) {
                    break;
                } else {
                    numbers[j + 1][i] += numbers[j][i];
                    numbers[j][i] = 0;
                }
            }
        }
        printNumber();

    }


    public int getNumber(int i, int j) {
        return numbers[i][j];
    }

    public void printNumber() {
        for (int[] line : numbers) {
            System.out.println(Arrays.toString(line));
        }
    }

    public void printTempNumber() {
        for (int[] line : tempNumbers) {
            System.out.println(Arrays.toString(line));
        }
    }

    public void redo(){
        for (int i = 0; i < tempNumbers.length; i++) {
            for (int j = 0; j < tempNumbers.length; j++) {
                numbers[i][j] = tempNumbers[i][j];
            }
        }
    }

    public void storeTempArray(){
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                tempNumbers[i][j] = numbers[i][j];
            }
        }
    }
}
