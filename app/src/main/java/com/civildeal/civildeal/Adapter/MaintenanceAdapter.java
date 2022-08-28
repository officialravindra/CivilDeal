package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.Activity.Maintenance;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MaintenanceAdapter extends RecyclerView.Adapter<MaintenanceAdapter.MyViewHolder> {

    private Context context;
    private List<MaintenanceResponse> maintenanceList;

    public MaintenanceAdapter(Context context, List<MaintenanceResponse> maintenanceList) {
        this.context = context;
        this.maintenanceList = maintenanceList;
    }

    @NonNull
    @Override
    public MaintenanceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaintenanceAdapter.MyViewHolder holder, int position) {

        MaintenanceResponse maintenanceResponse = maintenanceList.get(position);
        Picasso.get()
                .load("https://civildeal.com/public/assets/uploadsservice/"+maintenanceList.get(position).getBannerImage())
                .error(R.drawable.error_zip)
                .placeholder(R.drawable.ic_baseline_photo_24)
                .into(holder.maintenanceImage);


        holder.maintenanceName.setText(maintenanceList.get(position).getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Maintenance.class);
                intent.putExtra("maintenance_id",maintenanceList.get(position).getId().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return maintenanceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView maintenanceImage;
        TextView maintenanceName;
        CardView cardView;
        public MyViewHolder( View itemView) {
            super(itemView);

            maintenanceImage = itemView.findViewById(R.id.serviceImage);
            maintenanceName = itemView.findViewById(R.id.servicename);
            cardView = itemView.findViewById(R.id.fCard);
        }
    }
}
