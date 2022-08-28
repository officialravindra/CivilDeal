package com.civildeal.civildeal.Prefrences;

import android.content.Context;
import android.content.SharedPreferences;

import com.civildeal.civildeal.api.ModelResponse.EditProfileResponse;

public class SharedPrefForLocation {


    private static String SHARED_PREF_NAME  = "civildeal";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPrefForLocation(Context context) {
        this.context = context;
    }
    public void setLocation(String location_name){

        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("city_name",location_name);
        editor.apply();
    }

    public void setSelectedLocation(String selected_City){

        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("CityPos",selected_City);
        editor.apply();
    }

    public void setSelectedLocationId(String selected_city_id){

        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("city_id",selected_city_id);
        editor.apply();
    }

    public void setLeadLocation(String selected_city){

        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("city",selected_city);
        editor.apply();
    }

    public void setLeadLocationId(String selected_cityId){

        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("cityId",selected_cityId);
        editor.apply();
    }



    public String getLocation(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("city_name","0");


    }

    public String getSelectedLocation(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("CityPos","0");

    }

    public String getSelectedLocationId(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("city_id","0");

    }

    public String getLeadLocation(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("city","0");

    }

    public String getLeadLocationId(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
//        Log.e("tag","shared id"+getLeadLocationId());
        return sharedPreferences.getString("cityId","0");

    }

    public void saveLocation(String save_city){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("LeadCity",save_city);
        editor.apply();

    }

    public String getCity(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("LeadCity","0");

    }

    public void saveEditProfile(EditProfileResponse editProfileResponse){

        sharedPreferences=context.getSharedPreferences("edit", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("name",editProfileResponse.getName().toString());
        editor.putString("lastname",editProfileResponse.getLastname().toString());
        editor.putString("mobile",editProfileResponse.getMobile());
        editor.apply();
    }

    public String getname(){
        sharedPreferences=context.getSharedPreferences("edit",Context.MODE_PRIVATE);
        return sharedPreferences.getString("name","0");

    }

    public String getlastname(){
        sharedPreferences=context.getSharedPreferences("edit",Context.MODE_PRIVATE);
        return sharedPreferences.getString("lastname","0");

    }

    public String getmobile(){
        sharedPreferences=context.getSharedPreferences("edit",Context.MODE_PRIVATE);
        return sharedPreferences.getString("mobile","0");

    }

}
