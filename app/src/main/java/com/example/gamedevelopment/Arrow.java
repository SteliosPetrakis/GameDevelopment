package com.example.gamedevelopment;

import static com.example.gamedevelopment.GameView.screenRatioX;
import static com.example.gamedevelopment.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Arrow {

    int x, y, width, height;
    float speed = 50f;
    Bitmap arrow;

    Arrow(Resources res) {

        arrow = BitmapFactory.decodeResource(res, R.drawable.arrow);

        width = arrow.getWidth();
        height = arrow.getHeight();

        width /= 15;
        height /= 15;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        arrow = Bitmap.createScaledBitmap(arrow, width, height, false);

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}