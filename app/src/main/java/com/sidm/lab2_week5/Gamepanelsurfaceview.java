package com.sidm.lab2_week5;

/**
 * Created by 150576E on 11/24/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.graphics.Canvas;
import android.view.SurfaceView;

import java.io.CharConversionException;

public class Gamepanelsurfaceview extends SurfaceView implements SurfaceHolder.Callback {
    private Gamethread myThread = null; // Thread to control the rendering

    // 1a) Variables used for background rendering
    private Bitmap bg, scaledbg;    // bg is background and
                                    // scaledbg is scaled version of bg
    // 1b) Define Screen width and Screen height as integer
    int screenWidth, screenHeight;

    // 1c) Variables for defining background start and end point
    private short bgX = 0, bgY = 0;

    // Enemies
    //private enemy enemies[] = new enemy[3];
    private enemy Enemy1 = new enemy();

    // Creating Character
    private player Character = new player();

    // Variables for FPS
    public float FPS;
    float deltaTime;
    long dt;

    // Variable for Game State check
    private short GameState;

    //constructor for this GamePanelSurfaceView class
    public Gamepanelsurfaceview (Context context) {
        // Context is the current state of the application/object
        super(context);

        // Adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // 1d) Set information to get screen size
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        // 1e)load the image when this class is being instantiated
        bg = BitmapFactory.decodeResource(getResources(), R.drawable.seatexture);
        scaledbg = Bitmap.createScaledBitmap(bg, screenWidth, screenHeight, true);

        // Things needed for enemy

        Enemy1.enemyImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.virusemoji), screenWidth / 6, screenHeight / 10, true);
        Enemy1.posX = screenWidth / 2.5f;
        Enemy1.posY = screenHeight / 6;
        Enemy1.velX = 0.5f;
        Enemy1.velY = 0;
        Enemy1.timer = 0.f;
        Enemy1.timerChange = 2.0f;
        Enemy1.active = true;


//        enemies[0].posX = screenWidth / 5;
//        enemies[0].posY = screenHeight / 5;
//        enemies[0].active = true;
//
//        enemies[1].active = false;
//        enemies[2].active = false;
//
//        for(int i = 0; i < 3; i++)
//        {
//            enemies[i].enemyImage.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.virusemoji),screenWidth/3, screenHeight/7, true);
//        }
        // Things needed for player
        Character.playerImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.player), screenWidth / 4, screenHeight / 7, true);
        Character.posX = screenWidth / 2.7f;
        Character.posY = screenHeight / 1.2f;
        Character.movePlayer = false;
        Character.firingRate = 1.4f;
        Character.updateFiringTime = 0.f;

        // Create the game loop thread
        myThread = new Gamethread(getHolder(), this);

        // Make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    //must implement inherited abstract methods
    public void surfaceCreated(SurfaceHolder holder){
        // Create the thread
        if (!myThread.isAlive()){
            myThread = new Gamethread(getHolder(), this);
            myThread.startRun(true);
            myThread.start();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder){
        // Destroy the thread
        if (myThread.isAlive()){
            myThread.startRun(false);

        }
        boolean retry = true;
        while (retry) {
            try {
                myThread.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
            }
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    public void RenderGameplay(Canvas canvas) { //edit
        // 2) Re-draw 2nd image after the 1st image ends
        if(canvas == null) { return; }
        canvas.drawBitmap(scaledbg, bgX, bgY, null); // 1st bg image
        canvas.drawBitmap(scaledbg, bgX, bgY - screenHeight, null); // 2nd image

        canvas.drawBitmap(Enemy1.enemyImage, Enemy1.posX, Enemy1.posY, null);
        // Draw Character
        canvas.drawBitmap(Character.playerImage, Character.posX, Character.posY, null); // location of the ship

        // Bonus) To print FPS on the screen
        RenderTextOnScreen(canvas, "FPS : " + FPS, 130, 75, 30, 255, 255, 0, 0);
    }


    //Update method to update the game play
    public void update(float dt, float fps){
        FPS = fps;
        Character.updateFiringTime += dt;
        Enemy1.posX += Enemy1.velX;
        Enemy1.timer += dt;
        switch (GameState) {
            case 0: {
                // 3) Update the background to allow panning effect
                bgY += 100 * dt; // temp value to speed of panning
                if(bgY > screenHeight)
                    bgY = 0;
                if(Enemy1.timer > Enemy1.timerChange)
                {
                    Enemy1.timer = 0;
                    Enemy1.velX = -Enemy1.velX;
                }
                // 4e) Update the spaceship images / shipIndex so that the animation will occur.
            }
            break;
        }
    }

    // Rendering is done on Canvas
    public void doDraw(Canvas canvas){ //edit
        switch (GameState)
        {
            case 0:
                RenderGameplay(canvas);
                break;
        }
    }

    public void RenderTextOnScreen(Canvas canvas, String text, int posX, int posY, int textsize,int alpha, int red, int green, int blue)
    {
        Paint paint = new Paint();

        paint.setARGB(alpha, red, green, blue);
        paint.setStrokeWidth(100);
        paint.setTextSize(textsize);
        canvas.drawText(text, posX, posY, paint);
    }

    //Collision Check -- To be completed
    public boolean CheckCollision(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2)
    {
        if (x2 >= x1 && x2 <= x1+w1){   // Start to detect collision of the top left corner
            if (y2 >= y1 && y2 <= y1+h1)  	// Comparing yellow box to blue box
                return true;
            if(y2 + h2 >= y1 && y2 + h2 <= y1 + h1)//Bottom Left
                return true;
        }
        if (x2+w2>=x1 && x2+w2<=x1+w1){ // Top right corner
            if (y2>=y1 && y2<=y1+h1)
                return true;
            if(y2 + h2 >= y1 && y2 + h2 <= y1 + h1)
                return true;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        // 5) In event of touch on screen, the spaceship will relocate to the point of touch
        short X = (short)event.getX(); // temp value of the screen touch
        short Y = (short)event.getY();

        int action = event.getAction();// Check for the action of touch

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                // To Check finger touch x, y with image
                if(CheckCollision((int)Character.posX, (int)Character.posY, Character.playerImage.getWidth(), Character.playerImage.getHeight(), X, Y, 0, 0))
                    Character.movePlayer = true;
                else
                    Character.movePlayer = false;
                break;

            case MotionEvent.ACTION_MOVE:
                if(Character.movePlayer)
                {
                    if(Character.updateFiringTime >= Character.firingRate)
                    {
                        Character.updateFiringTime = 0.f;
                        // Do firing codes here
                    }
                    Character.posX = (float)(X - Character.playerImage.getWidth() / 2);
                    Character.posY = (float)(Y - Character.playerImage.getHeight() / 2);
                }
                break;
        }

//        if(event.getAction() == MotionEvent.ACTION_DOWN)
//        {
//            mX = (short)(X - player.getWidth()/2);
//            mY = (short)(Y - player.getHeight()/2);
//        }

        return true;
//      return super.onTouchEvent(event);
    }
}
