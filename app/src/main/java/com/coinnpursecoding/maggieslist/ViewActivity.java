package com.coinnpursecoding.maggieslist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import javax.xml.transform.Result;

import soup.neumorphism.NeumorphCardView;

public class ViewActivity extends MainActivity {

    public static String listName;
    public static String listValues;
    EditText title2;
    EditText listText2;
    NeumorphCardView saveBtn2, deleteBtn;

    mySQLiteDBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        title2 = (EditText) findViewById(R.id.editTitle2);
        listText2 = (EditText) findViewById(R.id.listEditText2);
        saveBtn2 = (NeumorphCardView) findViewById(R.id.saveButton2);
        deleteBtn = (NeumorphCardView) findViewById(R.id.deleteButton);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        // Adjusts window size based on display size
        getWindow().setLayout((int)(width * .9), (int)(height));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);

        Intent j = getIntent();

        listName = j.getStringExtra("list name");
        listValues = j.getStringExtra("list values");
        title2.setText(listName);
        listText2.setText(listValues);


        db = new mySQLiteDBHandler(this);


        saveBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = listText2.getText().toString();
                String title = title2.getText().toString();
                if (text.equals("")){
                    Toast.makeText(ViewActivity.this,
                            "The list is empty.", Toast.LENGTH_SHORT).show();
                } else if (title.equals("")){
                    Toast.makeText(ViewActivity.this,
                            "You forgot to add a title.", Toast.LENGTH_SHORT).show();
                } else if (!text.equals(listValues) || !title.equals(listName)){
                    boolean success = db.removeData(listName);
                    // Removes entry from ListView
                    if(success) {
                        String newEntry = listText2.getText().toString();
                        String newName = title2.getText().toString();
                        Intent resultIntent = new Intent();

                        resultIntent.putExtra("list name", newName);
                        resultIntent.putExtra("list values", newEntry);
                        setResult(2, resultIntent);
                        finish();
                        Toast.makeText(ViewActivity.this, "List updated!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ViewActivity.this, "Error: Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    finish();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ViewActivity.this, R.style.AlertDialogTheme)
                        //.setIcon(R.drawable.)
                        .setTitle("Hold on ...")
                        .setMessage("Are you sure you want to delete this list?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Deletes entry from database, returns a boolean value
                                boolean success = db.removeData(listName);

                                // Removes entry from ListView
                                if(success) {
                                    Intent resultIntent = new Intent();

                                    resultIntent.putExtra("list name", listName);
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();
                                    Toast.makeText(ViewActivity.this, "List deleted!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ViewActivity.this, "Error: Something went wrong...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        listText2.setText("");
    }
}