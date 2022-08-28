package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.CityResponse;
import com.civildeal.civildeal.api.ModelResponse.CityResultReaponse;
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

public class VendorDetails extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    ImageView vendorProfile;
    TextView vendorName,desc,address,quality,experience,projectDetails,companyname;
    Button sendEnquiry;
    SharedPrefManager sharedPrefManager;
    String vendor_id,service_id,product_id,lead_type,vendor_type,City_Selected;
    ArrayList<String> names = new ArrayList<String>();
    Spinner cityNamesSpinner;
//    int citi_id = 0;
    int citi_id[];
    String cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_details);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        vendor_type = sharedPrefManager.getVendorType();
        Log.e("tag",""+vendor_type);


        Intent intent = getIntent();
        vendor_id = intent.getStringExtra("vendor_id");

//        Toast.makeText(this, ""+vendor_id, Toast.LENGTH_SHORT).show();




        vendorProfile = findViewById(R.id.vendorimage);
        vendorName = findViewById(R.id.name);
        companyname = findViewById(R.id.companyName);
        desc = findViewById(R.id.Desc);
        address = findViewById(R.id.address);
        quality = findViewById(R.id.quality);
        experience = findViewById(R.id.experience);
        projectDetails = findViewById(R.id.details);
        sendEnquiry = findViewById(R.id.sendEnquiry);
        progressBar = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.llayout);



        getVendorDetails();



        sendEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSendEnquiry();

            }
        });
    }

    private void getVendorDetails() {

        Log.d("tag",""+vendor_id);

        progressBar.setVisibility(View.VISIBLE);
        Call<ProfileResultResponse> call = Api_Client
                .getInstance()
                .getProfileDetails(vendor_id);
        call.enqueue(new Callback<ProfileResultResponse>() {
            @Override
            public void onResponse(Call<ProfileResultResponse> call, Response<ProfileResultResponse> response) {


                if (!response.isSuccessful()){

                    progressBar.setVisibility(View.GONE);
                    Log.e("tag","Message");
//                    Toast.makeText(VendorDetails.this, "Response Fail"+response.code(), Toast.LENGTH_SHORT).show();
                    return;

                }else {
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    ProfileResultResponse profileResultResponse = response.body();
                    List<ProfileResponse> vendorList = profileResultResponse.getData();
                    Log.e("tag","vendorList");
//                    vendorProfile.setImageResource(Integer.parseInt(vendorList.get(0).getUserimage()));
                    Picasso.get()
                            .load("https://civildeal.com/public/assets/uploads/vendor/"+vendorList.get(0).getUserimage())
                            .error(R.drawable.icon_image)
                            .placeholder(R.drawable.icon_image)
                            .into(vendorProfile);

                    Log.e("profile",""+vendorList.get(0).getWorkImage1());
                    vendorName.setText(vendorList.get(0).getName());
                    companyname.setText(vendorList.get(0).getCompanyName());
                    desc.setText(vendorList.get(0).getShortDescription());
                    address.setText(vendorList.get(0).getAddress());
                    quality.setText(vendorList.get(0).getQuality());
                    experience.setText(vendorList.get(0).getExperience());
                    projectDetails.setText(vendorList.get(0).getProjectDetail());
                    service_id = String.valueOf(vendorList.get(0).getServiceId());
                    product_id = vendorList.get(0).getProductId();
                    lead_type = vendorList.get(0).getVendorType();

                    Log.e("tag","service_id"+service_id);
                    Log.e("tag","product_id"+product_id);

                }
            }

            @Override
            public void onFailure(Call<ProfileResultResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
//                Toast.makeText(VendorDetails.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("tag","failure "+t.getLocalizedMessage());

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
//            Intent intent = new Intent(VendorDetails.this, Service.class);
//            startActivity(intent);
//            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSendEnquiry(){

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

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(VendorDetails.this, android.R.layout.simple_spinner_item, names);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cityNamesSpinner.setAdapter(adapter);


                    } else {
//                        Toast.makeText(VendorDetails.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("tag","fail "+response.body().getMessage());


                    }

                }

                @Override
                public void onFailure(Call<CityResponse> call, Throwable t) {
//                    Toast.makeText(VendorDetails.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

                if (!requirment.getText().toString().isEmpty() && !name.getText().toString().isEmpty() && !phone.getText().toString().isEmpty()){
//                    Toast.makeText(VendorDetails.this, "Submited", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(VendorDetails.this, ""+citi_id, Toast.LENGTH_SHORT).show();

                    saveEnquiry();

                    alertDialog.dismiss();
                }else {
                    Toast.makeText(VendorDetails.this, "please fill all details", Toast.LENGTH_SHORT).show();
                }

            }

            private void saveEnquiry() {
                Log.e("tag","cityId "+cityId);
                Log.e("tag","product "+product_id);
                Call<SaveVendorEnquiryResultResponse> call = Api_Client
                        .getInstance()
                        .getenquiry(name.getText().toString(),email.getText().toString(),requirment.getText().toString(), Integer.parseInt(cityId),phone.getText().toString(),vendor_id,service_id,product_id,lead_type);
                call.enqueue(new Callback<SaveVendorEnquiryResultResponse>() {
                    @Override
                    public void onResponse(Call<SaveVendorEnquiryResultResponse> call, Response<SaveVendorEnquiryResultResponse> response) {

                        if (response.body().getCode().equals(200)){
                            try {
                                SaveVendorEnquiryResultResponse saveVendorEnquiryResultResponse = response.body();
                                List<SaveVendorEnquiryResponse> enquiryList = Collections.singletonList(saveVendorEnquiryResultResponse.getData());
                                Toast.makeText(VendorDetails.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("tag",""+response.body().getMessage());
                                Log.e("tag","product1 "+product_id);
                                Log.e("tag","cityId "+cityId);
                                Log.e("tag","vendor_id "+vendor_id);
                                Log.e("tag","lead_type "+lead_type);
                            }catch (Exception e){
                                Log.d("tag","Exception "+response.body().getMessage());
                            }




                        }else {

                            Log.e("tag",""+response.body().getMessage());
//                            Toast.makeText(VendorDetails.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }




                    }

                    @Override
                    public void onFailure(Call<SaveVendorEnquiryResultResponse> call, Throwable t) {
                        Log.d("tag","fail "+t.getLocalizedMessage());

                    }
                });

            }
        });



    }
}