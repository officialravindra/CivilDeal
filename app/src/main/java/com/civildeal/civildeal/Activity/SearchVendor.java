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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.SearchAdapter;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.SearchResponse;
import com.civildeal.civildeal.api.ModelResponse.SearchResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchVendor extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<SearchResultResponse> vendorList;
    String city_id;
    String type;
    String keyword;
    String vendor_id;
    LinearLayout layout;
    ImageView noFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vendor);
        Intent intent = getIntent();
        city_id = intent.getStringExtra("cityId");
        type = intent.getStringExtra("Type");
        keyword = intent.getStringExtra("keyword");
        Log.e("tag",""+keyword);
        Log.e("tag",""+type);
        Log.e("tag",""+city_id);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout = findViewById(R.id.layout);
        noFound = findViewById(R.id.nofound);
        recyclerView = findViewById(R.id.RV);

        getSearchedVendorList();

      }

    private void getSearchedVendorList() {

        Log.e("tag1","in function"+city_id+type+keyword);
//        Toast.makeText(this, ""+city_id+type+keyword, Toast.LENGTH_SHORT).show();
        Call<SearchResultResponse> call = Api_Client
                .getInstance()
                .getVendorList(city_id,type.toLowerCase(),keyword.toLowerCase());
        call.enqueue(new Callback<SearchResultResponse>() {
            @Override
            public void onResponse(Call<SearchResultResponse> call, Response<SearchResultResponse> response) {

                if (!response.isSuccessful()){

                    Log.e("tag","failed");
//                    Toast.makeText(SearchVendor.this, "Response Fail"+response.code(), Toast.LENGTH_SHORT).show();
                    return;

                }else if (response.body().getStatus().equals(true)) {

                    try {
                        SearchResultResponse searchResultResponse = response.body();
                        List<SearchResponse> vendorList = searchResultResponse.getData();
                        Log.e("tag","sucess true"+vendorList.size());

                        SearchAdapter searchAdapter = new SearchAdapter(SearchVendor.this,vendorList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchVendor.this,1);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setAdapter(searchAdapter);
                        searchAdapter.notifyDataSetChanged();
                        Log.e("tag1",""+keyword);
                        Log.e("tag1",""+type);
                        Log.e("tag1",""+city_id);
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }




//                    Log.e("tag","sucess"+vendorList.size());

                }
                else if (response.body().getCode() == 200) {


                    Log.e("tag","sucess 200");
                    SearchResultResponse searchResultResponse = response.body();
                    List<SearchResponse> vendorList = searchResultResponse.getData();
                    SearchAdapter searchAdapter = new SearchAdapter(SearchVendor.this,vendorList);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchVendor.this,1);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();

                   // Log.e("tag","sucess"+vendorList.size());
                }
                else if (response.body().getCode().equals(200)) {


                    Log.e("tag","sucess 200");
                    SearchResultResponse searchResultResponse = response.body();
                    List<SearchResponse> vendorList = searchResultResponse.getData();
                    SearchAdapter searchAdapter = new SearchAdapter(SearchVendor.this,vendorList);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchVendor.this,1);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();

                   // Log.e("tag","sucess"+vendorList.size());
                }
                else
                {
                    noFound.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
//                    Toast.makeText(SearchVendor.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(SearchVendor.this,MainActivity.class));

                    Log.e("tag","sucess"+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<SearchResultResponse> call, Throwable t) {


                Log.e("tag","failure");
//                Toast.makeText(SearchVendor.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(SearchVendor.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}