package com.RijalJSleepFN;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.RijalJSleepFN.model.BedType;
import com.RijalJSleepFN.model.City;

public class CreateRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        Spinner bedSpin = (Spinner) findViewById(R.id.bedSpinner);
        Spinner citySpin = (Spinner) findViewById(R.id.citySpinner);

        bedSpin.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));
        citySpin.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, City.values()));





    }
}