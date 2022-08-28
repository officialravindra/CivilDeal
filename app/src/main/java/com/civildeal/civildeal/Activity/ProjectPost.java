package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.MaintenanceAdapter;
import com.civildeal.civildeal.Adapter.ProductAdapter;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResponse;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResultResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.civildeal.civildeal.BuildConfig;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.CityListResponse;
import com.civildeal.civildeal.api.ModelResponse.CityListResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProjectPostResponse;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectPost extends AppCompatActivity {

    Toolbar toolbar;
    EditText name,phone,title,email,size,minBudget,maxBudget,otherCity,description;
    Spinner cityNamesSpinner,select_size,typeSpinner,subcategoryspinner;
    ImageView chooseImage,choosenImage;
    Button submit;
    String[] sizeSelect;
    String City_Selected,cityId,sizename,Size_Selected,vendor_id,Type_Selected,Sub_Category_Selected;
    int citi_id[];
    ArrayList<String> names = new ArrayList<String>();
    SharedPrefManager sharedPrefManager;
    ArrayList<String> citylist = new ArrayList<>();
    ArrayAdapter typeadapter;
    String subCategory,typename;
    TextView selecttype;
    ArrayList<String> listType;
    String[] type;
    String[] sub_category;
    CardView cardView;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    private String  user_dp = "";
    final int REQUEST_EXTERNAL_STORAGE = 100;
//    final List<String> img = new ArrayList<>();
    ArrayList<String> img;
    JsonArray questionArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_post);

        img = new ArrayList<>();
        cityNamesSpinner = findViewById(R.id.citySpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        subcategoryspinner = findViewById(R.id.servicetypeSpinner);
        selecttype = findViewById(R.id.servicetype);
        cardView = findViewById(R.id.serviceTypecard);
        select_size = findViewById(R.id.size_spinner);
        chooseImage = findViewById(R.id.choose_image);
        choosenImage = findViewById(R.id.choosen_image);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        title = findViewById(R.id.title);
        email = findViewById(R.id.enteremail);
        size = findViewById(R.id.size);
        minBudget = findViewById(R.id.minbudget);
        maxBudget = findViewById(R.id.maxbudget);
        maxBudget = findViewById(R.id.maxbudget);
        otherCity = findViewById(R.id.otherCity);
        description = findViewById(R.id.desc);
        submit = findViewById(R.id.submit);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        vendor_id = String.valueOf(sharedPrefManager.getUserId());

        sizeSelect = getResources().getStringArray(R.array.selectSize);

        ArrayAdapter typeAdapter = new ArrayAdapter(ProjectPost.this, android.R.layout.simple_spinner_item,sizeSelect);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_size.setAdapter(typeAdapter);

        select_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                select_size.setSelection(i);
                Size_Selected = sizeSelect[i];
                // getSelectedTypeCategories();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Size_Selected = sizeSelect[0];
                select_size.setSelection(0);
            }
        });

        cityNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (citi_id != null) {
                    Log.e("tag", "citi_id " + citi_id[position]);
                    cityId = String.valueOf(citi_id[position]);
                    Log.e("tag","cityId "+cityId);
//                    Toast.makeText(getContext(), " not null", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                }

                if (cityId.equals("1000")){
                    otherCity.setVisibility(View.VISIBLE);
                }else {
                    otherCity.setVisibility(View.GONE);
                }
                cityNamesSpinner.setSelection(position);
                City_Selected = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                cityId = String.valueOf(citi_id[0]);
                City_Selected = String.valueOf(0);
                cityNamesSpinner.setSelection(0);

            }
        });

        type = getResources().getStringArray(R.array.selectLeadType);
        ArrayAdapter categoryAdapter = new ArrayAdapter(ProjectPost.this, android.R.layout.simple_spinner_item,type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(categoryAdapter);


        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                int pos = listType.indexOf(typeadapter.getItem(i));
                subCategory = adapterView.getItemAtPosition(i).toString();
                if (subCategory.equals("service")) {
                    getServiceList();
                    selecttype.setText("Service");
                    cardView.setVisibility(View.VISIBLE);
                    selecttype.setVisibility(View.VISIBLE);
                    Log.e("tag", "service" + subCategory);
                }
                else if (subCategory.equals("product")) {

                    getProductList();
                    selecttype.setText("Product");
                    cardView.setVisibility(View.VISIBLE);
                    selecttype.setVisibility(View.VISIBLE);
                    Log.e("tag", "product" + subCategory);
                }
                else if (subCategory.equals("maintenance")) {
                    getMaintenanceList();
                    selecttype.setText("Maintenance");
                    cardView.setVisibility(View.VISIBLE);
                    selecttype.setVisibility(View.VISIBLE);
                    Log.e("tag", "maintenance " + subCategory);
                } else {
//                Log.e("tag", "vendor_type " + Type_Selected);
                    cardView.setVisibility(View.GONE);
                    selecttype.setVisibility(View.GONE);
                }
                Type_Selected = type[i];
                typeSpinner.setSelection(i);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                subCategory = adapterView.getItemAtPosition(0).toString();
                if (subCategory.equals("service")) {
                    getServiceList();
                    cardView.setVisibility(View.VISIBLE);
                    selecttype.setVisibility(View.VISIBLE);
                    Log.e("tag", "service" + subCategory);
                }
                else if (subCategory.equals("product")) {

                    getProductList();
                    cardView.setVisibility(View.VISIBLE);
                    selecttype.setVisibility(View.VISIBLE);
                    Log.e("tag", "product" + subCategory);
                }
                else if (subCategory.equals("maintenance")) {
                    getMaintenanceList();
                    cardView.setVisibility(View.VISIBLE);
                    selecttype.setVisibility(View.VISIBLE);
                    Log.e("tag", "maintenance " + subCategory);
                } else {
                    cardView.setVisibility(View.GONE);
                    selecttype.setVisibility(View.GONE);
//                Log.e("tag", "vendor_type " + Type_Selected);
                }
                Type_Selected = type[0];
                typeSpinner.setSelection(0);
            }
        });

        subcategoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typename = parent.getItemAtPosition(position).toString();
                subcategoryspinner.setSelection(position);
                Sub_Category_Selected = sub_category[position];
