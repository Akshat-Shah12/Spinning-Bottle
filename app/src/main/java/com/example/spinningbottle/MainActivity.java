package com.example.spinningbottle;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random random = new Random();
    private int lastDir;
    private boolean spinning;
    ImageView bottle;
    MediaPlayer sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottle= findViewById(R.id.spinBottle);
        sound = MediaPlayer.create(MainActivity.this,R.raw.sound);

    }
    public void spin(View view){
        if (!spinning) {
            int newDir = random.nextInt(1080)+1800;
            float pivotX = bottle.getWidth() / 2;
            float pivotY = bottle.getHeight() / 2;
            Animation rotate = new RotateAnimation(lastDir,newDir,pivotX,pivotY);
            rotate.setDuration(3000);
            rotate.setFillAfter(true);
            rotate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    sound.start();
                    spinning = true;
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    spinning = false;
                    sound.stop();
                    lastDir=lastDir%720;
                    try {
                        sound.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            lastDir = newDir;
            bottle.startAnimation(rotate);
        }
    }
}