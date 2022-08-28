package com.civildeal.civildeal.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.civildeal.civildeal.Activity.MainActivity;
import com.civildeal.civildeal.Adapter.ProductLeadAdapter;
import com.civildeal.civildeal.Model.ProductLeadModel;
import com.civildeal.civildeal.Prefrences.SharedPrefForLocation;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.ProductLeadResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductLeadResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductLeadFragment extends Fragment implements ProductLeadAdapter.ItemClickListener{


    RecyclerView productRecyclerView;
    ProductLeadAdapter productLeadAdapter;
    List<ProductLeadResultResponse> productList;
    ProductLeadModel productLeadModel;
    SharedPrefManager sharedPrefManager;
    SharedPrefForLocation sharedPrefForLocation;
    String vendor_id,city_id;
    private int lastposition;
    GridLayoutManager mgridLayoutManager;
    LinearLayout layout;
    ImageView noFound;
    SwipeRefreshLayout swipeRefreshLayout;

    public ProductLeadFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_lead, container, false);

        productRecyclerView = view.findViewById(R.id.productleadRv);
        swipeRefreshLayout = view.findViewById(R.id.swipereferesh);
        layout = view.findViewById(R.id.layout);
        noFound = view.findViewById(R.id.nofound);
        sharedPrefManager=new SharedPrefManager(getActivity());
        vendor_id = String.valueOf(sharedPrefManager.getUserId());
        Log.e("tag",""+vendor_id);
//        Toast.makeText(getActivity(), ""+vendor_id, Toast.LENGTH_SHORT).show();
        sharedPrefForLocation = new SharedPrefForLocation(getContext());
        city_id = sharedPrefForLocation.getLeadLocationId();
        mgridLayoutManager = new GridLayoutManager(getActivity(), 1);

        productRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                lastposition = mgridLayoutManager.findFirstCompletelyVisibleItemPosition();
                Log.e("position","positionfirst "+lastposition);
            }
        });

        getProductList();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("tag","swipe");
                getProductList();
            }
        });




        return view;
    }

    public void getProductList() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Leads Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<ProductLeadResultResponse> call = Api_Client
                .getInstance()
                .getProductLeadList(sharedPrefManager.getUserId(),sharedPrefForLocation.getLeadLocationId());
        call.enqueue(new Callback<ProductLeadResultResponse>() {
            @Override
            public void onResponse(Call<ProductLeadResultResponse> call, Response<ProductLeadResultResponse> response) {
                progressDialog.dismiss();
                if (response.body().getCode().equals(200)) {
                    try {
                        swipeRefreshLayout.setRefreshing(false);
                        ProductLeadResultResponse productLeadResultResponse = response.body();
                        List<ProductLeadResponse> productList = productLeadResultResponse.getData();
                        ProductLeadAdapter productLeadAdapter = new ProductLeadAdapter(getActivity(), productList, ProductLeadFragment.this,ProductLeadFragment.this);
                        productRecyclerView.setLayoutManager(mgridLayoutManager);
                        productRecyclerView.setAdapter(productLeadAdapter);

                        productRecyclerView.scrollToPosition(lastposition);
                        Log.e("position","position "+lastposition);
                        Log.e("tag",""+response.body().getMessage());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }


//                    productLeadAdapter.notifyDataSetChanged();
                }else {
                    noFound.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProductLeadResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("failure",t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void onClickBuyNow(RecyclerView.ViewHolder vh, Object item, int pos) {
        String x= String.valueOf(pos);

//        Toast.makeText(getActivity(), "item added"+pos, Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).updateCount();
//        getProductList();
    }

    @Override
    public void onResume() {
        super.onResume();
        getProductList();
    }
}