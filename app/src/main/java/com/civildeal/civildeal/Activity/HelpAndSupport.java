package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.civildeal.civildeal.Prefrences.SharedPrefForLocation;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.ContactUsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpAndSupport extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv_contact_us_no,emailus;
    ImageView img_call;
    EditText contact_us;
    Button submit;
    String vendor_id,email,mobile,name,location;
    SharedPrefManager sharedPrefManager;
    SharedPrefForLocation sharedPrefForLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPrefForLocation = new SharedPrefForLocation(HelpAndSupport.this);
        img_call = findViewById(R.id.img_call);
        tv_contact_us_no = findViewById(R.id.tv_contact_us_no);
        contact_us = findViewById(R.id.editfeedback);
        submit = findViewById(R.id.btn_contact_us);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        vendor_id = String.valueOf(sharedPrefManager.getUserId());
        mobile = sharedPrefManager.getUserMobile();
        email = sharedPrefManager.getEmail();
        name = sharedPrefManager.getUserName();
        location = sharedPrefManager.getLocation();
        Log.e("tag","help "+location);


        tv_contact_us_no.setText(Constant.contact_us_no);

        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + Constant.contact_us_no);
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    startActivity(i);
                } catch (SecurityException s) {
                    s.printStackTrace();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contact_us.getText().toString().isEmpty()){
                    contact_us.requestFocus();
                    contact_us.setError("Please Give your feedback");
                    return;

                }else {

                    sendFeedback();
                }
            }
        });
    }

    private void sendFeedback() {
        ProgressDialog progressDialog = new ProgressDialog(HelpAndSupport.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("We are processing...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Call<ContactUsResponse> call = Api_Client
                .getInstance().sendFeedback(vendor_id,contact_us.getText().toString(),name,mobile,email,location);
        call.enqueue(new Callback<ContactUsResponse>() {
            @Override
            public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {
                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){

                    Toast.makeText(HelpAndSupport.this, "Sucessfully Submitted", Toast.LENGTH_SHORT).show();
                    contact_us.setText("");
                }
                else {
                    Toast.makeText(HelpAndSupport.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("tag",""+t.getLocalizedMessage());

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(HelpAndSupport.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}