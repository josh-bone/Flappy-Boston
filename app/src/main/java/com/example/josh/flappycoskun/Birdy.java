package com.example.josh.flappycoskun;

//Created by Josh Bone - 12/1/18

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

class Birdy {
    private static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private Bitmap image;

    public static int height = screenHeight/10;
    static int width = height;
    public double gravity = 2.1;
    public double velocity = 0;

    public int x = 100;
    public int y = screenHeight/2; //character position

    public Birdy(Bitmap bmp){
        image = bmp;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

    public void update(boolean inGame){
        if(inGame){
            velocity -= gravity;
            y -= velocity;
        }
    }
}
