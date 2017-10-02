package com.mangedha.knit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.mangedha.knit.R;
import com.mangedha.knit.helpers.UserHelper;

public class SplashActivity extends MangedhaKnitActivity {

    int splashtime = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if(UserHelper.getState()){
                    i = new Intent(SplashActivity.this, ProductsActivity.class);
                }else{
                    i = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(i);
                finish();
            }
        }, splashtime);

    }
}
