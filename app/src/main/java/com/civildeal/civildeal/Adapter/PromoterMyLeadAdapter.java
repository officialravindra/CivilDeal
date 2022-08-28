package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.ModelResponse.PromoterMyLeadResponse;
import com.civildeal.civildeal.api.ModelResponse.PromoterMyLeadResultResponse;

import java.util.List;

public class PromoterMyLeadAdapter extends RecyclerView.Adapter<PromoterMyLeadAdapter.MyViewHolder> {

    Context context;
    List<PromoterMyLeadResponse> promoterMyLeadList;

    public PromoterMyLeadAdapter(Context context, List<PromoterMyLeadResponse> promoterMyLeadList) {
        this.context = context;
        this.promoterMyLeadList = promoterMyLeadList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promoter_my_lead_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (promoterMyLeadList.get(position).getLeadtype().equals("service")){
            holder.name.setText("Service Name : "+ promoterMyLeadList.get(position).getServiceRequestName());

        }
        if (promoterMyLeadList.get(position).getLeadtype().equals("product")){
            holder.name.setText("Product Name : "+ promoterMyLeadList.get(position).getServiceRequestName());
        }
        if (promoterMyLeadList.get(position).getLeadtype().equals("maintenance")){
            holder.name.setText("Maintenance Name : "+ promoterMyLeadList.get(position).getServiceRequestName());
        }

        holder.price.setText("Price : "+ promoterMyLeadList.get(position).getLeadprice().toString());
        holder.location.setText("Location : "+ promoterMyLeadList.get(position).getLocation());
        holder.date.setText(promoterMyLeadList.get(position).getCreatedAt());
        holder.desc.setText("Description : "+promoterMyLeadList.get(position).getQuery());

        try{
            if (promoterMyLeadList.get(position).getStatus()==1){
                holder.status.setText("Status : Active");
            }else {
                holder.status.setText("Status : Inactive");
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d("tag","exp_fail "+position+e.getMessage());
        }



    }

    @Override
    public int getItemCount() {
        return promoterMyLeadList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,location,date,desc,bypromoter,status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            desc = itemView.findViewById(R.id.desc);
            bypromoter = itemView.findViewById(R.id.bypromoter);
            status = itemView.findViewById(R.id.status);
        }
    }
}
