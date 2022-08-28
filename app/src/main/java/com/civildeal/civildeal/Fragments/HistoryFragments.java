package com.civildeal.civildeal.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.HistoryAdapter;
import com.civildeal.civildeal.Model.HistoryModel;
import com.civildeal.civildeal.Prefrences.SharedPrefForLocation;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.OrderResponse;
import com.civildeal.civildeal.api.ModelResponse.OrderResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragments extends Fragment {

    RecyclerView recyclerView;
    HistoryModel historyModel;
    List<OrderResultResponse> orderList;
    SharedPrefManager sharedPrefManager;
    CardView cardView;
    String vendor_id,location;
    SharedPrefForLocation sharedPrefForLocation;;

    public HistoryFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.history);
        cardView = view.findViewById(R.id.card);
        sharedPrefManager=new SharedPrefManager(getActivity());
        vendor_id = String.valueOf(sharedPrefManager.getUserId());
//        Toast.makeText(getActivity(), "vendor_id"+vendor_id, Toast.LENGTH_SHORT).show();

        sharedPrefForLocation = new SharedPrefForLocation(getActivity());
        location =sharedPrefForLocation.getSelectedLocation();
        Log.e("tag","location "+location);

        getHistory();


        return view;
    }

    private void getHistory() {

        Log.e("tag",""+vendor_id);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<OrderResultResponse> call = Api_Client
                .getInstance()
                .getOrderList(vendor_id);
        call.enqueue(new Callback<OrderResultResponse>() {
            @Override
            public void onResponse(Call<OrderResultResponse> call, Response<OrderResultResponse> response) {
                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){

                    try {
                        cardView.setVisibility(View.VISIBLE);
                        Log.e("tag",""+response.body().getMessage());
                        OrderResultResponse orderResultResponse = response.body();
                        List<OrderResponse> orderList = orderResultResponse.getData();
                        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(), orderList);
                        GridLayoutManager mgridLayoutManager = new GridLayoutManager(getActivity(), 1);
                        recyclerView.setLayoutManager(mgridLayoutManager);
                        recyclerView.setAdapter(historyAdapter);
//                    recyclerView.setNestedScrollingEnabled(false);
                        historyAdapter.notifyDataSetChanged();
                        Log.e("tag",""+vendor_id);
                        Log.e("tag","sucess "+response.body().getData());
                        Log.e("tag","size "+orderList.size());
//                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){

                        Log.d("tag","exception "+response.body().getMessage());
                    }


                }else if (response.body().getStatus().equals("true")){

                    try {
                        cardView.setVisibility(View.VISIBLE);
                        Log.e("tag",""+response.body().getMessage());
                        OrderResultResponse orderResultResponse = response.body();
                        List<OrderResponse> orderList = orderResultResponse.getData();
                        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(), orderList);
                        GridLayoutManager mgridLayoutManager = new GridLayoutManager(getActivity(), 1);
                        recyclerView.setLayoutManager(mgridLayoutManager);
                        recyclerView.setAdapter(historyAdapter);
                        historyAdapter.notifyDataSetChanged();
                        Log.e("tag",""+vendor_id);
                        Log.e("tag","sucess "+response.body().getData());
//                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){

                        Log.d("tag","exception "+response.body().getMessage());
                    }

                }
                else if (response.body().getCode().equals(400)){
                    cardView.setVisibility(View.GONE);
                    Log.e("tag",""+response.body().getMessage());
//                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<OrderResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("failure1 ",t.getLocalizedMessage());
//                Toast.makeText(getActivity(), ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}