package com.RijalJSleepFN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.RijalJSleepFN.model.Facility;
import com.RijalJSleepFN.model.Room;


/**
 * DetailRoomActivity is an activity that displays detailed information about a room,
 * such as its name, price, size, address, bed type, and facilities.
 * The user can also place an order for the room from this activity.
 *
 * @author Ibrahim Rijal
 */
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
            if (tempRoom.facility.get(i).equals(Facility.AC)) {
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                Intent move = new Intent(DetailRoomActivity.this, MainActivity.class);
                startActivity(move);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem register = menu.findItem(R.id.box_add_icon);
        MenuItem refresh = menu.findItem(R.id.refresh);
        MenuItem acc = menu.findItem(R.id.acc_icon);
        MenuItem box = menu.findItem(R.id.box_add_icon);
        MenuItem search = menu.findItem(R.id.search_button);
        search.setVisible(false);
        register.setVisible(false);
        refresh.setVisible(false);
        acc.setVisible(false);
        box.setVisible(false);
        return true;
    }
}