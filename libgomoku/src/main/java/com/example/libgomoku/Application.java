package com.example.libgomoku;

import java.util.Scanner;


public class Application {


    public static void main(String[] args) {
        System.out.println("Gomoku project");



        Board board = new Board();

        Interface ui = new Interface();

        Scanner scanner = new Scanner ( System.in );

        mainloop(board, ui,scanner);
    }

    private static void mainloop(Board board,Interface ui, Scanner scanner){
        int player = 1;
        int winner = 0; // 0 means the game has not been won yet; 1 means player won has won....
        int numPlayers = 3;

        String input;

        while (winner == 0){

            System.out.println("It's Player "+ Integer.toString(player) +"'s turn");

            do {
                System.out.print("Enter position x y: ");
                input = scanner.nextLine();
            } while ( !doMove(player,input,board)); // Do move - returns False if move is invalid


            player = player % numPlayers + 1; //Toggle player

            ui.printboard(board); //Print the board

            winner = checkWin(); //Check if game is won ( returns who won )

            System.out.println(""); //Print a new line


        }

        System.out.println("Player "+ Integer.toString(winner) +" has won");

    }

    private static int checkWin() {
        //Check for a winner >>> return 1 for player 1, and 0 for no winner

        return 0;
    }

    private static boolean doMove(int player,String move,Board board){
        //Need to add validity checks
        String[] temp = move.split(" ");

        int x = Integer.parseInt(temp[0]) - 1; //Convert string to int
        int y = Integer.parseInt(temp[1]) - 1; //Convert string to int

        return board.setSquare(x, y, player); //Update square

    }
}
