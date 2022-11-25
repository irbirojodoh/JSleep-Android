package com.RijalJSleepFN;

import androidx.appcompat.app.AppCompatActivity;

import com.RijalJSleepFN.model.*;
import java.lang.*;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.RijalJSleepFN.request.BaseApiService;
import com.RijalJSleepFN.request.UtilsApi;
import com.google.gson.Gson;
import java.io.*;
import java.util.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    String name;
    static ArrayList<Room> roomList = new ArrayList<Room>();

    public static Account savedAccount;

    List<String> nameStr;
    List<Room> temp ;
    List<Room> acc ;
    ListView lv;
    BaseApiService mApiService;
    Context mContext;
    Button next, prev;
    int currentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getAPIService();
        mContext = this;
        next = findViewById(R.id.nextButton);
        prev = findViewById(R.id.prevButton);


        lv = findViewById(R.id.listViewMain);
        lv.setOnItemClickListener(this::onItemClick);


        System.out.println("test");
        acc = getRoomList(10,10);





        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(temp.size()>currentPage){
                    currentPage=1;
                    return;
                }
                currentPage++;
                try {
                    acc = getRoomList(currentPage-1, 1);  //return null
                    Toast.makeText(mContext, "page "+currentPage, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPage<=1){
                    currentPage=1;
                    Toast.makeText(mContext, "this is the first page", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentPage--;
                try {
                    acc = getRoomList(currentPage-1, 1);  //return null
                    Toast.makeText(mContext, "page "+currentPage, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });






        /*
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

         */

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

    protected List<Room> getRoomList(int page, int pageSize) {
        //System.out.println(pageSize);
        mApiService.getAllRoom(page, pageSize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    temp = response.body();
                    nameStr = getName(temp);
                    System.out.println("name extracted"+temp.toString());
                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,nameStr);
                    lv = (ListView) findViewById(R.id.listViewMain);
                    lv.setAdapter(itemAdapter);
                    Toast.makeText(mContext, "getRoom success", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "get room failed", Toast.LENGTH_SHORT).show();
            }

        });
        return null;
    }

    public static ArrayList<String> getName(List<Room> list) {
        ArrayList<String> ret = new ArrayList<String>();
        int i;
        for (i = 0; i < list.size(); i++) {
            ret.add(list.get(i).name);
        }
        return ret;
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        Intent intent = new Intent();
        intent.setClass(this, DetailRoomActivity.class);
        DetailRoomActivity.tempRoom = temp.get(position);
        intent.putExtra("position", position);
        // Or / And
        intent.putExtra("id", id);
        startActivity(intent);
    }

}









