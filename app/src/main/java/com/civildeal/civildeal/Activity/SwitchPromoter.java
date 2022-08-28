package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.PromoterMyLeadAdapter;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.GetVendorProfilePicResponse;
import com.civildeal.civildeal.api.ModelResponse.GetvendorProfilePicResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProfileResponse;
import com.civildeal.civildeal.api.ModelResponse.ProfileResultResponse;
import com.civildeal.civildeal.api.ModelResponse.PromoterMyLeadResponse;
import com.civildeal.civildeal.api.ModelResponse.PromoterMyLeadResultResponse;
import com.civildeal.civildeal.api.ModelResponse.WalletResponse;
import com.civildeal.civildeal.api.ModelResponse.WalletResultResponse;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwitchPromoter extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView myLeadsRV;
    SharedPrefManager sharedPrefManager;
    Spinner typeSpinner;
    String[] type;
    String Type_Selected,vendor_id;
    ArrayAdapter typeadapter;
    EditText search;
    SearchView searchView;
    TextView username,userlastname,usermobile,walletamount,city;
    ImageView userImage;
    String keywords;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_promoter);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        myLeadsRV = findViewById(R.id.myleadsRV);
        typeSpinner = findViewById(R.id.category);
        search = findViewById(R.id.search);
        swipeRefreshLayout = findViewById(R.id.swipereferesh);
        searchView = findViewById(R.id.searchview);
        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        vendor_id = String.valueOf(sharedPrefManager.getUserId());

        type = getResources().getStringArray(R.array.selectLeadType);
        ArrayAdapter typeAdapter = new ArrayAdapter(SwitchPromoter.this, android.R.layout.simple_spinner_item,type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);


        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                int pos = listType.indexOf(typeadapter.getItem(i));


                Type_Selected = type[i];
                typeSpinner.setSelection(i);
                if (Type_Selected.equals("select type")) {
                    getMyLead(keywords);
                    Log.e("tag", "select1" + Type_Selected);
                }
                else if (Type_Selected.equals("service")) {
                    getMyLead(keywords);
                    Log.e("tag", "service" + Type_Selected);
                }
                else if (Type_Selected.equals("product")) {

                    getMyLead(keywords);
                    Log.e("tag", "product" + Type_Selected);
                }
                else if (Type_Selected.equals("maintenance")) {
                    getMyLead(keywords);
                    Log.e("tag", "maintenance " + Type_Selected);
                } else {
//                Log.e("tag", "vendor_type " + Type_Selected);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


                Type_Selected = type[0];
                typeSpinner.setSelection(0);
//                if (Type_Selected.equals("select type")) {
//                    getMyLead();
//                    Log.e("tag", "select" + Type_Selected);
//                }
//                else if (Type_Selected.equals("service")) {
//                    getMyLead();
//                    Log.e("tag", "service" + Type_Selected);
//                }
//                else if (Type_Selected.equals("product")) {
//
//                    getMyLead();
//                    Log.e("tag", "product" + Type_Selected);
//                }
//                else if (Type_Selected.equals("maintenance")) {
//                    getMyLead();
//                    Log.e("tag", "maintenance " + Type_Selected);
//                }
//                else {
////                Log.e("tag", "vendor_type " + Type_Selected);
//                }
            }
        });




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                updateNavigationViewHeader();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Log.d("tag","be "+search.getText().toString());

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("tag","on "+search.getText().toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.d("tag","search "+search.getText().toString());

                getMyLead(s.toString());

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("tag","swipe");
                getMyLead(keywords);
            }
        });


