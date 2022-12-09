package com.RijalJSleepFN;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.RijalJSleepFN.model.Account;
import com.RijalJSleepFN.request.BaseApiService;
import com.RijalJSleepFN.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {


    BaseApiService mApiService;
    EditText name, username, password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService= UtilsApi.getAPIService();
        mContext = this;

        Button register = findViewById(R.id.registerPageRegisterButton);
        name = findViewById(R.id.registerNameInput);
        username = findViewById(R.id.registerEmailInput);
        password = findViewById(R.id.registerPagePassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Account account = requestRegister(name.getText().toString(),username.getText().toString(), password.getText().toString());
            }
        });



    }

    protected Account requestRegister(String name, String email, String password){
        mApiService.register(name, email, password).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Berhasil register", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(move);

                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "gagal register", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}