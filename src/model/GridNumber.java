package model;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class GridNumber {
    private final int X_COUNT;   //row
    private final int Y_COUNT;   //column

    private int[][] numbers;     //boards  4*4

    private int YourScore = 0;
    private int YourHighScore = 0;
    //private int[] WinScores = {16, 512, 1024, 2048, 4096};
    private int score = 0;
    private int redoScore = 0;
    private int higherScore = 0;

    long startTime = 0;
    long endTime = 0;

    long elapsedTime = 0;

    private int[][] tempNumbers;
    //    private boolean[] LOSS_CHECK= {true,true,true};
    private boolean[] LOSS_CHECK_inside;
    private boolean[] LOSS_CHECK;

    private boolean Lost = false;

    private int winScore;
    int steps;


    static Random random = new Random();

    private String username;
    private int size;

    private boolean gameOver;
    private boolean gameWin;

    public GridNumber(int xCount, int yCount, String username, int size, int winScore) {
        this.winScore = winScore;
//        System.out.println(winScore);
//        System.out.println(size);
        this.size = size;
        this.username = username;
        System.out.println(username);
        this.X_COUNT = xCount;
        this.Y_COUNT = yCount;
        this.numbers = new int[this.X_COUNT][this.Y_COUNT];
        this.tempNumbers = new int[this.X_COUNT][this.Y_COUNT];

        this.LOSS_CHECK_inside = new boolean[xCount];
        this.LOSS_CHECK = new boolean[xCount];
        this.gameOver = false;
        this.gameWin = false;

        //call method
        this.initialNumbers();   //need to create a spawn

//        this.load("aaaaa");
//        GameOver();
        this.steps = 0;
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
        startTime = System.currentTimeMillis();
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
        this.steps = 0;
        this.score = 0;
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
        redoScore = score;
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
                    int highCombinedNumber = numbers[i][j] += numbers[i][j - 1];
                    numbers[i][j - 1] = 0;
                    score += highCombinedNumber;
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
        System.out.println("WaiScore: " + score + "-----------");

        GameWin();
        GameOver();

        if (checkBoardFull()) {
            randomNextTile();
        }
        printNumber();
    }//end method

    public void moveLeft() {
        storeTempArray();
        redoScore = score;
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
                    int highCombinedNumber = numbers[i][k] += numbers[i][k + 1];
                    numbers[i][k + 1] = 0;//end for i
                    score += highCombinedNumber;
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

        System.out.println("WaiScore: " + score + "-----------");

        GameWin();
        GameOver();

        if (checkBoardFull()) {
            randomNextTile();
        }
        printNumber();
    }//end method

    public void moveUp() {
        storeTempArray();
        redoScore = score;
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
                    int highCombinedNumber = numbers[j][i] += numbers[j + 1][i];
                    numbers[j + 1][i] = 0;
                    score += highCombinedNumber;
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

        System.out.println("WaiScore: " + score + "-----------");

        GameWin();
        GameOver();

        if (checkBoardFull()) {
            randomNextTile();
        }
        printNumber();
    }//end method

    public void moveDown() {
        storeTempArray();
        redoScore = score;
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
                    int highCombinedNumber = numbers[j][i] += numbers[j - 1][i];
                    numbers[j - 1][i] = 0;
                    score += highCombinedNumber;
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


        System.out.println("WaiScore: " + score + "-----------");

        GameWin();
        GameOver();

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
        this.steps--;
        score = redoScore;
        for (int i = 0; i < tempNumbers.length; i++) {
            for (int j = 0; j < tempNumbers.length; j++) {
                numbers[i][j] = tempNumbers[i][j];
            }
        }
        printNumber();
    }

    //saveFile
    public void save(String filepath) {
        this.endTime = System.currentTimeMillis();
         elapsedTime = this.endTime - this.startTime;
        long minutes = (elapsedTime/1000) / 60;
        long seconds = (elapsedTime/1000) % 60;
        System.out.println(minutes +"minutes "+ seconds + "seconds");

//        if(score> higherScore){
//            higherScore = score;
//        }else{
//
//        }

        File f1 = new File(username);
        boolean bool = f1.mkdir();
        if(bool){
            System.out.println("Folder is created successfully");
        }else{
            System.out.println("Error Found!");
        }

        File f2 = new File(username+"//"+String.valueOf(size));
        boolean bool2 = f2.mkdir();
        if(bool2){
            System.out.println("Folder is created successfully");
        }else{
            System.out.println("Error Found!");
        }


        try {
            FileWriter writer = new FileWriter(username+"\\"+ size+ "\\" +filepath+".txt");
            writer.write(score +"\n");
            writer.write( elapsedTime+"\n");
            for (int i = 0; i < numbers.length; i++) {
                for (int j = 0; j < numbers.length; j++) {
                    if (j == numbers.length - 1)
                        writer.write(numbers[i][j] + "\n");
                    else writer.write(numbers[i][j] + "-");
                }
            }
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    //loadfile
    public void load(String filepath) {
        try {
            FileReader fr = new FileReader(username+"\\"+ size+"\\" +filepath + ".txt");
            BufferedReader br = new BufferedReader(fr); //read line by line
            score = Integer.parseInt(br.readLine());
            elapsedTime = Integer.parseInt(br.readLine());
            for (int i = 0; i < numbers.length; i++) {
                String[] arrayLine = br.readLine().split("-");
//                System.out.println(arrayLine[i]);
                for (int j = 0; j < numbers.length; j++) {
//                        System.out.println(Integer.parseInt(arrayLine[i]));
                    numbers[i][j] = Integer.parseInt(arrayLine[j]);
                }
            }

            fr.close();
            printNumber();
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    public void storeTempArray() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                tempNumbers[i][j] = numbers[i][j];
            }
        }
    }

    public int getSteps() {
        return steps;
    }


    public void GameOver() {
        for (int i = 0; i < 4; i++) {
            LOSS_CHECK_inside[i] = true;
        }
        for (int i = 0; i < 4; i++) {
            LOSS_CHECK[i] = true;
        }
        //horizontal check
        for (int i = 0; i < numbers.length; i++) {
            int count1 = 0;
            for (int j = 0; j < numbers.length; j++) {
                if (j + 1 == numbers.length) {
                    break;
                }
                if (numbers[i][j] == 0) {
                    break;
                } else if (numbers[i][j + 1] == numbers[i][j]) {
                    break;
                } else {
                    count1++;
                }//end for i

            }//end for j
            if (count1 == numbers.length - 1) {
                LOSS_CHECK_inside[i] = false;
            }
        }//end for i


        //vertical check
        for (int i = 0; i < numbers.length; i++) {
            int count1 = 0;
            for (int j = 0; j < numbers.length; j++) {
                if (j + 1 == numbers.length) {
                    break;
                }
                if (numbers[j][i] == 0) {
                    break;
                } else if (numbers[j + 1][i] == numbers[j][i]) {
                    break;
                } else {
                    count1++;
                }//end for i

            }//end for j
            if (count1 == numbers.length - 1) {
                LOSS_CHECK[i] = false;
            }
        }

        int count_lossesV = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (LOSS_CHECK[i] == false) {
                count_lossesV++;
            }
//            System.out.println(count_lossesV);
        }

        int count_lossesH = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (LOSS_CHECK_inside[i] == false) {
                count_lossesH++;
            }
        }//end for
// && count_lossesH==4

        if (count_lossesV == 4 && count_lossesH == 4) {
            Lost = true;
            this.gameWin = true;
            System.out.println("------YOU LOOSE------");

        }
//        for (int p=0; p < 4; p++){
//            System.out.println(LOSS_CHECK_inside[p]);
//        }
//        for (int p=0; p < 4; p++){
//            System.out.println(LOSS_CHECK_inside[p]);
//        }


    }//end method

    //gamewin
    public void GameWin() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                if (numbers[i][j] == winScore) {
                    System.out.println("You Won-wai");
                    this.gameWin = true;
                    break;
                }
            }
        }

    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWin() {
        return gameWin;
    }
}
