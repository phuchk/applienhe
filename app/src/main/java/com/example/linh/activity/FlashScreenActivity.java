package com.example.linh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.linh.R;

public class FlashScreenActivity extends AppCompatActivity {
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        new flashScreen().start();
        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 1:
                        Intent intent=new Intent(FlashScreenActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                }
            }
        };
    }

    class flashScreen extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }

}