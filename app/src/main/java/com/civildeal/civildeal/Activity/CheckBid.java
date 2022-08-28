package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.civildeal.civildeal.Adapter.CheckBidAdapter;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.CheckBidResponse;
import com.civildeal.civildeal.api.ModelResponse.CheckBidResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckBid extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView check_bid_rv;
    SharedPrefManager sharedPrefManager;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bid);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        check_bid_rv = findViewById(R.id.check_bid_rv);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        user_id = String.valueOf(sharedPrefManager.getUserId());


        getMyPost();
    }

    public void getMyPost() {
        Log.d("tag","user_id "+user_id);
        ProgressDialog progressDialog = new ProgressDialog(CheckBid.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Call<CheckBidResultResponse> call = Api_Client
                .getInstance()
                .userProjectList(user_id);
        call.enqueue(new Callback<CheckBidResultResponse>() {
            @Override
            public void onResponse(Call<CheckBidResultResponse> call, Response<CheckBidResultResponse> response) {

                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){

                    try {
                        CheckBidResultResponse checkBidResultResponse = response.body();
                        List<CheckBidResponse> projectList = checkBidResultResponse.getData();
                        CheckBidAdapter checkBidAdapter = new CheckBidAdapter(CheckBid.this,projectList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(CheckBid.this, 1);
                        check_bid_rv.setLayoutManager(gridLayoutManager);
                        check_bid_rv.setAdapter(checkBidAdapter);
                        checkBidAdapter.notifyDataSetChanged();
                        Log.d("tag","Sucess "+response.body().getMessage());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }

                }else {

                    Log.d("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<CheckBidResultResponse> call, Throwable t) {
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