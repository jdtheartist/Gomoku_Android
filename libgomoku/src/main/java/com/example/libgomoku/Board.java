package com.example.libgomoku;


/**
 * Created by judah on 2/1/2018.
 */

public class Board {
    int boardSize = 5;
    Square squares[] = new Square[boardSize * boardSize];

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

