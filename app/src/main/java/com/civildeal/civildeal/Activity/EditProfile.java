package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.civildeal.civildeal.BuildConfig;
import com.civildeal.civildeal.Prefrences.SharedPrefForLocation;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.EditProfileResponse;
import com.civildeal.civildeal.api.ModelResponse.EditProfileResultResponse;
import com.civildeal.civildeal.api.ModelResponse.GetVendorProfilePicResponse;
import com.civildeal.civildeal.api.ModelResponse.GetvendorProfilePicResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProfileResponse;
import com.civildeal.civildeal.api.ModelResponse.ProfileResultResponse;
import com.civildeal.civildeal.api.ModelResponse.UpdateProfilePicResponse;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {

    Toolbar toolbar;
    ImageView userprofilePic,uploadImage;
    EditText Name,phone,address,panNo,desc,lastName,comapnyName,rateValue,email;
    List<ProfileResultResponse> profiledetails;
    SharedPrefManager sharedPrefManager;
    SharedPrefForLocation sharedPrefForLocation;
    String vendor_id;
    private static final int REQUEST_CODE =1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    FileCompressor mCompressor;
    private String  user_dp = "";

    Button saveProfile;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userprofilePic = findViewById(R.id.profile_pic);
        uploadImage = findViewById(R.id.change_image);
        Name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.mobile);
        address = findViewById(R.id.address);
        panNo = findViewById(R.id.panNo);
        desc = findViewById(R.id.desc);
        comapnyName = findViewById(R.id.companyname);
        rateValue = findViewById(R.id.ratevalue);
        email = findViewById(R.id.useremail);

        saveProfile = findViewById(R.id.save_profile);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        vendor_id = String.valueOf(sharedPrefManager.getUserId());
        sharedPrefForLocation = new SharedPrefForLocation(getApplicationContext());


        if (userprofilePic!=null){
            uploadImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_CODE);
                    selectImage();
                }
            });
        }


        loadingProfile();


        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfile();
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
                                .into(userprofilePic);
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

    private void selectImage() {

        final CharSequence[] items = {"Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setItems(items, (dialog, item) -> {
//            if (items[item].equals("Take Photo")) {
//                requestStoragePermission(true);
//            } else
                if (items[item].equals("Choose from Library")) {
                requestStoragePermission(false);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_TAKE_PHOTO) {
//                try {
//                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
//                    user_dp = getFileToByte(mPhotoFile.getAbsolutePath());
//                    //  Toast.makeText(this, user_dp, Toast.LENGTH_SHORT).show();
//                    updateImage(studentId, user_dp);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            } else
                if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = new File(getRealPathFromUri(selectedImage));
                    user_dp = getFileToByte(mPhotoFile.getAbsolutePath());
                    //   Toast.makeText(this, user_dp, Toast.LENGTH_SHORT).show();
                    updateImage(vendor_id, user_dp);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

//        try {
//            switch (requestCode) {
//
//                case REQUEST_CODE:
//                    if (resultCode == Activity.RESULT_OK) {
//                        Uri selectedImage = data.getData();
//                        //data gives you the image uri. Try to convert that to bitmap
//                        break;
//                    } else if (resultCode == Activity.RESULT_CANCELED) {
//                        Log.e("tag", "Selecting picture cancelled");
//                    }
//                    break;
//            }
//        } catch (Exception e) {
//            Log.e("tag", "Exception in onActivityResult : " + e.getMessage());
//        }
    }

    private void requestStoragePermission(boolean isCamera) {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                dispatchTakePictureIntent();
                            } else {
                                dispatchGalleryIntent();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    public static String getFileToByte(String filePath) {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try {
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 70, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeString;
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void loadingProfile(){
//        ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Please wait..");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();
        Call<ProfileResultResponse> call = Api_Client
                .getInstance()
                .getProfileDetails(vendor_id);
        call.enqueue(new Callback<ProfileResultResponse>() {
            @Override
            public void onResponse(Call<ProfileResultResponse> call, Response<ProfileResultResponse> response) {

                if(response.body().getCode().equals(200)) {
//                    progressDialog.dismiss();

                    try {
                        ProfileResultResponse profileResultResponse = response.body();
                        List<ProfileResponse> profiledetails = profileResultResponse.getData();

//                    Picasso.get()
//                            .load("https://civildeal.com/public/assets/uploads/vendor/"+profiledetails.get(0).getUserimage())
//                            .error(R.drawable.icon_image)
//                            .placeholder(R.drawable.icon_image)
//                            .into(userprofilePic);

                        Name.setText(profiledetails.get(0).getName());
                        lastName.setText(profiledetails.get(0).getLastname());
                        phone.setText(profiledetails.get(0).getMobile());
                        address.setText(profiledetails.get(0).getAddress());
                        panNo.setText(profiledetails.get(0).getPan());
                        desc.setText(profiledetails.get(0).getShortDescription());
                        comapnyName.setText(profiledetails.get(0).getCompanyName());
                        rateValue.setText(profiledetails.get(0).getServiceAmount());
                        email.setText(profiledetails.get(0).getEmail());
                        getProfilePic();

//                Name.setText(sharedPrefManager.getUser().getName());
//                phone.setText(sharedPrefManager.getUser().getMobile());
////                address.setText(sharedPrefManager.getUser().getAddress());
//                type.setText(sharedPrefManager.getUser().getType());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }



                }
                else
                {
//                    progressDialog.dismiss();
//                    Toast.makeText(EditProfile.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ProfileResultResponse> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(EditProfile.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("tag","fail "+t.getLocalizedMessage());

            }
        });


    }

    private void updateProfile() {

        Call<EditProfileResultResponse> call = Api_Client
                .getInstance()
                .editProfile(vendor_id,Name.getText().toString(),lastName.getText().toString(),phone.getText().toString(),address.getText().toString(),panNo.getText().toString(),desc.getText().toString(),comapnyName.getText().toString(),rateValue.getText().toString(),email.getText().toString());
        call.enqueue(new Callback<EditProfileResultResponse>() {
            @Override
            public void onResponse(Call<EditProfileResultResponse> call, Response<EditProfileResultResponse> response) {

                if (!response.isSuccessful()){

                    Toast.makeText(EditProfile.this, "Response Fail"+response.code(), Toast.LENGTH_SHORT).show();
                    return;

                }else if(response.body().getCode().equals(200)) {
                    EditProfileResultResponse editProfileResultResponse = response.body();
                    List<EditProfileResponse> editProfile = Collections.singletonList(editProfileResultResponse.getData());
                    loadingProfile();
                    Name.setEnabled(false);
                    lastName.setEnabled(false);
                    phone.setEnabled(false);
                    address.setEnabled(false);
                    panNo.setEnabled(false);
                    desc.setEnabled(false);
                    comapnyName.setEnabled(false);
                    rateValue.setEnabled(false);
                    email.setEnabled(false);
                    Log.e("tag",""+response.body().getMessage());
                    Log.e("tag",""+response.body().getData());

                    sharedPrefForLocation.saveEditProfile(editProfileResultResponse.getData());

                    Toast.makeText(EditProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfile.this,Profile.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
//                    Toast.makeText(EditProfile.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("tag",""+response.body().getMessage());

                }

            }

            @Override
            public void onFailure(Call<EditProfileResultResponse> call, Throwable t) {

                Toast.makeText(EditProfile.this, "Network Problem: Please Check your Internet Conncetions", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void updateImage(String vendor_id, String profile_pic) {

        ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading image..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Log.d("tag","pic "+profile_pic);
        Call<UpdateProfilePicResponse> call = Api_Client
                .getInstance()
                .updateImage(vendor_id,"data:image/jpeg;base64,"+profile_pic);
        call.enqueue(new Callback<UpdateProfilePicResponse>() {
            @Override
            public void onResponse(Call<UpdateProfilePicResponse> call, Response<UpdateProfilePicResponse> response) {
                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){

                    UpdateProfilePicResponse updateProfilePicResponse = response.body();
                    Log.e("tag",""+response.body().getMessage());
                    Toast.makeText(EditProfile.this, "Profile Pic Uploaded Successfully", Toast.LENGTH_SHORT).show();

                }else {
                    
                    Log.e("tag",""+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<UpdateProfilePicResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("tag",""+t.getLocalizedMessage());

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(EditProfile.this, Profile.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditProfile.this,Profile.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadingProfile();
    }
}