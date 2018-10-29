package com.amostra.comanda.ui.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amostra.comanda.App;
import com.amostra.comanda.R;
import com.amostra.net.api.API;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(50);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent;

                    App.getInstance().setApi(new API(getApplicationContext()));
                    if (App.getInstance().getApi().validateToken()) intent = new Intent(SplashActivity.this, MainActivity.class);
                    else intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).start();
    }
}



