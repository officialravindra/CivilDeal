package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.civildeal.civildeal.Adapter.CheckVendorBidsAdapter;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.CheckVendorListResponse;
import com.civildeal.civildeal.api.ModelResponse.CheckVendorListResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckInterestedVendors extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView check_interested_vendor_rv;
    String post_id;
    SharedPrefManager sharedPrefManager;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_interested_vendors);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        check_interested_vendor_rv = findViewById(R.id.check_interested_vendor_rv);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        user_id = String.valueOf(sharedPrefManager.getUserId());

        Intent intent = getIntent();
        post_id = intent.getStringExtra("post_id");
        
        getInterestedVendors();
    }

    private void getInterestedVendors() {
        Log.d("tag","post_id "+post_id);
        ProgressDialog progressDialog = new ProgressDialog(CheckInterestedVendors.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Call<CheckVendorListResultResponse> call = Api_Client
                .getInstance()
                .getBidsVendorList(post_id);
        call.enqueue(new Callback<CheckVendorListResultResponse>() {
            @Override
            public void onResponse(Call<CheckVendorListResultResponse> call, Response<CheckVendorListResultResponse> response) {

                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){

                    try {
                        CheckVendorListResultResponse checkVendorListResultResponse = response.body();
                        List<CheckVendorListResponse> vendorsList = checkVendorListResultResponse.getData();
                        CheckVendorBidsAdapter checkVendorBidsAdapter = new CheckVendorBidsAdapter(CheckInterestedVendors.this,vendorsList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(CheckInterestedVendors.this, 1);
                        check_interested_vendor_rv.setLayoutManager(gridLayoutManager);
                        check_interested_vendor_rv.setAdapter(checkVendorBidsAdapter);
                        checkVendorBidsAdapter.notifyDataSetChanged();
                        Log.d("tag","Sucess "+response.body().getMessage());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }

                }else {

                    Log.d("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<CheckVendorListResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("tag","failuer "+t.getLocalizedMessage());

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId()==android.R.id.home){
//            Intent intent = new Intent(Cart.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}