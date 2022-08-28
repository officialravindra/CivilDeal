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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.MyCartAdapter;
import com.civildeal.civildeal.Adapter.SearchLeadAdapter;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.ItemAddToCartResponse;
import com.civildeal.civildeal.api.ModelResponse.ItemAddToCartResultResponse;
import com.civildeal.civildeal.api.ModelResponse.SearchLeadResponse;
import com.civildeal.civildeal.api.ModelResponse.SearchLeadResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchLead extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    TextView text;
    SharedPrefManager sharedPrefManager;
    String vendor_id,type;
    String City,keyword;
    String list_id;
    GridLayoutManager mgridLayoutManager;
    private int lastposition;
    MenuItem cartIconMenuItem;
    public TextView countTv;
    static String count_no ;
    ImageButton imageButton;
    LinearLayout layout;
    ImageView noFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_lead);

        toolbar=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        layout = findViewById(R.id.layout);
        noFound = findViewById(R.id.nofound);
        sharedPrefManager=new SharedPrefManager(SearchLead.this);
        vendor_id = String.valueOf(sharedPrefManager.getUserId());
        type = sharedPrefManager.getVendorType().toString();
        Log.e("tag","vendor_id "+vendor_id);
        Log.e("tag","type "+type);


        Intent intent = getIntent();
        City = intent.getStringExtra("cityId");
        keyword = intent.getStringExtra("keyword");
//        list_id = intent.getStringExtra("list_id");
        Log.e("tag","City "+City);
        Log.e("tag","keyword "+keyword);
        Log.e("tag","list_id "+list_id);


        recyclerView = findViewById(R.id.RV);
        mgridLayoutManager = new GridLayoutManager(SearchLead.this, 1);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                lastposition = mgridLayoutManager.findFirstCompletelyVisibleItemPosition();
            }
        });

        getSearchLead();

    }

    public void getSearchLead() {

//        Log.e("tag1","vendor_type "+type);
//        Log.e("tag1","citi "+City);
////        Log.e("tag1","list_id "+list_id);
//        Log.e("tag1","keyword "+keyword);
        ProgressDialog progressDialog = new ProgressDialog(SearchLead.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Searching...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Call<SearchLeadResultResponse> call = Api_Client
                .getInstance()
                .leadlist(type,City,keyword,vendor_id);
        call.enqueue(new Callback<SearchLeadResultResponse>() {
            @Override
            public void onResponse(Call<SearchLeadResultResponse> call, Response<SearchLeadResultResponse> response) {
                progressDialog.dismiss();
                if(response.body().getStatus().equals(true)) {
                    try {
                        SearchLeadResultResponse searchLeadResultResponse = response.body();
                        List<SearchLeadResponse> serviceList = searchLeadResultResponse.getData();
                        SearchLeadAdapter serviceLeadAdapter = new SearchLeadAdapter(SearchLead.this, serviceList,type, SearchLead.this);

                        recyclerView.setLayoutManager(mgridLayoutManager);
                        recyclerView.setAdapter(serviceLeadAdapter);
                        recyclerView.scrollToPosition(lastposition);
                        serviceLeadAdapter.notifyDataSetChanged();
                        getCartList();
                        Log.e("tag2",""+response.body().getMessage());

                        Log.e("tag","vendor_type1 "+type);
                        Log.e("tag","citi1 "+City);
//                    Log.e("tag","list_id "+list_id);
                        Log.e("tag","keyword1 "+keyword);
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }


                }
                else
                {
                    noFound.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.VISIBLE);
//                    text.setVisibility(View.VISIBLE);
//                    Toast.makeText(SearchLead.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("tag",""+response.body().getMessage());
                    Log.e("tag","vendor_type "+type);
                    Log.e("tag","citi "+City);
//                    Log.e("tag","list_id "+list_id);
                    Log.e("tag","keyword "+keyword);
                }

            }

            @Override
            public void onFailure(Call<SearchLeadResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("failure",t.getLocalizedMessage());

            }
        });
    }

    public void getCartList() {

        Call<ItemAddToCartResultResponse> call = Api_Client
                .getInstance()
                .getCartList(vendor_id,type);
        call.enqueue(new Callback<ItemAddToCartResultResponse>() {
            @Override
            public void onResponse(Call<ItemAddToCartResultResponse> call, Response<ItemAddToCartResultResponse> response) {

                if(response.body().getCode().equals(200)) {

                    try {
                        ItemAddToCartResultResponse itemAddToCartResultResponse = response.body();
                        List<ItemAddToCartResponse> myCartList = itemAddToCartResultResponse.getData();
                        MyCartAdapter myCartAdapter = new MyCartAdapter(SearchLead.this,myCartList);

                        count_no = countTv.getText().toString();
                        Integer x = myCartList.size();
                        count_no = x.toString();
                        countTv.setText(count_no);
//                    Toast.makeText(MainActivity.this, "count "+count_no, Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }





                }
                else
                {
                    Log.d("tag","fail "+response.body().getMessage());
//                    Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ItemAddToCartResultResponse> call, Throwable t) {

                Log.e("failure",t.getLocalizedMessage());

            }
        });
    }

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(SearchLead.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        getSearchLead();
        Log.e("tag","onresume");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        cartIconMenuItem = menu.findItem(R.id.cart);
        View actionView = cartIconMenuItem.getActionView();
        Log.e("tag1","menu"+actionView);

        if (actionView!=null){
            Log.e("tag", String.valueOf("count"+actionView!=null));
            countTv=actionView.findViewById(R.id.count_tv);
            imageButton=actionView.findViewById(R.id.cart_ic_image);



            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //countTv.setText("2");
//                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SearchLead.this, Cart.class);
                    startActivity(intent);
                }
            });
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cart) {
            return true;
        }
        onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}