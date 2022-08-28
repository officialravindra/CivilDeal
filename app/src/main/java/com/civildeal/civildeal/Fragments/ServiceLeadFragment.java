package com.civildeal.civildeal.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TextView;

import com.civildeal.civildeal.Activity.MainActivity;
import com.civildeal.civildeal.Adapter.ServiceLeadAdapter;
import com.civildeal.civildeal.Prefrences.SharedPrefForLocation;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.AddToCartResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceLeadResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceLeadResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceLeadFragment extends Fragment implements ServiceLeadAdapter.ItemClickListener{

    RecyclerView serviceRecyclerView;
    Activity activity;
    ServiceLeadAdapter serviceLeadAdapter;
    List<ServiceLeadResultResponse> serviceList;
    private String query,location,leadprice,servicename,serviceImage;
    SharedPrefManager sharedPrefManager;
    SharedPrefForLocation sharedPrefForLocation;
    String vendor_id,city_id;
    public TextView countTv;
    private int lastposition;
    GridLayoutManager mgridLayoutManager;
    ProgressDialog progressDialog;
    LinearLayout layout;
    ImageView noFound;
    SwipeRefreshLayout swipeRefreshLayout;

    List<AddToCartResultResponse> addCart;


    public ServiceLeadFragment(Activity activity) {
        activity = activity;
        // Required empty public constructor
    }

    public ServiceLeadFragment() {
        activity = activity;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_lead, container, false);

        serviceRecyclerView = view.findViewById(R.id.serviceleadRv);
        swipeRefreshLayout = view.findViewById(R.id.swipereferesh);

        sharedPrefManager=new SharedPrefManager(getActivity());
        vendor_id = String.valueOf(sharedPrefManager.getUserId());
        Log.e("tag","vendor_id "+vendor_id);
//        Toast.makeText(getActivity(), ""+vendor_id, Toast.LENGTH_SHORT).show();

        sharedPrefForLocation = new SharedPrefForLocation(getActivity());
        city_id = sharedPrefForLocation.getLeadLocationId();
        Log.e("tag","city_id_service "+city_id);

        layout = view.findViewById(R.id.layout);
        noFound = view.findViewById(R.id.nofound);
        serviceRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                lastposition = mgridLayoutManager.findFirstCompletelyVisibleItemPosition();
                Log.e("position","positionfirst "+lastposition);
            }
        });

        activity = (MainActivity) getActivity();
        getServiceList();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("tag","swipe");
                getServiceList();
            }
        });


        return view;
    }

    public void getServiceList() {

        mgridLayoutManager = new GridLayoutManager(activity, 1);

        Log.e("tag","getService1");
        Log.e("tag","vendor_id "+vendor_id);
        Log.e("tag","city_id "+city_id);

//        if (getActivity()!=null){
//            progressDialog = new ProgressDialog(getActivity());
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Please wait..");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.show();
//        }

        Call<ServiceLeadResultResponse> call = Api_Client
                .getInstance()
                .getServiceLeadList(sharedPrefManager.getUserId(),sharedPrefForLocation.getLeadLocationId());
        call.enqueue(new Callback<ServiceLeadResultResponse>() {
            @Override
            public void onResponse(Call<ServiceLeadResultResponse> call, Response<ServiceLeadResultResponse> response) {
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }

                try {
                    if (response.body().getCode().equals(200)) {
                        try {
                            swipeRefreshLayout.setRefreshing(false);
                            ServiceLeadResultResponse serviceLeadResultResponse = response.body();
                            List<ServiceLeadResponse> serviceList = serviceLeadResultResponse.getData();
                            ServiceLeadAdapter serviceLeadAdapter = new ServiceLeadAdapter((MainActivity) getActivity(), serviceList, ServiceLeadFragment.this,ServiceLeadFragment.this);
                            serviceRecyclerView.setLayoutManager(mgridLayoutManager);
                            serviceRecyclerView.setAdapter(serviceLeadAdapter);

                            serviceRecyclerView.scrollToPosition(lastposition);
//                    Log.e("position","position "+lastposition);
                            Log.e("tag",""+response.body().getMessage());

                            //  serviceLeadAdapter.notifyDataSetChanged();
                        }catch (Exception e){
                            Log.d("tag","Exception "+response.body().getMessage());
                        }


                    }else {

                        noFound.setVisibility(View.VISIBLE);
                        layout.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ServiceLeadResultResponse> call, Throwable t) {
//                progressDialog.dismiss();
                Log.e("failure",t.getLocalizedMessage());

            }
        });
    }

    public void getServiceLeadList(Context context){
        sharedPrefManager=new SharedPrefManager(context);
        sharedPrefForLocation = new SharedPrefForLocation(context);

        activity = (Activity) context;
        getServiceList();
        Log.e("tag","getService");
    }

    @Override
    public void onClickBuyNow(RecyclerView.ViewHolder vh, Object item, int pos) {

        String x= String.valueOf(pos);

//        Toast.makeText(getActivity(), "item added"+pos, Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).updateCount();
        //countTv.setText(Integer.toString(pos));
//        countTv.setText(x);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("tag","onResume");
        getServiceList();
    }
}