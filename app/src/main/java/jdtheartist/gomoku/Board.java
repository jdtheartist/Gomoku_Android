package jdtheartist.gomoku;


/**
 * Created by judah on 2/1/2018.
 */

public class Board {
    int boardSize = 10;
    int left, top, right, bottom;
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

    public Board(int left,int top,int right,int bottom) {

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

        int a, b, count;
        count = 0;

        for (a = 0; a < boardSize; a++) {
            for (b = 0; b < boardSize; b++) {
                squares[count] = new Square(b, a);
                count++;
            }
        }

    }

    public int checkWin(int lastX,int lastY) {
        int checkState = getSquareState(lastX,lastY);

        for (int count = 0; count < 8; count++){

            System.out.println("Count: " + Integer.toString(count));

            int checkX = lastX + DIRECTIONS[count][0];
            int checkY = lastY + DIRECTIONS[count][1];

            if (getSquareState(checkX, checkY) == checkState){
                int rowLength = 1;

                while (getSquareState(checkX,checkY) == checkState && rowLength < 5){
                    rowLength ++;
                    checkX = checkX + DIRECTIONS[count][0];
                    checkY = checkY + DIRECTIONS[count][1];

                    System.out.println("Last X: " + Integer.toString(checkX));
                    System.out.println("Last Y: " + Integer.toString(checkY));


                }

                if (rowLength == 5)
                    return checkState;
            }
        }
        return 0;
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
        if (position<squares.length && position >= 0){
            squares[position].setState(player); //Set the state of that square to be occupied by player
            return true;
        }


        //Check if valid
        return false;
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

