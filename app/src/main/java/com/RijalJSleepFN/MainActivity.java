package com.RijalJSleepFN;

import androidx.appcompat.app.AppCompatActivity;

import com.RijalJSleepFN.model.*;
import java.lang.*;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;


import java.io.*;
import java.util.*;



public class MainActivity extends AppCompatActivity {

    String name;
    static ArrayList<Room> roomList = new ArrayList<Room>();

    public static Account savedAccount;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Gson gson = new Gson();
       // File file = new File(getFilesDir(), "randomRoomList.json");
        MenuItem item;

        try {

            InputStream filepath = getAssets().open("randomRoomList.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));

            Room[] roomTemp = gson.fromJson(reader, Room[].class);
            Collections.addAll(roomList, roomTemp);
        } catch (Exception e) {
            e.printStackTrace();
        }


        ArrayList<String> names = new ArrayList<String>();
        for (Room room : roomList) {
            names.add(room.name);
        }
        ArrayAdapter<String > arrayAdapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                names );
        ListView lv = (ListView) findViewById(R.id.listViewMain);
        lv.setAdapter(arrayAdapter);

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.acc_icon:
                Intent move = new Intent(MainActivity.this, AboutMe.class);
                startActivity(move);
                return true;
            case R.id.box_add_icon:
                Intent move2 = new Intent(MainActivity.this, CreateRoomActivity.class);
                startActivity(move2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem register = menu.findItem(R.id.box_add_icon);
        if(savedAccount.renter == null){
            register.setVisible(false);
        }
        else {
            register.setVisible(true);
        }
        return true;
    }

}









