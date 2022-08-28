package com.civildeal.civildeal.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.civildeal.civildeal.Activity.SearchLead;
import com.civildeal.civildeal.Adapter.LeadsPagerAdapter;
import com.civildeal.civildeal.Adapter.MaintenanceAdapter;
import com.civildeal.civildeal.Adapter.ProductAdapter;
import com.civildeal.civildeal.Adapter.ServiceAdapter;
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
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;


public class LeadsFragment extends Fragment {

    Spinner locactionSpinner;
    String[] location;
    Spinner categorySpinner;
    String[] type;

    TabLayout tabLayout;
    ViewPager viewPager;
    SearchView searchView;
    String city,vendor_id,city_id;
    String CitySelected,vendor_type;
    String TypeSelected;
    SharedPrefManager sharedPrefManager;
    List<CityResultReaponse> cityList;
    ArrayList<String> names = new ArrayList<String>();
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String > list_adapter;
    ArrayList<String> listType;
    ArrayList<String> listById;
    int citi_id[];
    String list_id;
    String cityId;
    ArrayAdapter typeadapter;
    SharedPrefForLocation sharedPrefForLocation;
    String selectedCity,selectedCityName;

    private static int REQUESTCODE_TURNON_GPS = 2000;
    LocationManager locationManager;



    public LeadsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leads, container, false);

        locactionSpinner = view.findViewById(R.id.locations);
        searchView = view.findViewById(R.id.searchview);
        location = getResources().getStringArray(R.array.citylocation);

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, location);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locactionSpinner.setAdapter(adapter);
        categorySpinner = view.findViewById(R.id.category);
        list = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.lv1);
        sharedPrefManager = new SharedPrefManager(getActivity());
        vendor_type = sharedPrefManager.getVendorType();
        Log.e("tag","vendor_type "+vendor_type);
        sharedPrefForLocation = new SharedPrefForLocation(getContext());
        selectedCity = sharedPrefForLocation.getLeadLocation();
        Log.e("tag1","selected city_lead "+selectedCity);

        vendor_id = String.valueOf(sharedPrefManager.getUserId());
        Log.e("tag","vendor_id_service "+vendor_id);
//        Toast.makeText(getActivity(), ""+vendor_id, Toast.LENGTH_SHORT).show();

        city_id = sharedPrefForLocation.getLeadLocationId();

        if (vendor_type.equals("service")) {
            getServiceList();
            getMaintenanceList();
            Log.e("tag", "service_search" + vendor_type);
        } else if (vendor_type.equals("product")) {

            getProductList();
            Log.e("tag", "product" + vendor_type);
        } else if (vendor_type.equals("maintenance")) {
            getMaintenanceList();
            Log.e("tag", "maintenance " + vendor_type);
        } else {
            Log.e("tag", "vendor_type " + vendor_type);
        }

        getCities();


