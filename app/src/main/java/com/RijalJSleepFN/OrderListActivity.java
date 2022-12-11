package com.RijalJSleepFN;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;


import com.RijalJSleepFN.model.*;
import com.RijalJSleepFN.request.BaseApiService;
import com.RijalJSleepFN.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**

 @author Ibrahim Rijal
 This class is used to display the order list
 It contains the onCreate method, which is called when the activity is created,
 as well as the onItemClick method, which is called when an item in the list view is clicked.
 The getPaymentList method is used to retrieve a list of payments for a given account.
 */
public class OrderListActivity extends AppCompatActivity {

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



    /**
     * Called when the activity is created.
     *
     * @param savedInstanceState a bundle containing the saved state of the activity
     */
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



    /**

     Called when an item in the list view is clicked.
     @param l the list view
     @param v the view that was clicked
     @param position the position of the item that was clicked
     @param id the id of the item that was clicked
     */
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


    /**

     Retrieves a list of payments for a given account.
     @param accId the id of the account
     @param page the page number of the list to retrieve
     @param pageSize the number of items per page
     @return a list of payments
     */
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
        mApiServiceStatic.getRoom(id).enqueue(new Callback<Room>() {
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

    /**
     * Method to display menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    /**
     This method is called when a menu item is selected.
     @param item the selected menu item
     @return true if the menu item was handled successfully, false otherwise
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                Intent move = new Intent(OrderListActivity.this, MainActivity.class);
                startActivity(move);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     This method is called to prepare the options menu.
     @param menu the options menu to prepare
     @return true if the menu was prepared successfully, false otherwise
     */
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem register = menu.findItem(R.id.box_add_icon);
        MenuItem refresh = menu.findItem(R.id.refresh);
        MenuItem acc = menu.findItem(R.id.acc_icon);
        MenuItem box = menu.findItem(R.id.box_add_icon);
        MenuItem logout = menu.findItem(R.id.logout);
        logout.setVisible(false);
        register.setVisible(false);
        refresh.setVisible(false);
        acc.setVisible(false);
        box.setVisible(false);
        return true;
    }




}