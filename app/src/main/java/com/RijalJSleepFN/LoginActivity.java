package com.RijalJSleepFN;

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

public class LoginActivity extends AppCompatActivity {

    BaseApiService mApiService;
    EditText username, password;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService= UtilsApi.getAPIService();
        mContext = this;
        Button register = findViewById(R.id.loginRegisterButton);
        Button login = findViewById(R.id.loginPageLoginButton);
        username = findViewById(R.id.loginEmailInput);
        password = findViewById(R.id.loginPagePassword);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestLogin(username.getText().toString(), password.getText().toString());

            }
        });

    }
/*
    protected Account requestAccount(){
        mApiService.getAccount(0).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account = response.body();
                    Toast.makeText(mContext, "Berhasil login bos", Toast.LENGTH_SHORT).show();
                    System.out.println(account.toString());

                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println("sad");
                Toast.makeText(mContext, "No account id = 0", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

 */

    protected Account requestLogin(String email, String password){
        mApiService.login(email, password).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account = response.body();
                    MainActivity.savedAccount = account;
                    Toast.makeText(mContext, "Halo gan", Toast.LENGTH_SHORT).show();
                    System.out.println(account.toString());
                    Intent move = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(move);


                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println("sad");
                Toast.makeText(mContext, "Gagal gan", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}