package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.ModelResponse.DirectLeadResponse;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class DirectLeadAdapter extends RecyclerView.Adapter<DirectLeadAdapter.MyViewHolder> {

    Context context;
    List<DirectLeadResponse> directLeadList;
    String date,vendor_type;
    SharedPrefManager sharedPrefManager;

    public DirectLeadAdapter(Context context, List<DirectLeadResponse> directLeadList) {
        this.context = context;
        this.directLeadList = directLeadList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.direct_lead_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get()
                .load("https://civildeal.com/public/assets/uploadsservice/" + directLeadList.get(position).getImage())
                .error(R.drawable.icon_image)
                .placeholder(R.drawable.icon_image)
                .into(holder.imageView);
        if (vendor_type.equals("service")){
            holder.serviceName.setText("Service Name : " +directLeadList.get(position).getServicename());
        }else if (vendor_type.equals("product")){

            holder.serviceName.setText("Product Name : " +directLeadList.get(position).getServicename());
        }else if (vendor_type.equals("maintenance")){
            holder.serviceName.setText("Maintenance Name : " +directLeadList.get(position).getServicename());
        }

        holder.name.setText("Name : " +directLeadList.get(position).getName());
        holder.email.setText("Email : " +directLeadList.get(position).getEmail());
        holder.query.setText("Query : " +directLeadList.get(position).getQuery());
        holder.location.setText("Location : " +directLeadList.get(position).getLocationName());
        holder.mobile.setText(directLeadList.get(position).getPhone());
        Log.e("tag","servicename "+directLeadList.get(position).getServicename());

        date = directLeadList.get(position).getCreatedAt();

        String oldFormat= "yyyy-MM-dd HH:mm:ss";
        String newFormat= "MMM-dd-yyyy HH:mm:ss";

        String formatedDate = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
        formatedDate = timeFormat.format(myDate);
        Log.e("tag","new "+formatedDate);

        StringTokenizer tk = new StringTokenizer(formatedDate);
        String startDate = tk.nextToken();
        String startTime = tk.nextToken();

        holder.date.setText(startDate);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + directLeadList.get(position).getPhone());
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
        return directLeadList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView serviceName,name,email,query,location,mobile,date;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.serviceImage);
            serviceName = itemView.findViewById(R.id.servicename);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            query = itemView.findViewById(R.id.query);
            location = itemView.findViewById(R.id.location);
            mobile = itemView.findViewById(R.id.mobile);
            date = itemView.findViewById(R.id.date);
            cardView = itemView.findViewById(R.id.mobilecard);
            sharedPrefManager=new SharedPrefManager(context);
            vendor_type = sharedPrefManager.getVendorType();
            Log.e("tag","vendor_type"+vendor_type);
        }
    }
}
