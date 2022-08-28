package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.ProductVendorListAdapter;
import com.civildeal.civildeal.Model.ServiceVendorListModel;
import com.civildeal.civildeal.Prefrences.SharedPrefForLocation;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.CityResponse;
import com.civildeal.civildeal.api.ModelResponse.CityResultReaponse;
import com.civildeal.civildeal.api.ModelResponse.ProductEnquiryResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductEnquiryResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProfileResponse;
import com.civildeal.civildeal.api.ModelResponse.ProfileResultResponse;
import com.civildeal.civildeal.api.ModelResponse.SaveVendorEnquiryResponse;
import com.civildeal.civildeal.api.ModelResponse.SaveVendorEnquiryResultResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView Rv;
    List<ProfileResultResponse> vendorList;
    ServiceVendorListModel serviceVendorListModel;
    SharedPrefManager sharedPrefManager;
    SharedPrefForLocation sharedPrefForLocation;
    String product_id;
    String vendor_type,city_id;

    ImageView productImage;
    TextView productName;
    Button contactNow;
    String vendor_id,service_id,lead_type,City_Selected;
    ArrayList<String> names = new ArrayList<String>();
    int citi_id[];
    String cityId;
    LinearLayout layout;
    ImageView noFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        contactNow = findViewById(R.id.contactNow);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        vendor_type = sharedPrefManager.getUserType().toString();

        sharedPrefForLocation = new SharedPrefForLocation(Product.this);
        city_id = sharedPrefForLocation.getSelectedLocationId();

        layout = findViewById(R.id.layout);
        noFound = findViewById(R.id.nofound);
        Rv=findViewById(R.id.serviceRV);
        Intent intent = getIntent();
        product_id = intent.getStringExtra("product_id");
        Log.e("tag",""+product_id);

        getProductList();

        contactNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEnquiry();

            }
        });

        getVendorList();



    }

    private void sendEnquiry() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//
