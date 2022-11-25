package com.RijalJSleepFN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.RijalJSleepFN.model.Account;
import com.RijalJSleepFN.model.BedType;
import com.RijalJSleepFN.model.City;
import com.RijalJSleepFN.model.Facility;
import com.RijalJSleepFN.model.Price;
import com.RijalJSleepFN.model.Room;
import com.RijalJSleepFN.request.BaseApiService;
import com.RijalJSleepFN.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoomActivity extends AppCompatActivity {


    EditText roomName, roomPrice, roomSize, roomAddress;
    Spinner bedSpin, citySpin;
    Button submitRoom, cancel;
    CheckBox ac, refrig, wifi, bathub, balcony, restaurant, pool, fitness;
    ArrayList<Facility> facility = new ArrayList<Facility>();
    BedType bedType;
    Price price;
    City city;

    BaseApiService mApiService;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        mApiService = UtilsApi.getAPIService();
        mContext = this;


        //SpinnerObject
        bedSpin = (Spinner) findViewById(R.id.bedSpinner);
        citySpin = (Spinner) findViewById(R.id.citySpinner);

        //Button Objcet
        ac = findViewById(R.id.ac);
        refrig = findViewById(R.id.refrigerator);
        wifi = findViewById(R.id.wifi);
        bathub = findViewById(R.id.bathub);
        balcony = findViewById(R.id.balcony);
        restaurant = findViewById(R.id.restaurant);
        pool = findViewById(R.id.pool);
        fitness = findViewById(R.id.fitness);

        //EditText Object
        roomName = findViewById(R.id.nameInput);
        roomPrice = findViewById(R.id.priceInput);
        roomSize = findViewById(R.id.sizeInput);
        roomAddress = findViewById(R.id.addressInput);

        //Button Object
        submitRoom = findViewById(R.id.submitRoom);
        cancel = findViewById(R.id.cancel);

        bedSpin.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));
        citySpin.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, City.values()));

        submitRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ac.isChecked()) {
                    facility.add(Facility.AC);
                }
                if (refrig.isChecked()) {
                    facility.add(Facility.Refrigerator);
                }
                if (wifi.isChecked()) {
                    facility.add(Facility.WiFi);
                }
                if (bathub.isChecked()) {
                    facility.add(Facility.Baththub);
                }
                if (balcony.isChecked()) {
                    facility.add(Facility.Balcony);
                }
                if (restaurant.isChecked()) {
                    facility.add(Facility.Restaurant);
                }
                if (pool.isChecked()) {
                    facility.add(Facility.SwimmingPool);
                }
                if (fitness.isChecked()) {
                    facility.add(Facility.FitnessCenter);
                }
                String bed = bedSpin.getSelectedItem().toString();
                String cityStr = citySpin.getSelectedItem().toString();
                bedType = BedType.valueOf(bed);
                city = City.valueOf(cityStr);

                Integer priceObj = new Integer(roomPrice.getText().toString());
                Integer sizeObj = new Integer(roomSize.getText().toString());

                int priceInt = priceObj.parseInt(roomPrice.getText().toString());
                int sizeInt = sizeObj.parseInt(roomSize.getText().toString());
                //price.price = priceInt;
                Room room = requestRoom(MainActivity.savedAccount.id, roomName.getText().toString(), sizeInt, priceInt, facility, city, roomAddress.getText().toString(), bedType);

            }
        });


    }

    protected Room requestRoom(int id, String name, int size, int price, ArrayList<Facility> facility, City city, String address, BedType bedType) {
        mApiService.room(id, name, size, price, facility, city, address, bedType).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, "Berhasil buat room", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Toast.makeText(mContext, "gagal buat room", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}