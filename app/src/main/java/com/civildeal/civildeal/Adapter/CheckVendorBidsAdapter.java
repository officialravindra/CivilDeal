package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.Activity.BidderImages;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.ModelResponse.CheckVendorListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CheckVendorBidsAdapter extends RecyclerView.Adapter<CheckVendorBidsAdapter.MyViewHolder> {

    Context context;
    List<CheckVendorListResponse> vendorsList;

    public CheckVendorBidsAdapter(Context context, List<CheckVendorListResponse> vendorsList) {
        this.context = context;
        this.vendorsList = vendorsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_vendor_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText("Name : " +vendorsList.get(position).getName());
        holder.contactn.setText(vendorsList.get(position).getMobile());
        holder.budget.setText("Bid Amount : " +vendorsList.get(position).getBidAmount()+ " ₹");
        holder.time.setText("Estimated Time : " +vendorsList.get(position).getEstimateTime());
        holder.date.setText("Date : " +vendorsList.get(position).getCreatedAt());

        Picasso
                .get()
                .load("https://civildeal.com/public/assets/uploads/vendor/"+vendorsList.get(position).getUserimage())
                .placeholder(R.drawable.icon_image)
                .into(holder.imageView);

        holder.seeImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, BidderImages.class);
                intent.putExtra("post_id",vendorsList.get(position).getPostId().toString());
                intent.putExtra("vendor_id",vendorsList.get(position).getVendorId().toString());
                context.startActivity(intent);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + vendorsList.get(position).getMobile());
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    context.startActivity(i);
                } catch (SecurityException s) {
                    s.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return vendorsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,contactn,budget,time,date;
        ImageView imageView;
        RelativeLayout seeImages;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name =itemView.findViewById(R.id.name);
            contactn =itemView.findViewById(R.id.contactno);
            budget =itemView.findViewById(R.id.bid_amount);
            time =itemView.findViewById(R.id.estimated_time);
            date =itemView.findViewById(R.id.date);
            imageView =itemView.findViewById(R.id.image);
            seeImages =itemView.findViewById(R.id.seeImage);
            cardView = itemView.findViewById(R.id.mobilecard);
        }
    }
}
