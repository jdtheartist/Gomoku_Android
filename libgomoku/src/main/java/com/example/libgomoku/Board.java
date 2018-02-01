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
        /*int i;
        for (i = 0; i < boardSize * boardSize; i++) {
            System.out.println(Integer.toString(squares[i].getX()));
        }*/
    }


}

class Square {
    int x, y, state;

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

