package com.example.josh.flappycoskun;

//Main thread of execution for Flappy Coskun app.
//created by Josh Bone - 12/1/18

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread
{
    private SurfaceHolder surfaceHolder;
    private volatile boolean running = true;
    public static Canvas canvas;
    private com.example.josh.flappycoskun.GameView gameView;

    public MainThread(SurfaceHolder surfaceHolder, com.example.josh.flappycoskun.GameView gameView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run(){
        while(running){
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    //sleep(20);
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            }catch(Exception e){}
            finally{
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }
        }
    }

    public void setRunning(boolean isRunning){
        running = isRunning;
    }
}
