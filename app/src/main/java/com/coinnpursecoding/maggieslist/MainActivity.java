package com.coinnpursecoding.maggieslist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import soup.neumorphism.NeumorphCardView;

public class MainActivity extends AppCompatActivity {

    TextView greeting, emptyPrompt;
    ListView myListView;
    NeumorphCardView herLists, addBtn, jokeBtn, loveBtn, albumBtn;
    ImageView full;

    public String greetingStr;

    public String[] arrayOfJokes;
    public String[] arrayOfLove;
    public static ArrayList<String> list = new ArrayList<String>();
    mySQLiteDBHandler myDBH;

    public static String data;



    public boolean AddData(String listName, String listText){
            return myDBH.addData(listName, listText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        herLists = (NeumorphCardView) findViewById(R.id.cardView);
        jokeBtn = (NeumorphCardView) findViewById(R.id.jokeButton);
        addBtn = (NeumorphCardView) findViewById(R.id.addButton);

        myListView = (ListView) findViewById(R.id.listView);
        emptyPrompt = (TextView) findViewById(R.id.emptyText);

        myDBH = new mySQLiteDBHandler(this);

        if(myDBH.getDatabaseSize() == 0){
           emptyPrompt.setVisibility(View.VISIBLE);
        } else {
            myDBH.fillArrayList(list);
            emptyPrompt.setVisibility(View.INVISIBLE);
        }

        // Set ListView to created ArrayList values
        myListView.setAdapter(new CustomAdapter(this, list));
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                data = (String) parent.getItemAtPosition(position);
                Log.i("List name", data);

                if(myDBH.findList(data)) {
                    Intent i = new Intent(MainActivity.this, ViewActivity.class);
                    i.putExtra("list name", data);
                    i.putExtra("list values", myDBH.fillViewList(data));
                    startActivityForResult(i, 2);
                } else {
                    Toast.makeText(MainActivity.this, "ERROR: This list does not exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        arrayOfJokes = this.getResources().getStringArray(R.array.jokeArray);


        jokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String randomString = arrayOfJokes[new Random().nextInt(arrayOfJokes.length)];
                Toast toast = Toast.makeText(MainActivity.this, randomString, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER|Gravity.BOTTOM, 0, 50);
                toast.show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(i, 1);
            }
        });

        emptyPrompt.setText(getResources().getString((R.string.list_is_empty)));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Needs to be the same database? Adjust add functions?
        if(requestCode == 1) {
            if(resultCode == RESULT_OK && data != null) {
                Log.i("list added", Objects.requireNonNull(data.getStringExtra("list name")));
                Log.i("list content", Objects.requireNonNull(data.getStringExtra("list values")));
                list.clear();
                try {
                    AddData(Objects.requireNonNull(data.getStringExtra("list name")), Objects.requireNonNull(data.getStringExtra("list values")));
                    Toast.makeText(MainActivity.this, "List saved!", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error: Something went wrong...", Toast.LENGTH_SHORT).show();
                }
                myDBH.fillArrayList(list);
                myListView.setAdapter(new CustomAdapter(this, list));
                if (myDBH.getDatabaseSize() != 0){
                    emptyPrompt.setVisibility(View.INVISIBLE);
                }
            }
        }

        if(requestCode == 2) {
            if(resultCode == RESULT_OK){
                list.clear();
                myDBH.fillArrayList(list);
                myListView.setAdapter(new CustomAdapter(this, list));
                if (myDBH.getDatabaseSize() == 0){
                    emptyPrompt.setVisibility(View.VISIBLE);
                }
            }
            if(resultCode == 2 && data != null){
                AddData(Objects.requireNonNull(data.getStringExtra("list name")), Objects.requireNonNull(data.getStringExtra("list values")));
                list.clear();
                myDBH.fillArrayList(list);
                myListView.setAdapter(new CustomAdapter(this, list));
                if (myDBH.getDatabaseSize() == 0){
                    emptyPrompt.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}