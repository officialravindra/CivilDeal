package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import androidx.appcompat.widget.Toolbar;

import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.CityResponse;
import com.civildeal.civildeal.api.ModelResponse.CityResultReaponse;
import com.civildeal.civildeal.api.ModelResponse.PromoterProfileResponse;
import com.civildeal.civildeal.api.ModelResponse.PromoterProfileResultResponse;
import com.civildeal.civildeal.api.ModelResponse.PromoterUpdateProfileResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoterProfile extends AppCompatActivity {

    Toolbar toolbar;
    EditText name,phone,email,dob,vendorType,location,age,pancard,aadhaarcard,bankAccNo,bankname,bankAccHolder,bankIfscCode;
    Button submit;
    SharedPrefManager sharedPrefManager;
    Spinner genderSpineer;
    String[] gendertype;
    String Type_Selected;
    Spinner cityNamesSpinner;
    String City_Selected;
    int citi_id[];
    String cityId;
    ArrayList<String> names = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter_profile);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.enteremail);
        dob = findViewById(R.id.date);
        location = findViewById(R.id.Location);
        age = findViewById(R.id.age);
        pancard = findViewById(R.id.pancard);
        aadhaarcard = findViewById(R.id.adharcard);
        bankAccNo = findViewById(R.id.bankNumber);
        bankname = findViewById(R.id.Bankaccount);
        bankAccHolder = findViewById(R.id.holdername);
        bankIfscCode = findViewById(R.id.ifsccode);
        submit = findViewById(R.id.update);
        genderSpineer = findViewById(R.id.genderSpinner);
        cityNamesSpinner = findViewById(R.id.citySpinner);

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

        getCities();

        gendertype = getResources().getStringArray(R.array.selectgender);
        ArrayAdapter typeAdapter = new ArrayAdapter(PromoterProfile.this, android.R.layout.simple_spinner_item,gendertype);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpineer.setAdapter(typeAdapter);

        genderSpineer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Type_Selected = gendertype[i];
                genderSpineer.setSelection(i);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Type_Selected = gendertype[0];
                genderSpineer.setSelection(0);
            }
        });

        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        getPromoterProfile();
        
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                updateProfile();
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

                        try {
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
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PromoterProfile.this, android.R.layout.simple_spinner_item, names);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    cityNamesSpinner.setAdapter(adapter);
                                    cityNamesSpinner.setSelection(0);

                                    Log.d("tag", "inside names"+names.get(i));


                                }
                            }
                        }catch (Exception e){
                            Log.d("tag","Exception "+response.body().getMessage());
                        }



//                                            locactionSpinner.setSelection(getIndex(locactionSpinner,city));


                    }


                }


                @Override
                public void onFailure(Call<CityResponse> call, Throwable t) {
//                    Toast.makeText(PromoterProfile.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+t.getLocalizedMessage());


                }
            });
        }

    }

    private void updateProfile() {

        Call<PromoterUpdateProfileResponse> call = Api_Client
                .getInstance()
                .updatePromoterProfile(sharedPrefManager.getPromoterId(),name.getText().toString(),phone.getText().toString(),email.getText().toString(),dob.getText().toString(),Type_Selected,cityId,pancard.getText().toString(),aadhaarcard.getText().toString(),bankname.getText().toString(),bankAccHolder.getText().toString(),bankIfscCode.getText().toString(),bankAccNo.getText().toString(),age.getText().toString());
        call.enqueue(new Callback<PromoterUpdateProfileResponse>() {
            @Override
            public void onResponse(Call<PromoterUpdateProfileResponse> call, Response<PromoterUpdateProfileResponse> response) {

                if (response.body().getCode().equals(200)){
                    try {
                        getPromoterProfile();
                        Toast.makeText(PromoterProfile.this, "Profile Update Successfully", Toast.LENGTH_SHORT).show();
                        Log.d("tag","sucess "+response.body().getMessage());
                        Log.d("tag","name "+name.getText().toString());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }




                }else {

                    Log.d("tag","fail "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<PromoterUpdateProfileResponse> call, Throwable t) {
                Log.d("tag","fail "+t.getLocalizedMessage());

            }
        });
    }

    private void getPromoterProfile() {
        ProgressDialog progressDialog = new ProgressDialog(PromoterProfile.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading profile..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Log.d("tag","promoter_id "+sharedPrefManager.getPromoterId());

        Call<PromoterProfileResultResponse> call = Api_Client
                .getInstance()
                .getpromoterprofileDetails(sharedPrefManager.getPromoterId());
        call.enqueue(new Callback<PromoterProfileResultResponse>() {
            @Override
            public void onResponse(Call<PromoterProfileResultResponse> call, Response<PromoterProfileResultResponse> response) {
                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){

                    try {
                        PromoterProfileResultResponse  promoterProfileResultResponse = response.body();
                        List<PromoterProfileResponse> promoterprofiledetails = promoterProfileResultResponse.getData();


//                    Toast.makeText(PromoterProfile.this, ""+response.body().toString(), Toast.LENGTH_SHORT).show();
                        name.setText(promoterprofiledetails.get(0).getName());
                        phone.setText(promoterprofiledetails.get(0).getMobile());
                        email.setText(promoterprofiledetails.get(0).getEmail());
                        dob.setText(promoterprofiledetails.get(0).getDob());
                        location.setText(promoterprofiledetails.get(0).getLocationName());
                        age.setText(promoterprofiledetails.get(0).getAge().toString());
                        pancard.setText(promoterprofiledetails.get(0).getPanCard().toString());
                        aadhaarcard.setText(promoterprofiledetails.get(0).getAadharCard().toString());
                        bankAccNo.setText(promoterprofiledetails.get(0).getBankAccountNumber().toString());
                        bankname.setText(promoterprofiledetails.get(0).getBankAccount());
                        bankAccHolder.setText(promoterprofiledetails.get(0).getBankHolderName());
                        bankIfscCode.setText(promoterprofiledetails.get(0).getBankIfscCode());

                        Log.d("tag","gender "+promoterprofiledetails.get(0).getGender());
                        for(int i=0;i<gendertype.length;i++)
                        {
                            if(promoterprofiledetails.get(0).getGender().equals(gendertype[i]))
                            {
                                genderSpineer.setSelection(i);
                            }
                        }

//                    Toast.makeText(PromoterProfile.this, ""+promoterprofiledetails.get(0).getLocationName(), Toast.LENGTH_SHORT).show();
                        for(int i=0;i<names.size();i++)
                        {
                            if(promoterprofiledetails.get(0).getLocationName().equals(names.get(i)))
                            {
                                cityNamesSpinner.setSelection(i);
                            }
                        }
                        Log.e("tag","sucess "+response.body().getMessage());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }



                }else {

                    Log.d("tag","fail "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<PromoterProfileResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("tag","failuer "+t.getLocalizedMessage());

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(PromoterProfile.this, SwitchPromoter.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
//        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//
//        onBackPressed();
//        super.onBackPressed();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        getPromoterProfile();
    }
}