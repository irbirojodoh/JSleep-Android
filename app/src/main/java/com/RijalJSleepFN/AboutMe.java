package com.RijalJSleepFN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


public class AboutMe extends AppCompatActivity {
    TextView name, email, balance;
    Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        name = findViewById(R.id.nickname);
        email = findViewById(R.id.emailDetails);
        balance = findViewById(R.id.balance);

        name.setText(MainActivity.savedAccount.name);
        email.setText(MainActivity.savedAccount.email);
        balance.setText(String.valueOf(MainActivity.savedAccount.balance));
    }
}