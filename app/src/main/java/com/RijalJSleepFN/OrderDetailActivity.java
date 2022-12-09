package com.RijalJSleepFN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.RijalJSleepFN.model.*;
import com.RijalJSleepFN.request.BaseApiService;
import com.RijalJSleepFN.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    Context mContext;
    BaseApiService mApiService;
    static BaseApiService mApiServiceStatic;
    List<String> nameStr;
    List<Payment> temp ;
    List<Payment> acc ;
    ListView lv;
    Button next, prev;
    public static Room tempRoom = null;
    int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentPage= 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        mApiService= UtilsApi.getAPIService();
        mApiServiceStatic= UtilsApi.getAPIService();

        mContext=this;
        lv = findViewById(R.id.listViewOrder);
        next = findViewById(R.id.btnNext);
        prev = findViewById(R.id.btnBack);
        lv.setOnItemClickListener(this::onItemClick);
        System.out.println("gap sblm acc");
        acc = getPaymentList(MainActivity.savedAccount.id, 0, 10);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(temp.size()>currentPage){
                    currentPage=1;
                    return;
                }
                currentPage++;
                try {
                    acc = getPaymentList(MainActivity.savedAccount.id, currentPage-1, 10);  //return null
                    Toast.makeText(mContext, "Page "+currentPage, Toast.LENGTH_SHORT).show();
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
                    acc = getPaymentList(MainActivity.savedAccount.id, currentPage-1, 10);  //return null
                    Toast.makeText(mContext, "page "+currentPage, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }


    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        Intent intent = new Intent();
        intent.setClass(this, CheckoutActivity.class);
        CheckoutActivity.tempPayment = temp.get(position);
        intent.putExtra("position", position);
        // Or / And
        intent.putExtra("id", id);
        startActivity(intent);
    }

    protected List<Payment> getPaymentList(int accId, int page, int pageSize){
        mApiService.getRoomByRenter(accId,page, pageSize).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (response.isSuccessful()) {
                    temp = response.body();
                    nameStr = getName(temp);
                    System.out.println("name extracted"+temp.toString());
                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,nameStr);
                    System.out.println("gap1");
                    lv = (ListView) findViewById(R.id.listViewOrder);
                    System.out.println("listview"+lv.toString());
                    lv.setAdapter(itemAdapter);
                    System.out.println("display lv");
                    Toast.makeText(mContext, "getRoom success", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "get room failed", Toast.LENGTH_SHORT).show();
            }


        });

        return null;
    }

    protected static  Room loadRoom(int id){
        Room test;
        mApiServiceStatic.brumbrum(id).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    tempRoom = response.body();
                    System.out.println("Room loaded");
                    //Toast.makeText(mContext, "getAccount success", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(mContext, "get account failed", Toast.LENGTH_SHORT).show();
            }
        });
        return tempRoom;
    }


    public ArrayList<String> getName(List<Payment> list) {
        ArrayList<String> ret = new ArrayList<String>();
        int i;
        String fromDate, toDate;
            for (i = 0; i < list.size(); i++) {
                System.out.println("RoomID" + list.get(i).roomId);
                ret.add(MainActivity.roomName.get(list.get(i).roomId));

            }

        return ret;
    }




}