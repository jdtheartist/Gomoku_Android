package com.example.libgomoku;

public class Maingame {



    public static void main(String[] args) {
        System.out.println("Java project");
        Board board;
        board = new Board();
        Interface ui;
        ui = new Interface();
        while (1 == 1){
            board = mainloop(board, ui);
        }
    }

    public static Board mainloop(Board board,Interface ui){
        ui.printboard(board);
        System.out.println("");
        return board;
    }
}
