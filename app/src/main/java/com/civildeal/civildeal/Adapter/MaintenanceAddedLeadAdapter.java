package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceLeadByPromoterResponse;

import java.util.List;

public class MaintenanceAddedLeadAdapter extends RecyclerView.Adapter<MaintenanceAddedLeadAdapter.MyViewHolder> {

    Context context;
    List<MaintenanceLeadByPromoterResponse> maintenanceAddedLeadList;

    public MaintenanceAddedLeadAdapter(Context context, List<MaintenanceLeadByPromoterResponse> maintenanceAddedLeadList) {
        this.context = context;
        this.maintenanceAddedLeadList = maintenanceAddedLeadList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promoter_added_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(maintenanceAddedLeadList.get(position).getName());
        holder.email.setText(maintenanceAddedLeadList.get(position).getEmail());
        holder.phone.setText(maintenanceAddedLeadList.get(position).getPhone());
        holder.query.setText(maintenanceAddedLeadList.get(position).getQuery());
        holder.location.setText(maintenanceAddedLeadList.get(position).getLocation());
        holder.purchase.setText(maintenanceAddedLeadList.get(position).getPurchaseCount().toString());
        holder.date.setText(maintenanceAddedLeadList.get(position).getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return maintenanceAddedLeadList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,email,phone,query,location,purchase,date;

        public MyViewHolder(@NonNull View itemView) {
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
