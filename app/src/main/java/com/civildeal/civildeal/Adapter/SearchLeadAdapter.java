package com.civildeal.civildeal.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.Activity.PaymentActivity;
import com.civildeal.civildeal.Activity.SearchLead;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.AddToCartResponse;
import com.civildeal.civildeal.api.ModelResponse.SearchLeadResponse;
import com.civildeal.civildeal.api.ModelResponse.VendorCheckoutResponse;
import com.civildeal.civildeal.api.ModelResponse.WalletResponse;
import com.civildeal.civildeal.api.ModelResponse.WalletResultResponse;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchLeadAdapter extends RecyclerView.Adapter<SearchLeadAdapter.MyViewHolder>  {

    Context context;
    private List<SearchLeadResponse> serviceList;
    SharedPrefManager sharedPrefManager;
    SearchLead searchLead;
    String vendor_id;
    String type,email,mobile,vendor_type,name,vendor_type_id,lead_id;
    String price,single_lead,date;
    int walletAmount;
    SearchLeadAdapter searchLeadAdapter;
    ProgressDialog progressDialog;

    public SearchLeadAdapter(Context context, List<SearchLeadResponse> serviceList, String type, SearchLead searchLead) {
        this.context = context;
        this.serviceList = serviceList;
        this.type = type;
        this.searchLead = searchLead;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coloum_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (vendor_type.equals("service")){
            Picasso.get()
                    .load("https://civildeal.com/public/assets/uploadsservice/"+serviceList.get(position).getServiceImage())
                    .error(R.drawable.icon_image)
                    .placeholder(R.drawable.icon_image)
                    .into(holder.serviceImage);
        }else if (vendor_type.equals("product")){
            Log.e("tag",""+serviceList.get(position).getServiceImage());
            Picasso.get()
                    .load("https://civildeal.com/public/assets/uploads/product/"+serviceList.get(position).getServiceImage())
                    .error(R.drawable.icon_image)
                    .placeholder(R.drawable.icon_image)
                    .into(holder.serviceImage);
        }else if (vendor_type.equals("maintenance")){

            Picasso.get()
                    .load("https://civildeal.com/public/assets/uploadsservice/"+serviceList.get(position).getServiceImage())
                    .error(R.drawable.icon_image)
                    .placeholder(R.drawable.icon_image)
                    .into(holder.serviceImage);
        }

        holder.serviceName.setText("Need : " +serviceList.get(position).getQuery().trim());
        holder.Desc.setText("Service : " +serviceList.get(position).getServicename());
        holder.location.setText("Location : " +serviceList.get(position).getLocation());
        holder.price.setText("price : ₹" + serviceList.get(position).getLeadprice().toString());
        price = serviceList.get(position).getLeadprice().toString();
        date = serviceList.get(position).getCreatedAt();
        if (serviceList.get(position).getPromoterId()!=0){
            holder.promoter.setVisibility(View.VISIBLE);
            holder.promoter.setText("By Promoter");
        }else {
            holder.promoter.setVisibility(View.GONE);
        }

        getWalletamount();

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
//        Log.e("tag","new "+formatedDate);

        StringTokenizer tk = new StringTokenizer(formatedDate);
        String startDate = tk.nextToken();
        String startTime = tk.nextToken();


        holder.date.setText("Lead Date : " +startDate);


        if (serviceList.get(position).getIsAdded()==0){

            holder.add.setVisibility(View.VISIBLE);
            holder.buy.setVisibility(View.VISIBLE);
            holder.added.setVisibility(View.GONE);
//            Log.e("tag"," add0 "+serviceList.get(position).getIsAdded());


        }
        if (serviceList.get(position).getIsPurchased()==0)
        {
            holder.buy.setVisibility(View.VISIBLE);
            holder.add.setVisibility(View.VISIBLE);
            holder.purchased.setVisibility(View.GONE);
//            Log.e("tag"," purchased0 "+serviceList.get(position).getIsPurchased());


        }
        if (serviceList.get(position).getIsAdded()==1){

            holder.add.setVisibility(View.GONE);
            holder.added.setVisibility(View.VISIBLE);
            holder.buy.setVisibility(View.GONE);
            holder.purchased.setVisibility(View.GONE);
//            Log.e("added"," added "+serviceList.get(position).getIsPurchased());


        }
        if (serviceList.get(position).getIsPurchased()==1){

            holder.buy.setVisibility(View.GONE);
            holder.purchased.setVisibility(View.VISIBLE);
            holder.add.setVisibility(View.GONE);
            holder.added.setVisibility(View.GONE);
//            Log.e("tag"," purchased "+serviceList.get(position).getIsPurchased());
        }



        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<AddToCartResponse> call1 = Api_Client
                        .getInstance()
                        .addCart(vendor_type,vendor_id,serviceList.get(position).getId().toString());
                call1.enqueue(new Callback<AddToCartResponse>() {
                    @Override
                    public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {

                        if (!response.isSuccessful()){

                            Toast.makeText(context, "Response Fail"+response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }else if(response.body().getCode().equals(200)) {

//                            holder.add.setVisibility(View.GONE);
//                            holder.added.setVisibility(View.VISIBLE);
                            searchLead.getSearchLead();





                            Toast.makeText(context, "Item added to cart successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                        Log.e("failure",t.getLocalizedMessage());

                    }
                });
            }
        });

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (walletAmount<serviceList.get(position).getLeadprice()){
                    Log.e("tag","payment_gateway");
                    Intent intent = new Intent(context, PaymentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("price", serviceList.get(position).getLeadprice().toString());
                    intent.putExtra("lead_id", serviceList.get(position).getId().toString());
                    intent.putExtra("checkout_type", "single_lead");
                    Log.e("tag","price1 "+serviceList.get(position).getLeadprice());


                    context.startActivity(intent);

                }else if (walletAmount>=serviceList.get(position).getLeadprice()){

//                    progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please wait while we are checking your wallet balance...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    Call<VendorCheckoutResponse> call = Api_Client
                            .getInstance()
                            .walletPurchase(vendor_id,vendor_type,vendor_type_id,name,mobile,email,"single_lead", serviceList.get(position).getId().toString());
                    call.enqueue(new Callback<VendorCheckoutResponse>() {
                        @Override
                        public void onResponse(Call<VendorCheckoutResponse> call, Response<VendorCheckoutResponse> response) {
                        progressDialog.dismiss();
                            if (response.body().getCode().equals(200)) {

                                Toast.makeText(context, "We have successfully received the payment from your wallet", Toast.LENGTH_SHORT).show();
//                            context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                searchLead.getSearchLead();
                                Log.e("tag","paid "+response.body().getMessage());

                            }else {

                                Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("tag","fail "+response.body().getMessage());

                            }
                        }

                        @Override
                        public void onFailure(Call<VendorCheckoutResponse> call, Throwable t) {
                        progressDialog.dismiss();
                            Log.e("tag",""+t.getLocalizedMessage());

                        }
                    });

//                    walletCheckout();

                }

            }

            private void walletCheckout() {

            }


        });


    }


    private void getWalletamount() {
        Call<WalletResultResponse> call = Api_Client
                .getInstance()
                .getWallet(vendor_id);
        call.enqueue(new Callback<WalletResultResponse>() {
            @Override
            public void onResponse(Call<WalletResultResponse> call, Response<WalletResultResponse> response) {

                if (response.body().getCode().equals(200)){

                    WalletResultResponse walletResultResponse = response.body();
                    List<WalletResponse> walletList = Collections.singletonList(walletResultResponse.getData());

                    walletAmount = Integer.parseInt(walletList.get(0).getWalletAmount());
                    Log.e("tag","amount "+walletList.get(0).getWalletAmount());
                    Log.e("tag","wallet "+response.body().getMessage());

                }else if (response.body().getCode().equals(400)){


                    Log.e("tag","failwallet "+response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<WalletResultResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });
    }


    @Override
    public int getItemCount() {
        return serviceList.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView serviceImage;
        TextView serviceName,Desc,location,price,date,promoter;
        Button buy,purchased,add,added;

        public MyViewHolder( View itemView) {
            super(itemView);

            serviceImage = itemView.findViewById(R.id.serviceImage);
            serviceName = itemView.findViewById(R.id.servicename);
            Desc = itemView.findViewById(R.id.desc);
            purchased = itemView.findViewById(R.id.purchased);
            date = itemView.findViewById(R.id.date);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            buy = itemView.findViewById(R.id.buy);
            add = itemView.findViewById(R.id.addtocart);
            added = itemView.findViewById(R.id.added);
            promoter = itemView.findViewById(R.id.promoter);

            sharedPrefManager=new SharedPrefManager(context);
            vendor_id = String.valueOf(sharedPrefManager.getUserId());
            mobile = sharedPrefManager.getUserMobile();
            email = sharedPrefManager.getEmail();
            vendor_type = sharedPrefManager.getVendorType();
            vendor_type_id = sharedPrefManager.getUserType();
            name = sharedPrefManager.getUserName();

        }
    }
}
