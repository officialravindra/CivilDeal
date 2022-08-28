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
import com.civildeal.civildeal.api.ModelResponse.OrderResponse;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    Context context;
    List<OrderResponse> orderList;
    String vendor_type,vendor_id,leadDate,purchasedDate;
    SharedPrefManager sharedPrefManager;
    public static String contact_us_no;

    public HistoryAdapter(Context context, List<OrderResponse> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list,parent,false);
        Log.e("tag","create");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (vendor_type.equals("service")){

            Picasso.get()
                    .load("https://civildeal.com/public/assets/uploadsservice/"+orderList.get(position).getImage())
                    .error(R.drawable.icon_image)
                    .placeholder(R.drawable.icon_image)
                    .into(holder.imageView);

        }else if (vendor_type.equals("product")){

            Picasso.get()
                    .load("https://civildeal.com/public/assets/uploads/product/"+orderList.get(position).getImage())
                    .error(R.drawable.icon_image)
                    .placeholder(R.drawable.icon_image)
                    .into(holder.imageView);
        }else if (vendor_type.equals("maintenance")){

            Picasso.get()
                    .load("https://civildeal.com/public/assets/uploadsservice/"+orderList.get(position).getImage())
                    .error(R.drawable.icon_image)
                    .placeholder(R.drawable.icon_image)
                    .into(holder.imageView);
        }

        purchasedDate = orderList.get(position).getCreatedAt();



        String oldFormat= "yyyy-MM-dd HH:mm:ss";
        String newFormat= "MMM-dd-yyyy HH:mm:ss";

        String formatedDate = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(purchasedDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
        formatedDate = timeFormat.format(myDate);
        Log.e("tag","new "+formatedDate);

        StringTokenizer tk = new StringTokenizer(formatedDate);
        String newleadDate = tk.nextToken();
        String startTime = tk.nextToken();
        Log.e("tag","newleadDate "+newleadDate);

        if (orderList.get(position).getLeadDate()!=null){
            leadDate = orderList.get(position).getLeadDate();
            Log.e("tag","leaddate "+leadDate);
            String oldFormatdate= "yyyy-MM-dd HH:mm:ss";
            String newFormatdate= "MMM-dd-yyyy HH:mm:ss";

            String formateddate= "";
            SimpleDateFormat DateFormat = new SimpleDateFormat(oldFormatdate);
            Date date = null;
            try {
                date = DateFormat.parse(leadDate);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat timeFormatdate = new SimpleDateFormat(newFormatdate);
            formateddate = timeFormatdate.format(date);
            Log.e("tag","new "+formateddate);

            StringTokenizer tK = new StringTokenizer(formateddate);
            String newDate = tK.nextToken();
            String starttime = tK.nextToken();
            Log.e("tag","new "+newDate);
            holder.leaddate.setText("Lead Date : "+newDate.toString());
        }else {
            holder.leaddate.setText("Lead Date : ");
        }





        holder.query.setText(orderList.get(position).getQuery().trim());
        holder.name.setText("Name : " +orderList.get(position).getName());
        holder.location.setText("Location : " +orderList.get(position).getLocation());
        holder.amount.setText("Amount : ₹" +orderList.get(position).getAmount().toString());
        holder.orderId.setText("Order id : " +orderList.get(position).getOrderId());
        if (orderList.get(position).getMobile()!=null){
            holder.no_card.setVisibility(View.VISIBLE);
            holder.mobile.setText(orderList.get(position).getMobile());
        }else {
            holder.no_card.setVisibility(View.GONE);
        }

        holder.purchaseDate.setText("Purchase Date : "+newleadDate);

        if (vendor_type.equals("service")){
            holder.serviceName.setText("Service Name : " +orderList.get(position).getServiceName());
        }else if (vendor_type.equals("product")){
            holder.serviceName.setText("Product Name : " +orderList.get(position).getServiceName());
        }



        contact_us_no = orderList.get(position).getMobile();

        holder.mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri u = Uri.parse("tel:" + orderList.get(position).getMobile());
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    context.startActivity(i);
                } catch (SecurityException s) {
                    s.printStackTrace();
                }
            }
        });



//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, ViewOrder.class);
//                intent.putExtra("vendor_id",vendor_id);
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView,no_card;
        ImageView imageView;
        TextView query,name,serviceName,location,amount,orderId,leaddate,purchaseDate,mobile;

        public MyViewHolder( View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.orderCard);
            imageView = itemView.findViewById(R.id.Image);
            query = itemView.findViewById(R.id.query);
            name = itemView.findViewById(R.id.name);
            serviceName = itemView.findViewById(R.id.service_name);
            location = itemView.findViewById(R.id.location);
            amount = itemView.findViewById(R.id.amount);
            orderId = itemView.findViewById(R.id.orderid);
            mobile = itemView.findViewById(R.id.mobile);
            purchaseDate = itemView.findViewById(R.id.purchaseDate);
            leaddate = itemView.findViewById(R.id.leadDate);
            no_card = itemView.findViewById(R.id.no_card);

            sharedPrefManager=new SharedPrefManager(context);
            vendor_type = String.valueOf(sharedPrefManager.getVendorType());
        }
    }
}
