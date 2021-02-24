package com.coinnpursecoding.maggieslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    ImageView daisyView;
    TextView titleView;
    TextView titleView2;

    public void hideTitle(){
        daisyView.animate().alpha(0).setDuration(500);
        titleView.animate().alpha(0).setDuration(500);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        daisyView = (ImageView) findViewById(R.id.daisyImage);
        titleView = (TextView) findViewById(R.id.titleView);



        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                i.putExtra("id", "1");
                startActivity(i);
                finish();
                hideTitle();
                //overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        }, 2500);





    }
}