//        getMyLead();
    }

    private void updateNavigationViewHeader() {

        View header = navigationView.getHeaderView(0);
        username = header.findViewById(R.id.username);
        userlastname = header.findViewById(R.id.userLastname);
        usermobile = header.findViewById(R.id.userMobile);
        userImage = header.findViewById(R.id.userimage);
        walletamount = header.findViewById(R.id.walletbalance);
        city = header.findViewById(R.id.city);

        city.setText(" " +sharedPrefManager.getLocation());

//        if (!sharedPrefForLocation.getname().equals("0") && !sharedPrefForLocation.getlastname().equals("0")){
//
//            username.setText(sharedPrefForLocation.getname()+" "+sharedPrefForLocation.getlastname());
//            usermobile.setText(sharedPrefForLocation.getmobile());
//        }else {
//
//            username.setText(sharedPrefManager.getUserName()+ " "+sharedPrefManager.getUserLastName());
//            usermobile.setText(sharedPrefManager.getUserMobile());
//        }



        getProfilePic();
        loadingProfile();


        getWalletamount();

    }

    private void getMyLead(String text) {

//        ProgressDialog progressDialog = new ProgressDialog(SwitchPromoter.this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Please wait..");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();

        Log.d("tag","id "+sharedPrefManager.getPromoterId());
        Log.d("tag","type "+Type_Selected);
        Log.d("tag","keywords "+text);


        Call<PromoterMyLeadResultResponse> call = Api_Client
                .getInstance()
                .getMyLead(sharedPrefManager.getPromoterId(),Type_Selected,text);
        call.enqueue(new Callback<PromoterMyLeadResultResponse>() {
            @Override
            public void onResponse(Call<PromoterMyLeadResultResponse> call, Response<PromoterMyLeadResultResponse> response) {

//                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){

                    try {
                        swipeRefreshLayout.setRefreshing(false);
                        myLeadsRV.setVisibility(View.VISIBLE);
                        PromoterMyLeadResultResponse promoterMyLeadResultResponse = response.body();
                        List<PromoterMyLeadResponse> promoterMyLeadList = promoterMyLeadResultResponse.getData();
                        PromoterMyLeadAdapter promoterMyLeadAdapter = new PromoterMyLeadAdapter(SwitchPromoter.this,promoterMyLeadList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(SwitchPromoter.this, 1);
                        myLeadsRV.setLayoutManager(gridLayoutManager);
                        myLeadsRV.setAdapter(promoterMyLeadAdapter);
                        promoterMyLeadAdapter.notifyDataSetChanged();
                        Log.d("tag","Success Mylead"+response.body().getMessage());
                        Log.d("tag","response "+response.body().getData());
                    }catch (Exception e){

                        e.printStackTrace();
                    }


                }else {
                    myLeadsRV.setVisibility(View.GONE);
                    Toast.makeText(SwitchPromoter.this, "No available lead", Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<PromoterMyLeadResultResponse> call, Throwable t) {
//                progressDialog.dismiss();
                Log.d("tag","fail "+t.getLocalizedMessage());

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Menu nav_menu = navigationView.getMenu();
        Fragment fragment = null;

        if (id == R.id.Home) {
        }else if (id == R.id.addleads) {
            startActivity(new Intent(SwitchPromoter.this, AddPromoterLead.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        }
//        else if (id == R.id.myleads) {
//            startActivity(new Intent(SwitchPromoter.this, SwitchPromoter.class));
//            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
//
//        }
        else if (id == R.id.promoterprofile) {
            startActivity(new Intent(SwitchPromoter.this, PromoterProfile.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SwitchPromoter.this,MainActivity.class);
        startActivity(intent);
    }

    private void getProfilePic() {
        Call<GetvendorProfilePicResultResponse> call = Api_Client
                .getInstance()
                .getVendorImage(vendor_id);
        call.enqueue(new Callback<GetvendorProfilePicResultResponse>() {
            @Override
            public void onResponse(Call<GetvendorProfilePicResultResponse> call, Response<GetvendorProfilePicResultResponse> response) {

                if (response.body().getCode().equals(200)){
                    GetvendorProfilePicResultResponse getvendorProfilePicResultResponse = response.body();
                    List<GetVendorProfilePicResponse> profileImage = Collections.singletonList(getvendorProfilePicResultResponse.getData());
                    Picasso.get()
                            .load(profileImage.get(0).getImagePath()+profileImage.get(0).getUserimage())
                            .error(R.drawable.icon_image)
                            .placeholder(R.drawable.icon_image)
                            .into(userImage);
//                    Log.e("tag","image "+response.body().getData());
//                    Log.e("tag","image "+profileImage.get(0).getImagePath());
//                    Log.e("tag","image "+profileImage.get(0).getUserimage());

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

    private void getWalletamount() {

        Call<WalletResultResponse> call = Api_Client
                .getInstance()
                .getWallet(vendor_id);
        call.enqueue(new Callback<WalletResultResponse>() {
            @Override
            public void onResponse(Call<WalletResultResponse> call, Response<WalletResultResponse> response) {

                if (response.body().getCode().equals(200)){

                    try {
                        WalletResultResponse walletResultResponse = response.body();
                        List<WalletResponse> walletList = Collections.singletonList(walletResultResponse.getData());

                        walletamount.setText("₹ " +walletList.get(0).getWalletAmount().toString());
//                    Log.e("tag","amount "+walletList.get(0).getWalletAmount());
//                    Log.e("tag","wallet "+response.body().getMessage());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }


                }else if (response.body().getCode().equals(400)){

                    walletamount.setText("0");
                    Log.e("tag","fail "+response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<WalletResultResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });
    }

    private void loadingProfile(){

        Call<ProfileResultResponse> call = Api_Client
                .getInstance()
                .getProfileDetails(vendor_id);
        call.enqueue(new Callback<ProfileResultResponse>() {
            @Override
            public void onResponse(Call<ProfileResultResponse> call, Response<ProfileResultResponse> response) {

                if(response.body().getCode().equals(200)) {
//                    progressDialog.dismiss();
                    ProfileResultResponse profileResultResponse = response.body();
                    List<ProfileResponse> profiledetails = profileResultResponse.getData();

                    username.setText(profiledetails.get(0).getName()+ " "+profiledetails.get(0).getLastname());
                    usermobile.setText(profiledetails.get(0).getMobile());


                }
                else
                {
//                    progressDialog.dismiss();
//                    Toast.makeText(SwitchPromoter.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ProfileResultResponse> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(SwitchPromoter.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("tag","fail "+t.getLocalizedMessage());

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyLead(keywords);
    }
}