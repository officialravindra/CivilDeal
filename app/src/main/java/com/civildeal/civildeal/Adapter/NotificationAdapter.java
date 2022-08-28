package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.Model.NotificationDetailsModel;
import com.civildeal.civildeal.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    Context context;

    private List<NotificationDetailsModel> notificationDetails;

    public NotificationAdapter(Context context, List<NotificationDetailsModel> notificationDetails) {
        this.context = context;
        this.notificationDetails = notificationDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationlist,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.notification_title.setText(notificationDetails.get(position).getTitle());
        holder.notification_details.setText(notificationDetails.get(position).getDesc());

    }

    @Override
    public int getItemCount() {
        return notificationDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView notification_title, notification_details;

        public MyViewHolder( View itemView) {
            super(itemView);

            notification_title = itemView.findViewById(R.id.notification_title);
            notification_details = itemView.findViewById(R.id.notification_details);
        }
    }
}
