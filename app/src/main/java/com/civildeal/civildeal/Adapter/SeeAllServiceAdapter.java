package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.Activity.Service;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.ModelResponse.ServiceResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResultResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeeAllServiceAdapter extends RecyclerView.Adapter<SeeAllServiceAdapter.MyViewHolder> {

    private Context context;
    private List<ServiceResponse> serviceList;

    public SeeAllServiceAdapter(Context context, List<ServiceResponse> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.see_all_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Picasso.get()
                .load("https://civildeal.com/public/assets/uploadsservice/"+serviceList.get(position).getBannerImage())
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_error)
                .into(holder.serviceImage);

        holder.serviceName.setText(serviceList.get(position).getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Service.class);
                intent.putExtra("service_id",serviceList.get(position).getId().toString());
//                Toast.makeText(context, "service_id"+serviceList.get(position).getId(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView serviceImage;
        TextView serviceName;
        CardView cardView;

        public MyViewHolder( View itemView) {
            super(itemView);

            serviceImage = itemView.findViewById(R.id.serviceImage);
            serviceName = itemView.findViewById(R.id.servicename);
            cardView = itemView.findViewById(R.id.fCard);
        }
    }
}
