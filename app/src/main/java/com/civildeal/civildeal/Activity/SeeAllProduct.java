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

import com.civildeal.civildeal.Adapter.SeeAllProductAdapter;
import com.civildeal.civildeal.Model.SeeAllProductModel;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.ProductResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllProduct extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView recyclerView;
    SeeAllProductAdapter seeAllProductAdapter;
    List<ProductResultResponse> productList;
    SeeAllProductModel seeAllProductModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_product);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.RV);

        Call<ProductResultResponse> call1 = Api_Client
                .getInstance()
                .getProductList();
        call1.enqueue(new Callback<ProductResultResponse>() {
            @Override
            public void onResponse(Call<ProductResultResponse> call, Response<ProductResultResponse> response) {

                if (!response.isSuccessful()){

//                    Toast.makeText(SeeAllProduct.this, "Response Fail"+response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                    return;
                }else {

                    ProductResultResponse productResultResponse = response.body();
                    List<ProductResponse> productList = productResultResponse.getData();
                    SeeAllProductAdapter seeAllProductAdapter = new SeeAllProductAdapter(SeeAllProduct.this, productList);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(SeeAllProduct.this, 2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(seeAllProductAdapter);
                    seeAllProductAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<ProductResultResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });




//        productList = new ArrayList<>();
//        seeAllProductModel = new SeeAllProductModel(R.drawable.blocks,"Block");
//        productList.add(seeAllProductModel);
//        seeAllProductModel = new SeeAllProductModel(R.drawable.bricks,"Bricks");
//        productList.add(seeAllProductModel);
//        seeAllProductModel = new SeeAllProductModel(R.drawable.cctv_camera,"CCTV camera");
//        productList.add(seeAllProductModel);
//        seeAllProductModel = new SeeAllProductModel(R.drawable.blocks,"Block");
//        productList.add(seeAllProductModel);
//        seeAllProductModel = new SeeAllProductModel(R.drawable.bricks,"Bricks");
//        productList.add(seeAllProductModel);
//        seeAllProductModel = new SeeAllProductModel(R.drawable.cctv_camera,"CCTV camera");
//        productList.add(seeAllProductModel);





    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(SeeAllProduct.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}