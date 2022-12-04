package com.RijalJSleepFN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.RijalJSleepFN.model.Payment;
import com.RijalJSleepFN.request.BaseApiService;
import com.RijalJSleepFN.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.*;
public class PaymentActivity extends AppCompatActivity {


    Button orderBtn;
    Context mContext;
    BaseApiService mApiService;
    EditText fromDate, toDate;
    final String REGEX_DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mApiService= UtilsApi.getAPIService();
        mContext = this;

        orderBtn = findViewById(R.id.order);
        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Payment payment = requestPayment(MainActivity.savedAccount.id, MainActivity.savedAccount.renter.id, DetailRoomActivity.tempRoom.id,
                        fromDate.getText().toString(), toDate.getText().toString());
            }
        });


    }

    protected Payment requestPayment(int buyerId, int renterId, int roomId, String fromDate, String toDate) {

        System.out.println(fromDate);
        System.out.println(toDate);

        Pattern pattern = Pattern.compile(REGEX_DATE_PATTERN);
        Matcher matcher = pattern.matcher(fromDate);
        Matcher matcher2 = pattern.matcher(toDate);

        boolean isMatched = matcher.matches();
        boolean isMatched2 = matcher2.matches();



        if(isMatched && isMatched2) {
            mApiService.payment(buyerId, renterId, roomId, fromDate, toDate).enqueue(new Callback<Payment>() {
                @Override
                public void onResponse(Call<Payment> call, Response<Payment> response) {
                    if (response.isSuccessful()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date fromtgl=null;
                        Date totgl=null;
                        try{
                            fromtgl = sdf.parse(fromDate);
                            totgl = sdf.parse(toDate);
                            System.out.println("date  "+totgl);

                        }
                        catch (ParseException e){
                            e.printStackTrace();
                        }
                        long diffInMilliseconds = totgl.getTime() - fromtgl.getTime();
                        long diffInDays = diffInMilliseconds / (1000 * 60 * 60 * 24);

                        MainActivity.savedAccount.balance -= DetailRoomActivity.tempRoom.price.price * diffInDays;
                        Toast.makeText(mContext, "Payment Success", Toast.LENGTH_SHORT).show();
                        Intent move = new Intent(PaymentActivity.this, MainActivity.class);
                        startActivity(move);
                    } else {
                        Toast.makeText(mContext, "Payment Failed1", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Payment> call, Throwable t) {
                    Toast.makeText(mContext, "Payment Failed", Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Toast.makeText(mContext, "Date format is not valid", Toast.LENGTH_SHORT).show();
        }

        return null;
    }
}