package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class GridNumber {
    private final int X_COUNT;   //row
    private final int Y_COUNT;   //column

    private int[][] numbers;     //boards  4*4

    private int YourScore=0;
    private int YourHighScore;
    //private int[] WinScores = {16, 512, 1024, 2048, 4096};
    private int WinScore = 16;

    private int[][] tempNumbers;
//    private boolean[] LOSS_CHECK= {true,true,true};
    private boolean[] LOSS_CHECK_inside;
    private boolean Lost = false;

    int steps;



    static Random random = new Random();

    public GridNumber(int xCount, int yCount) {
        this.X_COUNT = xCount;
        this.Y_COUNT = yCount;
        this.numbers = new int[this.X_COUNT][this.Y_COUNT];
        this.tempNumbers = new int[this.X_COUNT][this.Y_COUNT];

        this.LOSS_CHECK_inside = new boolean[xCount];

        for(int i = 0; i<xCount; i++){
            LOSS_CHECK_inside[i] = true;
        }

        //call method
//        this.initialNumbers();   //need to create a spawn

        this.load("final");
        GameOver();
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
        this.steps = 0;
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


    //todo: finish the method of four direction moving.
//    public void moveRight() {
//        //for sorting/colliding
//        int count1=0;
//        int count2=0;
//        int count3=0;
//
//        for (int i = 0; i < numbers.length; i++) {
//            for(int j =numbers.length-1; j > 0; j--) {
//                for (int k = numbers.length - 1; k >= 0; k--) {//moving 0s to the left before starting to add the numbers
//                    if (k - 1 < 0) {//stacking
//                        // System.out.println('1');
//                        break;
//                    }
//                    if (numbers[i][k] == 0) {
//                        numbers[i][k] += numbers[i][k - 1];
//                        numbers[i][k - 1] = 0;
//                    }else{LOSS_CHECK_inside[k] = false;
//                   // System.out.println("----------------------------------------------------------------------");
//                    }
//                }//end for k
//            }//end for j
//            for (int o=0; o < LOSS_CHECK_inside.length; i++){
//                System.out.println(LOSS_CHECK_inside[i]);
//            }
//            for (int m = 0; m < numbers.length; m++) {
//                if(LOSS_CHECK_inside[m] ==false){
//                    count1++;
//                }
//            }
//
//            printNumber();
//        } //end for i
//        if(count1==numbers.length-1){
//            LOSS_CHECK[0] = false;
//        }
//
//
//        //for fusing
//        for (int i = 0; i < numbers.length; i++) {
//            int TempScore = 0;
//            for (int j = numbers.length - 1; j > 0; j--) {
//                if (j - 1 < 0) {//fusing
//                    // System.out.println('1');
//                    break;
//                } else if (numbers[i][j - 1] == numbers[i][j]) {//&& numbers[i][k]!=0 && numbers[i][k-1]!=0
//                    numbers[i][j] += numbers[i][j - 1];
//                    numbers[i][j - 1] = 0;
//                    // if(TempScore > YourScore){YourScore =TempScore;}
//                }else{LOSS_CHECK_inside[j] = false;}
//                //changing temp score
//                if(TempScore < numbers[i][j]){
//                    TempScore =numbers[i][j];
//                    System.out.println("TEMPSCORE-----------"+TempScore+"-----------");
//                }
//            }//end for j
//
//            for (int m = 0; m < numbers.length; m++) {
//                if(LOSS_CHECK_inside[m] ==false){
//                    count2++;
//                }}
//
//            if(TempScore >= YourScore){YourScore =TempScore;}//changing the player score
//            if(YourScore >= YourHighScore){YourHighScore =YourScore;}//changing the players high score
//
//
//            //stacking after fusing
//            for (int j = numbers.length - 1; j > 0; j--) {
//                if (numbers[i][j] == 0) {
//                    numbers[i][j] += numbers[i][j - 1];
//                    numbers[i][j - 1] = 0;}
//                else{LOSS_CHECK_inside[j] = false;}//endif
//            }//end for j
//            for (int m = 0; m < numbers.length; m++) {
//                if(LOSS_CHECK_inside[m] ==false){
//                    count3++;
//                }}
//        }//end for i
//
//        if(count2==numbers.length-1){
//            LOSS_CHECK[1] = false;
//        }else if(count3==numbers.length-1){
//            LOSS_CHECK[3] = false;
//        }
//
//        //to check if lost
//        int count_losses=0;
//        for(int i= 0; i< LOSS_CHECK.length;i++) {
//
//            if (LOSS_CHECK[i]==false){count_losses++;}
//            if(count_losses ==3){
//                Lost =true;
//                System.out.println("------YOU LOOSE------");
//                break;
//            }
//        }
//        if(YourScore >= WinScore && Lost==false){
//            System.out.println("--------YOU WIN---------");
//
//        }
//        System.out.println("YOUR SCORE-----------"+YourScore+"-----------");
//        System.out.println("YOUR HIGH SCORE-----------"+YourHighScore+"-----------");
//    }//end method
//    public void moveLeft() {
//        //sorting
//        for (int i = 0; i < numbers.length; i++) {
//            for (int j =0 ; j<numbers.length; j++) {
//                for (int k =0 ; k <numbers.length; k++) {//moving 0s to the left before starting to add the numbers
//                    if (k + 1 == numbers.length) {
//                        break;
//                    } else if(numbers[i][k]==0) {
//                        numbers[i][k] += numbers[i][k+1];
//                        numbers[i][k+1] = 0;
//                    }else{LOSS_CHECK[0] = false;}//end if
//                }//end for k
//
//                //adding the numbers
//            }//end for j
//            printNumber();
//        }//end for i
//
//        //fusing
//        for (int i = 0; i < numbers.length; i++) {
//            int TempScore =0;
//            for (int k =0 ; k <numbers.length; k++) {
//                if(k +1== numbers.length){
//                    break;
//                }
//                else if (numbers[i][k + 1] == numbers[i][k]) {
//                    numbers[i][k] += numbers[i][k + 1];
//                    numbers[i][k + 1] = 0;//end for i
//                }
//                //Setting the temp score
//                if(TempScore < numbers[i][k]){
//                    TempScore =numbers[i][k];
//                    System.out.println("TEMPSCORE-----------"+TempScore+"-----------");
//                }else{LOSS_CHECK[1] = false;}
//            }//end for k
//
//            //changing the player score
//            if(TempScore >= YourScore){YourScore =TempScore;}
//            if(YourScore >= YourHighScore){YourHighScore =YourScore;}//changing the players high score
//
//            for (int j =0 ; j<numbers.length; j++) {
//                if(j+1==numbers.length ){break;}
//                if (numbers[i][j] == 0) {
//                    numbers[i][j] += numbers[i][j + 1];
//                    numbers[i][j + 1] = 0;
//                }else{LOSS_CHECK[2] = false;}//end if
//            }//end for k
//        }//end for i
//
//        //to check if lost
//        int count_losses=0;
//        for(int i= 0; i< LOSS_CHECK.length;i++) {
//
//            if (LOSS_CHECK[i]==false){count_losses++;}
//            if(count_losses ==3){
//                Lost =true;
//                System.out.println("------YOU LOOSE------");
//                break;
//            }
//        }
//        if(YourScore >= WinScore && Lost == false){
//            System.out.println("--------YOU WIN---------");}
//        System.out.println("YOUR SCORE-----------"+YourScore+"-----------");
//        System.out.println("YOUR HIGH SCORE-----------"+YourHighScore+"-----------");
//    }//end method
//
//    public void moveUp() {
//        //sorting
//        for (int i = 0; i < numbers.length; i++) {
//            for (int j=0; j < numbers.length; j++) {
//
//                for (int k = 0; k < numbers.length; k++) {//for loop for shifting 0s
//                    if (k + 1 == numbers.length) {
//                        break;
//                    } else if (numbers[k][i] == 0) {
//                        numbers[k][i] += numbers[k + 1][i];
//                        numbers[k + 1][i] = 0;
//                    }else{LOSS_CHECK[0] = false;}//end if//end if
//                }//end for k
//            }
//        }
////fusing
//        for (int i = 0; i < numbers.length; i++) {
//            int TempScore =0;
//            for (int j=0; j < numbers.length; j++) {
//                //for loop for adding the numbers or not
//                if (j +1  == numbers.length) {
//                    break;
//                }else if(numbers[j][i] == numbers[j+1][i]){
//                    numbers[j ][i] += numbers[j+1][i];
//                    numbers[j+1][i] = 0;}
//                //Setting the temp score
//                if(TempScore <= numbers[j][i]){
//                    TempScore =numbers[j][i];
//                    System.out.println("TEMPSCORE-----------"+TempScore+"-----------");
//                }
//                else{LOSS_CHECK[1] = false;}//end if
//            }//end for j
//
//            if(TempScore >= YourScore){YourScore =TempScore;}
//            if(YourScore >= YourHighScore){YourHighScore =YourScore;}//changing the players high score
//
//            for (int j=0; j < numbers.length; j++) {
//                for (int k = 0; k < numbers.length; k++) {//for loop for shifting 0s
//                    if (k + 1 == numbers.length) {
//                        break;
//                    } else if (numbers[k][i] == 0) {
//                        numbers[k][i] += numbers[k + 1][i];
//                        numbers[k + 1][i] = 0;
//                    }else{LOSS_CHECK[2] = false;}//end if//end if
//                }//end for k
//            }
//            printNumber();
//        }//end for i
//
//        //to check if lost
//        int count_losses=0;
//        for(int i= 0; i< LOSS_CHECK.length;i++) {
//
//            if (LOSS_CHECK[i]==false){count_losses++;}
//            if(count_losses ==3){
//                Lost =true;
//                System.out.println("------YOU LOOSE------");
//                break;
//            }
//        }
//        if(YourScore >= WinScore && Lost==false){
//            System.out.println("--------YOU WIN---------");}
//        System.out.println("YOUR SCORE-----------"+YourScore+"-----------");
//        System.out.println("YOUR HIGH SCORE-----------"+YourHighScore+"-----------");
//    }//end method
//
//    public void moveDown() {
//        for (int i = 0; i < numbers.length; i++) {
//            for (int j = numbers.length - 1; j >= 0; j--) {
//                for (int k = numbers.length - 1; k >= 0; k--) {
//                    //for loop for adding the numbers or not
//                    if (k - 1 < 0) {
//                        break;
//                    } else if (numbers[k][i] == 0) {
//                        numbers[k][i] += numbers[k - 1][i];
//                        numbers[k - 1][i] = 0;
//                    }else{LOSS_CHECK[0] = false;}//end if//end if
//                }//end for k
//
//            }////end for j
//        }//end for i
//
//        //fusing
//        for (int i = 0; i < numbers.length; i++) {
//            int TempScore = 0;
//            for (int j = numbers.length - 1; j >= 0; j--) {
//                if (j - 1 <0) {
//                    break;
//                } else if(numbers[j][i] == numbers[j-1][i]){
//                    numbers[j][i] += numbers[j-1][i];
//                    numbers[j-1][i] = 0;
//                }else{LOSS_CHECK[1] = false;}//end if//end if//end if
//
//                //Setting the temp score
//                if(TempScore <= numbers[j][i]){
//                    TempScore =numbers[j][i];
//                    System.out.println("TEMPSCORE-----------"+TempScore+"-----------");}
//            }//end for j
//
//            //setting up YOUR SCORE
//            if(TempScore >= YourScore){YourScore =TempScore;}
//            if(YourScore >= YourHighScore){YourHighScore =YourScore;}//changing the players high score
//
//            for (int j = numbers.length - 1; j >= 0; j--) {
//                if(j-1<0){break;}
//                if (numbers[j][i] == 0) {
//                    numbers[j][i] += numbers[j - 1][i];
//                    numbers[j - 1][i] = 0;}else{LOSS_CHECK[2] = false;}//end if//end if
//            }//end for j
//            printNumber();
//        }//end for i
//        //to check if lost
//        int count_losses=0;
//        for(int i= 0; i< LOSS_CHECK.length;i++) {
//
//            if (LOSS_CHECK[i]==false){count_losses++;}
//            if(count_losses ==3){
//                Lost =true;
//                System.out.println("------YOU LOOSE------");
//                break;
//            }
//        }
//        if(YourScore >= WinScore&& Lost==false){
//            System.out.println("--------YOU WIN---------");}
//        System.out.println("YOUR SCORE-----------"+YourScore+"-----------");
//        System.out.println("YOUR HIGH SCORE-----------"+YourHighScore+"-----------");
//    }//enf method


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
                    if (j == numbers.length - 1)
                        writer.write( numbers[i][j]+ "\n");
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
            FileReader fr = new FileReader(filepath + ".txt");
            BufferedReader br = new BufferedReader(fr); //read line by line

                for(int i =0; i < numbers.length ; i++) {
                    String[] arrayLine = br.readLine().split("-");
                    System.out.println(arrayLine[i]);
                    for(int j=0; j < numbers.length; j++) {
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

    public int getSteps(){
        return steps;
    }

//    public void GameOver() {
//
//    }
    public void GameOver() {

        for(int i=0; i<numbers.length; i++){
            int count1=0;
            for(int j=0; j<numbers.length;j++){
                if(j+1==numbers.length){break;}
                if (numbers[i][j] == 0) {
                    break;
                }else if(numbers[i][j + 1] == numbers[i][j]) {break;
                }else{count1++;}//end for i

            }//end for j
            if(count1==numbers.length-1){
                LOSS_CHECK_inside[i] = false;
            }
        }//end for i
        for(int p=0; p<4 ;p++){
            System.out.println(LOSS_CHECK_inside[p]);
        }
        int count_losses=0;
        for(int i= 0; i< numbers.length;i++) {

            if (LOSS_CHECK_inside[i]==false){count_losses++;}
            if(count_losses ==4){
                Lost =true;
                System.out.println("------YOU LOOSE------");
                break;
            }
        }
    }//end method
}
