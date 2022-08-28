package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.SearchBidAdapter;
import com.civildeal.civildeal.BuildConfig;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.SearchBidResponse;
import com.civildeal.civildeal.api.ModelResponse.SearchBidResultResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

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

import static com.civildeal.civildeal.Activity.BidSubmission.clipData;

public class SearchBid extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView searchBidRV;
    String cityName,keyword,vendor_id;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    public static String  user_dp = "";
    SharedPrefManager sharedPrefManager;
    final int REQUEST_EXTERNAL_STORAGE = 100;
    public static ArrayList<String> img;
    public static JsonArray questionArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bid);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img = new ArrayList<>();

        searchBidRV = findViewById(R.id.search_bid_RV);

        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        vendor_id = sharedPrefManager.getUserId();


        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        keyword = intent.getStringExtra("keyword");
//        Toast.makeText(this, "city "+cityName+keyword, Toast.LENGTH_SHORT).show();
        Log.d("tag","city "+cityName);
        Log.d("tag","keyword "+keyword);


        getSerachList();
    }

//    public void selectImage() {
//
//        final CharSequence[] items = {"Choose from Library",
//                "Cancel"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(SearchBid.this);
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

    private void getSerachList() {
        ProgressDialog progressDialog = new ProgressDialog(SearchBid.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<SearchBidResultResponse> call = Api_Client
                .getInstance()
                .getSearchBidList(cityName,keyword,vendor_id);
        call.enqueue(new Callback<SearchBidResultResponse>() {
            @Override
            public void onResponse(Call<SearchBidResultResponse> call, Response<SearchBidResultResponse> response) {
                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){

                    try {
                        SearchBidResultResponse searchBidResultResponse = response.body();
                        List<SearchBidResponse> searchBidList = searchBidResultResponse.getData();
                        SearchBidAdapter searchBidAdapter = new SearchBidAdapter(SearchBid.this,searchBidList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchBid.this, 1);
                        searchBidRV.setLayoutManager(gridLayoutManager);
                        searchBidRV.setAdapter(searchBidAdapter);
                        searchBidAdapter.notifyDataSetChanged();

                        Log.d("tag","Sucess "+response.body().getMessage());
                    }catch (Exception e){
                        Log.d("tag","Exceptiion "+response.body().getMessage());

                    }

                }else {
                    Log.d("tag","fail "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<SearchBidResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("tag","failuer "+t.getLocalizedMessage());

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
}