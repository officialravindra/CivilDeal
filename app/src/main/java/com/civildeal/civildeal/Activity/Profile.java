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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.GetVendorProfilePicResponse;
import com.civildeal.civildeal.api.ModelResponse.GetvendorProfilePicResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProfileResponse;
import com.civildeal.civildeal.api.ModelResponse.ProfileResultResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    Toolbar toolbar;
    ImageView profilePic;
    TextView name,mobile;
    EditText Name,phone,address,panNo,desc,lastName,comapnyName,rateValue,email;
    List<ProfileResultResponse> profiledetails;
    ArrayList<String> names = new ArrayList<String>();
    SharedPrefManager sharedPrefManager;
    String vendor_id;

    Button editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profilePic = findViewById(R.id.profilePic);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        mobile = findViewById(R.id.mobile);
        Name = findViewById(R.id.Name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        panNo = findViewById(R.id.pan_no);
        desc = findViewById(R.id.desc);
        comapnyName = findViewById(R.id.companyname);
        rateValue = findViewById(R.id.ratevalue);
        email = findViewById(R.id.email);

        editProfile = findViewById(R.id.edit_profile);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        vendor_id = String.valueOf(sharedPrefManager.getUserId());
//        Toast.makeText(this, "vendor_id"+vendor_id, Toast.LENGTH_SHORT).show();

        getProfilePic();
        loadingProfile();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,EditProfile.class);
                startActivity(intent);
                finish();


            }
        });


    }

    private void getProfilePic() {
        Call<GetvendorProfilePicResultResponse> call = Api_Client
                .getInstance()
                .getVendorImage(vendor_id);
        call.enqueue(new Callback<GetvendorProfilePicResultResponse>() {
            @Override
            public void onResponse(Call<GetvendorProfilePicResultResponse> call, Response<GetvendorProfilePicResultResponse> response) {

                if (response.body().getCode().equals(200)){
                    try {
                        GetvendorProfilePicResultResponse getvendorProfilePicResultResponse = response.body();
                        List<GetVendorProfilePicResponse> profileImage = Collections.singletonList(getvendorProfilePicResultResponse.getData());
                        Picasso.get()
                                .load(profileImage.get(0).getImagePath()+profileImage.get(0).getUserimage())
                                .error(R.drawable.icon_image)
                                .placeholder(R.drawable.icon_image)
                                .into(profilePic);
                        Log.e("tag","image "+response.body().getData());
                        Log.e("tag","image "+profileImage.get(0).getImagePath());
                        Log.e("tag","image "+profileImage.get(0).getUserimage());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }


                }else {

                    Log.e("tag",""+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetvendorProfilePicResultResponse> call, Throwable t) {
                Log.e("tag",""+t.getLocalizedMessage());

            }
        });
    }

    private void loadingProfile(){
        ProgressDialog progressDialog = new ProgressDialog(Profile.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<ProfileResultResponse> call = Api_Client
                .getInstance()
                .getProfileDetails(vendor_id);
        call.enqueue(new Callback<ProfileResultResponse>() {
            @Override
            public void onResponse(Call<ProfileResultResponse> call, Response<ProfileResultResponse> response) {

                if (!response.isSuccessful()){

                    progressDialog.dismiss();
                    Toast.makeText(Profile.this, "Response Fail"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }else if (response.body().getCode().equals(200)){
                    progressDialog.dismiss();
                    try {
                        ProfileResultResponse profileResultResponse = response.body();
                        List<ProfileResponse> profiledetails = profileResultResponse.getData();

//                    Picasso.get()
//                            .load("https://civildeal.com/public/assets/uploads/vendor/"+profiledetails.get(0).getUserimage())
//                            .error(R.drawable.icon_image)
//                            .placeholder(R.drawable.icon_image)
//                            .into(profilePic);
                        name.setText(profiledetails.get(0).getName()+ " "+profiledetails.get(0).getLastname());
                        lastName.setText(profiledetails.get(0).getLastname());
                        mobile.setText(profiledetails.get(0).getMobile());
                        Name.setText(profiledetails.get(0).getName());
                        phone.setText(profiledetails.get(0).getMobile());
                        address.setText(profiledetails.get(0).getAddress());
                        panNo.setText(profiledetails.get(0).getPan());
                        desc.setText(profiledetails.get(0).getShortDescription());
                        comapnyName.setText(profiledetails.get(0).getCompanyName());
                        rateValue.setText(profiledetails.get(0).getServiceAmount());
                        email.setText(profiledetails.get(0).getEmail());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }


                }



            }

            @Override
            public void onFailure(Call<ProfileResultResponse> call, Throwable t) {
                progressDialog.dismiss();
//                Toast.makeText(Profile.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("tag","fail "+t.getLocalizedMessage());

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(Profile.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Profile.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}