//        type = getResources().getStringArray(R.array.selectType);


        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        //getTab();

        tabLayout.addTab(tabLayout.newTab().setText("SERVICE LEAD"));
        tabLayout.addTab(tabLayout.newTab().setText("PRODUCT LEAD"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        LeadsPagerAdapter leadsPagerAdapter = new LeadsPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(leadsPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getContext(), ""+Cities[i], Toast.LENGTH_SHORT).show();

                int pos = listType.indexOf(typeadapter.getItem(i));
                String keyword = listType.get(pos);
//                list_id = String.valueOf(Integer.parseInt(listById.get(i)));
                categorySpinner.setSelection(i);
                TypeSelected = type[i];
                Log.e("tag", "list_id " + list_id);
//                Intent intent = new Intent(getContext(), SearchLead.class);
//                // intent.putExtra("Type",TypeSelected);
//                intent.putExtra("cityId",cityId);
//                intent.putExtra("keyword",keyword);
//                intent.putExtra("list_id",list_id);
//                Log.e("tag","list_id1 "+list_id);
//                startActivity(intent);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                list_id = String.valueOf(Integer.parseInt(listById.get(0)));
                TypeSelected = type[0];
                categorySpinner.setSelection(0);
            }
        });



        list_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(list_adapter);

        /*ArrayAdapter list_adapter = adapter;*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int pos = list.indexOf(list_adapter.getItem(i));
                list_id = String.valueOf(Integer.parseInt(listById.get(i)));
//                Toast.makeText(getContext(), ""+pos, Toast.LENGTH_SHORT).show();
                Log.e("tag","pos "+pos);

                String keyword = list.get(pos);
                Log.e("tag",""+keyword);
                Intent intent = new Intent(getContext(), SearchLead.class);
               // intent.putExtra("Type",TypeSelected);
                intent.putExtra("cityId",cityId);
                intent.putExtra("keyword",keyword);
//                intent.putExtra("list_id",list_id);
                Log.e("tag","list_id1 "+list_id);
                startActivity(intent);


            }
        });

        listView.setVisibility(View.GONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(list.contains(query)){
                    list_adapter.getFilter().filter(query);
                    listView.setVisibility(View.VISIBLE);
                    Log.e("tag",""+query);
                }
                else{
                    listView.setVisibility(View.GONE);


//                    Toast.makeText(getContext(), "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                list_adapter.getFilter().filter(newText);
                listView.setVisibility(View.VISIBLE);

                Log.e("tag","new "+newText);
                if(newText.equals(""))
                {
                    listView.setVisibility(View.GONE);
                    Log.e("tag","listView "+listView);
                }
                return false;
            }
        });





        return view;
    }

    private void getMaintenanceList() {

        Call<MaintenanceResultResponse> call2 = Api_Client
                .getInstance()
                .getMaintenanceList();
        call2.enqueue(new Callback<MaintenanceResultResponse>() {
            @Override
            public void onResponse(Call<MaintenanceResultResponse> call, Response<MaintenanceResultResponse> response) {

                if (response.body().getCode().equals(200)){
                    MaintenanceResultResponse maintenanceResultResponse = response.body();
                    List<MaintenanceResponse> maintenanceList = maintenanceResultResponse.getData();
                    MaintenanceAdapter maintenanceAdapter = new MaintenanceAdapter(getActivity(), maintenanceList);
                    listType = new ArrayList<>();
                    listById = new ArrayList<>();
                    listType.clear();
                    for (int i =0;i<maintenanceList.size();i++){

                        listType.add(maintenanceList.get(i).getName().toLowerCase());
                        listById.add(maintenanceList.get(i).getId().toString());
                        type =new String[maintenanceList.size()];
                        type[i] = maintenanceList.get(i).getName().toLowerCase();
//                        Log.e("tag",""+listType.get(i));

                        list.add(type[i]);
//                        for (int j = 0; j < type.length; j++)
//                        {
//                            list.add(type[j]);
//                        }

                        if (i==maintenanceList.size()-1){

                            ArrayAdapter typeadapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listType);
                            typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            categorySpinner.setAdapter(typeadapter);
                        }
                    }

                }else {

//                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag",""+response.body().getMessage());
                }


            }


            @Override
            public void onFailure(Call<MaintenanceResultResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());

            }
        });
    }

    private void getProductList() {
        Call<ProductResultResponse> call1 = Api_Client
                .getInstance()
                .getProductList();
        call1.enqueue(new Callback<ProductResultResponse>() {
            @Override
            public void onResponse(Call<ProductResultResponse> call, Response<ProductResultResponse> response) {

                if (response.body().getCode().equals(200)){


                    ProductResultResponse productResultResponse = response.body();
                    List<ProductResponse> productList = productResultResponse.getData();
                    ProductAdapter productAdapter = new ProductAdapter(getActivity(), productList);
                    listType = new ArrayList<>();
                    listById = new ArrayList<>();
                    listType.clear();
                    for (int i =0;i<productList.size();i++){

                        listType.add(productList.get(i).getName().toLowerCase());
                        listById.add(productList.get(i).getId().toString());
                        type =new String[productList.size()];
                        type[i] = productList.get(i).getName().toLowerCase();
//                        Log.e("tag",""+listType.get(i));

                        list.add(type[i]);
                        if (i==productList.size()-1){

                            ArrayAdapter typeadapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listType);
                            typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            categorySpinner.setAdapter(typeadapter);
                        }
                    }


                }else {

//                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag",""+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ProductResultResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());

            }
        });
    }

    private void getServiceList() {
        Call<ServiceResultResponse> call = Api_Client
                .getInstance()
                .getServicesList();
        call.enqueue(new Callback<ServiceResultResponse>() {
            @Override
            public void onResponse(Call<ServiceResultResponse> call, Response<ServiceResultResponse> response) {

                if (response.body().getCode().equals(200)){

                    ServiceResultResponse serviceResultResponse = response.body();
                    List<ServiceResponse> serviceList = serviceResultResponse.getData();
//                    ServiceAdapter serviceAdapter = new ServiceAdapter(getActivity(), serviceList);
                    listType = new ArrayList<>();
                    listById = new ArrayList<>();
                    listType.clear();
                    for (int i =0;i<serviceList.size();i++){

                        listType.add(serviceList.get(i).getName().toLowerCase());
                        listById.add(serviceList.get(i).getId().toString());
                        type =new String[serviceList.size()];
                        type[i] = serviceList.get(i).getName().toLowerCase();
//                        Log.e("tag",""+listType.get(i));
//                        Log.e("tag","listById "+listById);

                        list.add(type[i]);
//                        for (int j = 0; j < type.length; j++)
//                        {
//                            list.add(type[j]);
//                        }


                        if (i==serviceList.size()-1){

                            typeadapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listType);
                            typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            categorySpinner.setAdapter(typeadapter);
                        }
                    }


                }else {

//                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag",""+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ServiceResultResponse> call, Throwable t) {

                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }

    private void getCities() {
        Log.e("tag","getCity");
        Call<CityResponse> call;

        {
            Api_Client.getInstance().getCityLeadList().enqueue(new Callback<CityResponse>() {
                @Override
                public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {

                    if (response.body().getMessage().equals("City fetch successfully")) {
//                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                        CityResponse cityResponse = response.body();
                        List<CityResultReaponse> cityList = cityResponse.getData();
                        Log.e("tag", "cityList");
                        citi_id = new int[cityList.size()];

                        for (int i = 0; i < cityList.size(); i++) {


                            names.add(cityList.get(i).getName().toString());
                            citi_id[i] = cityList.get(i).getId();
                            Log.d("tag", "names"+names.get(i));
                        }

                        if(getActivity() != null) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, names);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            locactionSpinner.setAdapter(adapter);


                            spinnerSelected();

                            if (sharedPrefForLocation.getCity()!=null){
                                locactionSpinner.setSelection(Integer.parseInt(sharedPrefForLocation.getCity()));
                                spinnerSelected();
                                Log.e("tag","ifCity");
                            }
                            else
                            {
                                locactionSpinner.setSelection(0);
                                Log.e("tag","elseCity");
                            }

//                            if (sharedPrefForLocation.getLeadLocation()!=null){
//                                locactionSpinner.setSelection(Integer.parseInt(
//                                        sharedPrefForLocation.getSelectedLocation()));
//                                Log.e("tag","city adapter "+sharedPrefForLocation.getLeadLocation());
//
//                                spinnerSelected();
//
//                            }else {
//                                locactionSpinner.setSelection(0);
//                                Log.e("tag","else adapter");
//                                //checkGPS();
//                            }

                        }

//                                            locactionSpinner.setSelection(getIndex(locactionSpinner,city));


                    }


                }


                @Override
                public void onFailure(Call<CityResponse> call, Throwable t) {
//                    Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag",""+t.getLocalizedMessage());


                }
            });
        }

    }



    private void checkGPS() {

        LocationRequest locationRequest = LocationRequest.create();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d("GPS_main", "OnSuccess");
                // GPS is ON
                getCurrentlocation();
            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull final Exception e) {
                Log.d("GPS_main", "GPS off");
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

    private void getCurrentlocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            return;
        }
        else if(ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {


            Log.d("gps","gps");
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 600000000, 100000, new android.location.LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        double latitude = location.getLatitude();
                        double Longitude = location.getLongitude();

                        LatLng latLng = new LatLng(latitude, Longitude);
                        if (getContext()!=null){

                            Geocoder geocoder = new Geocoder(getContext());
                            try {

                                List<Address> addressList = geocoder.getFromLocation(latitude, Longitude, 1);
                                String city= addressList.get(0).getLocality();
//                            Toast.makeText(getContext(), ""+city, Toast.LENGTH_SHORT).show();
                                Log.d("gps","gps ");

                                for(int i=0;i<names.size();i++)
                                {
                                    if(city.equals(names.get(i)))
                                    {
                                        locactionSpinner.setSelection(i);
                                        Log.d("gps","gps "+names
                                                .get(i));
                                    }
                                    else{
//                                    names.add(city);
//                                    if(city.equals(names.get(i)))
//                                    {
//                                        locactionSpinner.setSelection(i);
//                                        CitySelected =city;
//                                        Log.d("gps","gps "+names
//                                                .get(i));
//                                    }
//                                        locactionSpinner.setSelection(0);
                                    }

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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

/*
            else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 600, 10, new android.location.LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        double latitude = location.getLatitude();
                        double Longitude = location.getLongitude();

                        LatLng latLng = new LatLng(latitude, Longitude);
                        Geocoder geocoder = new Geocoder(getContext());
                        try {

                            List<Address> addressList = geocoder.getFromLocation(latitude, Longitude, 1);
                            String city= addressList.get(0).getLocality();

                            for(int i=0;i<names.size();i++)
                            {
                                if(city.equals(names.get(i)))
                                {
                                    locactionSpinner.setSelection(i);
                                    Log.d("gps","gps "+names
                                            .get(i));

                                }
                                else{
                                    names.add(city);
                                    if(city.equals(names.get(i)))
                                    {
                                        locactionSpinner.setSelection(i);
                                        Log.d("gps","gps "+names
                                                .get(i));
                                    }
                                }

                                ho gayi set but ye 2 location change ker raha hashCode()khandar and quila khandar
                            }

                            Toast.makeText(getContext(), ""+city, Toast.LENGTH_SHORT).show();
                            Log.d("gps","gps");


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
*/


        }

    }


    void spinnerSelected(){


        locactionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//                getCities();
                Log.e("tag","location spinner");
                if (citi_id != null) {

                    cityId = String.valueOf(citi_id[i]);

                    sharedPrefForLocation.setLeadLocationId(cityId);
                    Log.e("tag","cityId_lead "+cityId);
                } else {

                    Log.e("tag","else");
                }

                city = String.valueOf(adapterView.getSelectedItemId());
                sharedPrefForLocation.saveLocation(city);
                Log.e("tag","city_lead "+city);
                selectedCity = sharedPrefForLocation.getCity();
//                Toast.makeText(getActivity(), ""+selectedCity, Toast.LENGTH_SHORT).show();

                ServiceLeadFragment serviceLeadFragment = new ServiceLeadFragment();
//                serviceLeadFragment.getServiceLeadList(getContext());
                locactionSpinner.setSelection(i);
                CitySelected = String.valueOf(i);

                LeadsPagerAdapter leadsPagerAdapter = new LeadsPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(leadsPagerAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.e("tag","nothing selected");
                cityId = String.valueOf(citi_id[0]);
                CitySelected = String.valueOf(0);
                locactionSpinner.setSelection(0);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("tag","onresume");


        LeadsPagerAdapter leadsPagerAdapter = new LeadsPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(leadsPagerAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("tag","onpause");
    }
}