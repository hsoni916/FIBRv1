package com.example.fibr;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.FIBR.R;

import java.util.Objects;

public class boiler extends AppCompatActivity {
    Handler timer;
    ImageView imageView,logo;
    Button enter;
    Intent permissionactivity;
    private String[] permissions = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CHANGE_NETWORK_STATE};
    Intent intent;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.logo);
        enter = findViewById(R.id.enter);
        final Handler logoreveal =new Handler();
        logoreveal.postDelayed(new Runnable() {
            @Override
            public void run() {
                final Animation show = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.show);
                final Animation translate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.transpose);
                final Animation show1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.show);
                logo.startAnimation(show);
                show.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        logo.startAnimation(translate);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                translate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                       enter.startAnimation(show1);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                show1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        enter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                permissionactivity = new Intent(getApplicationContext(),PermissionActivity.class);
                                permissionactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                permissionactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                permissionactivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(permissionactivity);
                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        },1200);
    }
}