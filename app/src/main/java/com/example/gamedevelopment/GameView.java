package com.example.gamedevelopment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Enemy[] birds;

    private Enemy_Red[] red_birds;

    private Arrow button;

    private Arrow[] arrows;

    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private List<Bullet> bullets;
    private int sound;
    private UFO flight;

    private float flightAltitude = 0f;
    private GameActivity activity;
    private Background background1, background2;
    public static int health = 10;
    private int bullets_count = 0;

    public static int dead = 0;
    public static void decreaseHealth() {
        health -=1;
        Log.d("SteliosTest5", "health =  " + health);
    }


    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        button = new Arrow(getResources());
        button.arrow = Arrow.shoot;

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        sound = soundPool.load(activity, R.raw.shoot, 1);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 2340f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        flight = new UFO(this, screenY, getResources());
        flightAltitude = flight.y;

        bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        int temp = 3;
        birds = new Enemy[4];
        for (int i=0; i<4; i++){
            birds[i] = new Enemy(getResources());
            birds[i].speed = temp;
            temp+= 1.5;
        }

        temp = 3;

        red_birds = new Enemy_Red[4];
        for (int i=0; i<4; i++) {
            red_birds[i] = new Enemy_Red(getResources());
            red_birds[i].speed = temp;
            temp+= 1.5;
        }

        arrows = new Arrow[4];
        for (int i=0; i<4; i++)
            arrows[i] = new Arrow(getResources());

        random = new Random();
    }

    @Override
    public void run() {
        while (isPlaying) {
            try {
                update ();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            draw ();
            sleep ();
        }
    }

    private void update () throws InterruptedException {


        background1.x = 0;
        background2.x = screenX;

        if (flight.isMoving)
        if(Math.abs(flightAltitude - (flight.y+flight.height/2)) > flight.height/5f)     // Remove small movements created by user's slight shifting on button touch - press
        {
            if (flightAltitude < (flight.y + flight.height/2))
                flight.y -= (int) (flight.speed * screenRatioY);
            else
                flight.y += (int) (flight.speed * screenRatioY);
        }

        if (flight.isShooting)
            if(flight.toShoot==0)
                flight.toShoot++;

        if (flight.y < 0)
            flight.y = 0;

        if (flight.y >= screenY - flight.height)
            flight.y = screenY - flight.height;

        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if (bullet.x > screenX)
                trash.add(bullet);

            bullet.x += (int)(bullet.speed * screenRatioX);


            for (Enemy bird : birds) {
                if (Rect.intersects(bird.getCollisionShape(), bullet.getCollisionShape())) {
                    score++;
                    dead++;
                    bird.respawn = true;
                    bullet.x = screenX + 500;
                    bird.wasShot = true;
                }
            }

            for (Enemy_Red bird : red_birds) {
                if (Rect.intersects(bird.getCollisionShape(), bullet.getCollisionShape())) {
                    score++;
                    dead++;
                    bullet.x = screenX + 500;
                    bird.wasShot = true;
                    bird.respawn = true;
                }
            }


        }


        arrows[0].x = 100;
        arrows[0].y = 200;

        arrows[0].arrow = Arrow.getArrowBitmap();

        arrows[1].x = 200;
        arrows[1].y = 300;

        arrows[1].arrow = Arrow.getArrowBitmap();

        arrows[2].x = 300;
        arrows[2].y = 400;

        arrows[2].arrow = Arrow.getArrowBitmap();

        arrows[3].x = 400;
        arrows[3].y = 500;

        arrows[3].arrow = Arrow.getArrowBitmap();


        for (Bullet bullet : trash)
            bullets.remove(bullet);

        int i;

        for (i = 0; i < birds.length; i++) {


            Enemy bird = birds[i];




            bird.getCurrentX();
            bird.getCurrentY();

            if (health == 0)
            {
                isGameOver = true;
                Log.d("SteliosTest4", "GameOver = " + isGameOver);
                return;
            }



            if (bird.x == bird.xpoints[0] && bird.y == bird.ypoints[0]) {
                bird.previouspoint = 0; // Reset previous point to the start
            }



            if (bird.x == bird.xpoints[bird.previouspoint] && bird.y == bird.ypoints[bird.previouspoint]) {
                int nextPoint = bird.previouspoint + 1;
                if (nextPoint >= bird.xpoints.length) {
                    nextPoint = 0;
                }

                int targetX = bird.xpoints[nextPoint];
                int targetY = bird.ypoints[nextPoint];

                // Calculate movement direction
                int moveX = Integer.compare(targetX, bird.x);
                int moveY = Integer.compare(targetY, bird.y);

                bird.x += bird.speed * moveX;
                bird.y += bird.speed * moveY;

                if (bird.x == targetX && bird.y == targetY) {
                    bird.previouspoint = nextPoint;
                }
            }

            if (bird.x < bird.xpoints[0] && bird.y > bird.ypoints[13]) {
                if (!bird.wasShot) {
                    isGameOver = true;
                    return;
                }

                int bound = (int)(30f * screenRatioX);
                bird.speed = random.nextInt(bound);

                if (bird.speed < (int)(10f * screenRatioX))
                    bird.speed = (int)(10f * screenRatioX);

                bird.x = screenX;
                bird.y = random.nextInt(screenY - bird.height);

                bird.wasShot = false;
            }


        }


        if(dead >= birds.length) {
            for (i = 0; i < red_birds.length; i++) {


                Enemy_Red bird = red_birds[i];


                bird.getCurrentX();
                bird.getCurrentY();

                if (health == 0) {
                    isGameOver = true;
                    Log.d("SteliosTest4", "GameOver = " + isGameOver);
                    return;
                }


                if (bird.x == bird.xpoints[0] && bird.y == bird.ypoints[0]) {
                    bird.previouspoint = 0; // Reset previous point to the start
                }


                if (bird.x == bird.xpoints[bird.previouspoint] && bird.y == bird.ypoints[bird.previouspoint]) {
                    int nextPoint = bird.previouspoint + 1;
                    if (nextPoint >= bird.xpoints.length) {
                        nextPoint = 0;
                    }

                    int targetX = bird.xpoints[nextPoint];
                    int targetY = bird.ypoints[nextPoint];

                    // Calculate movement direction
                    int moveX = Integer.compare(targetX, bird.x);
                    int moveY = Integer.compare(targetY, bird.y);

                    bird.x += bird.speed * moveX;
                    bird.y += bird.speed * moveY;

                    if (bird.x == targetX && bird.y == targetY) {
                        bird.previouspoint = nextPoint;
                    }
                }

                if (bird.x < bird.xpoints[0] && bird.y > bird.ypoints[14]) {
                    if (!bird.wasShot) {
                        isGameOver = true;
                        return;
                    }

                    int bound = (int) (30f * screenRatioX);

                    if (bird.speed < (int) (10f * screenRatioX))
                        bird.speed = (int) (10f * screenRatioX);

                    bird.x = screenX;
                    bird.y = random.nextInt(screenY - bird.height);

                    bird.wasShot = false;
                }


            }
        }

    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            for (int i = 0; i < birds.length; i++) {
                Enemy bird = birds[i];
                canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);
            }

            if(dead >= birds.length) {

                for (int i = 0; i < red_birds.length; i++) {
                    Enemy_Red bird = red_birds[i];
                    canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);
                }
            }
            canvas.drawText(score + "", screenX / 2f, 164, paint);

            Paint paint2 = new Paint();
            paint2.setTextSize(80);
            paint2.setColor(Color.BLACK);

            canvas.drawText("Lives: " + health, screenX / 1.1f, 120, paint2);

            if (isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(flight.getDead(), flight.x, flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting();
                return;
            }

            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, paint);

            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);



            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {
        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }
    }

    private void sleep () {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause () {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // In order to Handle multi-finger-touch
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        int fingerId = event.getPointerId(pointerIndex);
        int pointerCount = event.getPointerCount();
        int x = (int)event.getX();
        int y = (int)event.getY();


        switch (action) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                for (int i = 0; i < pointerCount; i++) {
                    Log.e("PointerCount --->>", String.valueOf(event.getPointerCount()) + "   " + String.valueOf(i));
                    if (event.getX(i) < screenX / 2) {
                        flight.isMoving = true;
                        flightAltitude = event.getY(i);
                    }
                    else
                        flight.isShooting = true;
                        bullets_count++;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                for (int i = 0; i < pointerCount; i++) {
                    if (event.getX(i) < screenX / 2)
                        flight.isMoving = false;
                    else
                        flight.isShooting = false;
                }
                break;
        }
//        }
        return true;
    }

    public void newBullet() {
        if (!prefs.getBoolean("isMute", false))
            soundPool.play(sound, 1, 1, 0, 0, 1);

        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height / 4);
        bullets.add(bullet);
    }

    public int getScreenY() {

        return 0;
    }

    public int getScreenX() {

        return 0;
    }


}