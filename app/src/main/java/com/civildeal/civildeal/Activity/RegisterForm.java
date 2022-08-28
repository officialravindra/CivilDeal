package com.civildeal.civildeal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.MaintenanceAdapter;
import com.civildeal.civildeal.Adapter.ProductAdapter;
import com.civildeal.civildeal.Adapter.ServiceAdapter;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.CityResponse;
import com.civildeal.civildeal.api.ModelResponse.CityResultReaponse;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResponse;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductResultResponse;
import com.civildeal.civildeal.api.ModelResponse.RegisterResponse;
import com.civildeal.civildeal.api.ModelResponse.RegisterResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResultResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterForm extends AppCompatActivity {

    TextInputEditText name, mobileno, password;
    Button submit;

    Spinner cityNamesSpinner, typeSpinner,subtypespinner;
    String[] city;
    String[] type;
    List<CityResultReaponse> cityList;
    ArrayList<String> names = new ArrayList<String>();
    String City_Selected, Type_Selected,Sub_Category_Selected;
    int citi_id[];
    String cityId;
    String subCategory,typename;
    ArrayList<String> listType;
    String[] sub_category;
    ArrayAdapter typeadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        name = findViewById(R.id.username);
        mobileno = findViewById(R.id.userphone_no);
        password = findViewById(R.id.userpassword);
        submit = findViewById(R.id.submit);


        cityNamesSpinner = findViewById(R.id.citySpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        subtypespinner = findViewById(R.id.subtypeSpinner);

        city = getResources().getStringArray(R.array.cityNames);
        type = getResources().getStringArray(R.array.selectType);
//
//       ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,city);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//       cityNamesSpinner.setAdapter(adapter);

        ArrayAdapter typeAdapter = new ArrayAdapter(RegisterForm.this, android.R.layout.simple_spinner_item, type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getContext(), ""+Cities[i], Toast.LENGTH_SHORT).show();
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
                typeSpinner.setSelection(i);
                Type_Selected = type[i];
                // getSelectedTypeCategories();
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

        cityNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (citi_id != null) {
                    Log.e("tag", "citi_id " + citi_id[position]);
                    cityId = String.valueOf(citi_id[position]);
//                    Toast.makeText(getContext(), " not null", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                }
                cityNamesSpinner.setSelection(position);
                City_Selected = String.valueOf(position);
//                Toast.makeText(RegisterForm.this, "" + city_id, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                cityId = String.valueOf(citi_id[0]);
                City_Selected = String.valueOf(0);
                cityNamesSpinner.setSelection(0);
//                Toast.makeText(RegisterForm.this, "" + city_id, Toast.LENGTH_SHORT).show();

            }
        });

        subtypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                typename = parent.getItemAtPosition(position).toString();
                subtypespinner.setSelection(position);
                Sub_Category_Selected = sub_category[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                typename = parent.getItemAtPosition(0).toString();

                Sub_Category_Selected = sub_category[0];
                subtypespinner.setSelection(0);

            }
        });

        getCities();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Animation myAnim = AnimationUtils.loadAnimation(RegisterForm.this,R.anim.bounce);
                submit.startAnimation(myAnim);
                checkDataEntered();
            }
        });


    }


    private void getServiceList() {
        ProgressDialog progressDialog = new ProgressDialog(RegisterForm.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Log.d("tag","service ");
        Call<ServiceResultResponse> call = Api_Client
                .getInstance()
                .getServicesList();
        call.enqueue(new Callback<ServiceResultResponse>() {
            @Override
            public void onResponse(Call<ServiceResultResponse> call, Response<ServiceResultResponse> response) {

                progressDialog.dismiss();
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

                                typeadapter = new ArrayAdapter(RegisterForm.this, android.R.layout.simple_spinner_item, listType);
                                typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subtypespinner.setAdapter(typeadapter);
                            }
                        }
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }



                }else {
                    Log.d("tag",""+response.body().getMessage());

//                    Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServiceResultResponse> call, Throwable t) {

                progressDialog.dismiss();
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

                                ArrayAdapter typeadapter = new ArrayAdapter(RegisterForm.this, android.R.layout.simple_spinner_item, listType);
                                typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subtypespinner.setAdapter(typeadapter);
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

                                ArrayAdapter typeadapter = new ArrayAdapter(RegisterForm.this, android.R.layout.simple_spinner_item, listType);
                                typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subtypespinner.setAdapter(typeadapter);
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
//                        Toast.makeText(RegisterForm.this, "Success", Toast.LENGTH_SHORT).show();

                        try {
                            CityResponse cityResponse = response.body();
                            List<CityResultReaponse> cityList = cityResponse.getData();
                            Log.e("tag", "cityList"+cityList.get(0).getId());

                            citi_id = new int[cityList.size()];
                            for (int i = 0; i < cityList.size(); i++) {
                                Log.e("tag", "names");
                                names.add(cityList.get(i).getName().toString());
                                citi_id[i] = cityList.get(i).getId();
                                Log.e("tag", "getname");

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterForm.this, android.R.layout.simple_spinner_item, names);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cityNamesSpinner.setAdapter(adapter);
                        }catch (Exception e){
                            Log.d("tag","Exception "+response.body().getMessage());
                        }



                    } else {
//                        Toast.makeText(RegisterForm.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("tag","fail "+response.body().getMessage());


                    }

                }

                @Override
                public void onFailure(Call<CityResponse> call, Throwable t) {
//                    Toast.makeText(RegisterForm.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+t.getLocalizedMessage());


                }
            });
        }

    }



    public void checkDataEntered() {


        if (name.getText().toString().isEmpty()) {
            name.requestFocus();
            name.setError("Please enter your name");
            return;

        } else if (mobileno.getText().toString().trim().isEmpty()) {
            Log.e("tag", "erro2");
            mobileno.requestFocus();
            mobileno.setError("Please enter your Mobile no");
            return;

        } else if (password.getText().toString().isEmpty()) {
            Log.e("tag", "erro3");
            password.requestFocus();
            password.setError("Please enter your password");
            return;

        } else if (cityNamesSpinner == null) {
            Toast.makeText(this, "Please select your city", Toast.LENGTH_SHORT).show();
        } else if (type == null) {

            Toast.makeText(this, "Please select your type", Toast.LENGTH_SHORT).show();
        } else {

            Log.e("tag1", "" + cityId);

            Call<RegisterResultResponse> call;

            {
                Api_Client.getInstance().getRegister(name.getText().toString(), mobileno.getText().toString(), password.getText().toString(), Integer.parseInt(cityId), Type_Selected,typename,typename).enqueue(new Callback<RegisterResultResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResultResponse> call, Response<RegisterResultResponse> response) {


                        if (response.body().getCode().equals(200)) {

                            try {
                                RegisterResultResponse registerResultResponse = response.body();
                                List<RegisterResponse> registerList = Collections.singletonList(registerResultResponse.getData());
                                Toast.makeText(RegisterForm.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();


                                Intent intent = new Intent(RegisterForm.this, Login.class);
                                startActivity(intent);
                                finish();
                            }catch (Exception e){
                                e.printStackTrace();
                                Log.d("tag","eception "+e.getMessage());
                            }



                        } else {
                            try {
                                Log.e("tag", "fail " + response.body().getMessage());
                            }catch (Exception e){
                                e.printStackTrace();
                                Log.d("tag","fail_rxp "+e.getMessage());

                            }

//                            Toast.makeText(RegisterForm.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<RegisterResultResponse> call, Throwable t) {
                        try {
                            Log.e("failure", t.getLocalizedMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.d("tag","fail_rxp1 "+e.getMessage());

                        }

//                        Toast.makeText(RegisterForm.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterForm.this,Login.class);
        startActivity(intent);
        finish();
    }
}