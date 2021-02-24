package com.coinnpursecoding.maggieslist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import soup.neumorphism.NeumorphCardView;

public class AlbumActivity extends AppCompatActivity {

    NeumorphCardView card1, card2, card3, card4, card5, card6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        card1 = (NeumorphCardView) findViewById(R.id.cardOne);
        card2 = (NeumorphCardView) findViewById(R.id.cardTwo);
        card3 = (NeumorphCardView) findViewById(R.id.cardThree);
        card4 = (NeumorphCardView) findViewById(R.id.cardFour);
        card5 = (NeumorphCardView) findViewById(R.id.cardFive);
        card6 = (NeumorphCardView) findViewById(R.id.cardSix);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        // Adjusts window size based on display size
        getWindow().setLayout((int)(width * .9), (int)(height * .9));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlbumActivity.this, FullscreenActivity.class);
                startActivity(i);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlbumActivity.this, FullscreenActivity.class);
                i.putExtra("picture", R.drawable.image1);
                startActivity(i);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlbumActivity.this, FullscreenActivity.class);
                i.putExtra("picture", R.drawable.image2);
                startActivity(i);
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlbumActivity.this, FullscreenActivity.class);
                i.putExtra("picture", R.drawable.image3);
                startActivity(i);
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlbumActivity.this, FullscreenActivity.class);
                i.putExtra("picture", R.drawable.image4);
                startActivity(i);
            }
        });

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlbumActivity.this, FullscreenActivity.class);
                i.putExtra("picture", R.drawable.image5);
                startActivity(i);
            }
        });

    }
}