package com.civildeal.civildeal.Prefrences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.civildeal.civildeal.api.ModelResponse.LoginResponse;

public class SharedPrefManager {

    private static String SHARED_PREF_NAME  = "civildeal";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public void saveUser(LoginResponse loginResponse){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("id",loginResponse.getId().toString());
        editor.putString("name",loginResponse.getName());
        editor.putString("lastname",loginResponse.getLastname().toString());
        editor.putString("email", String.valueOf(loginResponse.getEmail()));
        editor.putString("mobile",loginResponse.getMobile());
        editor.putString("password",loginResponse.getPassword());
        editor.putString("type",loginResponse.getType().toString());
        Log.e("type",""+loginResponse.getType());
        editor.putString("vendor_type",loginResponse.getVendorType());
        editor.putString("service_id", String.valueOf(loginResponse.getServiceId()));
        editor.putString("product_id", String.valueOf(loginResponse.getProductId()));
        editor.putString("userimage", String.valueOf(loginResponse.getProductId()));
        editor.putString("location_id", String.valueOf(loginResponse.getLocationId()));
        editor.putString("locationName", String.valueOf(loginResponse.getLocationName()));
//        editor.putString("location_name", String.valueOf(loginResponse.getLocationId()));
        editor.putBoolean("logged",true);
        editor.apply();
    }


    public boolean isLoggedIn(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged",false);

    }

    public String getUserId(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("id","0");

    }
    public String getUserType(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("type","0");

    }

    public String getUserserviceid(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("service_id","0");

    }

    public String getUserproductid(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("product_id","0");

    }

    public String getUserName(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("name","0");

    }

    public String getUserLastName(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("lastname","0");

    }

    public String getUserMobile(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("mobile","0");

    }

    public String getVendorType(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("vendor_type","0");

    }

    public String getEmail(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("email","0");

    }

    public String getUserimage(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("userimage","0");

    }
    public void logout(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
//    public void setLocation(String location_name){
//
//        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
//        editor=sharedPreferences.edit();
//        editor.putString("city_name",location_name);
//        editor.apply();
//    }

    public String getLocation(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("locationName","0");

    }

    public void savePromoterId(String promoter_id){

        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("promoter_id",promoter_id);
        editor.apply();
    }

    public String getPromoterId(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("promoter_id","0");


    }

}
