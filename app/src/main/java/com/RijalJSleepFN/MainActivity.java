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
    public static Account savedAccount2;

    List<String> nameStr;
    static List<String> roomName = new ArrayList<>();
    List<Room> temp ;
    List<Room> acc ;
    ListView lv;
    BaseApiService mApiService;
    static BaseApiService mApiServiceStatic;
    Context mContext = this;
    Button next, prev;
    int currentPage;
    boolean count = false;
    Account empty;


    /**
     * Initializes the activity.
     *
     * @param savedInstanceState a bundle containing the state of the activity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getAPIService();
        mApiServiceStatic = UtilsApi.getAPIService();

        next = findViewById(R.id.nextButton);
        prev = findViewById(R.id.prevButton);


        lv = findViewById(R.id.listViewMain);
        lv.setOnItemClickListener(this::onItemClick);


        System.out.println("test");

        acc = getRoomList(0,10);



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
    }
    /**
     * Initializes the options menu for this activity.
     *
     * @param menu the options menu to initialize
     * @return true if the menu was successfully created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    /**
     * Handles menu item selection.
     *
     * @param item The selected menu item.
     *
     * @return True if the menu item was successfully handled, false otherwise.
     */
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
            case R.id.refresh:
                finish();
                startActivity(getIntent());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**

     This method is called when the options menu is being prepared for display.
     @param menu The options menu to be prepared
     @return true if the menu was successfully prepared, false otherwise
     */
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem register = menu.findItem(R.id.box_add_icon);
        MenuItem home = menu.findItem(R.id.home);
        home.setVisible(false);
        if(savedAccount.renter == null){
            register.setVisible(false);
        }
        else {
            register.setVisible(true);
        }
        return true;
    }


    /**

     This method retrieves a list of rooms from the API and updates the list view with the names of the rooms.
     @param page The page number to retrieve
     @param pageSize The number of rooms per page
     @return null
     */
    protected List<Room> getRoomList(int page, int pageSize) {
        //System.out.println(pageSize);
        mApiService.getAllRoom(page, pageSize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    temp = response.body();
                    nameStr = getName(temp);
                    roomName.addAll(nameStr);
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


    /**
     This static method retrieves an account with the given ID from the API and updates the savedAccount field.
     @param id The ID of the account to retrieve
     @return null
     */
    public static Account reloadAccount(int id){
        mApiServiceStatic.getAccount(id).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                     MainActivity.savedAccount = response.body();
                     MainActivity.savedAccount2 = response.body();
                    //Toast.makeText(mContext, "getAccount success", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(mContext, "get account failed", Toast.LENGTH_SHORT).show();
            }
        });

        try {
            System.out.println("Saved 2" + savedAccount2.name);
        }catch(NullPointerException e){
            System.out.println("NILLL");
        }
        return null;
    }

    /**

     This static method returns a list of strings containing the names of rooms in the given list.
     @param list The list of rooms
     @return An ArrayList of strings containing the names of the rooms
     */
    public static ArrayList<String> getName(List<Room> list) {
        ArrayList<String> ret = new ArrayList<String>();
        int i;
        for (i = 0; i < list.size(); i++) {
            ret.add(list.get(i).name);
        }
        return ret;
    }


    /**

     This method is called when an item in the list view is clicked. It starts the DetailRoomActivity and passes the position and id of the clicked item as extra data.
     @param l The AdapterView where the click happened
     @param v The view within the AdapterView that was clicked
     @param position The position of the view in the adapter
     @param id The row id of the item that was clicked
     */
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









