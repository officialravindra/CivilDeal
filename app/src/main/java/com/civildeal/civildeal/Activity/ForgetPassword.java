package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.OtpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {

    Toolbar toolbar;
    EditText phone_number_edt;
    Button submit;
    public static String str_phone;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phone_number_edt = findViewById(R.id.phone_number_edt);
        submit = findViewById(R.id.btn_otp);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_phone = phone_number_edt.getText().toString().trim();
                if (str_phone != null && str_phone.length() != 10) {
                    phone_number_edt.setError("Please enter your phone number");

                } else {
                    getOTP();
                }

            }
        });
    }

    private void getOTP() {
        ProgressDialog progressDialog = new ProgressDialog(ForgetPassword.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Updating your Password...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<OtpResponse> call = Api_Client
                .getInstance()
                .getOtp(str_phone);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {

                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){

                    OtpResponse otpResponse = response.body();

                    Intent i = new Intent(ForgetPassword.this, OTPVerifyActivity.class);
                    i.putExtra("otp",otpResponse.getOtp().toString());
                    i.putExtra("mobile_no",str_phone);
                    startActivity(i);
                    finish();
                    Log.e("tag",""+response.body().getMessage());
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(ForgetPassword.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("tag",""+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                progressDialog.dismiss();

                Log.e("tag",""+t.getLocalizedMessage());

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(ForgetPassword.this, Login.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}