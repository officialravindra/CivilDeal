 package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.ProjectPostListAdapter;
import com.civildeal.civildeal.BuildConfig;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.CityResponse;
import com.civildeal.civildeal.api.ModelResponse.CityResultReaponse;
import com.civildeal.civildeal.api.ModelResponse.ProjectPostListResponse;
import com.civildeal.civildeal.api.ModelResponse.ProjectPostListResultResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// @ReportsCrashes(mailTo = "akabhinavv3@gmail.com",
//         customReportContent = { ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT },
//         mode = ReportingInteractionMode.TOAST,
//         resToastText = R.string.resToastText)
public class BidSubmission extends AppCompatActivity {

    Toolbar toolbar;
    EditText entercity;
    SearchView searchproject,search_city;
    RecyclerView bid_list_rv;
    ListView listView,city_listview;
    ArrayAdapter<String > list_adapter;
    ArrayAdapter<String> city_adapter;
    ArrayList<String> list;
    ArrayList<String> city_list;
    String city_name;
    String[] type;
    SharedPrefManager sharedPrefManager;
    String vendor_id,city;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    public static String  user_dp = "";
    public static BufferedWriter out;
    Boolean append;
    final int REQUEST_EXTERNAL_STORAGE = 100;
    public static ArrayList<String> img;
    public static JsonArray questionArray;
    public static ClipData clipData;
    public static String no_of_pic;
    HashSet<String> hashSet = new HashSet<String>();
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_submission);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bid_list_rv =findViewById(R.id.bid_recycler_view);

        list = new ArrayList<>();
        city_list = new ArrayList<>();
        listView = findViewById(R.id.lv1);
        city_listview = findViewById(R.id.city_listView);
        entercity = findViewById(R.id.searchcity);
        searchproject = findViewById(R.id.searchview);
        search_city = findViewById(R.id.city_search);
        swipeRefreshLayout = findViewById(R.id.swipereferesh);

        img = new ArrayList<>();

        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        vendor_id = sharedPrefManager.getUserId();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("tag","swipe");
                getProjectList(city);
            }
        });


        getCities();

        city_listview.setVisibility(View.GONE);

//        entercity.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                city_listview.setVisibility(View.VISIBLE);
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                city_listview.setVisibility(View.VISIBLE);
////                city_adapter.getFilter().filter(s);
//
//                if (city_list.contains(s)){
//
//                    city_adapter.getFilter().filter(s);
//                    city_listview.setVisibility(View.VISIBLE);
//                }else {
//                    city_adapter.getFilter().filter(s);
//                    city_listview.setVisibility(View.GONE);
//
//                    Log.d("tag","else "+s);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                city_listview.setVisibility(View.VISIBLE);
//            }
//        });

        search_city.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(city_list.contains(query)){
                    city_adapter.getFilter().filter(query);
                    city_listview.setVisibility(View.VISIBLE);
                    Log.e("tag",""+query);
                }
                else{
                    city_listview.setVisibility(View.GONE);


//                    Toast.makeText(getContext(), "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                city_adapter.getFilter().filter(newText);
                city_listview.setVisibility(View.VISIBLE);

                Log.e("tag","new "+newText);
                if(newText.equals(""))
                {
                    city_listview.setVisibility(View.GONE);
                    Log.e("tag","list "+city_listview);
                }
                return false;
            }
        });


        city_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int pos = city_list.indexOf(city_adapter.getItem(position));

                Log.e("tag","pos "+pos);

                city_name = city_list.get(pos);
                getProjectList(city_name);
                Log.d("tag","city_name "+city_name);
                search_city.setQuery(city_name,false);
                city_listview.setVisibility(View.GONE);
            }
        });

        list_adapter = new ArrayAdapter<String>(BidSubmission.this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(list_adapter);
        getProjectSearchList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int pos = list.indexOf(list_adapter.getItem(i));
//                list_id = String.valueOf(Integer.parseInt(listById.get(i)));
//                Toast.makeText(getContext(), ""+pos, Toast.LENGTH_SHORT).show();
                Log.e("tag","pos "+pos);

                String keyword = list.get(pos);
                Log.e("tag",""+keyword);
                Intent intent = new Intent(BidSubmission.this, SearchBid.class);
                // intent.putExtra("Type",TypeSelected);
//                intent.putExtra("cityName",entercity.getText().toString());
                intent.putExtra("cityName",city_name);
                intent.putExtra("keyword",keyword);
//                intent.putExtra("list_id",list_id);
//                Log.e("tag","list_id1 "+list_id);
                startActivity(intent);


            }
        });

        listView.setVisibility(View.GONE);
        searchproject.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(list.contains(query)){
                    list_adapter.getFilter().filter(query);
                    listView.setVisibility(View.VISIBLE);
                    Log.e("tag",""+query);
                }
                else{
                    listView.setVisibility(View.GONE);


//                    Toast.makeText(getContext(), "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                list_adapter.getFilter().filter(newText);
                listView.setVisibility(View.VISIBLE);

                Log.e("tag","new "+newText);
                if(newText.equals(""))
                {
                    listView.setVisibility(View.GONE);
                    Log.e("tag","listView "+listView);
                }
                return false;
            }
        });



        getProjectList(city_name);
    }


