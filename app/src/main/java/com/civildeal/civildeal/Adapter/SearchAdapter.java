package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.Activity.VendorDetails;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.ModelResponse.SearchResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    Context context;
    List<SearchResponse> vendorList;
    String vendor_id;

    public SearchAdapter(Context context, List<SearchResponse> vendorList) {
        this.context = context;
        this.vendorList = vendorList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get()
                .load("https://civildeal.com/public/assets/uploads/vendor/"+vendorList.get(position).getUserimage())
                .error(R.drawable.icon_image)
                .placeholder(R.drawable.icon_image)
                .into(holder.image);
        holder.serviceName.setText(vendorList.get(position).getCompanyName());
        holder.name.setText(vendorList.get(position).getServiceProuductName());
        holder.desc.setText("Description : "+vendorList.get(position).getDescription());
        holder.experience.setText(vendorList.get(position).getExperience());
        holder.location.setText(vendorList.get(position).getLocationName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent(context, VendorDetails.class);
                intent.putExtra("",vendorList.get(position).getId());
//
                context.startActivity(intent);*/

                Intent intent = new Intent(context, VendorDetails.class);
                intent.putExtra("vendor_id",vendorList.get(position).getId().toString());
//                Toast.makeText(context, "vendor_id"+vendorList.get(position).getId(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return vendorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,desc,experience,serviceName,location;
        ImageView image;
        CardView cardView;
        Button view;

        public MyViewHolder( View itemView) {
            super(itemView);

            serviceName=itemView.findViewById(R.id.serviceProductName);
            name=itemView.findViewById(R.id.vendorname);
            desc=itemView.findViewById(R.id.Desc);
            experience=itemView.findViewById(R.id.experience);
            image=itemView.findViewById(R.id.vendorimage);
            cardView=itemView.findViewById(R.id.fCardview);
            view=itemView.findViewById(R.id.view);
            location=itemView.findViewById(R.id.location);
        }
    }
}
