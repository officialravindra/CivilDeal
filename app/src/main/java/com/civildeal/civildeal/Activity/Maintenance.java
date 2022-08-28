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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.MaintenanceVendorListAdapter;
import com.civildeal.civildeal.Prefrences.SharedPrefForLocation;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.ProfileResponse;
import com.civildeal.civildeal.api.ModelResponse.ProfileResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Maintenance extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView Rv;
    List<ProfileResultResponse> vendorList;
    String maintenance_id;
    SharedPrefManager sharedPrefManager;
    String vendor_type,city_id;
    SharedPrefForLocation sharedPrefForLocation;
    LinearLayout layout;
    ImageView noFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        vendor_type = sharedPrefManager.getUserType().toString();

        sharedPrefForLocation = new SharedPrefForLocation(Maintenance.this);
        city_id = sharedPrefForLocation.getSelectedLocationId();

        Intent intent = getIntent();
        maintenance_id = intent.getStringExtra("maintenance_id");
//        Toast.makeText(this, ""+maintenance_id, Toast.LENGTH_SHORT).show();

        layout = findViewById(R.id.layout);
        noFound = findViewById(R.id.nofound);
        Rv=findViewById(R.id.serviceRV);

        getVendorList();


    }

    private void getVendorList() {

        ProgressDialog progressDialog = new ProgressDialog(Maintenance.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<ProfileResultResponse> call = Api_Client
                .getInstance()
                .vendorlist("maintenance",maintenance_id,city_id);
        call.enqueue(new Callback<ProfileResultResponse>() {
            @Override
            public void onResponse(Call<ProfileResultResponse> call, Response<ProfileResultResponse> response) {

                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){
                    try {
                        ProfileResultResponse profileResultResponse = response.body();
                        List<ProfileResponse> vendorList = profileResultResponse.getData();
                        Log.e("tag",""+response.body().getMessage());
                        MaintenanceVendorListAdapter maintenanceVendorListAdapter = new MaintenanceVendorListAdapter(Maintenance.this,vendorList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(Maintenance.this,1);
                        Rv.setLayoutManager(gridLayoutManager);
                        Rv.setAdapter(maintenanceVendorListAdapter);
                        maintenanceVendorListAdapter.notifyDataSetChanged();
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }



                }else {
                    noFound.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.VISIBLE);
//                    Toast.makeText(Maintenance.this, "No Vendor Found For Selected Location", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResultResponse> call, Throwable t) {
                progressDialog.dismiss();
//                Toast.makeText(Maintenance.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("tag","fail "+t.getLocalizedMessage());

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(Maintenance.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//            Intent intent = new Intent(Maintenance.this, MainActivity.class);
//            startActivity(intent);
            finish();
        super.onBackPressed();
    }
}