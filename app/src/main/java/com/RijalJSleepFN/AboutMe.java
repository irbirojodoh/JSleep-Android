package com.RijalJSleepFN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.RijalJSleepFN.model.Account;
import com.RijalJSleepFN.model.Renter;
import com.RijalJSleepFN.request.BaseApiService;
import com.RijalJSleepFN.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AboutMe extends AppCompatActivity {
    TextView name, email, balance;
    EditText renterName, renterAddress, renterPhone;
    Button regRenter, reg, cancel;
    CardView noRenter, inputRenter, yesRenter;
    TextView renterNameText, renterAddressText, renterPhoneText, renterNameVar, renterAddressVar, renterPhoneVar;
    Context mContext;
    BaseApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mApiService= UtilsApi.getAPIService();
        mContext = this;



        //Untuk detail akun
        name = findViewById(R.id.nickname);
        email = findViewById(R.id.emailDetails);
        balance = findViewById(R.id.balance);
        name.setText(MainActivity.savedAccount.name);
        email.setText(MainActivity.savedAccount.email);
        balance.setText(String.valueOf(MainActivity.savedAccount.balance));

        //Belom ada renter
        noRenter = findViewById(R.id.cvNoRenter);
        regRenter = findViewById(R.id.registerRenter);

        //Ngisi renter
        inputRenter = findViewById(R.id.cvInputRenter);
        renterName = findViewById(R.id.rtrNameInput);
        renterAddress = findViewById(R.id.rtrAddressInput);
        renterPhone = findViewById(R.id.rtrPhoneInput);
        reg = findViewById(R.id.submitRenter);
        cancel = findViewById(R.id.cancelRenter);

        //Sudah ada renter
        yesRenter = findViewById(R.id.cvYesRenter);
        renterNameText = findViewById(R.id.rtrName);
        renterAddressText = findViewById(R.id.rtrAddress);
        renterPhoneText = findViewById(R.id.rtrPhone);
        renterNameVar = findViewById(R.id.rtrNameVar);
        renterAddressVar = findViewById(R.id.rtrAddressVar);
        renterPhoneVar = findViewById(R.id.rtrPhoneVar);


        if (MainActivity.savedAccount.renter == null) {
            noRenter.setVisibility(CardView.VISIBLE);
            inputRenter.setVisibility(CardView.GONE);
            yesRenter.setVisibility(CardView.GONE);

            //Kalo tombol register renter dipencet pas belom ada renter
            regRenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noRenter.setVisibility(CardView.GONE);
                    inputRenter.setVisibility(CardView.VISIBLE);
                    yesRenter.setVisibility(CardView.GONE);

                    reg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Renter renter = requestRenter(MainActivity.savedAccount.id, renterName.getText().toString(), renterAddress.getText().toString(), renterPhone.getText().toString());
                            //Renter renter = requestRenter(1, "Bachul69", "Jalan janda bali", "081234567890");

                            noRenter.setVisibility(CardView.GONE);
                            inputRenter.setVisibility(CardView.GONE);
                            yesRenter.setVisibility(CardView.VISIBLE);

                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            noRenter.setVisibility(CardView.VISIBLE);
                            inputRenter.setVisibility(CardView.GONE);
                            yesRenter.setVisibility(CardView.GONE);
                        }
                    });



                }
            });
        }
        else {
            noRenter.setVisibility(CardView.GONE);
            inputRenter.setVisibility(CardView.GONE);
            yesRenter.setVisibility(CardView.VISIBLE);
            renterNameVar.setText(MainActivity.savedAccount.renter.username);
            renterAddressVar.setText(MainActivity.savedAccount.renter.address);
            renterPhoneVar.setText(MainActivity.savedAccount.renter.phoneNumber);
        }
    }

    protected Renter requestRenter(int id, String uname, String addr, String phone ) throws NullPointerException {
        System.out.println("Id: " + id);
        System.out.println("Username: " + uname);
        System.out.println("Address: " + addr);
        System.out.println("Phone: " + phone);
        mApiService.renter(id, uname, addr, phone).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if(response.isSuccessful()){
                    Renter renter = response.body();
                    MainActivity.savedAccount.renter = renter;
                    Toast.makeText(mContext, "Berhasil register renter", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(AboutMe.this, MainActivity.class);
                    startActivity(move);

                }
            }

            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                Toast.makeText(mContext, "gagal register renter", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }


}

