package com.civildeal.civildeal.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.civildeal.civildeal.Activity.SearchVendor;
import com.civildeal.civildeal.Activity.SeeAllMaintenance;
import com.civildeal.civildeal.Activity.SeeAllProduct;
import com.civildeal.civildeal.Activity.SeeAllServices;
import com.civildeal.civildeal.Adapter.MaintenanceAdapter;
import com.civildeal.civildeal.Adapter.ProductAdapter;
import com.civildeal.civildeal.Adapter.ServiceAdapter;
import com.civildeal.civildeal.Adapter.TestimonialViewPagerAdapter;
import com.civildeal.civildeal.Adapter.ViewPagerAdapter;
import com.civildeal.civildeal.Model.MaintenanceData;
import com.civildeal.civildeal.Model.ProductData;
import com.civildeal.civildeal.Model.TestimonialData;
import com.civildeal.civildeal.Prefrences.SharedPrefForLocation;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.CityResponse;
import com.civildeal.civildeal.api.ModelResponse.CityResultReaponse;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResponse;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResultResponse;
import com.civildeal.civildeal.api.ModelResponse.TestResponse;
import com.civildeal.civildeal.api.ModelResponse.TestResultResponse;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;


public class HomeFragment extends Fragment {

    Spinner locactionSpinner;
    String[] location;
    Spinner categorySpinner;
    String[] type;
    String city;
    String CitySelected;
    String TypeSelected, typeName;
    SharedPrefForLocation sharedPrefManager;
    SharedPrefManager sharedPrefmanager;
    List<CityResultReaponse> cityList;
    ArrayList<String> names = new ArrayList<String>();
    String selectedCity, user_location;
    static int y = 0;
    ProgressDialog progressDialog;


    private ProgressDialog pDialog;
    private AlertDialog dialog;

    private static int REQUESTCODE_TURNON_GPS = 2000;
    LocationManager locationManager;

    SearchView searchView;
    View rootView;
    ProgressBar progressBar, productBar, maintenanceBar;

    ViewPager viewPager;
    RecyclerView serviceRecyclerView;
    ServiceAdapter serviceAdapter;
    List<ServiceResultResponse> serviceList;
    CardView cardView;

    RecyclerView productRecyclerView;
    ProductAdapter productLeadAdapter;
    List<ProductResultResponse> productList;
    ProductData productLeadData;
    CardView cardView1;


    RecyclerView maintenanceRecyclerView;
    MaintenanceAdapter maintenanceLeadAdapter;
    List<MaintenanceResultResponse> maintenanceList;
    MaintenanceData maintenanceLeadData;
    CardView cardView2;

    ViewPager mviewPager;
    List<TestimonialData> testimonialDataList;
    TestimonialViewPagerAdapter testimonialViewPagerAdapter;


    ListView listView;
    ArrayList<String> list;
    int citi_id[];
    String cityId, Location;


    ArrayAdapter<String> adapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        location = getResources().getStringArray(R.array.citylocation);

        locactionSpinner = view.findViewById(R.id.location);

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        searchView = view.findViewById(R.id.searchView);
        rootView = view.findViewById(R.id.root_layout);

        sharedPrefManager = new SharedPrefForLocation(getActivity());
        serviceRecyclerView = view.findViewById(R.id.serviceleadRv);
        progressBar = view.findViewById(R.id.pBar);
        productBar = view.findViewById(R.id.pBar1);
        maintenanceBar = view.findViewById(R.id.pBar2);
        productRecyclerView = view.findViewById(R.id.productleadRv);
        maintenanceRecyclerView = view.findViewById(R.id.maintenanceleadRv);
        list = new ArrayList<>();

        categorySpinner = view.findViewById(R.id.category);
        viewPager = view.findViewById(R.id.ViewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());
        viewPager.setAdapter(viewPagerAdapter);

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();
        sharedPrefmanager = new SharedPrefManager(getActivity());

        Log.e("tag", "City " + sharedPrefManager.getSelectedLocation());

//        checkGPS();

//        locactionSpinner.setSelection(0, false);
        getCities();


//        getProductList();
//        getMaintenanceList();

//        getServiceList();
        new Handler().postDelayed(this::getServiceList, 000);
        new Handler().postDelayed(this::getProductList, 300);
        new Handler().postDelayed(this::getMaintenanceList, 500);


