package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.MaintenanceAdapter;
import com.civildeal.civildeal.Adapter.ProductAdapter;
import com.civildeal.civildeal.Adapter.ServiceAdapter;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.CityResponse;
import com.civildeal.civildeal.api.ModelResponse.CityResultReaponse;
import com.civildeal.civildeal.api.ModelResponse.FreeListingResultResponse;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResponse;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResultResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreeListing extends AppCompatActivity {

    Toolbar toolbar;
    EditText name,email,phone,desc;
    Button submit;
    Spinner cityNamesSpinner, typeSpinner,subcategoryspinner;
    String[] type;
    String[] sub_category;
    String[] city;
    List<CityResultReaponse> cityList;
    ArrayList<String> names = new ArrayList<String>();
    String City_Selected,Type_Selected,Sub_Category_Selected;
    int citi_id[];
    String cityId;
    ArrayList<String> listType;
    ArrayList<String> list;
    String serviceName,productName,maintenanceName;
    ArrayAdapter typeadapter;
    String subCategory,typename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_listing);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        desc = findViewById(R.id.description);
        submit = findViewById(R.id.submit);
        cityNamesSpinner = findViewById(R.id.citySpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        subcategoryspinner = findViewById(R.id.servicetypeSpinner);
        serviceName = "null";
        productName = "null";
        maintenanceName = "null";



        getCities();


        cityNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (citi_id != null) {
                    Log.e("tag", "citi_id " + citi_id[position]);
                    cityId = String.valueOf(citi_id[position]);
//                    Toast.makeText(getContext(), " not null", Toast.LENGTH_SHORT).show();
                } else
                {
//                    Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                }
                cityNamesSpinner.setSelection(position);
                City_Selected = names.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                cityId = String.valueOf(citi_id[0]);
                City_Selected = names.get(0);
                cityNamesSpinner.setSelection(0);

            }
        });

        type = getResources().getStringArray(R.array.selectType);
        ArrayAdapter typeAdapter = new ArrayAdapter(FreeListing.this, android.R.layout.simple_spinner_item,type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);


        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                int pos = listType.indexOf(typeadapter.getItem(i));
                subCategory = adapterView.getItemAtPosition(i).toString();
                if (subCategory.equals("service")) {
                    getServiceList();
                    Log.e("tag", "service" + subCategory);
                }
                else if (subCategory.equals("product")) {

                    getProductList();
                    Log.e("tag", "product" + subCategory);
                }
                else if (subCategory.equals("maintenance")) {
                    getMaintenanceList();
                    Log.e("tag", "maintenance " + subCategory);
                } else {
//                Log.e("tag", "vendor_type " + Type_Selected);
                }
                Type_Selected = type[i];
                typeSpinner.setSelection(i);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                subCategory = adapterView.getItemAtPosition(0).toString();
                if (subCategory.equals("service")) {
                    getServiceList();
                    Log.e("tag", "service" + subCategory);
                }
                else if (subCategory.equals("product")) {

                    getProductList();
                    Log.e("tag", "product" + subCategory);
                }
                else if (subCategory.equals("maintenance")) {
                    getMaintenanceList();
                    Log.e("tag", "maintenance " + subCategory);
                } else {
//                Log.e("tag", "vendor_type " + Type_Selected);
                }
                Type_Selected = type[0];
                typeSpinner.setSelection(0);
            }
        });
//        subCategory = typeSpinner.getSelectedItem().toString();




//            if (typeSpinner.getSelectedItem().equals("service")) {
//                getServiceList();
//                Log.e("tag", "service" + subCategory);
//            }
//            else if (typeSpinner.getSelectedItem().equals("product")) {
//
//                getProductList();
//                Log.e("tag", "product" + subCategory);
//            }
//            else if (typeSpinner.getSelectedItem().equals("maintenance")) {
//                getMaintenanceList();
//                Log.e("tag", "maintenance " + subCategory);
//            } else {
////                Log.e("tag", "vendor_type " + Type_Selected);
//            }





        subcategoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typename = parent.getItemAtPosition(position).toString();
                subcategoryspinner.setSelection(position);
                Sub_Category_Selected = sub_category[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typename = parent.getItemAtPosition(0).toString();

                Sub_Category_Selected = sub_category[0];
                subcategoryspinner.setSelection(0);

            }
        });

//        Toast.makeText(this, ""+typename, Toast.LENGTH_SHORT).show();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkDataEntered();
            }
        });