//    public void selectImage() {
//
//        final CharSequence[] items = {"Choose from Library",
//                "Cancel"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(BidSubmission.this);
//        builder.setItems(items, (dialog, item) -> {
////            if (items[item].equals("Take Photo")) {
////                requestStoragePermission(true);
////            } else
//            if (items[item].equals("Choose from Library")) {
//                requestStoragePermission(false);
//            } else if (items[item].equals("Cancel")) {
//                dialog.dismiss();
//            }
//        });
//        builder.show();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
////        if (resultCode == RESULT_OK) {
////            if (requestCode == REQUEST_TAKE_PHOTO) {
////                try {
////                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
////                    user_dp = getFileToByte(mPhotoFile.getAbsolutePath());
////                    //  Toast.makeText(this, user_dp, Toast.LENGTH_SHORT).show();
////                    updateImage(studentId, user_dp);
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////
////            } else
//        if (requestCode == REQUEST_GALLERY_PHOTO) {
//            Uri selectedImage = data.getData();
//            try {
//                mPhotoFile = new File(getRealPathFromUri(selectedImage));
////                Picasso
////                        .get()
////                        .load(selectedImage)
////                        .into(choosenImage);
//                user_dp = getFileToByte(mPhotoFile.getAbsolutePath());
////                //   Toast.makeText(this, user_dp, Toast.LENGTH_SHORT).show();
////                updateImage(vendor_id, user_dp);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//        }
//
////        try {
////            switch (requestCode) {
////
////                case REQUEST_CODE:
////                    if (resultCode == Activity.RESULT_OK) {
////                        Uri selectedImage = data.getData();
////                        //data gives you the image uri. Try to convert that to bitmap
////                        break;
////                    } else if (resultCode == Activity.RESULT_CANCELED) {
////                        Log.e("tag", "Selecting picture cancelled");
////                    }
////                    break;
////            }
////        } catch (Exception e) {
////            Log.e("tag", "Exception in onActivityResult : " + e.getMessage());
////        }
//    }
//
//    private void requestStoragePermission(boolean isCamera) {
//        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        // check if all permissions are granted
//                        if (report.areAllPermissionsGranted()) {
//                            if (isCamera) {
//                                dispatchTakePictureIntent();
//                            } else {
//                                dispatchGalleryIntent();
//                            }
//                        }
//                        // check for permanent denial of any permission
//                        if (report.isAnyPermissionPermanentlyDenied()) {
//                            // show alert dialog navigating to Settings
//                            showSettingsDialog();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
//                .onSameThread()
//                .check();
//    }
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//                // Error occurred while creating the File
//            }
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        BuildConfig.APPLICATION_ID + ".provider",
//                        photoFile);
//
//                mPhotoFile = photoFile;
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//
//            }
//        }
//    }
//
//    private void dispatchGalleryIntent() {
//        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
//    }
//
//    private void showSettingsDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Need Permissions");
//        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
//        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
//            dialog.cancel();
//            openSettings();
//        });
//        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
//        builder.show();
//
//    }
//
//    // navigating user to app settings
//    private void openSettings() {
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", getPackageName(), null);
//        intent.setData(uri);
//        startActivityForResult(intent, 101);
//    }
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        String mFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
//        return mFile;
//    }
//
//    public static String getFileToByte(String filePath) {
//        Bitmap bmp = null;
//        ByteArrayOutputStream bos = null;
//        byte[] bt = null;
//        String encodeString = null;
//        try {
//            bmp = BitmapFactory.decodeFile(filePath);
//            bos = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.JPEG, 80, bos);
//            bt = bos.toByteArray();
//            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return encodeString;
//    }
//
//    public String getRealPathFromUri(Uri contentUri) {
//        Cursor cursor = null;
//        try {
//            String[] proj = {MediaStore.Images.Media.DATA};
//            cursor = getContentResolver().query(contentUri, proj, null, null, null);
//            assert cursor != null;
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }

    public void getProjectList(String city_name) {
        ProgressDialog progressDialog = new ProgressDialog(BidSubmission.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Log.d("tag","vendor_id1 "+vendor_id);
        Log.d("tag","city_name1 "+city_name);
        Call<ProjectPostListResultResponse> call =Api_Client
                .getInstance()
                .getProjectPostList(vendor_id,city_name);
        call.enqueue(new Callback<ProjectPostListResultResponse>() {
            @Override
            public void onResponse(Call<ProjectPostListResultResponse> call, Response<ProjectPostListResultResponse> response) {

                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){
                    try {
                        swipeRefreshLayout.setRefreshing(false);
                        ProjectPostListResultResponse projectPostListResultResponse = response.body();
                        List<ProjectPostListResponse> projectPostList = projectPostListResultResponse.getData();
                        ProjectPostListAdapter projectPostListAdapter = new ProjectPostListAdapter(BidSubmission.this,projectPostList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(BidSubmission.this, 1);
                        bid_list_rv.setLayoutManager(gridLayoutManager);
                        bid_list_rv.setAdapter(projectPostListAdapter);
                        projectPostListAdapter.notifyDataSetChanged();
                        Log.d("tag","Sucess "+response.body().getMessage());
                    }catch (Exception e){


                        try {
                            createFileOnDevice(append);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        Toast.makeText(BidSubmission.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                }else {

                    Log.d("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ProjectPostListResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("tag","failuer "+t.getLocalizedMessage());

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

//                        citi_id = new int[cityList.size()];
                        for (int i = 0; i < cityList.size(); i++) {

                            city_list.add(cityList.get(i).getName().toString());
//                            citi_id[i] = cityList.get(i).getId();
                            Log.d("tag", "names"+city_list.get(i));

                            if(i == cityList.size()-1)
                            {
                                city_adapter = new ArrayAdapter<String>(BidSubmission.this, android.R.layout.simple_list_item_1,city_list);
                                city_listview.setAdapter(city_adapter);



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


    private void getProjectSearchList() {

        Log.d("tag","vendor_id "+vendor_id);
        Call<ProjectPostListResultResponse> call =Api_Client
                .getInstance()
                .getProjectPostList(vendor_id,entercity.getText().toString());
        call.enqueue(new Callback<ProjectPostListResultResponse>() {
            @Override
            public void onResponse(Call<ProjectPostListResultResponse> call, Response<ProjectPostListResultResponse> response) {

                if (response.body().getCode().equals(200)){

                    ProjectPostListResultResponse projectPostListResultResponse = response.body();
                    List<ProjectPostListResponse> projectPostList = projectPostListResultResponse.getData();
                    list.clear();
                    for (int i =0;i<projectPostList.size();i++){


                        type =new String[projectPostList.size()];
                        type[i] = projectPostList.get(i).getTitle().toLowerCase();
//                        Log.e("tag",""+listType.get(i));

                        list.add(type[i]);

                        //To remove duplicate entry from list
                        hashSet.addAll(list);
                        list.clear();
                        list.addAll(hashSet);
                        Log.d("tag","project "+list);


                    }
                }else {

                    Log.d("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ProjectPostListResultResponse> call, Throwable t) {
                Log.d("tag","failuer "+t.getLocalizedMessage());

            }
        });
    }

    private void createFileOnDevice(Boolean append) throws IOException {
        /*
         * Function to initially create the log file and it also writes the time of creation to file.
         */
        File Root = Environment.getExternalStorageDirectory();
        if(Root.canWrite()){
            File  LogFile = new File(Root, "Log.txt");
            FileWriter LogWriter = new FileWriter(LogFile, append);
            out = new BufferedWriter(LogWriter);
            Date date = new Date();
            out.write("Logged at" + String.valueOf(date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "\n"));
            out.close();

        }
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


//    private void getProjectList() {
//
//        ProgressDialog progressDialog = new ProgressDialog(BidSubmission.this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Please wait..");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();
//        Call<ProfileResultResponse> call = Api_Client
//                .getInstance()
//                .vendorlist("service",service_id,city_id);
//        call.enqueue(new Callback<ProfileResultResponse>() {
//            @Override
//            public void onResponse(Call<ProfileResultResponse> call, Response<ProfileResultResponse> response) {
//
//                progressDialog.dismiss();
//                if (response.body().getCode().equals(200)){
//
//                    ProfileResultResponse profileResultResponse = response.body();
//                    List<ProfileResponse> vendorList = profileResultResponse.getData();
////                    Log.e("tag","vendorList");
//                    ServiceVendorListsAdapter serviceVendorListsAdapter = new ServiceVendorListsAdapter(Service.this,vendorList);
////                    Log.e("tag","adapter");
//                    GridLayoutManager gridLayoutManager = new GridLayoutManager(Service.this,1);
//                    Rv.setLayoutManager(gridLayoutManager);
//                    Rv.setAdapter(serviceVendorListsAdapter);
//                    serviceVendorListsAdapter.notifyDataSetChanged();
//                    Log.e("tag","cityId "+city_id);
//
//                }
//                else {
//
//                    layout.setVisibility(View.VISIBLE);
//                    noFound.setVisibility(View.VISIBLE);
////                    Toast.makeText(Service.this, "No Vendor Found For Selected Location", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProfileResultResponse> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(Service.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

//     @Override
//     protected void attachBaseContext(Context base) {
//         super.attachBaseContext(base);
//
//         Log.d("tag","crash");
//         // The following line triggers the initialization of ACRA
//         ACRA.init(this.getApplication());
//     }

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

            clipData = data.getClipData();

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
                        no_of_pic = String.valueOf(clipData.getItemCount());
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
                    bitmap.compress(Bitmap.CompressFormat.JPEG,70,stream);
                    bitmaps.add(bitmap);
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

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for (final Bitmap b : bitmaps) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//
//                                choosenImage.setImageBitmap(b);
//                                Log.d("tag","images "+b);
//                            }
//                        });
//
//                        try {
//                            Thread.sleep(3000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }).start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProjectList(city_name);
    }
}