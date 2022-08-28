package com.civildeal.civildeal.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.Activity.BidSubmission;
import com.civildeal.civildeal.Activity.CheckBidDetails;
import com.civildeal.civildeal.Activity.SearchBid;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.SavePlaceBidResponse;
import com.civildeal.civildeal.api.ModelResponse.SearchBidResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBidAdapter extends RecyclerView.Adapter<SearchBidAdapter.MyViewHolder> {
    Context context;
    List<SearchBidResponse> searchBidList;
    EditText bid_amount;
    EditText time;
    EditText description,choose_image;
    String vendor_id;
    ImageView choosen_img;
    SharedPrefManager sharedPrefManager;
    final int REQUEST_EXTERNAL_STORAGE = 100;

    public SearchBidAdapter(Context context, List<SearchBidResponse> searchBidList) {
        this.context = context;
        this.searchBidList = searchBidList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_post_lists,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get()
                .load(searchBidList.get(position).getImagePath()+ "/" +searchBidList.get(position).getImages())
                .error(R.drawable.icon_image)
                .placeholder(R.drawable.icon_image)
                .into(holder.project_image);
        Log.d("tag","image "+searchBidList.get(position).getImagePath()+ "/" +searchBidList.get(position).getImages());

        holder.title.setText("Title : " +searchBidList.get(position).getTitle());
        holder.budget.setText("Budget : " +searchBidList.get(position).getMinBujet()+ "-" +searchBidList.get(position).getMaxBujet());
        holder.location.setText("Location : " +searchBidList.get(position).getLocationName());
        holder.desc.setText("Details : " +searchBidList.get(position).getDescription());
        holder.date.setText("Date : " +searchBidList.get(position).getCreatedAt());
        holder.readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CheckBidDetails.class);
                intent.putExtra("post_id",searchBidList.get(position).getId().toString());
                context.startActivity(intent);
            }
        });

        if (searchBidList.get(position).getIsBid()==0){

            holder.place_bid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = holder.itemView;
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    ViewGroup viewGroup =v.findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(context).inflate(R.layout.submit_bid, viewGroup, false);

                    Button submit = dialogView.findViewById(R.id.submit);
                    bid_amount = dialogView.findViewById(R.id.amount);
                    time = dialogView.findViewById(R.id.time);
                    description = dialogView.findViewById(R.id.description);
                    choose_image = dialogView.findViewById(R.id.choose_image);
                    choosen_img = dialogView.findViewById(R.id.choosen_image);


                    builder.setView(dialogView);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                    alertDialog.getWindow().setGravity(Gravity.CENTER);
                    alertDialog.show();

                    choosen_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            ((SearchBid) context).selectImage();
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
//                    return;
                            } else {
                                ((SearchBid) context).launchGalleryIntent();

                            }
                        }
                    });

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            checkValidation();

                        }
                    });
                }

                private void checkValidation() {
                    if (bid_amount.getText().toString().isEmpty()) {
                        bid_amount.requestFocus();
                        bid_amount.setError("Please enter amount");
                        return;

                    } else if (time.getText().toString().trim().isEmpty()) {

                        time.requestFocus();
                        time.setError("Please enter estimated time");
                        return;

                    } else if (description.getText().toString().isEmpty()) {

                        description.requestFocus();
                        description.setError("Please enter description");
                        return;

                    }
                    else {
                        SubmitBid();
                    }
                }


                private void SubmitBid() {

                    JsonObject jsonObj = new JsonObject();
                    JsonArray roleArray = new JsonArray();

                    jsonObj.addProperty("vendor_id",vendor_id);
                    jsonObj.addProperty("post_id",searchBidList.get(position).getId().toString());
                    jsonObj.addProperty("bid_amount",bid_amount.getText().toString());
                    jsonObj.addProperty("estimate_time",time.getText().toString());
                    jsonObj.addProperty("description",description.getText().toString());
                    JsonArray jsonArray2 = new Gson().toJsonTree(BidSubmission.img).getAsJsonArray();
                    jsonObj.add("image", jsonArray2);

                    Log.d("tag","json "+jsonObj);

                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();

                    Call<SavePlaceBidResponse> call = Api_Client
                            .getInstance()
                            .saveBid(jsonObj);
                    call.enqueue(new Callback<SavePlaceBidResponse>() {
                        @Override
                        public void onResponse(Call<SavePlaceBidResponse> call, Response<SavePlaceBidResponse> response) {

                            progressDialog.dismiss();
                            if (response.body().getCode()==200){
                                Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("tag","Sucess "+response.body().getMessage());
                            }else if (response.body().getMessage().equals("validation error")){

                                Toast.makeText(context, "You Can't Bid On Your Project", Toast.LENGTH_SHORT).show();

                            }else {
                                Log.d("tag","fail "+response.body().getMessage());
                            }

                        }

                        @Override
                        public void onFailure(Call<SavePlaceBidResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.d("tag","failuer "+t.getLocalizedMessage());

                        }
                    });

//                    ProgressDialog progressDialog = new ProgressDialog(context);
//                    progressDialog.setCancelable(false);
//                    progressDialog.setMessage("Please wait...");
//                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                    progressDialog.show();
//
//                    Call<SavePlaceBidResponse> call = Api_Client
//                            .getInstance()
//                            .saveBid(vendor_id,searchBidList.get(position).getId().toString(),bid_amount.getText().toString(),time.getText().toString(),description.getText().toString(),"data:image/jpeg;base64,"+SearchBid.user_dp);
//                    call.enqueue(new Callback<SavePlaceBidResponse>() {
//                        @Override
//                        public void onResponse(Call<SavePlaceBidResponse> call, Response<SavePlaceBidResponse> response) {
//                            progressDialog.dismiss();
//                            if (response.body().getCode().equals(200)){
//                                Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                Log.d("tag","Sucess "+response.body().getMessage());
//                            }else {
//
//                                Log.d("tag","fail "+response.body().getMessage());
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<SavePlaceBidResponse> call, Throwable t) {
//                            progressDialog.dismiss();
//                            Log.d("tag","failuer "+t.getLocalizedMessage());
//                        }
//                    });
                }
            });
        }

        if (searchBidList.get(position).getIsBid()==1){

            holder.place_bid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You already submitted bid", Toast.LENGTH_SHORT).show();
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return searchBidList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView project_image;
        TextView title, budget,location,desc,readmore,date;
        Button place_bid;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            project_image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            budget = itemView.findViewById(R.id.budgettext);
            location = itemView.findViewById(R.id.location);
            desc = itemView.findViewById(R.id.desc);
            place_bid = itemView.findViewById(R.id.placebid);
            readmore = itemView.findViewById(R.id.readmore);
            date = itemView.findViewById(R.id.date);
            sharedPrefManager=new SharedPrefManager(context);
            vendor_id = sharedPrefManager.getUserId();

        }
    }
}
