package com.example.libgomoku;

import java.util.Arrays;

/**
 * Created by judah on 2/1/2018.
 */

public class Interface {
    private String p1char = "X";
    private String p2char = "O";

    public void printboard(Board board){
        int i, j;

        char[] chars = new char[board.boardSize*2];
        Arrays.fill(chars,'-');
        String line = new String(chars);
        String str = "|" + line + "|";

        System.out.println(str);

        for (i = 0; i < board.boardSize; i++){
            System.out.print("|");
            for (j = 0; j < board.boardSize; j++){
                switch (board.getSquareState(j, i)){
                    case ( 1): System.out.print(p1char+' '); break;
                    case (-1): System.out.print(p2char+' '); break;
                    case ( 0): System.out.print("* ");
                }
            }
            System.out.println("|");
        }

        System.out.println(str);

    }
}
