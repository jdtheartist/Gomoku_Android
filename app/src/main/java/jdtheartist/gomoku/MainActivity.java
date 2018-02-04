package jdtheartist.gomoku;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.*;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity {

    // gameView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);
    }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        gameView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        gameView.pause();
    }

    class GameView extends SurfaceView implements Runnable {

        // This is our thread
        Thread gameThread = null;

        // This is new. We need a SurfaceHolder
        // When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
        SurfaceHolder ourHolder;

        // A boolean which we will set and unset
        // when the game is running- or not.
        volatile boolean playing;

        // A Canvas and a Paint object
        Canvas canvas;
        Paint paint;

        Boolean pressed = false;

        // This variable tracks the game frame rate
        long fps;

        // This is used to help calculate the fps
        private long timeThisFrame;

        // Gomoku board object
        Board board;

        //To keep track of whose turn it is
        int player, numPlayers, winner;

        //Screen width and height
        int height, minx, miny, padding;


        public GameView(Context context) {


            // The next line of code asks the
            // SurfaceView class to set up our object.
            // How kind.
            super(context);

            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();


            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            int height = Resources.getSystem().getDisplayMetrics().heightPixels;
            padding = 40;
            board = new Board(padding, (height / 2 - width / 2) + padding, width - padding, (height / 2 + width / 2) - padding);
            player = 1;
            numPlayers = 2;
            winner = 0;

        }
        public void restart(){
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            int height = Resources.getSystem().getDisplayMetrics().heightPixels;
            padding = 40;
            board = new Board(padding, (height / 2 - width / 2) + padding, width - padding, (height / 2 + width / 2) - padding);
            player = 1;
            numPlayers = 2;
            winner = 0;
        }
        public void run() {
            while (playing) {

                // Capture the current time in milliseconds in startFrameTime
                long startFrameTime = System.currentTimeMillis();

                // Update the frame
                update();

                // Draw the frame
                draw();

                // Calculate the fps this frame
                // We can then use the result to
                // time animations and more.
                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame > 0) {
                    fps = 1000 / timeThisFrame;
                }

            }

        }

        public void update() {
            //Everything that needs to be updated goes in here
        }

        public void draw() {

            // Make sure our drawing surface is valid or we crash
            if (ourHolder.getSurface().isValid()) {
                // Lock the canvas ready to draw
                // Make the drawing surface our canvas object
                canvas = ourHolder.lockCanvas();

                // Draw the background color
                if (pressed)
                    canvas.drawColor(Color.argb(255,  26, 128, 182));
                else
                    canvas.drawColor(Color.rgb( 182, 155, 76));

                //draw the board

                paint.setStrokeWidth(2);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                //canvas.drawRect(board.left, board.top, board.right, board.bottom ,paint);

                paint.setColor(Color.BLACK);
                for (int i = 0; i < board.boardSize; i++){
                    //Sorry for this spaghetti
                    canvas.drawLine(board.left,board.top - i * ((board.top - board.bottom)/(board.boardSize - 1)),board.right, board.top - i * ((board.top - board.bottom)/(board.boardSize - 1)), paint);
                    canvas.drawLine(board.right - i * ((board.right - board.left)/(board.boardSize - 1)),board.top,board.right - i * ((board.right - board.left)/(board.boardSize - 1)), board.bottom, paint);
                }




                //Draw the gomoko pieces

                for (Square square: board.squares){
                    switch(square.getState()){
                        case (1):
                            paint.setColor(Color.rgb(130, 82, 1));//brown
                            break;
                        case (2):
                            paint.setColor(Color.rgb(237,237,237));//white
                            break;
                        default:
                            continue;//leave loop
                    }

                    int x = (int)( (float)board.left + (((float)square.getX())/((float)board.boardSize-1)) * ((float)board.right - (float)board.left));
                    int y = (int)( (float)board.top + (((float)square.getY())/((float)board.boardSize-1)) * ((float)board.bottom - (float)board.top));
                    canvas.drawCircle(x,y,25,paint);

                }

                paint.setColor(Color.argb(255,  249, 129, 0));

                // Make the text a bit bigger
                paint.setTextSize(45);
                paint.setStrokeWidth(2);
                // Display the current fps on the screen
                canvas.drawText("FPS:" + fps, 20, 40, paint);

                //Draw which player it is
                paint.setColor(Color.BLACK);
                paint.setTextSize(100);
                canvas.drawText("Player:" + player, board.right/2, board.top/2, paint);

                // Draw everything to the screen
                // and unlock the drawing surface
                ourHolder.unlockCanvasAndPost(canvas);
            }

        }

        // shutdown our thread.
        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        // If SimpleGameEngine Activity is started theb
        // start our thread.
        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        public void doMove(float x,float y){

            y -= (board.top-padding);
            float height = (board.bottom+padding) - (board.top-padding);
            float width = Resources.getSystem().getDisplayMetrics().widthPixels ;

            int newx = (int)((x/width)*board.boardSize);
            int newy = (int)((y/height)*board.boardSize);

            Log.d("newx", Integer.toString(newx));
            Log.d("newy", Integer.toString(newy));

            board.setSquare(newx,newy,player);

            player = player % numPlayers + 1; //Toggle player

            winner = board.checkWin(newx,newy);

            if (winner > 0){
                restart();
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                // Player has touched the screen
                case MotionEvent.ACTION_DOWN:

                    pressed = true;
                    //board.setSquare(3,3,1);
                    Log.d("Rx", Float.toString(motionEvent.getRawX()));
                    Log.d("Ry", Float.toString(motionEvent.getRawY()));
                    Log.d("x", Float.toString(motionEvent.getX()));
                    Log.d("y", Float.toString(motionEvent.getY()));
                    //if (motionEvent.getRawX() > board.left && motionEvent.getRawX() < board.right && motionEvent.getRawY() > board.top && motionEvent.getRawY() < board.bottom)
                        doMove(motionEvent.getX(),motionEvent.getY());
                    //doMove(motionEvent.getX(),motionEvent.getY());
                    break;

                // Player has removed finger from screen
                case MotionEvent.ACTION_UP:

                    // Set isMoving so Bob does not move
                    pressed = false;
                   // board.setSquare(3,8,2);
                    break;
            }
            return true;
        }
    }
}
