//package com.example.gamedevelopment;
//
//import static com.example.gamedevelopment.GameView.screenRatioX;
//import static com.example.gamedevelopment.GameView.screenRatioY;
//
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Rect;
//
//public class UFO {
//
//    int toShoot = 0;
//    boolean isShooting = false;
//    boolean isMoving = false;
//    float speed = 17f;
//    int x, y, width, height, wingCounter = 0, shootCounter = 1;
//    Bitmap ufo1, ufo2, shoot1, shoot2, shoot3, shoot4, shoot5, dead;
//    private GameView gameView;
//    boolean up;
//    boolean down;
//    boolean right;
//    boolean left;
//
//    UFO(GameView gameView, int screenY, Resources res) {
//
//        this.gameView = gameView;
//
//        ufo1 = BitmapFactory.decodeResource(res, R.drawable.saucer1);
//        ufo2 = BitmapFactory.decodeResource(res, R.drawable.saucer2);
//
//        width = ufo1.getWidth();
//        height = ufo1.getHeight();
//
//        width /= 4;
//        height /= 4;
//
//        width = (int) (width * screenRatioX);
//        height = (int) (height * screenRatioY);
//
//        ufo1 = Bitmap.createScaledBitmap(ufo1, width, height, false);
//        ufo2 = Bitmap.createScaledBitmap(ufo2, width, height, false);
//
//        shoot1 = BitmapFactory.decodeResource(res, R.drawable.shoot1);
//        shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot2);
//        shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot3);
//        shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot4);
//        shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot5);
//
//        shoot1 = Bitmap.createScaledBitmap(shoot1, width, height, false);
//        shoot2 = Bitmap.createScaledBitmap(shoot2, width, height, false);
//        shoot3 = Bitmap.createScaledBitmap(shoot3, width, height, false);
//        shoot4 = Bitmap.createScaledBitmap(shoot4, width, height, false);
//        shoot5 = Bitmap.createScaledBitmap(shoot5, width, height, false);
//
//        dead = BitmapFactory.decodeResource(res, R.drawable.dead);
//        dead = Bitmap.createScaledBitmap(dead, width, height, false);
//
//        y = screenY / 2;
//        x = (int) (64 * screenRatioX);
//
//    }
//
//    Bitmap getFlight() {
//
//        if (toShoot != 0) {
//
//            if (shootCounter == 1) {
//                shootCounter++;
//                return shoot1;
//            }
//
//            if (shootCounter == 2) {
//                shootCounter++;
//                return shoot2;
//            }
//
//            if (shootCounter == 3) {
//                shootCounter++;
//                return shoot3;
//            }
//
//            if (shootCounter == 4) {
//                shootCounter++;
//                return shoot4;
//            }
//
//            shootCounter = 1;
//            toShoot--;
//            gameView.newBullet();
//
//            return shoot5;
//        }
//
//        if (wingCounter == 0) {
//            wingCounter++;
//            return ufo1;
//        }
//        wingCounter--;
//
//        return ufo2;
//    }
//
//    Rect getCollisionShape() {
//        return new Rect(x, y, x + width, y + height);
//    }
//
//    boolean isMovingUp() {
//        return up;
//    }
//
//    boolean isMovingDown() {
//        return down;
//    }
//
//    boolean isMovingRight() {
//        return right;
//    }
//
//    boolean isMovingLeft() {
//        return left;
//    }
//
//    void setMovingUp(boolean up) {
//        this.up = up;
//    }
//
//    void setMovingDown(boolean down) {
//        this.down = down;
//    }
//
//    void setMovingRight(boolean right) {
//        this.right = right;
//    }
//
//    void setMovingLeft(boolean left) {
//        this.left = left;
//    }
//
//    void update() {
//        if (isMovingUp()) {
//            y -= speed;
//        }
//        if (isMovingDown()) {
//            y += speed;
//        }
//        if (isMovingRight()) {
//            x += speed;
//        }
//        if (isMovingLeft()) {
//            x -= speed;
//        }
//
//        // Update the UFO's position based on the current direction
//
//        // Clamp the UFO's position to the screen bounds
//        if (y < 0)
//            y = 0;
//        if (y > gameView.getScreenY() - height)
//            y = gameView.getScreenY() - height;
//        if (x < 0)
//            x = 0;
//        if (x > gameView.getScreenX() - width)
//            x = gameView.getScreenX() - width;
//    }
//
////    public Bitmap getDead() {
////    }
//}
