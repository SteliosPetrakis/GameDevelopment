package com.example.gamedevelopment;

import static com.example.gamedevelopment.GameView.screenRatioX;
import static com.example.gamedevelopment.GameView.screenRatioY;
import static com.example.gamedevelopment.GameView.decreaseHealth;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.util.Random;

public class Enemy {

    Random random = new Random();
//    int temp_speed = 5;
    public int speed;
//    = temp_speed;

    public boolean wasShot = false;
    int x = 0, y = 0, width, height, birdCounter = 1;
    public int[] xpoints = new int[14];
    public int[] ypoints = new int[14];

    public Bitmap bird_list;

    public boolean alive = true;
    public int previouspoint = 0;
    Bitmap enemy1, enemy2, enemy3, enemy4;

    Enemy(Resources res) {

//        speed = temp_speed;

//        temp_speed *= 3;

        xpoints[0] = 0;
        ypoints[0] = 300;

        xpoints[1] = 260;
        ypoints[1] = 300;

        xpoints[2] = 260;
        ypoints[2] = 600;

        xpoints[3] = 540;
        ypoints[3] = 600;

        xpoints[4] = 540;
        ypoints[4] = 300;

        xpoints[5] = 820;
        ypoints[5] = 300;

        xpoints[6] = 820;
        ypoints[6] = 600;

        xpoints[7] = 1120;
        ypoints[7] = 600;

        xpoints[8] = 1120;
        ypoints[8] = 300;

        xpoints[9] = 1400;
        ypoints[9] = 300;

        xpoints[10] = 1400;
        ypoints[10] = 600;

        xpoints[11] = 1680;
        ypoints[11] = 600;

        xpoints[12] = 1680;
        ypoints[12] = 300;

        xpoints[13] = 2500;
        ypoints[13] = 300;


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

        y = ypoints[0]; // Set initial y position to the starting point of the path
    }

    public void getCurrentX() {
        if (previouspoint == 13) {
            previouspoint = 0;
        }

        int nextPoint = previouspoint + 1;
        if (nextPoint >= xpoints.length) {
            nextPoint = 0;
        }

        int targetX = xpoints[nextPoint];

        if (x < targetX) {
            x += speed;
            if (x >= targetX) {
                previouspoint = nextPoint;
            }
        }
//        else if (x > targetX) {
//            x -= speed;
//            if (x <= targetX) {
//                previouspoint = nextPoint;
//            }
//        }

        else if (x >= xpoints[13] && alive && !wasShot) {
            alive = false;
            GameView.dead += 1;
            decreaseHealth();
        }
    }

    public void getCurrentY() {
        if (previouspoint == 13) {
            previouspoint = 0;
        }

        int nextPoint = previouspoint + 1;
        if (nextPoint >= ypoints.length) {
            nextPoint = 0;
        }

        int targetY = ypoints[nextPoint];

        if (y < targetY) {
            y += speed;
            if (y >= targetY) {
                previouspoint = nextPoint;
            }
        } else if (y > targetY) {
            y -= speed;
            if (y <= targetY) {
                previouspoint = nextPoint;
            }
        }
    }

    Bitmap getBird() {
        if (birdCounter == 1) {
            birdCounter++;
            return enemy1;
        }
        else if (birdCounter == 2) {
            birdCounter++;
            return enemy2;
        } else if (birdCounter == 3) {
            birdCounter++;
            return enemy3;
        } else {
            birdCounter = 1;
            return enemy4;
        }
    }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }
}
