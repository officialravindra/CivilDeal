package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.OrderResponse;
import com.civildeal.civildeal.api.ModelResponse.OrderResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrder extends AppCompatActivity {

    Toolbar toolbar;
    TextView orderId,name,mobile,query,vendorType,amount,location,paymentStatus;
    SharedPrefManager sharedPrefManager;
    String vendor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderId = findViewById(R.id.orderid);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobileno);
        query = findViewById(R.id.query);
        vendorType = findViewById(R.id.vendorType);
        amount = findViewById(R.id.price);
        location = findViewById(R.id.location);
        paymentStatus = findViewById(R.id.paymentStatus);
        sharedPrefManager=new SharedPrefManager(getApplicationContext());
//        vendor_id = String.valueOf(sharedPrefManager.getUserId());
//        Toast.makeText(this, "vendor_id"+vendor_id, Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        vendor_id = intent.getStringExtra("vendor_id");


        getHistoryDetails();


    }

    private void getHistoryDetails() {

        Call<OrderResultResponse> call = Api_Client
                .getInstance()
                .getOrderListDetails(Integer.parseInt(vendor_id));
        call.enqueue(new Callback<OrderResultResponse>() {
            @Override
            public void onResponse(Call<OrderResultResponse> call, Response<OrderResultResponse> response) {

                if (!response.isSuccessful()){

                    Toast.makeText(ViewOrder.this, "Response Fail"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }else if(response.body().getCode().equals(200)){


                    OrderResultResponse orderResultResponse = response.body();
                    List<OrderResponse> orderList = orderResultResponse.getData();
                    Log.e("tag","orderList");
                    orderId.setText(orderList.get(0).getOrderId());
                    name.setText(orderList.get(0).getName());
//                    mobile.setText(orderList.get(0).getPhone().toString());
                    query.setText(orderList.get(0).getQuery());
//                    vendorType.setText(orderList.get(0).getVendorType());
                    amount.setText("₹ " +orderList.get(0).getAmount().toString());
                    location.setText(orderList.get(0).getLocation());
                    paymentStatus.setText(orderList.get(0).getPaymentStatus());
//                    Toast.makeText(ViewOrder.this, "fetched", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(ViewOrder.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<OrderResultResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(ViewOrder.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}