        type = getResources().getStringArray(R.array.selectType);

//        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, location);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        locactionSpinner.setAdapter(adapter);

        ArrayAdapter typeadapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, type);
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(typeadapter);


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getContext(), ""+Cities[i], Toast.LENGTH_SHORT).show();
                typeName = adapterView.getItemAtPosition(i).toString();
                if (typeName.equals("service")) {
                    getService();
//                    Log.e("tag", "service" + typeName);
                } else if (typeName.equals("product")) {

                    getProduct();
//                    Log.e("tag", "product" + typeName);
                } else if (typeName.equals("maintenance")) {
                    getMaintenance();
//                    Log.e("tag", "maintenance " + typeName);
                } else {
//                Log.e("tag", "vendor_type " + Type_Selected);
                }
                categorySpinner.setSelection(i);
                TypeSelected = type[i];
                getSelectedTypeCategories();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (typeName.equals("service")) {
                    getService();
//                    Log.e("tag", "service" + typeName);
                } else if (typeName.equals("product")) {

                    getProduct();
//                    Log.e("tag", "product" + typeName);
                } else if (typeName.equals("maintenance")) {
                    getMaintenance();
//                    Log.e("tag", "maintenance " + typeName);
                } else {
//                Log.e("tag", "vendor_type " + Type_Selected);
                }
                TypeSelected = type[0];
                categorySpinner.setSelection(0);
            }
        });

        listView = (ListView) view.findViewById(R.id.lv1);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int pos = list.indexOf(adapter.getItem(i));
//                Toast.makeText(getContext(), ""+pos, Toast.LENGTH_SHORT).show();

                String keyword = list.get(pos);
                Intent intent = new Intent(getContext(), SearchVendor.class);
                intent.putExtra("Type", TypeSelected);
                intent.putExtra("cityId", cityId);
                intent.putExtra("keyword", keyword);
                Log.e("cityId ", "" + cityId);
                Log.e("cityId ", "" + TypeSelected);
                Log.e("cityId ", "" + keyword);
                startActivity(intent);


            }
        });

        listView.setVisibility(View.GONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                if (list.contains(query)) {
                    //adapter.getFilter().filter(query);
                    Log.e("tag", "list " + list);
                    listView.setVisibility(View.VISIBLE);

                } else {
                    listView.setVisibility(View.GONE);
//                    Toast.makeText(getContext(), "No Match found", Toast.LENGTH_LONG).show();
                    Log.e("tag", "gone");
                    searchView.post(new Runnable() {
                        @Override
                        public void run() {
                            searchView.clearFocus();
                            Log.e("tag", "clear");
                        }
                    });
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                listView.setVisibility(View.VISIBLE);
                Log.e("tag", "new text " + newText);
                Log.e("tag", "list1 " + list);


                if (newText.equals("")) {
                    listView.setVisibility(View.GONE);

                }
                return false;
            }
        });




      /*  Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

   */


        cardView = view.findViewById(R.id.seeAllServices);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeeAllServices.class);
                startActivity(intent);
            }
        });


        cardView1 = view.findViewById(R.id.seeAllProduct);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeeAllProduct.class);
                startActivity(intent);
            }
        });


        cardView2 = view.findViewById(R.id.seeAllMaintenance);

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SeeAllMaintenance.class);
                startActivity(intent);
            }
        });

/*

        testimonialDataList = new ArrayList<>();
        testimonialDataList.add(new TestimonialData(R.drawable.itsme, "I'm so happy with this portal....very useful", "Akabhinav"));
        testimonialDataList.add(new TestimonialData(R.drawable.itsme, "I'm so happy with this portal....very useful", "Akabhinav"));
        testimonialDataList.add(new TestimonialData(R.drawable.itsme, "I'm so happy with this portal....very useful", "Akabhinav"));
        testimonialDataList.add(new TestimonialData(R.drawable.itsme, "I'm so happy with this portal....very useful", "Akabhinav"));

        testimonialViewPagerAdapter = new TestimonialViewPagerAdapter(testimonialDataList, getContext());

        mviewPager = view.findViewById(R.id.viewPager);
        mviewPager.setAdapter(testimonialViewPagerAdapter);
*/


