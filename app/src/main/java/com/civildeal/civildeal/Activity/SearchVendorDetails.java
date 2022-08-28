package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.civildeal.civildeal.R;

public class SearchVendorDetails extends AppCompatActivity {

    Toolbar toolbar;
    ImageView vendorProfile;
    TextView vendorName,desc,address,quality,experience,projectDetails;
    Button sendEnquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vendor_details);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vendorProfile = findViewById(R.id.vendorimage);
        vendorName = findViewById(R.id.name);
        desc = findViewById(R.id.Desc);
        address = findViewById(R.id.address);
        quality = findViewById(R.id.quality);
        experience = findViewById(R.id.experience);
        projectDetails = findViewById(R.id.details);
        sendEnquiry = findViewById(R.id.sendEnquiry);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(SearchVendorDetails.this, SearchVendor.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}