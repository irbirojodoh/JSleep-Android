package com.RijalJSleepFN;

import static java.lang.String.*;
import java.util.regex.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


/**
 * @author Rijal
 * @version 1.0
 * @created 30-Mar-2020 11:00:00 AM
 *
 */

public class AboutMe extends AppCompatActivity {
    TextView name, email, balance;
    EditText renterName, renterAddress, renterPhone, topupInput;
    Button regRenter, reg, cancel, topupSubmit, showBooking;
    CardView noRenter, inputRenter, yesRenter;
    TextView renterNameText, renterAddressText, renterPhoneText, renterNameVar, renterAddressVar, renterPhoneVar;
    Context mContext;
    BaseApiService mApiService;

    String regex = "^.+$";



    /**
     This method is called when the AboutMe activity is created. It sets up the views and sets the visibility of the different view groups based on the savedAccount field.
     @param savedInstanceState The saved instance state
     */
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
        balance.setText(String.format("%.0f", MainActivity.savedAccount.balance));

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
        showBooking = findViewById(R.id.showBookings);
        yesRenter = findViewById(R.id.cvYesRenter);
        renterNameText = findViewById(R.id.rtrName);
        renterAddressText = findViewById(R.id.rtrAddress);
        renterPhoneText = findViewById(R.id.rtrPhone);
        renterNameVar = findViewById(R.id.rtrNameVar);
        renterAddressVar = findViewById(R.id.rtrAddressVar);
        renterPhoneVar = findViewById(R.id.rtrPhoneVar);



        //Untuk topup
        topupInput = findViewById(R.id.topupAmount);
        topupSubmit = findViewById(R.id.isiUlang);




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
            showBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AboutMe.this, OrderListActivity.class);
                    startActivity(intent);
                }
            });
        }

        topupSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value = topupInput.getText().toString();
                if (value.isEmpty()) {
                    Toast.makeText(mContext, "Enter top-up amount", Toast.LENGTH_SHORT).show();
                } else{
                    Double topupVal = new Double(topupInput.getText().toString());
                    double finalValue = topupVal.doubleValue();
                    if (finalValue < 10000) {
                        Toast.makeText(mContext, "Minimum top-up amount is Rp. 10.000", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean topup2 = requestTopUp(MainActivity.savedAccount.id, finalValue);
                    }

                }
            }
        });
    }

    /**

     This method sends a request to register a renter with the given ID, username, address, and phone number. It updates the savedAccount field if the request is successful.
     @param id The ID of the renter
     @param uname The username of the renter
     @param addr The address of the renter
     @param phone The phone number of the renter
     @return null
     @throws NullPointerException
     */

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
                    Intent move = new Intent(AboutMe.this, AboutMe.class);
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

    /**

     This method sends a request to top up the balance of the account with the given ID by the given amount. It updates the savedAccount field if the request is successful.
     @param id The ID of the account
     @param balance The amount to top up the balance by
     @return null
     */
    protected Boolean requestTopUp(int id, double balance ){
        System.out.println("Id: " + id);
        System.out.println("TopUp: " + balance);
        mApiService.topUp(id,balance).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Boolean topUpResult = response.body();
                    System.out.println("TOPUP SUCCESSFUL!!") ;
                    //MainActivity.savedAccount.balance += balance;


                    Toast.makeText(mContext, "Top Up Successful", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(AboutMe.this, AboutMe.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("TOPUP ERROR!!");
                System.out.println(t.toString());
                Toast.makeText(mContext,"Top Up Failed",Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }


    /**

     This method creates the options menu for the activity.
     @param menu The menu to be created
     @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**

     This method handles the selection of items in the options menu. If the user selects the "home" item, it navigates to the MainActivity.
     @param item The selected menu item
     @return true if the item is handled by this method, otherwise calls the superclass method
     */

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                Intent move = new Intent(AboutMe.this, MainActivity.class);
                startActivity(move);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**

     This method prepares the options menu by hiding certain items.
     @param menu The options menu to be prepared
     @return true
     */
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

