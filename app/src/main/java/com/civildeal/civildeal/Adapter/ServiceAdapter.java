package com.civildeal.civildeal.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.civildeal.civildeal.Activity.Service;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.ModelResponse.ServiceResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResultResponse;
import com.civildeal.civildeal.api.ModelResponse.TestResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private ProgressDialog pDialog;
    private AlertDialog dialog;
    private Context context;
    private List<ServiceResponse> serviceList;


    public ServiceAdapter(Context context, List<ServiceResponse> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


//        Picasso.get()
//                .load(serviceList.get(position).getImageUrl()+serviceList.get(position).getBannerImage())
//                .error(R.drawable.ic_error)
//                .placeholder(R.drawable.ic_error)
//                .into(holder.serviceImage);


        Glide.with(context)
                .load(serviceList.get(position).getImageUrl()+serviceList.get(position).getBannerImage())
//                .placeholder(R.drawable.plumber)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.d("tag","fail "+e.getLocalizedMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .dontAnimate()
                .into(holder.serviceImage);

        Log.d("tag","image "+serviceList.get(position).getImageUrl()+serviceList.get(position).getBannerImage());

        holder.serviceName.setText(serviceList.get(position).getName());

        holder.cardView.setTag(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Service.class);
                intent.putExtra("service_id",serviceList.get(position).getId().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView serviceImage;
        TextView serviceName;

        public MyViewHolder( View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.fCard);
            serviceImage = itemView.findViewById(R.id.serviceImage);
            serviceName = itemView.findViewById(R.id.servicename);


            pDialog = new ProgressDialog(context);
            pDialog.setCancelable(false);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false); // if you want user to wait for some process to finish,
            builder.setView(R.layout.layout_loading_dialog);
            dialog = builder.create();
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
