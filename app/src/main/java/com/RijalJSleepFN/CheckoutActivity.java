package com.RijalJSleepFN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.RijalJSleepFN.model.BedType;
import com.RijalJSleepFN.model.Facility;
import com.RijalJSleepFN.model.Invoice;
import com.RijalJSleepFN.model.Payment;
import com.RijalJSleepFN.model.Room;
import com.RijalJSleepFN.request.BaseApiService;
import com.RijalJSleepFN.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    public static Payment tempPayment;
    public static Room checkoutRoom = null;
    TextView roomName, roomPrice, roomSize, roomAddress, roomBedtype, fromDate, toDate, totalPayment, status, rateText;
    CheckBox ac, refrig, wifi, bathub, balcony, restaurant, pool, fitness;
    Button order, cancel, rate;
    static BaseApiService mApiServiceStatic;
    LinearLayout checkout, ratingView;
    CardView rating;
    Spinner ratingSpinner;

    BaseApiService mApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        mContext = this;
        mApiServiceStatic = UtilsApi.getAPIService();
        mApiService = UtilsApi.getAPIService();
        loadRoom(tempPayment.roomId);
        roomName = (TextView) findViewById(R.id.roomNameCheckoutVar);
        roomPrice = (TextView) findViewById(R.id.roomPriceCheckoutVar);
        roomSize = (TextView) findViewById(R.id.roomSizeCheckoutVar);
        roomAddress = (TextView) findViewById(R.id.roomAddressCheckoutVar);
        roomBedtype = (TextView) findViewById(R.id.roomBedTypeCheckoutVar);
        ac = findViewById(R.id.acCheckout);
        refrig = findViewById(R.id.refrigeratorCheckout);
        wifi = findViewById(R.id.wifiCheckout);
        bathub = findViewById(R.id.bathubCheckout);
        balcony = findViewById(R.id.balconyCheckout);
        restaurant = findViewById(R.id.restaurantCheckout);
        pool = findViewById(R.id.poolCheckout);
        fitness = findViewById(R.id.fitnessCheckout);
        order = findViewById(R.id.checkout);
        cancel = findViewById(R.id.cancelCheckout);
        fromDate = findViewById(R.id.fromDateCheckout);
        toDate = findViewById(R.id.toDateCheckout);
        totalPayment = findViewById(R.id.totalPrice);
        status = findViewById(R.id.status);
        checkout = findViewById(R.id.cancelAccept);
        rating = findViewById(R.id.cvRating);
        ratingSpinner = findViewById(R.id.spRating);
        rate = findViewById(R.id.rateButton);
        rateText = findViewById(R.id.rateText);
        ratingView = findViewById(R.id.ratingView);


        if(!tempPayment.status.equals(Invoice.PaymentStatus.WAITING)){
            order.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
        }
        if(tempPayment.rating.equals(Invoice.RoomRating.NONE)) {
            ratingView.setVisibility(View.GONE);
        }


        try {
            roomName.setText(checkoutRoom.name);
            roomPrice.setText(String.valueOf(checkoutRoom.price.price));
            roomSize.setText(String.valueOf(checkoutRoom.size));
            roomAddress.setText(checkoutRoom.address);
            roomBedtype.setText(checkoutRoom.bedType.toString());
            fromDate.setText(tempPayment.from.toString().substring(0,10));
            toDate.setText(tempPayment.to.toString().substring(0,10));
            status.setText(tempPayment.status.toString());
            rateText.setText(tempPayment.rating.toString());

            long diffInMilliseconds = tempPayment.to.getTime() - tempPayment.from.getTime();
            long diffInDays = diffInMilliseconds / (1000 * 60 * 60 * 24);
            totalPayment.setText(String.valueOf(diffInDays * checkoutRoom.price.price));

            for (int i = 0; i < checkoutRoom.facility.size(); i++) {
                if (checkoutRoom.facility.get(i).equals(Facility.AC)) {
                    ac.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.Refrigerator)) {
                    refrig.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.WiFi)) {
                    wifi.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.Baththub)) {
                    bathub.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.Balcony)) {
                    balcony.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.Restaurant)) {
                    restaurant.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.SwimmingPool)) {
                    pool.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.FitnessCenter)) {
                    fitness.setChecked(true);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("data isnt loaded");
            Intent move3 = new Intent(CheckoutActivity.this, CheckoutActivity.class);
            startActivity(move3);
        }


        if (tempPayment.status.equals(Invoice.PaymentStatus.SUCCESS)){
            rating.setVisibility(View.VISIBLE);
            checkout.setVisibility(View.GONE);
            ratingSpinner.setAdapter(new ArrayAdapter<Invoice.RoomRating>(this, android.R.layout.simple_spinner_item, Invoice.RoomRating.values()));



            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String rateStr = ratingSpinner.getSelectedItem().toString();
                    System.out.println(rateStr);
                    requestRating(tempPayment.id, rateStr);
                    finish();
                    startActivity(getIntent());

                }
            });

        }




        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestAccept(tempPayment.id);
                Intent move3 = new Intent(CheckoutActivity.this, OrderDetailActivity.class);
                startActivity(move3);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 cancelDialog();

            }
        });


    }

    protected static  Room loadRoom(int id){
        mApiServiceStatic.brumbrum(id).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    CheckoutActivity.checkoutRoom = response.body();
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
        return CheckoutActivity.checkoutRoom;
    }

    protected Boolean requestCancel(int id){
        mApiServiceStatic.cancelPayment(id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    System.out.println("Payment cancelled");
                    Toast.makeText(mContext, "Payment cancelled", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(mContext, "getAccount success", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(mContext, "get account failed", Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    protected Boolean requestAccept(int id){
        mApiServiceStatic.acceptPayment(id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    System.out.println("Payment Accepted");
                    Toast.makeText(mContext, "Payment Accepted", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(mContext, "get account failed", Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    protected Boolean requestRating(int id, String rating){
        mApiService.rating(id, rating).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    System.out.println("Room rated");
                    Toast.makeText(mContext, "Room rated", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(mContext, "get account failed", Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }


    private void cancelDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Are you sure you want to cancel the order?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Press yes to cancel")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        requestCancel(tempPayment.id);
                        Intent move = new Intent(CheckoutActivity.this, AboutMe.class);
                        startActivity(move);


                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }




}