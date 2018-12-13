package com.example.josh.flappycoskun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class background {
    private Bitmap image; //top
    private int yPos, xPos;
    public static int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    public static int width = Resources.getSystem().getDisplayMetrics().widthPixels * 3;

    public background(Bitmap bmp){
        image = bmp;
        yPos = 0;
        xPos = 0;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, xPos, yPos, null);
    }

    public void update(boolean inGame, double velocity){
        if(inGame)
            xPos -= velocity;
    }

    public int getxPos(){
        return xPos;
    }
    public int getyPos(){
        return yPos;
    }
    public void resetyPos(){
        yPos = - height + (int) (Math.random() * (height/2) );
    }
    public void setxPos(int x){
        xPos = x;
    }

}