//        Timer mytimer = new Timer();
//        mytimer.scheduleAtFixedRate(new MytimerTasks(),2000,4000);


        return view;
    }


    private void getService() {

        Call<ServiceResultResponse> call = Api_Client
                .getInstance()
                .getServicesList();
        call.enqueue(new Callback<ServiceResultResponse>() {
            @Override
            public void onResponse(Call<ServiceResultResponse> call, Response<ServiceResultResponse> response) {

                if (response.body().getCode().equals(200)) {
                    try {
                        ServiceResultResponse serviceResultResponse = response.body();
                        List<ServiceResponse> serviceList = serviceResultResponse.getData();

                        // list = new ArrayList<>();
                        list.clear();
                        for (int i = 0; i < serviceList.size(); i++) {
                            list.add(serviceList.get(i).getName().toLowerCase());
//                        Log.e("tag","service "+list);

                            if (i == serviceList.size() - 1) {
                                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
                                listView.setAdapter(adapter);
                            }
                        }
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }



//                    getProductList();
//                    new Handler().postDelayed(this::get, 000);
                } else {


//                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ServiceResultResponse> call, Throwable t) {

                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }

    private void getProduct() {

        Call<ProductResultResponse> call1 = Api_Client
                .getInstance()
                .getProductList();
        call1.enqueue(new Callback<ProductResultResponse>() {
            @Override
            public void onResponse(Call<ProductResultResponse> call, Response<ProductResultResponse> response) {

                if (response.body().getCode().equals(200)) {

                    try {
                        ProductResultResponse productResultResponse = response.body();
                        List<ProductResponse> productList = productResultResponse.getData();

                        Log.e("tag", "" + response.body().getMessage());
                        //  list = new ArrayList<>();
                        list.clear();
                        for (int i = 0; i < productList.size(); i++) {
                            list.add(productList.get(i).getName().toLowerCase());
                            Log.e("tag", "product " + list);

                            if (i == productList.size() - 1) {
                                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
                                listView.setAdapter(adapter);
                            }
                        }
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }



//                    getMaintenanceList();

                } else {


//                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ProductResultResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());


            }
        });

    }

    private void getMaintenance() {

        Call<MaintenanceResultResponse> call2 = Api_Client
                .getInstance()
                .getMaintenanceList();
        call2.enqueue(new Callback<MaintenanceResultResponse>() {
            @Override
            public void onResponse(Call<MaintenanceResultResponse> call, Response<MaintenanceResultResponse> response) {

                if (response.body().getCode().equals(200)) {

                    try {
                        MaintenanceResultResponse maintenanceResultResponse = response.body();
                        List<MaintenanceResponse> maintenanceList = maintenanceResultResponse.getData();

                        // list = new ArrayList<>();
                        list.clear();
                        for (int i = 0; i < maintenanceList.size(); i++) {
                            list.add(maintenanceList.get(i).getName().toLowerCase());
                            Log.e("tag", "maintenance " + list);

                            if (i == maintenanceList.size() - 1) {
                                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
                                listView.setAdapter(adapter);
                            }

                        }
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }


                } else {


//                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }


            }


            @Override
            public void onFailure(Call<MaintenanceResultResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());


            }
        });


    }


    private void getSelectedTypeCategories() {
    }

    private void getServiceList() {
        progressBar.setVisibility(View.VISIBLE);
        Call<ServiceResultResponse> call = Api_Client
                .getInstance()
                .getServicesList();
        call.enqueue(new Callback<ServiceResultResponse>() {
            @Override
            public void onResponse(Call<ServiceResultResponse> call, Response<ServiceResultResponse> response) {

                if (response.body().getCode().equals(200)) {
                    progressBar.setVisibility(View.GONE);
                    serviceRecyclerView.setVisibility(View.VISIBLE);
                    ServiceResultResponse serviceResultResponse = response.body();
                    List<ServiceResponse> serviceList = serviceResultResponse.getData();
                    ServiceAdapter serviceAdapter = new ServiceAdapter(getActivity(), serviceList);
                    LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                    serviceRecyclerView.setLayoutManager(mlinearLayoutManager);
                    serviceRecyclerView.setAdapter(serviceAdapter);
                    serviceAdapter.notifyDataSetChanged();
                    // list = new ArrayList<>();
//                    list.clear();
//                    for(int i=0;i<serviceList.size();i++)
//                    {
//                        list.add(serviceList.get(i).getName().toLowerCase());
//                        Log.e("tag",""+list);
//                    }
//                    getProductList();
//                    new Handler().postDelayed(this::get, 000);
                } else {


//                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ServiceResultResponse> call, Throwable t) {

                Log.e("failure", t.getLocalizedMessage());
            }
        });


    }

    public void getProductList() {

        productBar.setVisibility(View.VISIBLE);
        Call<ProductResultResponse> call1 = Api_Client
                .getInstance()
                .getProductList();
        call1.enqueue(new Callback<ProductResultResponse>() {
            @Override
            public void onResponse(Call<ProductResultResponse> call, Response<ProductResultResponse> response) {

                if (response.body().getCode().equals(200)) {

                    productBar.setVisibility(View.GONE);
                    productRecyclerView.setVisibility(View.VISIBLE);
                    ProductResultResponse productResultResponse = response.body();
                    List<ProductResponse> productList = productResultResponse.getData();
                    ProductAdapter productAdapter = new ProductAdapter(getActivity(), productList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                    productRecyclerView.setLayoutManager(linearLayoutManager);
                    productRecyclerView.setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();
                    Log.e("tag", "" + response.body().getMessage());
                    //  list = new ArrayList<>();
                    // list.clear();
//                    for(int i=0;i<productList.size();i++)
//                    {
//                        list.add(productList.get(i).getName().toLowerCase());
//                    }

//                    getMaintenanceList();

                } else {


//                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ProductResultResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());


            }
        });

    }

    public void getMaintenanceList() {
        maintenanceBar.setVisibility(View.VISIBLE);

        Call<MaintenanceResultResponse> call2 = Api_Client
                .getInstance()
                .getMaintenanceList();
        call2.enqueue(new Callback<MaintenanceResultResponse>() {
            @Override
            public void onResponse(Call<MaintenanceResultResponse> call, Response<MaintenanceResultResponse> response) {

                if (response.body().getCode().equals(200)) {

                    maintenanceBar.setVisibility(View.GONE);
                    maintenanceRecyclerView.setVisibility(View.VISIBLE);
                    MaintenanceResultResponse maintenanceResultResponse = response.body();
                    List<MaintenanceResponse> maintenanceList = maintenanceResultResponse.getData();
                    MaintenanceAdapter maintenanceAdapter = new MaintenanceAdapter(getActivity(), maintenanceList);
                    LinearLayoutManager nlinearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                    maintenanceRecyclerView.setLayoutManager(nlinearLayoutManager);
                    maintenanceRecyclerView.setAdapter(maintenanceAdapter);
                    maintenanceAdapter.notifyDataSetChanged();
                    // list = new ArrayList<>();
                    // list.clear();
//                    for(int i=0;i<maintenanceList.size();i++)
//                    {
//                        list.add(maintenanceList.get(i).getName().toLowerCase());
//
//                    }
                } else {


//                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }


            }


            @Override
            public void onFailure(Call<MaintenanceResultResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());


            }
        });

    }


    void checkGPS() {
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Please wait..");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();
        LocationRequest locationRequest = LocationRequest.create();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d("tag", "OnSuccess");
                // GPS is ON
                getCurrentlocation();
            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull final Exception e) {
                Log.d("tag", "GPS off");
                // GPS off
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(getActivity(), REQUESTCODE_TURNON_GPS);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }


    void getCurrentlocation() {


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.e("tag", "permission");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            getCurrentlocation();
            return;
        } else if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


            Log.d("gps", "gps");
//            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 600000000, 100000, new android.location.LocationListener() {
//                    @Override
//                    public void onLocationChanged(@NonNull Location location) {
//                        double latitude = location.getLatitude();
//                        double Longitude = location.getLongitude();
//
//                        LatLng latLng = new LatLng(latitude, Longitude);
//                        if (getContext()!=null){
//
//                            Geocoder geocoder = new Geocoder(getContext());
//                            try {
//
//                                List<Address> addressList = geocoder.getFromLocation(latitude, Longitude, 1);
//                                String city= addressList.get(0).getLocality();
////                            Toast.makeText(getContext(), ""+city, Toast.LENGTH_SHORT).show();
//
//
//                                Log.d("gps","gps ");
//                                for(int i=0;i<names.size();i++)
//                                {
//                                    if(city.equals(names.get(i)))
//                                    {
//                                        locactionSpinner.setSelection(i,false);
//                                        Log.d("gps","gps if "+names
//                                                .get(i));
//                                        y=1;
//
//
////                                        progressDialog.dismiss();
//                                        sharedPrefManager.setSelectedLocation(names.get(i));
//                                        break;
//                                    }
//                                    else{
//                                        Log.d("gps","gps else"+city);
////                                        progressDialog.dismiss();
//                                        y=1;
////                                    names.add(city);
////                                    if(city.equals(names.get(i)))
////                                    {
////                                        locactionSpinner.setSelection(i);
////                                        CitySelected =city;
////                                        Log.d("gps","gps "+names
////                                                .get(i));
////                                    }
////                                        locactionSpinner.setSelection(0);
//
//                                        locactionSpinner.setSelection(0,false);
//                                    }
//
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//
//
//
//                    @Override
//                    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                    }
//
//                    @Override
//                    public void onProviderEnabled(String provider) {
//                        Toast.makeText(getActivity(), "GPS is Enabled", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onProviderDisabled(String provider) {
//                        Toast.makeText(getActivity(), "GPS is Disabled", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//
//
//            }


            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 600000000, 100000, new android.location.LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        double latitude = location.getLatitude();
                        double Longitude = location.getLongitude();

                        LatLng latLng = new LatLng(latitude, Longitude);
                        Geocoder geocoder = new Geocoder(getContext());
                        try {

                            List<Address> addressList = geocoder.getFromLocation(latitude, Longitude, 1);
                            String city = addressList.get(0).getLocality();

                            for (int i = 0; i < names.size(); i++) {
                                if (city.equals(names.get(i))) {
                                    locactionSpinner.setSelection(i, false);
                                    Log.d("tag", "gps1 " + names
                                            .get(i));
                                    y = 1;
//                                    progressDialog.dismiss();
//                                    sharedPrefManager.setSelectedLocation(String.valueOf(i));
                                    sharedPrefManager.setLocation(names.get(i));
                                    break;

                                } else {
                                    names.add(city);
                                    if (city.equals(names.get(i))) {
                                        locactionSpinner.setSelection(i);
                                        Log.d("gps", "gps " + names
                                                .get(i));
                                    }
//                                    Log.e("tag","network_else");
                                    y = 1;
//                                    progressDialog.dismiss();
                                    locactionSpinner.setSelection(i, false);
                                }

                            }

//                            Toast.makeText(getContext(), ""+city, Toast.LENGTH_SHORT).show();
//                            Log.d("tag","gps2");


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        Toast.makeText(getActivity(), "GPS is Enabled", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Toast.makeText(getActivity(), "GPS is Disabled", Toast.LENGTH_SHORT).show();

                    }
                });


            }


        }
    }


    private void getCities() {

        Call<CityResponse> call;

        {
            Api_Client.getInstance().getCityList().enqueue(new Callback<CityResponse>() {
                @Override
                public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {

                    if (response.body().getMessage().equals("City fetch successfully")) {
//                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        try {
                            CityResponse cityResponse = response.body();
                            List<CityResultReaponse> cityList = cityResponse.getData();
                            Log.e("tag", "cityList");

                            citi_id = new int[cityList.size()];
//                        Log.e("citi_id",""+citi_id);

                            for (int i = 0; i < cityList.size(); i++) {

                                names.add(cityList.get(i).getName().toString());
                                citi_id[i] = cityList.get(i).getId();
//                            Log.d("tag", "names"+citi_id[i]);

                            }

                            if (getActivity() != null) {
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, names);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                locactionSpinner.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                Log.e("tag", "getCity");
                                //   spinnerSelected();

                                // setCity();
                                if (!sharedPrefManager.getSelectedLocation().equals("0")) {
                                    Location = sharedPrefManager.getSelectedLocation();
                                    Log.e("maintag", "if " + Location);

                                    setCity();


                                } else {
                                    user_location = sharedPrefmanager.getLocation();
//        Toast.makeText(getActivity(), "location "+user_location, Toast.LENGTH_SHORT).show();
                                    sharedPrefManager.setSelectedLocation(user_location);
                                    Log.e("maintag", "save_city " + sharedPrefManager.getSelectedLocation());
                                    Log.e("tag", "register_city " + sharedPrefmanager.getLocation());
                                    Location = sharedPrefManager.getSelectedLocation();
                                    setCity();

                                }


                         /*   if (y==0){
//                            locactionSpinner.setSelection(0);
//                            checkGPS();
                                setCity();
//                                spinnerSelected();
                            Log.e("tag","current_city_else");
                        }else if (y==1 || sharedPrefManager.getSelectedLocation()!= null){
//                                checkGPS();
//                                spinnerSelected();
                            locactionSpinner.setSelection(Integer.parseInt(sharedPrefManager.getSelectedLocationId()),false);
                            Log.e("city","city_else "+sharedPrefManager.getSelectedLocation());
                        }
*/
                            }

//                                            locactionSpinner.setSelection(getIndex(locactionSpinner,city));
                        }catch (Exception e){
                            Log.d("tag","Exception "+response.body().getMessage());
                        }




                    } else {

                        Toast.makeText(getActivity(), "No city list", Toast.LENGTH_SHORT).show();
                    }


                }


                @Override
                public void onFailure(Call<CityResponse> call, Throwable t) {
//                    Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","failure "+t.getLocalizedMessage());


                }
            });
        }

    }

    private void spinnerSelected() {

        locactionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                sharedPrefManager.setSelectedLocation(String.valueOf(names.get(i)));
                locactionSpinner.setSelection(i);

                if (citi_id != null) {
                    cityId = String.valueOf(citi_id[i]);

                    Log.e("tag", "cityId " + cityId);
//                    Toast.makeText(getActivity(), ""+cityId, Toast.LENGTH_SHORT).show();
                    sharedPrefManager.setSelectedLocationId(cityId);


                } else {
                    Log.e("tag", "else spinner");
//                    Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                }
                city = String.valueOf(adapterView.getSelectedItemId());
                Log.e("tag", "city " + city);
                //sharedPrefManager.setSelectedLocation(names.get(Integer.parseInt(city)));
                selectedCity = sharedPrefManager.getSelectedLocation();
                Log.e("tag", "shared_city " + selectedCity);
                //locactionSpinner.setSelection(i,false);
                CitySelected = String.valueOf(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                cityId = String.valueOf(citi_id[0]);
                CitySelected = String.valueOf(0);
                locactionSpinner.setSelection(0, false);
            }
        });
    }

    public void setCity() {
        String city = sharedPrefManager.getSelectedLocation();
        Log.e("tag", "String_city " + city);

        for (int i = 0; i < names.size(); i++) {
            if (city.equals(names.get(i))) {
                locactionSpinner.setSelection(i, false);
                Log.d("tag", "register " + names
                        .get(i));
                y = 1;
//                progressDialog.dismiss();
                sharedPrefManager.setSelectedLocation(String.valueOf(names.get(i)));
                break;

            } else {
//                names.add(city);
//                if(city.equals(names.get(i)))
//                {
//                    locactionSpinner.setSelection(i);
//                    Log.d("gps","gps "+names
//                            .get(i));
//                }
                Log.e("tag", "network_else");
                y = 1;
//                progressDialog.dismiss();
                locactionSpinner.setSelection(0, false);
            }

            spinnerSelected();

        }

    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            if (getActivity() == null)
                return;

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

//    public class MytimerTasks extends TimerTask{
//
//        @Override
//        public void run() {
//            if (getActivity() == null)
//                return;
//
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (mviewPager.getCurrentItem() == 0){
//                        mviewPager.setCurrentItem(1);
//                    }else if (mviewPager.getCurrentItem() == 1){
//                        mviewPager.setCurrentItem(2);
//                    }else {
//                        mviewPager.setCurrentItem(0);
//                    }
//                }
//            });
//        }
//    }


    @Override
    public void onResume() {
        super.onResume();
//        if(!sharedPrefManager.getSelectedLocation().equals("0"))
//        {            Location = sharedPrefManager.getSelectedLocation();
//
//            setCity();
//
//        }
//        else
//            Log.e("tag","save_city "+sharedPrefManager.getSelectedLocation());
//        Log.e("tag","register_city "+sharedPrefmanager.getLocation());
//        {
//            user_location = sharedPrefmanager.getLocation();
////        Toast.makeText(getActivity(), "location "+user_location, Toast.LENGTH_SHORT).show();
//            sharedPrefManager.setSelectedLocation(user_location);
//
//            Location = sharedPrefManager.getSelectedLocation();
//            setCity();
//
//        }


//        searchView.setQuery("", false);
        rootView.requestFocus();
    }
}
