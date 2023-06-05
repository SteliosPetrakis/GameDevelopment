package com.example.gamedevelopment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Arrow {

    public int x;
    public int y;
    public Bitmap arrow;
    private int width;
    private int height;
    private float speed = 10f;
    private Bitmap[] arrows_list = new Bitmap[4];
    private static Bitmap arrowUp, arrowDown, arrowRight, arrowLeft;
    public static Bitmap shoot;
    private static int currentDirection;

    private int current = 0;

    public Arrow(Resources res, float screenRatioX, float screenRatioY) {
        arrowUp = BitmapFactory.decodeResource(res, R.drawable.arrow_up);
        arrowDown = BitmapFactory.decodeResource(res, R.drawable.arrow_down);
        arrowRight = BitmapFactory.decodeResource(res, R.drawable.arrow_right);
        arrowLeft = BitmapFactory.decodeResource(res, R.drawable.arrow_left);
        shoot = BitmapFactory.decodeResource(res, R.drawable.shoot_button);
//        arrows_list.add(arrowUp);

        width = arrowUp.getWidth();
        height = arrowUp.getHeight();

        width /= 8;
        height /= 8;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        arrowUp = Bitmap.createScaledBitmap(arrowUp, width, height, false);
        arrowDown = Bitmap.createScaledBitmap(arrowDown, width, height, false);
        arrowRight = Bitmap.createScaledBitmap(arrowRight, width, height, false);
        arrowLeft = Bitmap.createScaledBitmap(arrowLeft, width, height, false);
        shoot = Bitmap.createScaledBitmap(shoot, width, height, false);

        x = (int) (screenRatioX / 2);
        y = (int) (screenRatioY / 2);
        currentDirection = 0;

        arrows_list[0] = arrowUp;
        arrows_list[1] = arrowDown;
        arrows_list[2] = arrowLeft;
        arrows_list[3] = arrowRight;

        arrow = arrows_list[current];
        current += 1;
        Log.d("List", "current = " + current);
    }

    public Arrow(Resources resources) {
        this(resources, 1.0f, 1.0f);
    }

    public void update(int direction) {
        if (direction != 0) {
            currentDirection = direction;
        }

        switch (currentDirection) {
            case 1:
                y -= speed;
                break;
            case 2:
                y += speed;
                break;
            case 3:
                x += speed;
                break;
            case 4:
                x -= speed;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Bitmap getArrowBitmap() {
        switch (currentDirection) {
//            case 0:
//                return arrowUp;
            case 1:
                currentDirection+=1;
                return arrowDown;
            case 2:
                currentDirection+=1;
                return arrowRight;
            case 3:
                currentDirection=0;
                return arrowLeft;
            default:
                currentDirection+=1;
                return arrowUp;
        }
    }
}
