package com.example.josh.flappycoskun;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class PhotoHandler {

    public static Bitmap resizeBitmap(Bitmap bmp, int newWidth, int newHeight) {
        //returns a NEW bitmap similar to bmp (the input argument), but resized.
        int oldWidth = bmp.getWidth();
        int oldHeight = bmp.getHeight();

        //resizing
        Matrix matrix = new Matrix();
        matrix.postScale(((float) newWidth) / oldWidth, ((float) newHeight) / oldHeight);

        Bitmap resizedBMP = Bitmap.createBitmap(bmp, 0, 0, oldWidth, oldHeight, matrix, false);
        bmp.recycle(); //free memory (https://developer.android.com/reference/android/graphics/Bitmap#recycle())
        return resizedBMP;
    }
}