//        getServiceList();
    }

    private void getServiceList() {
        Call<ServiceResultResponse> call = Api_Client
                .getInstance()
                .getServicesList();
        call.enqueue(new Callback<ServiceResultResponse>() {
            @Override
            public void onResponse(Call<ServiceResultResponse> call, Response<ServiceResultResponse> response) {

                if (response.body().getCode().equals(200)){
                    try {
                        ServiceResultResponse serviceResultResponse = response.body();
                        List<ServiceResponse> serviceList = serviceResultResponse.getData();
//                    ServiceAdapter serviceAdapter = new ServiceAdapter(getApplicationContext(), serviceList);
                        listType = new ArrayList<>();
//                    listById = new ArrayList<>();
                        listType.clear();
                        for (int i =0;i<serviceList.size();i++){

                            listType.add(serviceList.get(i).getName().toLowerCase());
//                        listById.add(serviceList.get(i).getId().toString());
                            sub_category =new String[serviceList.size()];
                            sub_category[i] = serviceList.get(i).getName().toLowerCase();
                            Log.e("tag",""+sub_category);
//                        Log.e("tag","listById "+listById);



                            if (i==serviceList.size()-1){

                                typeadapter = new ArrayAdapter(FreeListing.this, android.R.layout.simple_list_item_1, listType);
                                typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subcategoryspinner.setAdapter(typeadapter);
                            }
                        }
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }




                }else {

//                    Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ServiceResultResponse> call, Throwable t) {

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


                    try {
                        ProductResultResponse productResultResponse = response.body();
                        List<ProductResponse> productList = productResultResponse.getData();
                        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), productList);
                        listType = new ArrayList<>();
//                    listById = new ArrayList<>();
                        listType.clear();
                        for (int i =0;i<productList.size();i++){

                            listType.add(productList.get(i).getName().toLowerCase());
//                        listById.add(productList.get(i).getId().toString());
                            sub_category =new String[productList.size()];
                            sub_category[i] = productList.get(i).getName().toLowerCase();
                            Log.e("tag",""+listType.get(i));


                            if (i==productList.size()-1){

                                ArrayAdapter typeadapter = new ArrayAdapter(FreeListing.this, android.R.layout.simple_list_item_1, listType);
                                typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subcategoryspinner.setAdapter(typeadapter);
                            }
                        }
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }



                }else {

//                    Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ProductResultResponse> call, Throwable t) {

                Log.e("failure", t.getLocalizedMessage());

            }
        });
    }

    private void getMaintenanceList() {

        Call<MaintenanceResultResponse> call2 = Api_Client
                .getInstance()
                .getMaintenanceList();
        call2.enqueue(new Callback<MaintenanceResultResponse>() {
            @Override
            public void onResponse(Call<MaintenanceResultResponse> call, Response<MaintenanceResultResponse> response) {

                if (response.body().getCode().equals(200)){

                    try {
                        MaintenanceResultResponse maintenanceResultResponse = response.body();
                        List<MaintenanceResponse> maintenanceList = maintenanceResultResponse.getData();
                        MaintenanceAdapter maintenanceAdapter = new MaintenanceAdapter(getApplicationContext(), maintenanceList);
                        listType = new ArrayList<>();
//                    listById = new ArrayList<>();
                        listType.clear();
                        for (int i =0;i<maintenanceList.size();i++){

                            listType.add(maintenanceList.get(i).getName().toLowerCase());
//                        listById.add(maintenanceList.get(i).getId().toString());
                            sub_category =new String[maintenanceList.size()];
                            sub_category[i] = maintenanceList.get(i).getName().toLowerCase();
                            Log.e("tag",""+listType.get(i));



                            if (i==maintenanceList.size()-1){

                                ArrayAdapter typeadapter = new ArrayAdapter(FreeListing.this, android.R.layout.simple_list_item_1, listType);
                                typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subcategoryspinner.setAdapter(typeadapter);
                            }
                        }
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }


                }else {

//                    Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }


            }


            @Override
            public void onFailure(Call<MaintenanceResultResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());

            }
        });
    }

    private void getCities() {

        Call<CityResponse> call;

        {
            Api_Client.getInstance().getCityList().enqueue(new Callback<CityResponse>() {
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

                            if(i == cityList.size()-1)
                            {
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(FreeListing.this, android.R.layout.simple_spinner_item, names);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    cityNamesSpinner.setAdapter(adapter);
                                    cityNamesSpinner.setSelection(0);

                                    Log.d("tag", "inside names"+names.get(i));


                            }
                        }



//                                            locactionSpinner.setSelection(getIndex(locactionSpinner,city));


                    }


                }


                @Override
                public void onFailure(Call<CityResponse> call, Throwable t) {
//                    Toast.makeText(FreeListing.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+t.getLocalizedMessage());


                }
            });
        }

    }



    public void checkDataEntered(){


        if (name.getText().toString().isEmpty()) {
            name.requestFocus();
            name.setError("Please enter your name");
            return;

        }
        else if (phone.getText().toString().isEmpty()) {
            phone.requestFocus();
            phone.setError("Please enter your phone");
            return;

        } else if (cityNamesSpinner == null) {
            Toast.makeText(this, "Please select your city", Toast.LENGTH_SHORT).show();
        } else if (typeSpinner == null) {

            Toast.makeText(this, "Please select your type", Toast.LENGTH_SHORT).show();

        }else if (desc.getText().toString().trim().isEmpty()){

            desc.requestFocus();
            desc.setError("Please enter your Description");
            return;

        }else {
            ProgressDialog progressDialog = new ProgressDialog(FreeListing.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait..");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            Call<FreeListingResultResponse> call =Api_Client
                    .getInstance()
                    .saveListing(name.getText().toString(),cityId,email.getText().toString(),phone.getText().toString(),desc.getText().toString(),Type_Selected,typename,typename,typename);
            call.enqueue(new Callback<FreeListingResultResponse>() {
                @Override
                public void onResponse(Call<FreeListingResultResponse> call, Response<FreeListingResultResponse> response) {
                    progressDialog.dismiss();
                    if (response.body().getCode().equals(200)){

                        try {
                            Log.e("tag","Sucess"+response.body().getMessage());
                            Toast.makeText(FreeListing.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(FreeListing.this, ""+Type_Selected, Toast.LENGTH_SHORT).show();
                            Log.e("tag",""+subCategory);
                            name.setText("");
                            email.setText("");
                            phone.setText("");
                            desc.setText("");

//                        Intent intent = new Intent(FreeListing.this, MainActivity.class);
//                        startActivity(intent);

                        }catch (Exception e){
                            Log.d("tag","Exception "+response.body().getMessage());
                        }



                    }else {


//                        FreeListingResultResponse freeListingResultResponse= response.body();
//                        Toast.makeText(FreeListing.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("tag","fail "+response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<FreeListingResultResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("failure",t.getLocalizedMessage());

                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(FreeListing.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}