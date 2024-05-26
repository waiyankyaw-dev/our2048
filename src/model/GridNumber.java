package model;

import java.io.FileWriter;
import java.io.IOException;
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
        this.initialNumbers();   //need to create a spawn
    }

    //
    public void randomNextTile() {
        int i = random.nextInt(X_COUNT);
        int j = random.nextInt(X_COUNT);
        while (numbers[i][j] != 0) {
            i = random.nextInt(X_COUNT);
            j = random.nextInt(X_COUNT);
        }
        numbers[i][j] = random.nextInt(10) < 9 ? 2 : 4; // 90% chance to get 2


//        for (int i = 0; i < numbers.length; i++) {
//            for (int j = 0; j < numbers[i].length; j++) {
//                //only spawn must come out form the tile having 0 value
//                if (numbers[i][j] == 0) {
//                    numbers[i][j] = random.nextInt(10) < 9 ? 2 : 4; // 90% chance to get 2
//                    break;
//                }
//            }
//        }
    }

//    public void fakeInitialNumbers() {
//
//        for (int i = 0; i < numbers.length; i++) {
//            for (int j = 0; j < numbers[i].length; j++) {
//                int powerNumber = random.nextInt(4);
//                if(powerNumber == 0){
//                    powerNumber++;
//                }
//                numbers[i][j] = random.nextInt(2) == 0 ? (int) Math.pow(2, powerNumber) : 0;
//            }
//
//        }
//    }

    public void initialNumbers() {
        int randFirstRow = random.nextInt(X_COUNT);
        int randFirstCol = random.nextInt(X_COUNT);
        int randSecondRow = random.nextInt(X_COUNT);
        int randSecondCol = random.nextInt(X_COUNT);

        //second location must not equal to first location
        while (randSecondRow == randFirstRow && randSecondCol == randFirstCol) {
            randSecondRow = random.nextInt(X_COUNT);
            randSecondCol = random.nextInt(X_COUNT);
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
    public void restart() {
        System.out.println("Restart run");
        for (int[] number : numbers) {
            Arrays.fill(number, 0);
        }
        initialNumbers();
//        fakeInitialNumbers();
        printNumber();
    }

    //todo: finish the method of four direction moving.
    public void moveRight() {
        storeTempArray();
        for (int i = 0; i < numbers.length; i++) {
            for (int j = numbers.length - 1; j > 0; j--) {
                for (int k = numbers.length - 1; k >= 0; k--) {//moving 0s to the left before starting to add the numbers
                    if (k - 1 < 0) {//stacking
                        // System.out.println('1');
                        break;
                    }
                    if (numbers[i][k] == 0) {
                        numbers[i][k] += numbers[i][k - 1];
                        numbers[i][k - 1] = 0;
                    }
                }//end for k
            }//end for j

        } //end for i

        for (int i = 0; i < numbers.length; i++) {
            for (int j = numbers.length - 1; j > 0; j--) {
                if (j - 1 < 0) {//fusing
                    // System.out.println('1');
                    break;
                } else if (numbers[i][j - 1] == numbers[i][j]) {//&& numbers[i][k]!=0 && numbers[i][k-1]!=0
                    numbers[i][j] += numbers[i][j - 1];
                    numbers[i][j - 1] = 0;
                    // System.out.println('3');}*/
                }


            }//end for j
            for (int j = numbers.length - 1; j > 0; j--) {
                if (numbers[i][j] == 0) {
                    numbers[i][j] += numbers[i][j - 1];
                    numbers[i][j - 1] = 0;
                }
            }

        }//end for i
        if (checkBoardFull()) {
            randomNextTile();
        }
        printNumber();
    }//end method

    public void moveLeft() {
        storeTempArray();
        //sorting
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {

                for (int k = 0; k < numbers.length; k++) {//moving 0s to the left before starting to add the numbers
                    if (k + 1 == numbers.length) {
                        break;
                    } else if (numbers[i][k] == 0) {
                        numbers[i][k] += numbers[i][k + 1];
                        numbers[i][k + 1] = 0;
                    }//end if
                }//end for k

                //adding the numbers
            }//end for j

        }//end for i

        //fusing
        for (int i = 0; i < numbers.length; i++) {
            for (int k = 0; k < numbers.length; k++) {
                if (k + 1 == numbers.length) {
                    break;
                } else if (numbers[i][k + 1] == numbers[i][k]) {
                    numbers[i][k] += numbers[i][k + 1];
                    numbers[i][k + 1] = 0;//end for i
                }
            }//end for k
            for (int j = 0; j < numbers.length; j++) {
                if (j + 1 == numbers.length) {
                    break;
                }
                if (numbers[i][j] == 0) {
                    numbers[i][j] += numbers[i][j + 1];
                    numbers[i][j + 1] = 0;
                }//end if
            }//end for k

        }//end for i
        if (checkBoardFull()) {
            randomNextTile();
        }
        printNumber();
    }//end method

    public void moveUp() {
        storeTempArray();
        //sorting
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {

                for (int k = 0; k < numbers.length; k++) {//for loop for shifting 0s
                    if (k + 1 == numbers.length) {
                        break;
                    } else if (numbers[k][i] == 0) {
                        numbers[k][i] += numbers[k + 1][i];
                        numbers[k + 1][i] = 0;
                    }//end if
                }//end for k
            }
        }
//fusing
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                //for loop for adding the numbers or not
                if (j + 1 == numbers.length) {
                    break;
                } else if (numbers[j][i] == numbers[j + 1][i]) {
                    numbers[j][i] += numbers[j + 1][i];
                    numbers[j + 1][i] = 0;
                }
            }//end for j
            for (int j = 0; j < numbers.length; j++) {
                if (j + 1 == numbers.length) {
                    break;
                }
                if (numbers[j][i] == 0) {
                    numbers[j][i] += numbers[j + 1][i];
                    numbers[j + 1][i] = 0;
                }//end if
            }
            for (int j = 0; j < numbers.length; j++) {

                for (int k = 0; k < numbers.length; k++) {//for loop for shifting 0s
                    if (k + 1 == numbers.length) {
                        break;
                    } else if (numbers[k][i] == 0) {
                        numbers[k][i] += numbers[k + 1][i];
                        numbers[k + 1][i] = 0;
                    }//end if
                }//end for k
            }

        }//end for i
        if (checkBoardFull()) {
            randomNextTile();
        }
        printNumber();
    }//end method

    public void moveDown() {
        storeTempArray();
        for (int i = 0; i < numbers.length; i++) {
            for (int j = numbers.length - 1; j >= 0; j--) {
                for (int k = numbers.length - 1; k >= 0; k--) {
                    //for loop for adding the numbers or not
                    if (k - 1 < 0) {
                        break;
                    } else if (numbers[k][i] == 0) {
                        numbers[k][i] += numbers[k - 1][i];
                        numbers[k - 1][i] = 0;
                    }
                }//end for k

            }////end for j
        }//end for i

        //fusing
        for (int i = 0; i < numbers.length; i++) {
            for (int j = numbers.length - 1; j >= 0; j--) {
                if (j - 1 < 0) {
                    break;
                } else if (numbers[j][i] == numbers[j - 1][i]) {
                    numbers[j][i] += numbers[j - 1][i];
                    numbers[j - 1][i] = 0;
                }//end if
            }//end for j
            for (int j = numbers.length - 1; j >= 0; j--) {
                if (j - 1 < 0) {
                    break;
                }
                if (numbers[j][i] == 0) {
                    numbers[j][i] += numbers[j - 1][i];
                    numbers[j - 1][i] = 0;
                }
            }

        }//end for i

        if (checkBoardFull()) {
            randomNextTile();
        }
        printNumber();
    }

    public boolean checkBoardFull() {
        for (int i = 0; i < tempNumbers.length; i++) {
            for (int j = 0; j < tempNumbers.length; j++) {
                if (numbers[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
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

    public void redo() {
        for (int i = 0; i < tempNumbers.length; i++) {
            for (int j = 0; j < tempNumbers.length; j++) {
                numbers[i][j] = tempNumbers[i][j];
            }
        }
        printNumber();
    }

    //saveFile
    public void save(String filepath) {
        try {
            FileWriter writer = new FileWriter(filepath + ".txt", true);
            for (int i = 0; i < numbers.length; i++) {
                for (int j = 0; j < numbers.length; j++) {
                    writer.write(numbers[i][j] + "-");
                    if (i == numbers.length - 1 && j == numbers.length - 1) {
                        writer.write("" + numbers[i][j]);
                    }

//                    if (i == numbers.length - 1 && j == numbers.length - 1)
//                        writer.write("" + numbers[i][j]);
//                    else writer.write(numbers[i][j] + "-");
                }
            }
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    //loadfile
    public void load() {

    }

    public void storeTempArray() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                tempNumbers[i][j] = numbers[i][j];
            }
        }
    }
}
