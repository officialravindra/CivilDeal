package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.Activity.CheckBid;
import com.civildeal.civildeal.Activity.CheckBidDetails;
import com.civildeal.civildeal.Activity.CheckInterestedVendors;
import com.civildeal.civildeal.Activity.SearchBid;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.CheckBidResponse;
import com.civildeal.civildeal.api.ModelResponse.ProjectExpireResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckBidAdapter extends RecyclerView.Adapter<CheckBidAdapter.MyViewHolder> {

    Context context;
    List<CheckBidResponse> projectList;

    public CheckBidAdapter(Context context, List<CheckBidResponse> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public CheckBidAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_project_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckBidAdapter.MyViewHolder holder, int position) {

        Picasso.get()
                .load(projectList.get(position).getImagePath()+ "/" +projectList.get(position).getImages())
                .error(R.drawable.icon_image)
                .placeholder(R.drawable.icon_image)
                .into(holder.project_image);

        Log.d("tag","image "+projectList.get(position).getImagePath()+ "/" +projectList.get(position).getImages());

        holder.title.setText("Title : " +projectList.get(position).getTitle());
        holder.budget.setText("Budget Range : " +projectList.get(position).getMinBujet() + "-" + projectList.get(position).getMaxBujet());
//        if (projectList.get(position).getLocationName().equals(null)){
//            holder.location.setText(projectList.get(position).getOtherLocation().toString());
//            Log.e("tag","other");
//        }else {
//            holder.location.setText("Location : " +projectList.get(position).getLocationName());
//            Log.e("tag","location");
//        }
        holder.location.setText("Location : " +projectList.get(position).getLocationName());

        holder.short_desc.setText("Details : " +projectList.get(position).getDescription());
        holder.date.setText("Date : " +projectList.get(position).getCreatedAt());

        holder.interested_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CheckInterestedVendors.class);
                intent.putExtra("post_id",projectList.get(position).getId().toString());
                context.startActivity(intent);
            }
        });

        holder.read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CheckBidDetails.class);
                intent.putExtra("post_id",projectList.get(position).getId().toString());
                context.startActivity(intent);
            }
        });

        holder.close_requirment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deletePost();
            }

            private void deletePost() {

                Log.d("tag","id "+projectList.get(position).getId().toString());

                Call<ProjectExpireResponse> call = Api_Client
                        .getInstance()
                        .expirePost(projectList.get(position).getId().toString());
                call.enqueue(new Callback<ProjectExpireResponse>() {
                    @Override
                    public void onResponse(Call<ProjectExpireResponse> call, Response<ProjectExpireResponse> response) {

                        if (response.body().getCode().equals(200)){

                            Toast.makeText(context, "Requirment Closed", Toast.LENGTH_SHORT).show();
                            ((CheckBid) context).getMyPost();
                        }

                    }

                    @Override
                    public void onFailure(Call<ProjectExpireResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView project_image;
        TextView title,budget,location,short_desc,read_more,date;
        Button interested_vendor,close_requirment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            project_image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            budget = itemView.findViewById(R.id.budgettext);
            location = itemView.findViewById(R.id.location);
            short_desc = itemView.findViewById(R.id.desc);
            interested_vendor = itemView.findViewById(R.id.placebid);
            read_more = itemView.findViewById(R.id.readmore);
            close_requirment = itemView.findViewById(R.id.close);
            date = itemView.findViewById(R.id.date);
        }
    }
}
