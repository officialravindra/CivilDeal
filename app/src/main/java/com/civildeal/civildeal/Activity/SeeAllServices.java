package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.SeeAllServiceAdapter;
import com.civildeal.civildeal.Model.SeeAllServiceModel;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.ServiceResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllServices extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView recyclerView;
    SeeAllServiceAdapter seeAllServiceAdapter;
    List<ServiceResultResponse> serviceList;
    SeeAllServiceModel seeAllServiceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_services);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.RV);

        getAllService();



    }

    private void getAllService() {

        Call<ServiceResultResponse> call = Api_Client
                .getInstance()
                .getServicesList();
        call.enqueue(new Callback<ServiceResultResponse>() {
            @Override
            public void onResponse(Call<ServiceResultResponse> call, Response<ServiceResultResponse> response) {

                if (!response.isSuccessful()){

//                    Toast.makeText(SeeAllServices.this, "Response Fail"+response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                    return;
                }else {

                    ServiceResultResponse serviceResultResponse = response.body();
                    List<ServiceResponse> serviceList = serviceResultResponse.getData();
                    SeeAllServiceAdapter seeAllServiceAdapter = new SeeAllServiceAdapter(SeeAllServices.this, serviceList);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(SeeAllServices.this, 2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(seeAllServiceAdapter);
                    seeAllServiceAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<ServiceResultResponse> call, Throwable t) {

                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(SeeAllServices.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}