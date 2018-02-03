package com.example.libgomoku;


/**
 * Created by judah on 2/1/2018.
 */

public class Board {
    int boardSize = 15;
    Square squares[] = new Square[boardSize * boardSize];
    int[][] DIRECTIONS = new int[][]{
            { 0, 1},
            { 1, 1},
            { 1, 0},
            { 1, -1},
            { 0, -1},
            { -1, -1},
            { -1, 0},
            { -1, 1}
    };

    public Board() {

        int x, y, count;
        count = 0;

        for (x = 0; x < boardSize; x++) {
            for (y = 0; y < boardSize; y++) {
                squares[count] = new Square(x, y);
                count++;
            }
        }

    }
    public int getSquareState(int x, int y){
        for(Square square: squares) {
            if (square.getX() == x && square.getY() == y)
                return square.getState();
        }
        return -1;
    }

    public boolean setSquare (int x, int y, int player){
        int position = boardSize * y + x; //Get position of a square in list from its x and y coordinates
        squares[position].setState(player); //Set the state of that square to be occupied by player

        //Check if valid
        return true;
    }


    public int checkWin(int lastX,int lastY) {
        int checkState = getSquareState(lastX,lastY);

        for (int count = 0; count < 8; count++){

            int checkX = lastX + DIRECTIONS[count][0];
            int checkY = lastY + DIRECTIONS[count][1];

            if (getSquareState(checkX, checkY) == checkState){
                int checkDirection =count;
                int rowLength = 1;

                while (getSquareState(checkX,checkY) == checkState && rowLength < 5){
                    rowLength ++;
                    checkX = checkX + DIRECTIONS[count][0];
                    checkY = checkY + DIRECTIONS[count][1];
                    System.out.println("Count: " + Integer.toString(count));
                    System.out.println("Last X: " + Integer.toString(lastX));
                    System.out.println("Last y: " + Integer.toString(lastY));
                    System.out.println("new X: " + Integer.toString(lastX));
                    System.out.println("new Y: " + Integer.toString(lastY));

                }

                if (rowLength == 5)
                    return checkState;
            }
        }
        return 0;
    }
}

class Square {
    private int x, y, state;

    public Square(int xx,int yy) {
        x = xx;
        y = yy;
        state = 0;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getState(){
        return state;
    }

    public void setState(int newstate){
        state = newstate;
    }

}

