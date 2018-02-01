package com.example.libgomoku;

/**
 * Created by judah on 2/1/2018.
 */

public class Interface {
    public void printboard(Board board){
        int i, j,count;
        count = 0;
        for (i = 0; i < board.boardSize; i++){
            for (j = 0; j < board.boardSize; j++){
                System.out.print(Integer.toString(board.squares[count].state));
                count ++;
            }
            System.out.println();
        }
    }
}
