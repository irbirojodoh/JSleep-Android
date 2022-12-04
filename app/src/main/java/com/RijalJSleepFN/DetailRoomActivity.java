package com.RijalJSleepFN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.RijalJSleepFN.model.Facility;
import com.RijalJSleepFN.model.Room;

public class DetailRoomActivity extends AppCompatActivity {


    TextView roomName, roomPrice, roomSize, roomAddress, roomBedtype;
    CheckBox ac, refrig, wifi, bathub, balcony, restaurant, pool, fitness;
    Button order;

    public static Room tempRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        roomName = findViewById(R.id.roomNameVar);
        roomPrice = findViewById(R.id.roomPriceVar);
        roomSize = findViewById(R.id.roomSizeVar);
        roomAddress = findViewById(R.id.roomAddressVar);
        roomBedtype = findViewById(R.id.roomBedTypeVar);

        order = findViewById(R.id.order);

        ac = findViewById(R.id.ac);
        refrig = findViewById(R.id.refrigerator);
        wifi = findViewById(R.id.wifi);
        bathub = findViewById(R.id.bathub);
        balcony = findViewById(R.id.balcony);
        restaurant = findViewById(R.id.restaurant);
        pool = findViewById(R.id.pool);
        fitness = findViewById(R.id.fitness);

        roomName.setText(tempRoom.name);
        roomPrice.setText(String.valueOf(tempRoom.price.price));
        roomSize.setText(String.valueOf(tempRoom.size));
        roomAddress.setText(tempRoom.address);
        roomBedtype.setText(tempRoom.bedType.toString());

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(DetailRoomActivity.this, PaymentActivity.class);
                startActivity(move);
            }
        });



        for (int i = 0; i < tempRoom.facility.size(); i++) {
            if (tempRoom.facility.get(i).equals(Facility.AC )) {
                ac.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Refrigerator)) {
                refrig.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.WiFi)) {
                wifi.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Baththub)) {
                bathub.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Balcony)) {
                balcony.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Restaurant)) {
                restaurant.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.SwimmingPool)) {
                pool.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.FitnessCenter)) {
                fitness.setChecked(true);
            }
        }








    }
}