//                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
//                ((TextView)view).setTextColor(Color.BLACK);

//                Toast.makeText(AddPromoterLead.this, "type "+typename, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typename = parent.getItemAtPosition(0).toString();

                Sub_Category_Selected = sub_category[0];
                subcategoryspinner.setSelection(0);

            }
        });

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectImage();
                if (ActivityCompat.checkSelfPermission(ProjectPost.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProjectPost.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
//                    return;
                } else {
                    launchGalleryIntent();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();

            }
        });

        getCities();
    }

    private void checkValidation() {

        if (name.getText().toString().isEmpty()) {
            name.requestFocus();
            name.setError("Please enter your name");
            return;

        } else if (title.getText().toString().trim().isEmpty()) {

            title.requestFocus();
            title.setError("Please enter your title");
            return;

        } else if (email.getText().toString().isEmpty()) {

            email.requestFocus();
            email.setError("Please enter your email");
            return;

        }else if (size.getText().toString().isEmpty()) {

            size.requestFocus();
            size.setError("Please enter your size");
            return;

        }else if (minBudget.getText().toString().isEmpty()) {

            minBudget.requestFocus();
            minBudget.setError("Please enter your min budget");
            return;

        }else if (maxBudget.getText().toString().isEmpty()) {

            maxBudget.requestFocus();
            maxBudget.setError("Please enter your max budget");
            return;

        }else if (description.getText().toString().isEmpty()) {

            description.requestFocus();
            description.setError("Please enter your description");
            return;

        }else {
            submitProjectPost();
        }
    }

    private void submitProjectPost() {

        ProgressDialog progressDialog = new ProgressDialog(ProjectPost.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
//        JsonArray questionArray = (JsonArray) new Gson().toJsonTree(img);
//        Log.d("tag","JsonArray"+questionArray.toString());

        JsonObject jsonObj = new JsonObject();
        JsonArray roleArray = new JsonArray();

        jsonObj.addProperty("vendor_id",vendor_id);
        jsonObj.addProperty("title",title.getText().toString());
        jsonObj.addProperty("name",name.getText().toString());
        jsonObj.addProperty("email",email.getText().toString());
        jsonObj.addProperty("location",cityId);
        jsonObj.addProperty("other_location",otherCity.getText().toString());
        jsonObj.addProperty("min_budget",minBudget.getText().toString());
        jsonObj.addProperty("max_budget",maxBudget.getText().toString());
        jsonObj.addProperty("description",description.getText().toString());
        jsonObj.addProperty("select_size",Size_Selected);
        jsonObj.addProperty("size",size.getText().toString());
//        jsonObj.addProperty("image","data:image/jpeg;base64,/9j/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wAARCAEYATADASIAAhEBAxEB/8QAHgABAAEFAAMBAAAAAAAAAAAAAAgEBQYHCQIDCgH/xABbEAAABQIEAgMJBw4KCAcBAAAAAQIDBAUGBwgREgkhEzHTFBUWGSJXkpPUGEFRVVZYYRcjMjQ4cXJ1doGRlaGzJDM2N0JDdIKxsidFUmJjorTBKEZTc4WU0dL/xAAUAQEAAAAAAAAAAAAAAAAAAAAA/8QAFBEBAAAAAAAAAAAAAAAAAAAAAP/aAAwDAQACEQMRAD8A6pgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAALdcFw0u1KLMq9aqEWlUqE0b0mbNdS0yygutS1qMiSX0mPZRq1AuKlRKnS5seo06W0l6PLiupcaebUWqVJUkzJRGXvkArQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFHWKzAt6mSqlVJsenU+K2p6RLlupaaZQRampa1GRJIvhMwFZ1CP+aXOxh1lTo5ncM46pczze+HbdPWlUt74FL95pvX+mvr0PaSjLQQ+zW8V2XV6o5YWXyG9VqrKc7k8JiiqdW44Z6EmCxoZrUZ8icUX4KD5KGEYS8P6l2jS5WNGbu6+90NbhS3KLPnKXIlOHzIpbxGa1rVpyYaM1n1GotDSAwovdFcVe8uZlbWGsOT/vt0mFp/zS5BF98yM/6tJj9hV7MVwqryRBnteEuGsuSfRtGpblKmanqZtr03RXzLXVPLUy1NLhERihzS8Tir3pRlYf4LwDw3w6jN9yIdhtpjzZLJctqSRyjNn/sI8o/fVzNI92WLibTrfoJYdY6UwsR8PJTRRFSpjSZEyM11aOEvlJbL4FeWXWSj0JIDp/lhzlYdZqqF09r1LuOvMNkudb1QNKJsb4VEnXRxvX+sRqXMtdp8hvUcgsWOH8zPgw8a8od2rrFNQs5bNJp05RS4iy5mUR7Ul7k66Gw7o4XMtVGe0bGyncWE0VBqxcfoyqFWo7nchXN3MbKekI9DTNY0I2lEfI1pLb/tJRoagHTcBS0yqQ61T40+ny2J0GS2l1iTGcS426hRapUlSTMlEZdRkegqgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAU1RqUSkQZE2dJZhw47anXpEhwm22kJLVSlKPQkkRczM+RDmpmz4sSGpztj4CMHXq2+53Kdz9zm82lwz2kmGyZGby9eRLUW3X7FK9SMBMHM5nGw6yrUDum6al3VXH2zVBt6AaVzZXwHt10bb163F6FyPTcfIcxqlceYriqXm5T6ax4NYaxJBdIyS1t0mHoepG65pulPkWhkkiPQz1JLZGZjMMJOH5siTca83V2OUalqWUyRS6nOV3XLUfMilvampJnoREy3q4fItUGW0Ydmb4nEuq0H6nWAtN+pzYENs4iJ0RpMaZIb6tGUp5RkHzPl9cPrNSdTSA3JU74y+8LqkP0i04zWJ2N5tG1KnuqTuiLMuZOrLUoyP+C3q4otCWfUoc68d8xl+5kLsVXr5rjtSdSaiiwm/rcSEgz+wZaI9EF1anzUrQtxmfMa2ddW+4pxxRrWozUpSj1MzPrMzHiAAAANo4BZlb/y13WVcsetuQTcNPddPeI3Ic1Bf0XmjPRXvkSi0UnU9qiHQyFc2Xzij0pqn15lvC7HImSbYltmndMWRciQo9pSkf8ADXtdSWpJPQjM+UQ82XnI7qHWlqbcQolJWg9DSZdRkfvGAn3RL2zE8K+8maPXYh3LhtJfPoo6nFuUuWRnqZxntN0Z7TUzQZFqfNSFloY6e5aM32HWaa3u7bSqhNVdhslTqBONLc2J8JqRr5aNepxBmn6SPkXMHLTxN3o1vHhxmApZYi2FMbKKuoy2UyJkdv3umSr7YQXI9T0cT1kpRkRDK8WOH/IhR4ONeUa7Xq7SEqOZGptLnKObEMuaiiu6kpzTmRsuaOF9ie8z0AdgAHNHKVxZI8+YzZOPDJW9XWV9ylcxMGywtwj2mmW1p9YXr1rSWzXXVLemo6TQJ8aqQmJkOQ1KiPtpdZfYWS0OIMtUqSouRkZcyMuRgKgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABrfHPMLYuXG2YVfv6rro1KmSygsvIiOyDU8aFLJO1tKjLyUKPUy05DZA1njzl1sfMna8G378pr1UpcKYU9lpiW7HNLxIUgj3NmRn5K1cj5cwENcwWbTKPmWp0al3fitdjNDZLVVIpEabFivr11JbqSjmbhly03GZF1kRHzFgy/Y15DstVXk1i0a7MerjxbU1Wr0qdKksI00NDSjZImyPnqaSIz10MzLQhvQ+FJluI9DtGeR/j2X2gFwpMtx9Vozz/APnZfaAI/Y8YzZLcyVyFWL7xVvWpG19q09pua1DhlpoZNMlG2pM9Oauaj98z0Iaw8EuHX8trv9XN9mE0fFR5b/khUP15M7QPFQ5b/khUP15M7QBC7wS4dfy2u/1c32YPBLh1/La7/VzfZhNA+FJluI9DtGeR/j2X2geKly2/JKd+vZfaAIX+CXDr+W13+rm+zB4JcOv5bXf6ub7MJo+Kiy3n/wCUJ/68mdoPzxUuW35Izv17L7QBC/wS4dfy2u/1c32YPBLh1/La7/VzfZhNHxUWW/TXwQn6fD38mdoPzxUuW35JTv17L7QBC/wS4dfy2u/1c32YbRwGxpyXZbLjVV7ExVvSnE99tU55qa7DmFpoXSsnG2qMveUWii94y1Mb/PhR5bk9dozy+/XZfaAXCky3GehWjO/XsvtAGiMf8ZshmZKtR61dldmx660W1dUo9LnRZEhOmhJeMmTJzTloaiNRaaEenIZLl+zcZSMtdLkUm0sVrsfobpeRSavGmyo0dWuprZSccjbM9T1JJkR9ZlrzG0j4UmW4j52jPL79dl9oBcKTLcZ6FaM8z/HsvtAG+MC8w9iZj7bnV6wKuus0uFLOC+8uI7HNLxISs07XEpM/JWk9SLTmNkjWWAuXSxstdsz6BYVMepdMmzDnPtPy3ZJqeNCUGe5wzMvJQnkXLkNmgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA4gcYKdJjZuEJZkOtJ8HYPJCzIvs3vgEIkVielRGmdISZe+Tyi/7ia/GI+66b/JyD/neHp4RFr0e8MztapldpUKs0521Zm+JPjoeaV9fjFzSojLqMy/OA0Rghm8xUwFueDVLcu2puRGHEqeo06U4/BlI15ocaUZp5lqW5Oii11IyMfQhg/ibTcZsLbYvakpU3ArsBqahpZ6qaNReU2Z++aVEpJ/SkfNViHSY1Av65KbCTtiQqnJjslrrohDykp/YRDudwsqk7KyQWP3Q4ZpjPVFpKle8hM14/0czAcf85V4ybhzWYsTGZj3ReEk1hG1wyLa04bRe/8AAghpsqrOVyOZIMj5fxqv/wBF0xArh3Pfdx1gz3HUKlJlmr4ekdUvX9o9F0W4/atRjxJGvSOwok0tS08l9ht5P7HCAfRPkzudV35UcKKm44brqrdhMOLM9TUtpBNK1+nVsx8+mKdUmNYnXaSZb6UlWJmhE4rT+PX9I7ccKy5Dr+SuzmFK3uUuVOgK+jSUtxJei6kcPcV/5z7u/HEz9+sB1wyn4ouYh8K29o7sg3ajbNv1uivL3eWSUR1us/oadQRfgjkJa1UmKuakEct8y7rY5G6r/wBRP0iYvDwxMVCwhzL2A879bqNjTqzGbV7y2I7jbun0mh5B/eQIY2r/ACopH9rZ/wA6QHTzjgS34szB/oXnGdyKtr0azTrzi/AIe8Pmoyn85eFSHJLziDqp+SpwzL+JdEveOP8AbeDv4FW/xiCFuRCrRbfzZ4d1Wc4TMKnzHpj7h/0W24zy1H+hJgM64nGKcm+M4d4sxZjpQaCliiMEhwyIjZbI3S5f8Zbv6BLjgoYeP+DWIeIE03nTlyWKJDcdUZ6JaT0z2mvwm4z6I5WXpc8q97xrlxTjNUyrTn57566+W64pxX7VD6BeH5hj9SnKPh5THWehnToBVeXr9kbkoze0V9JIWhP90BIkAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHDrjEfddN/k5B/wA7wj3lezL3BlUxCm3hbVOp1SqcilvUwm6olamkJcW2vfohSTMyNsuWunMxITjEfddN/k5B/wA7w1fkFy0W7msxnqdmXLUKlTIbVCk1BmTS1oS4l5DrKE670qI0/XD1LkZ6FzIBp6xbKruN+I0ejU+RT01usSVL6aqT2YTJrWrVRmtxSS1M1fYlqo+oiM+Q75YdYXoym5LnrZKa3MkWxbk+ZKmNpNKHJBtuvuqTrz271GRa89CLUfPhdVCXa1z1ejuOE85T5j0RThFoSjbcUgz0+naOr+EOO1XvPhG4hzq3PdnVGhwJ1s92PrNTrjaiaQzuM+ZmSJKUa/AggHIz+l+b/sJD57LPKycbafTia6Ik2pb5bdNOaKYw0Z/pbMaEpENNQrEOIpSUpefQ0alHoREaiLUz/OJocXIqPIzK0WbQ6lCqkF+2IiDdgyEPJStt19vaZpMyI9pJ5AJecFe5Tn5fLwoi1EpVOuQ3kl/soejtaftbUOSGK/8AOfd344mfv1jo5wQLlNusYr0BSi0eYp09tPv+Qt5tR/8AOgc48V/5z7u/HEz9+sBXYP4iu4Z3VLqKVLKPNpFRpElCOe9qVEdYMj+8biVf3RjlqfynpH9rZ/eJFxv6yJNiVSnxZG40T6VBqrC1Fpubkxm3i/QazT/dFutP+U9I/tjP7xIDpxxx/tvB38Crf4xBy/o9Zl0GacuE6bEg2nWd5dZJcbU2v9KVqL846gccf7bwd/Aq3+MQcxLdt+Xc9TODCTvkdA/IJPwpaaW6ovv7UGAveEVns4g4q2da8h9EVitViJTnHlq2khLryEGev3lGPp0hQ2afDZixmksx2EJabbQWhISktCIvvERD5aqPVJNCq0Oow3DZmQ3kSGXC60rQolJP9JEPp3w1vONiLh5bN1QzScWt02NUW9p6kROtJXp+bdp+YBkoAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAOHXGI+66b/JyD/neFZwaFpbzX1dSjJKStSYZqPkRF3RFFHxiPuum/ycg/53hCan1WbSVPKhS34an2lMOmw6ps3Gz01QrQy1SehakfLkAv2KU5iqYl3ZMiuJejSKvMdacSepKSp9ZkZffIyEwsPK8qi8IzEuOSth1S+WIRH8PKG6ZfoaMQYaZW+6httJrcWZJSlJamZn1ERe+J5Y+4cVrAXhnYZWvXoq6ZXbnu9yvy4DyTS6yjuZwm0rI+ZK2dCZkfMjVofMgECgM9RlmEtKKuYp2dTTSSymVmFH29eu99CdP2joRxtqIxT7twqmMR22UvwaiyfRoJOux1lXvf+4YDX/BouQ6VmmqtMUoibq1tyWiSfvrbeYdL9iViGuK386F3fjiZ+/WN7cM+5TtjOrhw4aiS1MfkwF6n19LFdSkvS2jROKnPE67T+GrzP36wElc8GHiqfg3ljvdtnRqr2FFpby09RuRkpWkz+k0PkX3kfQIq2n/Kekf2xn94kdTs0eHPhpwl8Ka00zvlWtTKPUiUn7ImltFHcL7315Cj/AHLG1C0uek/2xn94kB0444/23g7+BVv8YghbkQpMav5ssPKVMR0kSoS3obydOtDkZ5Cv2KMTS44323g7+BVv8Ygh1w9S/8AGdhV+NT/AHLgDRNyUOTa9xVOjzE7ZdOlOxHk/AttZoUX6UmO6/CpxG8PsnluRHXSdmW3Kk0V49eZJQvpWi/M08gv7o5P8QexPqfZw8TICGiajy6l31Z05EaZTaXz0/vOKL8wl1wR8R+gr+I9hvOlpJjR63FbM+o21Gy8ZF9JOM+iA6ygAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMUxExVs/CSkx6nedy0y2KfIfKMzJqslLDbjppNRII1dZ6JUenwEYqMQ8Q7ewps2pXXddSRR7fpqUrlzXG1rS0SlpQkzSgjUeqlJLkR9YhFmox7ykZtLJplsXRjM/SoVPqKak27SIkhLinCbW3tM3Iyy26OGfItdSLmA3FdeN+UW+6r30uW48KbhqXRpZ7sqvcUl7YnXanetJnoWp6FroWpiz+HmSUv67Bf8A+pTv/wCBBX3O+Q75wlz+pP2EPc75DvnCXP6k/YQHQO3scso9ozES6FcuFdElIPVL9O7ijrSf0KQkjIV145j8rOIrMVm671w1uZqKpS47dYfiS0sqMtFGgnCPaZlyMyHO73O+Q75wlz+pP2EPc75DvnCXP6k/YQE8YGJeS+lTo02FPwdhzYziXmJEePT0ONOJMjStKiRqlRGRGRlzIyF9vLMTlVxFOId13jhlc/cm7ufvy9El9Du03bOkI9uu1OunXoXwDnj7nfId84S5/Un7CHud8h3zhLn9SfsICetHxVya27VYlTpVUwgplSiOE9HmQ2YDTzKy6lIWlBGky+Ej1FPIxEyVS5Dr78vBp591anHHXI1PUpajPVSjM0amZmZmZ/SIJe53yHfOEuf1J+wh7nfId84S5/Un7CA6Mu5oMsj9qeC7t/YduWz3OUTvMuXGOH0Jcib6H7DYWheTppyGIt4gZKGnEuIkYMocQolJUmLTiMjI9SMj2deogp7nfId84S5/Un7CHud8h3zhLn9SfsIDodeWYjKriKcQ7rvHDK5zibu5zrL0SX0O7Tds6Qj267U66dehfALTQsWsnFr1eLVaNV8IqTVIi+kjzYLcBl5lWhluQtKSUk9DMtSP3zEB/c75DvnCXP6k/YQ9zvkO+cJc/qT9hAdALpxryhXxVlVW47gwor9TUhLSptUKFJeNCfsUmtaTPQtT0LXlqPK1Mb8otiVXvpbVx4VW9UujUz3ZSu4or2xWm5O9tJHoehalrpyIc/fc75DvnCXP6k/YQ9zvkO+cJc/qT9hAddcO8VbPxbpMiqWZctMuenR3zjOyqVJS+2h0kko0Gaeo9FJPT4DIZWIAZV8fMpGUuyana9r4zP1WFUKiqpOO1eJIU4lw20N7SNuMgtujZH1a6mfMTdw9xCt7FazaZddqVJFYt+pIUuJNbbWhLqUrUhRklZEotFJUXMi6gGRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADEcWcU7fwUw+rF6XTIdiUGlIQuU8yyp5aSW4ltOiE8z8paeoXm7LkiWba9Yr88nDg0qG9OkEyncvo2kKWraWpanok9CECMTeJ3ljxjsSqWfdtIu+p29VEITLiJp/RG4SVpcT5bb5KLRSEnyP3gF0xg4h+VfHDDit2Pc1yV5VDq6ENyih0mS07ohxLhbVbD08pCfe6tRFjvTw8z/ANfX56EnshXeH3Dw8215enL9sDw+4eHm2vL05ftgCh70cPP4+v30JPZB3o4efx9fvoSeyFd4fcPDzbXl6cv2wPD7h4eba8vTl+2AKHvRw8/j6/fQk9kHejh5/H1++hJ7IV3h9w8PNteXpy/bA8PuHh5try9OX7YAoe9HDz+Pr99CT2Qd6OHn8fX76EnshXeH3Dw8215enL9sDw+4eHm2vL05ftgCh70cPP4+v30JPZB3o4efx9fvoSeyFd4fcPDzbXl6cv2wPD7h4eba8vTl+2AKHvRw8/j6/fQk9kHejh5/H1++hJ7IV3h9w8PNteXpy/bA8PuHh5try9OX7YAoe9HDz+Pr99CT2Qd6OHn8fX76EnshXeH3Dw8215enL9sDw+4eHm2vL05ftgCh70cPP4+v30JPZB3o4efx9fvoSeyFd4fcPDzbXl6cv2wPD7h4eba8vTl+2AKHvTw8y/19fnoSeyEqMH+IflXwRw5olj2zcleKh0hC2opTKTJdd0W4pw9ytha+Us/e6hGXw+4eHm2vL05ftgFf3DwI9fqbXl6cv2wB1pwxxIoeLth0a8Lbfck0Orsd0RHXmlNKUjcadTSrmXNJ9Yygc+MO+KLlowosik2nbFKu+n0GksdBEiqp3Sm2gjNWm9b5qPmZ8zMTytO5Il5WtR6/AJwoNVhszo5PJ2rJt1CVp3Fqeh6KLUgF1AAAAAAAAAAAAAAAAAAAAAAAAAAFFW6NCuOjT6TUo6ZdOnx3IsmOvXa60tJpWk9OehpMy/ONAeLuy56F/orpHrZHaCRgAI5+Luy5+auj+tkdoHi7sufmro/rZHaCRgAI5+Luy5+auj+tkdoHi7sufmro/rZHaCRgAI5+Luy5+auj+tkdoHi7sufmro/rZHaCRgAI5+Luy5+auj+tkdoHi7sufmro/rZHaCRgAI5+Luy5+auj+tkdoHi7sufmro/rZHaCRgAI5+Luy5+auj+tkdoHi7sufmro/rZHaCRgAI5+Luy5+auj+tkdoHi7sufmro/rZHaCRgAI5+Luy5+auj+tkdoHi7sufmro/rZHaCRgAI5+Luy5+auj+tkdoHi7sufmro/rZHaCRgAI5+Luy56H/orpHrZHaDf9EosK3KNApNNjpiU6BHbixo6NdrTSEklCS156EkiL8wrQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGKYT319VDCyzby7h72eEVFhVfuLpel7n7oYQ70e/andt36btpa6a6F1DKxqrKd9yxg3+RlG/6FkbVAAAAAAAAAAAAAeidMbp0KRKe3dEw2p1exJqVtSWp6EXMz5dRAPeA0JUcwk+tJw1rdvW/XF0uu1STHVSkJguyaiyVPfebU2on1NoTvShRmpxsy2KJWnMjySnZkrWqtvVCsxotUVEhRabLdSphCV6TXVNNoIjX9khaFEsj0ItORqAbXAR4hZl7old5v8ARxV193XjPtvyHYBdI2x3ZyRrN5Op7lLcatEHtc266o1uCM5+HL826IzEmRLVQYkua53I7FeXIbirJD+xlDxuoMjPkTyG95c0bi5gN7gNaU3G5FUuo7bbs64mqy3A7vfivHBQphKul6JKi7p5m50R6GnchJrSS1IVqRYlZeaFdXw9tms1Sy6+utVSkqrL9PpbcZzo4aEoNcsv4SZE0ZuESUGrplbVaN8gG+AGhcSsxaJGGt81SyY1TcapFMdeautDDB09p8o6X0p0cXvUZJWjU+iNBGe01akZDaFo4k0e9bal3HAN5FvMqcNmqPklLMtlBeVIa0UZm1qSiJSiTu2mpJGk0qMMqAapj5gosuk0mYzZl0rfrbiSo0A2IqZFSaNpTpvNkqQSW0EhOp9MptRbklt1URHanM19sGlLsSh3HUIqWoa5EiNDa2xXJTzkdhhxKnUr6RUhpTOiUmSVmWpknVRBusBgtLxjoEvDyrXjUUTKBTKOuU3VGKgySn4S461IeStLJuEoyNJmRtmsjIyMjPUe/EqvXBSrWRULYKGRkZvSZkyDIn9BHJtSzU3EYNLshZmSEk2lST8oz1PbtMMzARTcza15EGiS5B21TX3qdTpiaQ+TrkmvOSJC2VtQFJcIkKRtTqk0umla9qyTpuOplZr67CqVbS1FotYlx2Ky54LQmnyqlJ7iUaUOzFblaocLafktIMukTtNwuYCUYCLreaOquNuw0XJZciImrMwFX81Ge7xME5DXI6NaO6ObpKShv+PJJ9Kk+SvIO40zMXcFcVZkx+TTrTodUpsaZIqcy2qhUIcpx2U4ylLUpt1pqKlaW0rSp/XlIb5H74SRAaZn4s3JZV/9zXcqhR7deiT6gtiCh1UumRWFpSw++5vNLnT6kkm0toMlntSbm1RjHZOOl9rtytvu0ViiVKNdbVISlFDm1pVPguU1mclcmPFcJxbpdMTatiiQlSiLmRblBIgBYbDrybns6kVVFVhVwpcdLvfCnR1x2HzPrUhpa1qbL/dUtRkZGRnqQvwAAAAAAAAAAAAAA1VlO+5Ywb/Iyjf9CyNqgAAAAAAAAAAAAPXIZ7ojutdItrek09I2eik6lpqR+8YAA1rbGAVGtquQ60qr1eq1ePVXawuXMVHSch5cM4ei0NMto0Jo+tKUqNREalK562T3KtttxWokavXFDgnHix5cViQwSJpRpC5DCnTNk1EpC3FF5BoJRaEolaEAALw7gDTT39z3HX4Wy4XLmh9A5GPuGU70/Tpa3MHqhzul7UnN5lv8k06Fp4vZfKW9QLit/wAJLhTbNYYlMFRUvsdzwikLNxxTBmz0mu41bScWtKSUZJIi5AAC/wBdwuh3HftEuedVqm4dHcOREpZGyUVDxtLa6TXoumI9riiNJOEg+Rmk9BiEbLHSKfQaZS4N13PATT4L1IalR34yXjpzmz+BqM2DI207C2r0J1Op6OamYAA90vLRb7lLrtGg1qu0a2azHUxIoEF9koiVGwljpUb2lOErYhPk79m4txoNXMZVRsLadQE1KLBnTmaJUJsibIoh9CqIfTtml5pJG2a0NqcUp40pUX1xSue0zSYAGNRcvUWFSqTEZvS60yaKtPeaoKfirfprRNKa6FvdHNDiDQsyM3kuKPakzVqlJl5U7LZatKp0iFFk1RtmQqkuOGqQhSlLp81U1tZqNBmanHlqNwz6yUe3YfMAAZxaNmwrMZqrcJ191NRqUiqOnIUkzS68vesk6EWiSPqI9T+EzFPe9keGbEA2a7V7cnQXzfjz6M62l1JmhSFJUl1Dja0mSj5LQrQyIy0MiMAAWmj4M0Oit2ow0/Pfg230r0WFIeJbT0pwzNUt7ydy3iNbpkeu0jdUe3XaabzTbGiU66K5cPdcuVVqo22wT8hSFdxsILyWWC2kSUbjUs9dxqUryjMiSSQALOnB+lIw8es9FRqqIUtw3ajOS+kplRUte983ndn9d5SVmgkmSVGSDQRJ0XdhHDvJ+KxJrdXiW80hlt624SmG4ElLSyWhKy6I3ElqREaW3EEoiIjIy5AACx0vL1To1ar8qr3JV7tplffVIqdDuGJTZUOWf9Whf8EJ00NaJ6NHSbU7S5devnScuVs2f39XZDz+Hr9YntTpD1sxIUcyJuOhhLCUqjrR0Xkqc2mkzJx1xRGRqAAGd2dadPsW2afQaUhxMGE30bZvOG44s9TNS1qPmpSlGajP3zMxeQAAAAAAAAAAAAAAAf/Z");

        JsonArray jsonArray2 = new Gson().toJsonTree(img).getAsJsonArray();
        jsonObj.add("image", jsonArray2);
        Gson listG = new Gson();
        String list2 = listG.toJson(img);
       // jsonObj.addProperty("image", String.valueOf(jsonArray2));


        Gson gson = new Gson();
        //jsonObj.addProperty("image", gson.toJson(questionArray));

      //  jsonObj.add("image",questionArray);

        //jsonObj.add("image",questionArray);
//        questionArray.add(jsonObj);

        Log.d("tag","json "+jsonObj);
        //Log.d("tag","jsonArray "+questionArray);


        Call<ProjectPostResponse> call = Api_Client
                .getInstance()
                .saveProjectPost1(jsonObj);

        call.enqueue(new Callback<ProjectPostResponse>() {
            @Override
            public void onResponse(Call<ProjectPostResponse> call, Response<ProjectPostResponse> response) {
                progressDialog.dismiss();
                if (response.body().getCode()==200){

                    try {
                        Log.d("tag"," sucess"+response.body().getMessage());
                        name.getText().clear();
                        title.getText().clear();
                        email.getText().clear();
                        size.getText().clear();
                        minBudget.getText().clear();
                        maxBudget.getText().clear();
                        otherCity.getText().clear();
                        description.getText().clear();
                        choosenImage.setImageResource(android.R.color.transparent);

                        Toast.makeText(ProjectPost.this, "Project Post Successfully", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        e.printStackTrace();
                        Log.d("tag"," exp "+e.getMessage());
                    }


                }else {
                    Log.d("tag","fail"+response.body().getMessage());

                    Toast.makeText(ProjectPost.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ProjectPostResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("tag","failuer"+t.getLocalizedMessage());
//                Toast.makeText(ProjectPost.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getServiceList() {
        ProgressDialog progressDialog = new ProgressDialog(ProjectPost.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<ServiceResultResponse> call = Api_Client
                .getInstance()
                .getServicesList();
        call.enqueue(new Callback<ServiceResultResponse>() {
            @Override
            public void onResponse(Call<ServiceResultResponse> call, Response<ServiceResultResponse> response) {
                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){

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

                            typeadapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, listType);
                            typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            subcategoryspinner.setAdapter(typeadapter);
                            typeadapter.notifyDataSetChanged();
                        }
                    }


                }else {

//                    Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
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
        ProgressDialog progressDialog = new ProgressDialog(ProjectPost.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<ProductResultResponse> call1 = Api_Client
                .getInstance()
                .getProductList();
        call1.enqueue(new Callback<ProductResultResponse>() {
            @Override
            public void onResponse(Call<ProductResultResponse> call, Response<ProductResultResponse> response) {
                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){


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

                            ArrayAdapter typeadapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, listType);
                            typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            subcategoryspinner.setAdapter(typeadapter);
                        }
                    }


                }else {

//                    Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ProductResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("failure", t.getLocalizedMessage());

            }
        });
    }

    private void getMaintenanceList() {
        ProgressDialog progressDialog = new ProgressDialog(ProjectPost.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<MaintenanceResultResponse> call2 = Api_Client
                .getInstance()
                .getMaintenanceList();
        call2.enqueue(new Callback<MaintenanceResultResponse>() {
            @Override
            public void onResponse(Call<MaintenanceResultResponse> call, Response<MaintenanceResultResponse> response) {
                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){
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

                            ArrayAdapter typeadapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, listType);
                            typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            subcategoryspinner.setAdapter(typeadapter);
                        }
                    }

                }else {

//                    Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }


            }


            @Override
            public void onFailure(Call<MaintenanceResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("failure", t.getLocalizedMessage());

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId()==android.R.id.home){
//            Intent intent = new Intent(Cart.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    private void getCities() {
        ProgressDialog progressDialog = new ProgressDialog(ProjectPost.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<CityListResultResponse> call;

        {
            Api_Client.getInstance().getCityLists().enqueue(new Callback<CityListResultResponse>() {
                @Override
                public void onResponse(Call<CityListResultResponse> call, Response<CityListResultResponse> response) {
                    progressDialog.dismiss();
                    if (response.body().getMessage().equals("City fetch successfully")) {
//                        Toast.makeText(RegisterForm.this, "Success", Toast.LENGTH_SHORT).show();

                        CityListResultResponse cityListResultResponse = response.body();
                        List<CityListResponse> cityList = cityListResultResponse.getData();
                        Log.e("tag", "cityList"+cityList.get(0).getId());

                        citi_id = new int[cityList.size()];
//                        citylist = new String[cityList.size()+1];
                        for (int i = 0; i < cityList.size(); i++) {
                            Log.e("tag", "names");
                            names.add(cityList.get(i).getName().toString());
                            citi_id[i] = cityList.get(i).getId();
                            Log.e("tag", "getname");

                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProjectPost.this, android.R.layout.simple_spinner_item, names);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cityNamesSpinner.setAdapter(adapter);


                    } else {
//                        Toast.makeText(ProjectPost.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("tag","fail "+response.body().getMessage());


                    }

                }

                @Override
                public void onFailure(Call<CityListResultResponse> call, Throwable t) {
                    progressDialog.dismiss();
//                    Toast.makeText(ProjectPost.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+t.getLocalizedMessage());


                }
            });
        }

    }

    public void launchGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    launchGalleryIntent();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EXTERNAL_STORAGE && resultCode == RESULT_OK) {


            final List<Bitmap> bitmaps = new ArrayList<>();

            ClipData clipData = data.getClipData();

            if (clipData != null) {
                //multiple images selecetd
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    Log.d("URI", imageUri.toString());
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,70,stream);
                        bitmaps.add(bitmap);

                        Log.d("tag","bit "+bitmaps);
                        byte[] bytes = stream.toByteArray();
                        user_dp = Base64.encodeToString(bytes,Base64.DEFAULT);
                        img.add("data:image/jpeg;base64," +user_dp);
                        questionArray = (JsonArray) new Gson().toJsonTree(img);
                        Log.d("tag","JsonArray"+questionArray.toString());
//                        textView.setText((CharSequence) img);
                        Log.d("img","img "+img);
                        Log.d("tag","image "+clipData.getItemCount());
//                        Toast.makeText(this, ""+clipData.getItemCount(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(this, ""+user_dp, Toast.LENGTH_SHORT).show();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //single image selected
                Uri imageUri = data.getData();
//                mPhotoFile = new File(getRealPathFromUri(imageUri));
//                images = Collections.singletonList(getFileToByte(mPhotoFile.getAbsolutePath()));

//                Log.d("tag","img "+mPhotoFile);
                Log.d("URI", imageUri.toString());
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap resized = Bitmap.createScaledBitmap(bitmap, 300, 400, true);
                    resized.compress(Bitmap.CompressFormat.JPEG,70,stream);
                    bitmaps.add(resized);
                    byte[] bytes = stream.toByteArray();
                    user_dp = Base64.encodeToString(bytes,Base64.DEFAULT);
                    img.add("data:image/jpeg;base64," +user_dp);




                    questionArray = (JsonArray) new Gson().toJsonTree(img);
                    Log.d("tag","JsonArray"+questionArray.toString());
                    Log.d("tag","image1 "+user_dp);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (final Bitmap b : bitmaps) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                choosenImage.setImageBitmap(b);
                                Log.d("tag","images "+b);
                            }
                        });

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}