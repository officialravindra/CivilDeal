package com.civildeal.civildeal.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.civildeal.civildeal.Activity.MainActivity;
import com.civildeal.civildeal.Adapter.MaintenanceAdapter;
import com.civildeal.civildeal.Adapter.ProductAdapter;
import com.civildeal.civildeal.Adapter.ServiceAdapter;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.CityResponse;
import com.civildeal.civildeal.api.ModelResponse.CityResultReaponse;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResponse;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResultResponse;
import com.civildeal.civildeal.api.ModelResponse.PostEnquiryResponse;
import com.civildeal.civildeal.api.ModelResponse.PostEnquiryResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResultResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostRequrimentsFragment extends Fragment {

    EditText requirment,name,phone;
    Spinner cityNamesSpinner, typeSpinner,subcategoryspinner;
    String[] city;
    String[] type;
    String[] sub_category;
    Button submit;
    List<PostEnquiryResponse> cityList;
    ArrayList<String> names = new ArrayList<String>();
    SharedPrefManager sharedPrefManager;
    String vendor_id,service_id,product_id,City_Selected,Type_Selected,Sub_Category_Selected;
    int citi_id[];
    String cityId;
    ArrayAdapter typeadapter;
    String subCategory,typename;
    ArrayList<String> listType;

    public PostRequrimentsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        requirment = view.findViewById(R.id.requirment);
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        cityNamesSpinner = view.findViewById(R.id.citySpinner);
        typeSpinner = view.findViewById(R.id.typeSpinner);
        subcategoryspinner = view.findViewById(R.id.servicetypeSpinner);

        submit = view.findViewById(R.id.submit);

        sharedPrefManager=new SharedPrefManager(getActivity());
        vendor_id = String.valueOf(sharedPrefManager.getUserId());
        service_id = String.valueOf(sharedPrefManager.getUserserviceid());
        product_id = String.valueOf(sharedPrefManager.getUserproductid());



        city = getResources().getStringArray(R.array.cityNames);
        type = getResources().getStringArray(R.array.selectType);

//        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,city);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        cityNamesSpinner.setAdapter(adapter);

        ArrayAdapter typeAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);


        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getContext(), ""+Cities[i], Toast.LENGTH_SHORT).show();
                subCategory = adapterView.getItemAtPosition(i).toString();
                if (subCategory.equals("service")) {
                    getServiceList();
                    Log.e("tag", "service" + subCategory);
                }
                else if (subCategory.equals("product")) {

                    getProductList();
                    Log.e("tag", "product" + subCategory);
                }
                else if (subCategory.equals("maintenance")) {
                    getMaintenanceList();
                    Log.e("tag", "maintenance " + subCategory);
                } else {
//                Log.e("tag", "vendor_type " + Type_Selected);
                }
                typeSpinner.setSelection(i);
                Type_Selected = type[i];
               // getSelectedTypeCategories();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                subCategory = adapterView.getItemAtPosition(0).toString();
                if (subCategory.equals("service")) {
                    getServiceList();
                    Log.e("tag", "service" + subCategory);
                }
                else if (subCategory.equals("product")) {

                    getProductList();
                    Log.e("tag", "product" + subCategory);
                }
                else if (subCategory.equals("maintenance")) {
                    getMaintenanceList();
                    Log.e("tag", "maintenance " + subCategory);
                } else {
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typename = parent.getItemAtPosition(0).toString();

                Sub_Category_Selected = sub_category[0];
                subcategoryspinner.setSelection(0);

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
        getCities();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkDataEntered();

            }
        });





        return view;
    }

    private void getServiceList() {
        Call<ServiceResultResponse> call = Api_Client
                .getInstance()
                .getServicesList();
        call.enqueue(new Callback<ServiceResultResponse>() {
            @Override
            public void onResponse(Call<ServiceResultResponse> call, Response<ServiceResultResponse> response) {

                if (response.body().getCode().equals(200)){

                    try {
                        ServiceResultResponse serviceResultResponse = response.body();
                        List<ServiceResponse> serviceList = serviceResultResponse.getData();
//                    ServiceAdapter serviceAdapter = new ServiceAdapter(getActivity(), serviceList);
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

                            try {
                                if (i==serviceList.size()-1){

                                    typeadapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listType);
                                    typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    subcategoryspinner.setAdapter(typeadapter);
                                }
                            }catch (Exception e){
                                Log.d("tag","exception");
                            }



                        }
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }



                }else {

                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServiceResultResponse> call, Throwable t) {

                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }

    private void getProductList() {

        Call<ProductResultResponse> call1 = Api_Client
                .getInstance()
                .getProductList();
        call1.enqueue(new Callback<ProductResultResponse>() {
            @Override
            public void onResponse(Call<ProductResultResponse> call, Response<ProductResultResponse> response) {

                if (response.body().getCode().equals(200)){

                    try {
                        ProductResultResponse productResultResponse = response.body();
                        List<ProductResponse> productList = productResultResponse.getData();
                        ProductAdapter productAdapter = new ProductAdapter(getActivity(), productList);
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

                                ArrayAdapter typeadapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listType);
                                typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subcategoryspinner.setAdapter(typeadapter);
                            }
                        }
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }




                }else {

                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResultResponse> call, Throwable t) {

                Log.e("failure", t.getLocalizedMessage());

            }
        });
    }

    private void getMaintenanceList() {

        Call<MaintenanceResultResponse> call2 = Api_Client
                .getInstance()
                .getMaintenanceList();
        call2.enqueue(new Callback<MaintenanceResultResponse>() {
            @Override
            public void onResponse(Call<MaintenanceResultResponse> call, Response<MaintenanceResultResponse> response) {

                if (response.body().getCode().equals(200)){

                    try {
                        MaintenanceResultResponse maintenanceResultResponse = response.body();
                        List<MaintenanceResponse> maintenanceList = maintenanceResultResponse.getData();
                        MaintenanceAdapter maintenanceAdapter = new MaintenanceAdapter(getActivity(), maintenanceList);
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

                                ArrayAdapter typeadapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listType);
                                typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subcategoryspinner.setAdapter(typeadapter);
                            }
                        }
                    }catch (Exception e){

                        Log.d("tag","Exception "+response.body().getMessage());
                    }


                }else {

                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<MaintenanceResultResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());

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

                        try {
                            CityResponse cityResponse = response.body();
                            List<CityResultReaponse> cityList = cityResponse.getData();
                            Log.e("tag","cityList");

                            citi_id = new int[cityList.size()];
                            for (int i=0;i<cityList.size();i++) {
                                Log.e("tag","names");
                                names.add(cityList.get(i).getName().toString());
                                citi_id[i] = cityList.get(i).getId();
                                Log.e("tag","getname");

                            }

                            if(getActivity() != null) {
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cityNamesSpinner.setAdapter(adapter);
                            }
                        }catch (Exception e){

                            Log.d("tag","Exception "+response.body().getMessage());
                        }




                    }

                    else {

                        Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<CityResponse> call, Throwable t) {
                    try{
//                        Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("tag",""+t.getLocalizedMessage());
                    }catch (Exception e){

                        e.printStackTrace();
                        Log.d("tag","Exc "+e.getMessage());
                    }



                }
            });
        }

    }


    public void checkDataEntered() {


        if (requirment.getText().toString().isEmpty()) {
            requirment.requestFocus();
            requirment.setError("Please enter your query");
            return;

        } else if (name.getText().toString().trim().isEmpty()) {
            Log.e("tag", "erro2");
            name.requestFocus();
            name.setError("Please enter your Name");
            return;

        } else if (phone.getText().toString().isEmpty()) {
            Log.e("tag", "erro3");
            phone.requestFocus();
            phone.setError("Please enter your phone");
            return;

        } else if (cityNamesSpinner == null) {
            Toast.makeText(getActivity(), "Please select your city", Toast.LENGTH_SHORT).show();
        } else if (type == null) {

            Toast.makeText(getActivity(), "Please select your type", Toast.LENGTH_SHORT).show();
        } else {

            postRequriment();

        }
    }

    private void postRequriment() {

        Log.e("tag","cityId1 "+cityId);
        Call<PostEnquiryResultResponse> call;

        {
            Api_Client.getInstance().savePost(name.getText().toString(),phone.getText().toString(),requirment.getText().toString(), cityId,Type_Selected,typename,typename).enqueue(new Callback<PostEnquiryResultResponse>() {
                @Override
                public void onResponse(Call<PostEnquiryResultResponse> call, Response<PostEnquiryResultResponse> response) {


                    if (response.body().getMessage().equals("Lead saved successfully")){
                        try {
                            Toast.makeText(getActivity(), "Thank You For inquiry", Toast.LENGTH_SHORT).show();

                            Log.e("tag","cityId success "+cityId);
                            name.setText("");
                            phone.setText("");
                            requirment.setText("");

                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);
                            ((Activity) getActivity()).overridePendingTransition(0, 0);


//                        Toast.makeText(getActivity(), ""+City_Selected, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getActivity(), ""+Type_Selected, Toast.LENGTH_SHORT).show();
//                        Log.e("tag1",""+response.body().getData().getQuery());
//                        Log.e("tag1",""+response.body().getData().getName());
//                        Log.e("tag1",""+response.body().getData().getPhone());
//                        Log.e("tag1",""+response.body().getData().getLocationId());
//                        Log.e("tag1",""+response.body().getData().getLeadtype());
//                        Log.e("tag1",""+response.body().getMessage());
//                        Log.e("tag1",""+response.body().getData().getId());
                        }catch (Exception e){
                            Log.d("tag","Exception "+response.body().getMessage());
                        }



                    }

                    else {

                        Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<PostEnquiryResultResponse> call, Throwable t) {
                    Log.e("failure",t.getLocalizedMessage());


                }
            });
        }
    }

}