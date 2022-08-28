package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.ModelResponse.ProductLeadByPromoterResponse;

import java.util.List;

public class ProductAddedLeadAdapter extends RecyclerView.Adapter<ProductAddedLeadAdapter.MyViewholder> {

    Context context;
    List<ProductLeadByPromoterResponse> productAddedLeadList;

    public ProductAddedLeadAdapter(Context context, List<ProductLeadByPromoterResponse> productAddedLeadList) {
        this.context = context;
        this.productAddedLeadList = productAddedLeadList;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promoter_added_list, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        holder.name.setText(productAddedLeadList.get(position).getName());
        holder.email.setText(productAddedLeadList.get(position).getEmail());
        holder.phone.setText(productAddedLeadList.get(position).getPhone());
        holder.query.setText(productAddedLeadList.get(position).getQuery());
        holder.location.setText(productAddedLeadList.get(position).getLocation());
        holder.purchase.setText(productAddedLeadList.get(position).getPurchaseCount().toString());
        holder.date.setText(productAddedLeadList.get(position).getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return productAddedLeadList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView name,email,phone,query,location,purchase,date;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            query = itemView.findViewById(R.id.query);
            location = itemView.findViewById(R.id.location);
            purchase = itemView.findViewById(R.id.purchaseCount);
            date = itemView.findViewById(R.id.date);
        }
    }
}
