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
import com.civildeal.civildeal.api.ModelResponse.ProfileResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductVendorListAdapter extends RecyclerView.Adapter<ProductVendorListAdapter.MyViewHolder> {

    List<ProfileResponse> vendorList;
    Context context;
    Context applicationContext;

    public ProductVendorListAdapter(List<ProfileResponse> vendorList, Context context) {
        this.vendorList = vendorList;
        this.context = context;
    }

    public ProductVendorListAdapter(Context applicationContext, List<ProfileResponse> vendorList) {
        this.applicationContext = applicationContext;
        this.vendorList = vendorList;
    }

    @NonNull
    @Override
    public ProductVendorListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_vendor_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVendorListAdapter.MyViewHolder holder, int position) {

        Picasso.get()
                .load("https://civildeal.com/public/assets/uploads/vendor/"+vendorList.get(position).getUserimage())
                .error(R.drawable.icon_image)
                .placeholder(R.drawable.icon_image)
                .into(holder.image);
        holder.productName.setText(vendorList.get(position).getCompanyName());
        holder.name.setText(vendorList.get(position).getServiceProuductName());
//        holder.desc.setText(vendorList.get(position).getVendorType());
        holder.experience.setText(vendorList.get(position).getExperience());
        holder.location.setText(vendorList.get(position).getLocationName());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(applicationContext, VendorDetails.class);
                intent.putExtra("vendor_id",vendorList.get(position).getId().toString());
                intent.putExtra("service_id",vendorList.get(position).getServiceId());
                intent.putExtra("product_id",vendorList.get(position).getProductId());
                intent.putExtra("type",vendorList.get(position).getType());
                applicationContext.startActivity(intent);
            }
        });

//        holder.contactUs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(applicationContext, ProductDetails.class);
//                intent.putExtra("vendor_id",vendorList.get(position).getId().toString());
//                intent.putExtra("service_id",vendorList.get(position).getServiceId());
//                intent.putExtra("product_id",vendorList.get(position).getProductId());
//                applicationContext.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return vendorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName,name,desc,experience,location;
        ImageView image;
        CardView cardView;
        Button view,contactUs;

        public MyViewHolder( View itemView) {
            super(itemView);

            productName=itemView.findViewById(R.id.serviceProductName);
            name=itemView.findViewById(R.id.vendorname);
            desc=itemView.findViewById(R.id.Desc);
            experience=itemView.findViewById(R.id.experience);
//            address=itemView.findViewById(R.id.location);
            image=itemView.findViewById(R.id.vendorimage);
            cardView=itemView.findViewById(R.id.fCardview);
            view=itemView.findViewById(R.id.view);
            contactUs=itemView.findViewById(R.id.contactus);
            location=itemView.findViewById(R.id.location);
        }
    }
}
