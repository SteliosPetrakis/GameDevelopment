package com.example.gamedevelopment;

import static com.example.gamedevelopment.GameView.screenRatioX;
import static com.example.gamedevelopment.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

public class Enemy {

    public int speed = 3;
    public boolean wasShot = true;
    int x = 0, y, width, height, birdCounter = 1;
    public int [] xpoints = new int[16];
    public int [] ypoints = new int[16];

    public int previouspoint = 0;
    Bitmap enemy1, enemy2, enemy3, enemy4;

    Enemy(Resources res) {

        xpoints[0] = 0;
        ypoints[0] = 120;

        xpoints[1] = 500;
        ypoints[1] = 120;

        xpoints[2] = 1000;
        ypoints[2] = 480;

        xpoints[3] = 1500;
        ypoints[3] = 480;

        xpoints[4] = 500;
        ypoints[4] = 480;

        xpoints[5] = 500;
        ypoints[5] = 480;

        xpoints[6] = 500;
        ypoints[6] = 480;

        xpoints[7] = 500;
        ypoints[7] = 480;

        xpoints[8] = 500;
        ypoints[8] = 480;

        xpoints[9] = 500;
        ypoints[9] = 480;

        xpoints[10] = 500;
        ypoints[10] = 480;

        xpoints[11] = 500;
        ypoints[11] = 480;

        xpoints[12] = 500;
        ypoints[12] = 480;

        xpoints[13] = 500;
        ypoints[13] = 480;

        xpoints[14] = 500;
        ypoints[14] = 480;

        xpoints[15] = 1500;
        ypoints[15] = 2000;


        enemy1 = BitmapFactory.decodeResource(res, R.drawable.enemy1);
        enemy2 = BitmapFactory.decodeResource(res, R.drawable.enemy2);
        enemy3 = BitmapFactory.decodeResource(res, R.drawable.enemy3);
        enemy4 = BitmapFactory.decodeResource(res, R.drawable.enemy4);

        width = enemy1.getWidth();
        height = enemy1.getHeight();

        width /= 8;
        height /= 8;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        enemy1 = Bitmap.createScaledBitmap(enemy1, width, height, false);
        enemy2 = Bitmap.createScaledBitmap(enemy2, width, height, false);
        enemy3 = Bitmap.createScaledBitmap(enemy3, width, height, false);
        enemy4 = Bitmap.createScaledBitmap(enemy4, width, height, false);

        y = -height;
    }

    public void getCurrentX(){
        if (xpoints[previouspoint] <= xpoints[previouspoint + 1])
        {
          x = x+speed;
          previouspoint += 1;
        }
    }

    public void getCurrentY(){
        if (ypoints[previouspoint] <= ypoints[previouspoint + 1])
        {
            y += speed;
            previouspoint += 1;
        }
    }


    Bitmap getBird () {

        if (birdCounter == 1) {
            birdCounter++;
            return enemy1;
        }

        if (birdCounter == 2) {
            birdCounter++;
            return enemy2;
        }

        if (birdCounter == 3) {
            birdCounter++;
            return enemy3;
        }

        birdCounter = 1;

        return enemy4;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}
