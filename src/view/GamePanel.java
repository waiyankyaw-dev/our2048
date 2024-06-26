package view;

import model.GridNumber;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends ListenerPanel {
    private final int COUNT = 4;
    private GridComponent[][] grids;

    private GridNumber model;
    private JLabel stepLabel;
    private int steps;
    private final int GRID_SIZE;

    private  String username;
    private int grid_size;
    private int winScore;

    public GamePanel(int size, String username, int grid_size, int winScore) {
        this.winScore = winScore;
        this.username = username;
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(new Color(187, 173, 160));
        this.setSize(size, size);


        this.GRID_SIZE = size / COUNT;    //
        this.grids = new GridComponent[COUNT][COUNT];  // for gui view
        this.model = new GridNumber(COUNT, COUNT, username, grid_size, winScore);     // for model
        initialGame();

    }

    public GridNumber getModel() {
        return model;
    }

    public void initialGame() {
        this.steps = 0;
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                grids[i][j] = new GridComponent(i, j, model.getNumber(i, j), this.GRID_SIZE);
                grids[i][j].setLocation(j * GRID_SIZE, i * GRID_SIZE);
                this.add(grids[i][j]);
            }
        }
        model.printNumber();//check the 4*4 numbers in game
        this.repaint();
    }

    public void updateGridsNumber() {
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                grids[i][j].setNumber(model.getNumber(i, j));
            }
        }        repaint();
    }

//    public boolean canCombine(){
//
//    }

    /**
     * Implement the abstract method declared in ListenerPanel.
     * Do move right.
     */
    @Override
    public void doMoveRight() {
        System.out.println("Click VK_RIGHT");
        this.afterMove();
        this.model.moveRight();
        this.updateGridsNumber();
    }

    @Override
    public void doMoveLeft() {
        System.out.println("Click VK_LEFT");
        this.afterMove();
        this.model.moveLeft();
        this.updateGridsNumber();
    }


    public void doMoveUp() {
        System.out.println("Click VK_UP");
        this.afterMove();
        this.model.moveUp();
        this.updateGridsNumber();
    }
    public void doMoveDown() {
        System.out.println("Click VK_DOWN");
        this.afterMove();
        this.model.moveDown();
        this.updateGridsNumber();
    }

    public void afterMove() {
        this.steps++;
        this.stepLabel.setText(String.format("Step: %d", this.steps));
    }


    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }
}