//        dialog.setMessage("Provide us your valuable feedback");

        LayoutInflater inflater = LayoutInflater.from(this);
        View reg_layout = inflater.inflate(R.layout.send_enquiry,null);
        final EditText requirment = reg_layout.findViewById(R.id.requirment);
        final EditText name = reg_layout.findViewById(R.id.name);
        final EditText email = reg_layout.findViewById(R.id.email);
        final EditText phone = reg_layout.findViewById(R.id.mobileNo);
        final Spinner cityNamesSpinner = reg_layout.findViewById(R.id.citySpinner);

        cityNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                citi_id = position;

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
//                Toast.makeText(RegisterForm.this, "" + city_id, Toast.LENGTH_SHORT).show();
                Log.e("tag","id"+ citi_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                cityId = String.valueOf(citi_id[0]);
                City_Selected = names.get(0);
                cityNamesSpinner.setSelection(0);
//                Toast.makeText(RegisterForm.this, "" + city_id, Toast.LENGTH_SHORT).show();

            }
        });

        dialog.setView(reg_layout);

        Call<CityResponse> call;

        {
            Api_Client.getInstance().getCityList().enqueue(new Callback<CityResponse>() {
                @Override
                public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {

                    if (response.body().getMessage().equals("City fetch successfully")) {
//                        Toast.makeText(VendorDetails.this, "Success", Toast.LENGTH_SHORT).show();

                        CityResponse cityResponse = response.body();
                        List<CityResultReaponse> cityList = cityResponse.getData();
                        Log.e("tag", "cityList");

                        citi_id = new int[cityList.size()];
                        for (int i = 0; i < cityList.size(); i++) {
                            Log.e("tag", "names");
                            names.add(cityList.get(i).getName().toString());
                            citi_id[i] = cityList.get(i).getId();
                            Log.e("tag", "getname");

                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Product.this, android.R.layout.simple_spinner_item, names);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cityNamesSpinner.setAdapter(adapter);


                    } else {
//                        Toast.makeText(Product.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("tag","fail "+response.body().getMessage());


                    }

                }

                @Override
                public void onFailure(Call<CityResponse> call, Throwable t) {
//                    Toast.makeText(Product.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+t.getLocalizedMessage());


                }
            });
        }

        //set button

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.e("tag","id "+citi_id);
                Log.e("tag","id "+vendor_type);



            }






        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (requirment.getText().toString().isEmpty() && name.getText().toString().isEmpty() && phone.getText().toString().isEmpty()){
//                    Toast.makeText(VendorDetails.this, "Submited", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(VendorDetails.this, ""+citi_id, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Product.this, "please fill all details", Toast.LENGTH_SHORT).show();



                }else {
                    alertDialog.dismiss();
                    saveEnquiry();
                }

            }

            private void saveEnquiry() {

                ProgressDialog progressDialog = new ProgressDialog(Product.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("We are processing...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                Log.e("tag","product "+product_id);
                Call<SaveVendorEnquiryResultResponse> call = Api_Client
                        .getInstance()
                        .getenquiry(name.getText().toString(),email.getText().toString(),requirment.getText().toString(), Integer.parseInt(cityId),phone.getText().toString(),vendor_id,service_id,product_id,"product");
                call.enqueue(new Callback<SaveVendorEnquiryResultResponse>() {
                    @Override
                    public void onResponse(Call<SaveVendorEnquiryResultResponse> call, Response<SaveVendorEnquiryResultResponse> response) {
                        progressDialog.dismiss();
                        if (response.body().getCode().equals(200)){

                            try {
                                SaveVendorEnquiryResultResponse saveVendorEnquiryResultResponse = response.body();
                                List<SaveVendorEnquiryResponse> enquiryList = Collections.singletonList(saveVendorEnquiryResultResponse.getData());
                                Toast.makeText(Product.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("tag",""+response.body().getMessage());
                                Log.e("tag","product1 "+product_id);
                                Log.e("tag","cityId "+cityId);
                                Log.e("tag","vendor_id "+vendor_id);
                                Log.e("tag","lead_type "+lead_type);
                            }catch (Exception e){
                                Log.e("tag","Exception "+response.body().getMessage());
                            }



                        }else {

                            Log.e("tag",""+response.body().getMessage());
//                            Toast.makeText(Product.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }




                    }

                    @Override
                    public void onFailure(Call<SaveVendorEnquiryResultResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("tag",""+t.getLocalizedMessage());

                    }
                });

            }
        });

    }

    private void getProductList() {

        Call<ProductEnquiryResultResponse> call1 = Api_Client
                .getInstance()
                .getProductDetails(product_id);
        call1.enqueue(new Callback<ProductEnquiryResultResponse>() {
            @Override
            public void onResponse(Call<ProductEnquiryResultResponse> call, Response<ProductEnquiryResultResponse> response) {

                if (response.body().getCode().equals(200)){
                    try {
                        ProductEnquiryResultResponse productEnquiryResultResponse = response.body();
                        List<ProductEnquiryResponse> productList = productEnquiryResultResponse.getData();

                        Picasso.get()
                                .load("https://civildeal.com/public/assets/uploads/product/"+productList.get(0).getProductImage())
                                .error(R.drawable.profile)
                                .placeholder(R.drawable.profile)
                                .into(productImage);

                        productName.setText(productList.get(0).getName());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }




                }else {


//                    Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ProductEnquiryResultResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());


            }
        });
    }

    private void getVendorList() {

        ProgressDialog progressDialog = new ProgressDialog(Product.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<ProfileResultResponse> call = Api_Client
                .getInstance()
                .vendorlist("product",product_id,city_id);
        call.enqueue(new Callback<ProfileResultResponse>() {
            @Override
            public void onResponse(Call<ProfileResultResponse> call, Response<ProfileResultResponse> response) {

                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){

                    try {
                        ProfileResultResponse profileResultResponse = response.body();
                        List<ProfileResponse> vendorList = profileResultResponse.getData();
                        Log.e("tag", "product_id"+product_id);
                        Log.e("tag", "city_id "+city_id);
                        ProductVendorListAdapter productVendorListAdapter = new ProductVendorListAdapter(Product.this, vendorList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(Product.this, 1);
                        Rv.setLayoutManager(gridLayoutManager);
                        Rv.setAdapter(productVendorListAdapter);
                        productVendorListAdapter.notifyDataSetChanged();
                    }catch (Exception e){
                        Log.d("tag","EXception "+response.body().getMessage());
                    }



                }else {

                    noFound.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.VISIBLE);
//                    Toast.makeText(Product.this, "No Vendor Found For Selected Location", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfileResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("tag","failuer");
//                Toast.makeText(Product.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(Product.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}