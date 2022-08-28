package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.civildeal.civildeal.Adapter.NotificationAdapter;
import com.civildeal.civildeal.Model.NotificationDetailsModel;
import com.civildeal.civildeal.R;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView recyclerView;
    List<NotificationDetailsModel> notificationDetails;
    NotificationDetailsModel notificationDetailsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_notification);
        GridLayoutManager mgridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(mgridLayoutManager);

        notificationDetails = new ArrayList<>();
        notificationDetailsModel = new NotificationDetailsModel("Notificaion","You have 1 notification");
        notificationDetails.add(notificationDetailsModel);
        notificationDetailsModel = new NotificationDetailsModel("Notificaion","You have 1 notification");
        notificationDetails.add(notificationDetailsModel);


        NotificationAdapter notificationAdapter = new NotificationAdapter(Notification.this,notificationDetails);
        recyclerView.setAdapter(notificationAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(Notification.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}