package com.coinnpursecoding.maggieslist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import soup.neumorphism.NeumorphCardView;

public class AddActivity extends MainActivity {

    TextView titleText;
    EditText listText;
    NeumorphCardView saveBtn;
    mySQLiteDBHandler dbHandler;

    public static String listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        titleText = (EditText) findViewById(R.id.editTitle);
        listText = (EditText) findViewById(R.id.listEditText);
        saveBtn = (NeumorphCardView) findViewById(R.id.saveButton);

        dbHandler = new mySQLiteDBHandler(AddActivity.this);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        // Adjusts window size based on display size
        getWindow().setLayout((int)(width * .9), (int)(height));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listName = titleText.getText().toString();
                if (listName.equals("")) {
                    Toast.makeText(AddActivity.this,
                            "You forgot to add a title.", Toast.LENGTH_SHORT).show();
                } else if (dbHandler.findList(listName)) {
                    Toast.makeText(AddActivity.this,
                            "That list already exists. Try a different name?", Toast.LENGTH_SHORT).show();
                } else {
                    String multiLines = listText.getText().toString();
                    String nameOfList = listName;
                    Intent resultIntent = new Intent();

                    resultIntent.putExtra("list values", multiLines);
                    resultIntent.putExtra("list name", nameOfList);
                    setResult(Activity.RESULT_OK, resultIntent);

                    finish();
                }

            }
        });

    }
}