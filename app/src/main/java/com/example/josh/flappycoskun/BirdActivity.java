package com.example.josh.flappycoskun;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class BirdActivity extends AppCompatActivity {

    public static Birdy chosenOne = null;
    private ImageButton redSoxBird;
    private ImageButton celticsBird;
    private ImageButton rhettBird;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        redSoxBird = (ImageButton)findViewById(R.id.sox_bird);

        redSoxBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.soxbird);
                chosenOne = new Birdy(PhotoHandler.resizeBitmap(b, Birdy.width, Birdy.height));
                Intent game = new Intent(BirdActivity.this, GameActivity.class);
                startActivity(game);
            }
        });

        celticsBird = (ImageButton)findViewById(R.id.celtics_bird);

        celticsBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.celticsbird);
                chosenOne = new Birdy(PhotoHandler.resizeBitmap(b, Birdy.width, Birdy.height));
                Intent game = new Intent(BirdActivity.this, GameActivity.class);
                startActivity(game);
            }
        });

        rhettBird = (ImageButton)findViewById(R.id.rhett_bird);

        rhettBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.rhettbird);
                chosenOne = new Birdy(PhotoHandler.resizeBitmap(b, Birdy.width, Birdy.height));
                Intent game = new Intent(BirdActivity.this, GameActivity.class);
                startActivity(game);
            }
        });
    }
}
