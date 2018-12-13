//created by Josh on 12/1/18
package com.example.josh.flappycoskun;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private com.example.josh.flappycoskun.MainThread thread; //note: NOT android.support.annotation.MainThread, but a custom MainThread class which extends Thread
    private Birdy birdy;
    private background back;
    public PipeClass pipe1, pipe2;
    private static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public ArrayList<PipeClass> pipes = new ArrayList<>(2);
    public int score, highScore; //score updated every time a pipe travels off-screen
    public boolean inGame = false;
    public int velocity = 12; //speed at which bird moves in x (or, conversely, the speed at which background moves toward bird)
    //initialize background colors
    int red = 100;
    int green = 0;
    int blue = 0;
    int counter = 0;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public GameView(Context context){
        super(context);

        getHolder().addCallback(this);

        thread = new com.example.josh.flappycoskun.MainThread(getHolder(), this);

        setFocusable(true);

        prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        editor = prefs.edit();
        highScore = prefs.getInt("highScore", 0);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        createLevel();

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){ //keep looping until it works
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    private void createLevel() {
        score = 0;
        velocity = 12;

        Bitmap tempscene = BitmapFactory.decodeResource(getResources(), R.drawable.backgroundone);
        Bitmap scene = PhotoHandler.resizeBitmap(tempscene, background.width, background.height);
        back = new background(scene);
        //initializing objects - default bird is red sox
        if(BirdActivity.chosenOne ==  null) {
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.soxbird);
            birdy = new Birdy(PhotoHandler.resizeBitmap(b, Birdy.width, Birdy.height));
        }else{
            birdy = BirdActivity.chosenOne;
        }

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.greenlinewall);
        bmp = PhotoHandler.resizeBitmap(bmp, PipeClass.width, PipeClass.height); //resize pipe image

        //initialize all three pipes
        pipe1 = new PipeClass(bmp, bmp, screenWidth);
        pipes.add(pipe1);
        pipe2 = new PipeClass(bmp, bmp, screenWidth + screenWidth/2);
        pipes.add(pipe2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(!inGame){
            inGame = true;
        }else {
            //on user touch, make the bird increase in height
            birdy.velocity = 28;
        }
        return super.onTouchEvent(event);
    }

    public void update() {
        counter++;
        if(counter%2 == 0) {
            logic();
            back.update(inGame, velocity/2);
        }
        else {
            birdy.update(inGame);
            for (PipeClass p : pipes) {
                p.update(inGame, velocity);
            }
        }
        if(back.getxPos() + background.width < screenWidth) {
            back.setxPos(0);
        }
    }
    public void logic(){
        //has character hit top or bottom of screen?
        if(birdy.y < 0) {
            storeScore();
            resetLevel();
        }
        if(birdy.y > screenHeight){
            storeScore();
            resetLevel();
        }

        for(PipeClass p : pipes) {
            if( (birdy.x > p.getxPos() && birdy.x < p.getxPos() + PipeClass.width) || (birdy.x + Birdy.width > p.getxPos() && birdy.x + Birdy.width < p.getxPos() + PipeClass.width)) {
                if (birdy.y < p.getyPos() + PipeClass.height) {
                    //colliding with top pipe
                    storeScore();
                    resetLevel();
                }
                if (birdy.y + Birdy.height > p.getyPos() + PipeClass.height + PipeClass.gapSpacing) {   //colliding with bottom pipe
                    storeScore();
                    resetLevel();
                }
            }
            if (p.getxPos() < 0) {
                //pipe has travelled off screen
                p.setxPos(screenWidth);
                p.resetyPos();
                score++;
                velocity = 12 + score/10; //increase speed
            }
        }
    }

    private void storeScore(){
        if(score > prefs.getInt("highScore", 0)) {
            editor.putInt("highScore", score);
            editor.apply(); //if doesn't work, try editor.commit() instead.
        }
    }
    private int loadScore(){ return prefs.getInt("highScore", 0); }

    public void resetLevel(){
        score = 0;
        highScore = loadScore();
        back.setxPos(0);
        inGame = false;

        pipe1.setxPos(screenWidth);
        pipe2.setxPos(screenWidth + screenWidth/2);
        pipe1.resetyPos();
        pipe2.resetyPos();

        birdy.y = screenHeight/2;
        birdy.velocity = 0;

        //background
        Random rand = new Random();
        red = rand.nextInt();
        blue = rand.nextInt();
        green = rand.nextInt();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
            back.draw(canvas);
            birdy.draw(canvas);
            //draw pipes
            for(PipeClass p : pipes)
                p.draw(canvas);
        }
        drawScores(canvas);
        if(!inGame){
            //draw the "tap to begin" prompt
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(screenWidth/10);
            canvas.drawText("tap to begin", screenWidth/4, screenHeight/2, paint);
        }
    }

    public void drawScores(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(screenWidth/10);
        canvas.drawText(Integer.toString(score), 0, 100, paint);
        String highScoreString = "High Score: ";
        highScoreString = highScoreString.concat(Integer.toString(highScore));
        canvas.drawText(highScoreString, screenWidth - paint.measureText(highScoreString), 100, paint);
    }
}
