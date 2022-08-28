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
import com.civildeal.civildeal.Activity.ProjectPost;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.ProjectPostListResponse;
import com.civildeal.civildeal.api.ModelResponse.ProjectPostListResultResponse;
import com.civildeal.civildeal.api.ModelResponse.SavePlaceBidResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectPostListAdapter extends RecyclerView.Adapter<ProjectPostListAdapter.MyViewHolder> {

    Context context;
    List<ProjectPostListResponse> projectPostList;
    String vendor_id,post_id;
    SharedPrefManager sharedPrefManager;
    EditText bid_amount;
    EditText time;
    ImageView choosen_img;
    TextView count;
    EditText description,choose_image;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> imag = new ArrayList<>();
//    String[] images;
    String post_image;
    String img;
    final int REQUEST_EXTERNAL_STORAGE = 100;



    public ProjectPostListAdapter(Context context, List<ProjectPostListResponse> projectPostList) {
        this.context = context;
        this.projectPostList = projectPostList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_post_lists, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        images.add(projectPostList.get(position).getImages().toString());
        Log.d("tag","aaa "+projectPostList.get(0).getImages().toString());
//        Log.d("tag","img "+images);
        post_image = images.get(0);
        Log.d("tag","post "+post_image);
        img = String.valueOf(post_image);
        Log.d("tag","img "+img);


//        Gson gson = new Gson();
//        String data= projectPostList.get(position).getImages().toString();
//        JsonParser jsonParser = new JsonParser();
//        JsonArray jsonArray = (JsonArray) jsonParser.parse(data);
//        Log.d("tag","json "+jsonArray);


//        String[] separated = projectPostList.get(position).getImages().toString().split(",");
//        Log.d("tag","sep "+separated);
//
//        for (int i =0; i<separated.length;i++){
//
//            Log.d("tag","img "+separated[0]);
//        }
//
//
//        separated[0] = separated[1].trim();
//        Log.d("tag", "array "+separated[0]);

            Picasso.get()
                    .load(projectPostList.get(position).getImagePath()+ "/" +projectPostList.get(position).getImages())
                    .error(R.drawable.icon_image)
                    .placeholder(R.drawable.icon_image)
                    .into(holder.project_image);

            Log.d("tag","bid "+projectPostList.get(position).getImagePath()+ "/" +projectPostList.get(position).getImages());


        holder.title.setText("Title : " +projectPostList.get(position).getTitle());
        holder.budget.setText("Budget : " +projectPostList.get(position).getMinBujet()+ "-" +projectPostList.get(position).getMaxBujet());
        holder.location.setText("Location : " +projectPostList.get(position).getLocationName());
        holder.desc.setText("Details : " +projectPostList.get(position).getDescription());
        holder.date.setText("Date : " +projectPostList.get(position).getCreatedAt());

        holder.readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CheckBidDetails.class);
                intent.putExtra("post_id",projectPostList.get(position).getId().toString());
                context.startActivity(intent);
            }
        });

        if(projectPostList.get(position).getIsBid()==0){
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
                    count = dialogView.findViewById(R.id.count);
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


                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
//                    return;
                            } else {
                                ((BidSubmission) context).launchGalleryIntent();

                            }
                        }
                    });

//                    if (BidSubmission.no_of_pic!=null){
//                        count.setText(BidSubmission.no_of_pic);
//                    }else {
//                        Log.d("tag","no_pic");
//                    }


                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            checkValidation();

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
                            Log.d("tag","vendor_id "+vendor_id);
                            Log.d("tag","post_id "+projectPostList.get(position).getId().toString());
                            Log.d("tag","data:image/jpeg;base64,"+BidSubmission.user_dp);

                            JsonObject jsonObj = new JsonObject();
                            JsonArray roleArray = new JsonArray();

                            jsonObj.addProperty("vendor_id",vendor_id);
                            jsonObj.addProperty("post_id",projectPostList.get(position).getId().toString());
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
                                        if (context instanceof BidSubmission) {
                                            ((BidSubmission)context).getProjectList("");
                                        }
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

//                            Call<SavePlaceBidResponse> call = Api_Client
//                                    .getInstance()
//                                    .saveBid(vendor_id,projectPostList.get(position).getId().toString(),bid_amount.getText().toString(),time.getText().toString(),description.getText().toString(),"data:image/jpeg;base64,"+BidSubmission.user_dp);
//                            call.enqueue(new Callback<SavePlaceBidResponse>() {
//                                @Override
//                                public void onResponse(Call<SavePlaceBidResponse> call, Response<SavePlaceBidResponse> response) {
//                                    progressDialog.dismiss();
//                                    if (response.body().getCode().equals(200)){
//                                        Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                        Log.d("tag","Sucess "+response.body().getMessage());
//                                    }else if (response.body().getMessage().equals("validation error")){
//
//                                        Toast.makeText(context, "You Can't Bid On Your Project", Toast.LENGTH_SHORT).show();
//
//                                    }else {
//                                        Log.d("tag","fail "+response.body().getMessage());
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<SavePlaceBidResponse> call, Throwable t) {
//                                    progressDialog.dismiss();
//                                    Log.d("tag","failuer "+t.getLocalizedMessage());
//                                }
//                            });
                        }
                    });
                }
            });
        }

        if (projectPostList.get(position).getIsBid()==1){

            holder.place_bid.setVisibility(View.GONE);
            holder.purchased.setVisibility(View.VISIBLE);

            Log.d("tag","isBid "+projectPostList.get(position).getIsBid());
//            holder.place_bid.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Toast.makeText(context, "You already submitted bid", Toast.LENGTH_SHORT).show();
//
//                }
//
//            });
        }else {

            Log.d("tag","isBid0 "+projectPostList.get(position).getIsBid());
            holder.place_bid.setVisibility(View.VISIBLE);
            holder.purchased.setVisibility(View.GONE);
        }


    }



    @Override
    public int getItemCount() {
        return projectPostList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView project_image;
        TextView title, budget,location,desc,readmore,date;
        Button place_bid,purchased;
        public MyViewHolder( View itemView) {
            super(itemView);

            project_image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            budget = itemView.findViewById(R.id.budgettext);
            location = itemView.findViewById(R.id.location);
            desc = itemView.findViewById(R.id.desc);
            place_bid = itemView.findViewById(R.id.placebid);
            readmore = itemView.findViewById(R.id.readmore);
            purchased = itemView.findViewById(R.id.purchased);
            date = itemView.findViewById(R.id.date);

            sharedPrefManager=new SharedPrefManager(context);
            vendor_id = sharedPrefManager.getUserId();
        }
    }
}
