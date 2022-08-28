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

import com.civildeal.civildeal.Adapter.SeeAllMaintenanceAdapter;
import com.civildeal.civildeal.Adapter.SeeAllProductAdapter;
import com.civildeal.civildeal.Model.SeeAllProductModel;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResponse;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllMaintenance extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView recyclerView;
    SeeAllProductAdapter seeAllProductAdapter;
    List<SeeAllProductModel> productList;
    SeeAllProductModel seeAllProductModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_maintenance);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.RV);
        getMaintenanceList();
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
//        recyclerView.setLayoutManager(gridLayoutManager);
//
//        productList = new ArrayList<>();
//        seeAllProductModel = new SeeAllProductModel(R.drawable.celectric_repairing,"Elecric Repairing");
//        productList.add(seeAllProductModel);
//        seeAllProductModel = new SeeAllProductModel(R.drawable.ac_repair,"AC Repairing");
//        productList.add(seeAllProductModel);
//        seeAllProductModel = new SeeAllProductModel(R.drawable.piunbing_repairing,"Plumbing Repairing");
//        productList.add(seeAllProductModel);
//        seeAllProductModel = new SeeAllProductModel(R.drawable.ro,"RO & Geyser Repairng");
//        productList.add(seeAllProductModel);
//        seeAllProductModel = new SeeAllProductModel(R.drawable.celectric_repairing,"Elecric Repairing");
//        productList.add(seeAllProductModel);
//        seeAllProductModel = new SeeAllProductModel(R.drawable.ac_repair,"AC Repairing");
//        productList.add(seeAllProductModel);
//        seeAllProductModel = new SeeAllProductModel(R.drawable.piunbing_repairing,"Plumbing Repairing");
//        productList.add(seeAllProductModel);
//        seeAllProductModel = new SeeAllProductModel(R.drawable.ro,"RO & Geyser Repairng");
//        productList.add(seeAllProductModel);
//
//        SeeAllProductAdapter seeAllProductAdapter = new SeeAllProductAdapter(this,productList);
//        recyclerView.setAdapter(seeAllProductAdapter);
    }

    private void getMaintenanceList() {

        Call<MaintenanceResultResponse> call2 = Api_Client
                .getInstance()
                .getMaintenanceList();
        call2.enqueue(new Callback<MaintenanceResultResponse>() {
            @Override
            public void onResponse(Call<MaintenanceResultResponse> call, Response<MaintenanceResultResponse> response) {

                if (!response.isSuccessful()) {

//                    Toast.makeText(SeeAllMaintenance.this, "Response Fail" + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                    return;
                }

                else {
                    MaintenanceResultResponse maintenanceResultResponse = response.body();
                    List<MaintenanceResponse> maintenanceList = maintenanceResultResponse.getData();
                    SeeAllMaintenanceAdapter seeAllMaintenanceAdapter = new SeeAllMaintenanceAdapter(SeeAllMaintenance.this, maintenanceList);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(SeeAllMaintenance.this, 2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(seeAllMaintenanceAdapter);
                    seeAllMaintenanceAdapter.notifyDataSetChanged();

                }


            }


            @Override
            public void onFailure(Call<MaintenanceResultResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(SeeAllMaintenance.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}