package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.civildeal.civildeal.Adapter.MaintenanceAddedLeadAdapter;
import com.civildeal.civildeal.Adapter.ProductAddedLeadAdapter;
import com.civildeal.civildeal.Adapter.ServiceAddedLeadAdapter;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceLeadByPromoterResponse;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceLeadByPromoterResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductLeadByPromoterResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductLeadByPromoterResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceLeadByPromoterResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceLeadByPromoterResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoterAddedLead extends AppCompatActivity {

    Toolbar toolbar;
    SharedPrefManager sharedPrefManager;
    RecyclerView serviceLeadRv,productLeadRv,maintenanceLeadRV;
    String user_id,Type_Selected;
    Spinner typeSpinner;
    String[] type;
    TextView service,product,maintenance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter_added_lead);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        serviceLeadRv = findViewById(R.id.serviceLeadRV);
        productLeadRv = findViewById(R.id.productLeadRV);
        maintenanceLeadRV = findViewById(R.id.maintenanceLeadRV);
        typeSpinner = findViewById(R.id.typeSpinner);
        service = findViewById(R.id.ServiceText);
        product = findViewById(R.id.productText);
        maintenance = findViewById(R.id.maintenanceText);

        type = getResources().getStringArray(R.array.selectLeadType);
        ArrayAdapter typeAdapter = new ArrayAdapter(PromoterAddedLead.this, android.R.layout.simple_spinner_item,type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);


        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                int pos = listType.indexOf(typeadapter.getItem(i));

                Type_Selected = type[i];
                typeSpinner.setSelection(i);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Type_Selected = type[0];
                typeSpinner.setSelection(0);
            }
        });

        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        user_id = String.valueOf(sharedPrefManager.getUserId());

        getServiceLead();
        getProductLead();
        getMaintenanceLead();
    }

    private void getMaintenanceLead() {
        ProgressDialog progressDialog = new ProgressDialog(PromoterAddedLead.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Call<MaintenanceLeadByPromoterResultResponse> call = Api_Client
                .getInstance()
                .getMaintenanceAddList(user_id);
        call.enqueue(new Callback<MaintenanceLeadByPromoterResultResponse>() {
            @Override
            public void onResponse(Call<MaintenanceLeadByPromoterResultResponse> call, Response<MaintenanceLeadByPromoterResultResponse> response) {
                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){
                    try {
                        maintenance.setVisibility(View.VISIBLE);
                        MaintenanceLeadByPromoterResultResponse maintenanceLeadByPromoterResultResponse = response.body();
                        List<MaintenanceLeadByPromoterResponse> maintenanceAddedLeadList = maintenanceLeadByPromoterResultResponse.getData();
                        MaintenanceAddedLeadAdapter maintenanceAddedLeadAdapter = new MaintenanceAddedLeadAdapter(PromoterAddedLead.this, maintenanceAddedLeadList);
                        LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(PromoterAddedLead.this, RecyclerView.HORIZONTAL, false);
                        maintenanceLeadRV.setLayoutManager(mlinearLayoutManager);
                        maintenanceLeadRV.setAdapter(maintenanceAddedLeadAdapter);
                        maintenanceAddedLeadAdapter.notifyDataSetChanged();
                        Log.d("tag","Sucess "+response.body().getMessage());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }


                }else {

                    Log.e("tag",""+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<MaintenanceLeadByPromoterResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("tag","failuer"+t.getLocalizedMessage());

            }
        });

    }

    private void getProductLead() {


        Call<ProductLeadByPromoterResultResponse> call = Api_Client
                .getInstance()
                .getProductAddList(user_id);
        call.enqueue(new Callback<ProductLeadByPromoterResultResponse>() {
            @Override
            public void onResponse(Call<ProductLeadByPromoterResultResponse> call, Response<ProductLeadByPromoterResultResponse> response) {

                if (response.body().getCode().equals(200)){
                    try {
                        product.setVisibility(View.VISIBLE);
                        ProductLeadByPromoterResultResponse productLeadByPromoterResultResponse = response.body();
                        List<ProductLeadByPromoterResponse> productAddedLeadList = productLeadByPromoterResultResponse.getData();
                        ProductAddedLeadAdapter productAddedLeadAdapter = new ProductAddedLeadAdapter(PromoterAddedLead.this, productAddedLeadList);
                        LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(PromoterAddedLead.this, RecyclerView.HORIZONTAL, false);
                        productLeadRv.setLayoutManager(mlinearLayoutManager);
                        productLeadRv.setAdapter(productAddedLeadAdapter);
                        productAddedLeadAdapter.notifyDataSetChanged();
                        Log.d("tag","Sucess "+response.body().getMessage());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }


                }else {

                    Log.e("tag",""+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ProductLeadByPromoterResultResponse> call, Throwable t) {
                Log.d("tag","failuer"+t.getLocalizedMessage());

            }
        });

    }

    private void getServiceLead() {

        Call<ServiceLeadByPromoterResultResponse> call = Api_Client
                .getInstance()
                .getServiceAddList(user_id);
        call.enqueue(new Callback<ServiceLeadByPromoterResultResponse>() {
            @Override
            public void onResponse(Call<ServiceLeadByPromoterResultResponse> call, Response<ServiceLeadByPromoterResultResponse> response) {

                if (response.body().getCode().equals(200)){
                    try {
                        service.setVisibility(View.VISIBLE);
                        ServiceLeadByPromoterResultResponse serviceLeadByPromoterResultResponse = response.body();
                        List<ServiceLeadByPromoterResponse> serviceAddedLeadList = serviceLeadByPromoterResultResponse.getData();
                        ServiceAddedLeadAdapter serviceAddedLeadAdapter = new ServiceAddedLeadAdapter(PromoterAddedLead.this, serviceAddedLeadList);
                        LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(PromoterAddedLead.this, RecyclerView.HORIZONTAL, false);
                        serviceLeadRv.setLayoutManager(mlinearLayoutManager);
                        serviceLeadRv.setAdapter(serviceAddedLeadAdapter);
                        serviceAddedLeadAdapter.notifyDataSetChanged();
                        Log.d("tag","Sucess "+response.body().getMessage());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }


                }else {

                    Log.e("tag",""+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServiceLeadByPromoterResultResponse> call, Throwable t) {
                Log.d("tag","failuer"+t.getLocalizedMessage());

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(PromoterAddedLead.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}