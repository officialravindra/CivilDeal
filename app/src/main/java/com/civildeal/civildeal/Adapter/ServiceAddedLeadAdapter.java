package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.Activity.UpdateLeadByPromoter;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.ModelResponse.ServiceLeadByPromoterResponse;

import java.util.List;

import retrofit2.Call;

public class ServiceAddedLeadAdapter extends RecyclerView.Adapter<ServiceAddedLeadAdapter.MyViewHolder> {

    Context context;
    List<ServiceLeadByPromoterResponse> serviceAddedLeadList;

    public ServiceAddedLeadAdapter(Context context, List<ServiceLeadByPromoterResponse> serviceAddedLeadList) {
        this.context = context;
        this.serviceAddedLeadList = serviceAddedLeadList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promoter_added_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(serviceAddedLeadList.get(position).getName());
        holder.email.setText(serviceAddedLeadList.get(position).getEmail());
        holder.phone.setText(serviceAddedLeadList.get(position).getPhone());
        holder.query.setText(serviceAddedLeadList.get(position).getQuery());
        holder.location.setText(serviceAddedLeadList.get(position).getLocation());
        holder.purchase.setText(serviceAddedLeadList.get(position).getPurchaseCount().toString());
        holder.date.setText(serviceAddedLeadList.get(position).getCreatedAt());

        holder.updateLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateLeadByPromoter.class);
                intent.putExtra("lead_id",serviceAddedLeadList.get(position).getId().toString());
                context.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return serviceAddedLeadList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,email,phone,query,location,purchase,date;
        ImageView updateLead;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            query = itemView.findViewById(R.id.query);
            location = itemView.findViewById(R.id.location);
            purchase = itemView.findViewById(R.id.purchaseCount);
            date = itemView.findViewById(R.id.date);
            updateLead = itemView.findViewById(R.id.updateLead);
        }
    }
}
