package com.RijalJSleepFN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.RijalJSleepFN.model.Account;
import com.RijalJSleepFN.model.Payment;
import com.RijalJSleepFN.request.BaseApiService;
import com.RijalJSleepFN.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.regex.*;


public class PaymentActivity extends AppCompatActivity {


    Button orderBtn;
    TextView roomName;
    Context mContext;
    BaseApiService mApiService;
    DatePickerDialog picker1, picker2;
    EditText fromDate, toDate;
    final String REGEX_DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    private String m_Text = "";
    private Account temp;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    /**
     * Method to initialize the activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mApiService= UtilsApi.getAPIService();
        mContext = this;
        roomName = findViewById(R.id.roomName);
        roomName.setText(DetailRoomActivity.tempRoom.name);
        orderBtn = findViewById(R.id.order);
        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        fromDate.setInputType(InputType.TYPE_NULL);
        toDate.setInputType(InputType.TYPE_NULL);



        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.


        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldrFrom = Calendar.getInstance();
                int dayDrom = cldrFrom.get(Calendar.DAY_OF_MONTH);
                int monthFrom = cldrFrom.get(Calendar.MONTH);
                int yearFrom = cldrFrom.get(Calendar.YEAR);
                // date picker dialog
                picker1 = new DatePickerDialog(PaymentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                fromDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, yearFrom, monthFrom, dayDrom);
                picker1.show();
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker2 = new DatePickerDialog(PaymentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                toDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker2.show();
            }
        });
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);

            }
        });





    }

    /**
     *
     *
     * @param buyerId
     * @param renterId
     * @param roomId
     * @param fromDate
     * @param toDate
     * @return Payment
     */
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
                       // MainActivity.reloadAccount(MainActivity.savedAccount.id);
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


    /**
     * Method to display menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    /**
     This method is called when a menu item is selected.
     @param item the selected menu item
     @return true if the menu item was handled successfully, false otherwise
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                Intent move = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(move);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     This method is called to prepare the options menu.
     @param menu the options menu to prepare
     @return true if the menu was prepared successfully, false otherwise
     */
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem register = menu.findItem(R.id.box_add_icon);
        MenuItem refresh = menu.findItem(R.id.refresh);
        MenuItem acc = menu.findItem(R.id.acc_icon);
        MenuItem box = menu.findItem(R.id.box_add_icon);
        MenuItem logout = menu.findItem(R.id.logout);
        logout.setVisible(false);
        register.setVisible(false);
        refresh.setVisible(false);
        acc.setVisible(false);
        box.setVisible(false);
        return true;
    }




}