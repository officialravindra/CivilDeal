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

import com.civildeal.civildeal.Adapter.DirectLeadAdapter;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.DirectLeadResponse;
import com.civildeal.civildeal.api.ModelResponse.DirectLeadResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inbox extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView directLeadRecyclerView;

    String vendor_id,vendor_type;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        vendor_id = String.valueOf(sharedPrefManager.getUserId());
        vendor_type = sharedPrefManager.getVendorType();

        directLeadRecyclerView = findViewById(R.id.directLead);

        getDirectLeadList();
    }

    private void getDirectLeadList() {

        Call<DirectLeadResultResponse> call = Api_Client
                .getInstance()
                .getDirectLeadList(vendor_id,vendor_type);

        call.enqueue(new Callback<DirectLeadResultResponse>() {
            @Override
            public void onResponse(Call<DirectLeadResultResponse> call, Response<DirectLeadResultResponse> response) {

                if (response.body().getCode().equals(200)){

                    DirectLeadResultResponse directLeadResultResponse = response.body();
                    List<DirectLeadResponse> directLeadList = directLeadResultResponse.getData();
                    DirectLeadAdapter directLeadAdapter = new DirectLeadAdapter(Inbox.this,directLeadList);
                    GridLayoutManager mgridLayoutManager = new GridLayoutManager(Inbox.this,1);
                    directLeadRecyclerView.setLayoutManager(mgridLayoutManager);
                    directLeadRecyclerView.setAdapter(directLeadAdapter);
                    directLeadAdapter.notifyDataSetChanged();
                    Log.e("tag",""+response.body().getMessage());
                    Log.e("tag",""+vendor_id);
                    Log.e("tag",""+vendor_type);
                }else {

                    Log.e("tag",""+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<DirectLeadResultResponse> call, Throwable t) {

                Log.e("tag",""+t.getLocalizedMessage());

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(Inbox.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}