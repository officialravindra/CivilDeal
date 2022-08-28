package com.civildeal.civildeal.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import com.civildeal.civildeal.Fragments.ServiceLeadFragment;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.AddToCartResponse;
import com.civildeal.civildeal.api.ModelResponse.AddToCartResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceLeadResponse;
import com.civildeal.civildeal.api.ModelResponse.VendorCheckoutResponse;
import com.civildeal.civildeal.api.ModelResponse.WalletResponse;
import com.civildeal.civildeal.api.ModelResponse.WalletResultResponse;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ServiceLeadAdapter extends RecyclerView.Adapter<ServiceLeadAdapter.MyViewHolder> implements PaymentResultListener {

    private Context context;
    private List<ServiceLeadResponse> serviceList;
    ServiceLeadFragment serviceLeadFragment;
    ItemClickListener listner;
    static int count;
    SharedPrefManager sharedPrefManager;
    String vendor_id, vendor_type, type,email,mobile,name,vendor_type_id;
    String lead_id;
    String price;
    String date;
    String single_lead;
    int walletAmount;

 /*   public ServiceLeadAdapter(ItemClickListner listner) {
        this.listner = listner;
    }*/

    public ServiceLeadAdapter(Context context, List<ServiceLeadResponse> serviceList, ItemClickListener listener, ServiceLeadFragment serviceLeadFragment) {
        this.context = context;
        this.serviceList = serviceList;
        this.listner = listener;
        this.serviceLeadFragment = serviceLeadFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coloum_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get()
                .load("https://civildeal.com/public/assets/uploadsservice/" + serviceList.get(position).getServiceImage())
                .error(R.drawable.icon_image)
                .placeholder(R.drawable.icon_image)
                .into(holder.serviceImage);
        holder.serviceName.setText("Need : " +serviceList.get(position).getQuery().toString().trim());
        holder.Desc.setText("Service : " +serviceList.get(position).getServicename().trim());
        holder.location.setText("Location : " +serviceList.get(position).getLocation());
//        holder.date.setText("Date " +serviceList.get(position).getUpdatedAt());
        holder.price.setText("price : ₹" + serviceList.get(position).getLeadprice().toString());
        price = String.valueOf(serviceList.get(position).getLeadprice());
        date = serviceList.get(position).getCreatedAt();
        if (serviceList.get(position).getPromoterId()!=0){
            holder.promoter.setVisibility(View.VISIBLE);
            holder.promoter.setText("By Promoter");
        }else {
            holder.promoter.setVisibility(View.GONE);
        }
//        Log.e("tag",""+date);
//        lead_id = serviceList.get(position).getId().toString();
       // getWalletamount();
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

//                Log.e("tag", "add " + serviceList.get(position).getId());
                if (vendor_type.equals("product")) {

                    Toast.makeText(context, "You can't add service and maintenance lead because you are a type of" + vendor_type, Toast.LENGTH_SHORT).show();

                } else {

//                    Toast.makeText(context, "" + serviceList.get(position).getId(), Toast.LENGTH_SHORT).show();
                    Log.e("tag", "add1 " + serviceList.get(position).getId());
                    new Handler().postDelayed(this::addCartItem, 000);


                }

            }

            private void addCartItem() {

                Call<AddToCartResponse> call1 = Api_Client
                        .getInstance()
                        .addCart(vendor_type, vendor_id, serviceList.get(position).getId().toString());
                call1.enqueue(new Callback<AddToCartResponse>() {
                    @Override
                    public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {


                        if (response.body().getCode().equals(200)) {

                            AddToCartResponse addToCartResponse = response.body();
                            List<AddToCartResultResponse> leadList = Collections.singletonList(addToCartResponse.getData());
                            Toast.makeText(context, "Service added in Cart", Toast.LENGTH_SHORT).show();
                            serviceLeadFragment.getServiceList();

                            notifyDataSetChanged();
                            Log.e("tag",""+response.body().getMessage());

//                            Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            listner.onClickBuyNow(holder, count, 1);
//                                Log.e("tag",""+serviceList.get(position).getId());

                        } else {
//                            Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("tag", "" + response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                        Log.e("failure", t.getLocalizedMessage());

                    }
                });

            }

        });

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vendor_type.equals("product")) {

                    Toast.makeText(context, "You can't buy service and maintenance lead because you are a type of" + vendor_type, Toast.LENGTH_SHORT).show();

                } else {

                    if (walletAmount>=serviceList.get(position).getLeadprice()){

                        ProgressDialog progressDialog = new ProgressDialog(context);
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
//                                    Log.e("tag",""+response.body().getData());
//                                    Log.e("tag",""+vendor_id);
//                                    Log.e("tag",""+vendor_type);
//                                    Log.e("tag",""+vendor_type_id);
//                                    Log.e("tag",""+name);
//                                    Log.e("tag",""+mobile);
//                                    Log.e("tag",""+email);
//                                    Log.e("tag",""+serviceList.get(position).getId());
                                    Toast.makeText(context, "We have successfully received the payment from your wallet", Toast.LENGTH_SHORT).show();
                                    serviceLeadFragment.getServiceList();
                                    Log.e("tag","paid "+response.body().getMessage());

                                }else {

//                                    Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.e("tag","fail "+response.body().getMessage());

                                }
                            }

                            @Override
                            public void onFailure(Call<VendorCheckoutResponse> call, Throwable t) {
                                progressDialog.dismiss();
                                Log.e("tag",""+t.getLocalizedMessage());

                            }
                        });
//                        walletCheckout();

                    }else if (walletAmount<serviceList.get(position).getLeadprice()){

                        Intent intent = new Intent(context, PaymentActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("price", serviceList.get(position).getLeadprice().toString());
                        intent.putExtra("lead_id", serviceList.get(position).getId().toString());
                        intent.putExtra("checkout_type", "single_lead");
                        Log.e("tag","lead_id1 "+serviceList.get(position).getId().toString());
//                        Toast.makeText(context, "price "+price+ "wallet "+ walletAmount, Toast.LENGTH_SHORT).show();

                        context.startActivity(intent);

                    }




                }



//                startPayment();
            }
        });




    }

    private void walletCheckout() {

    }

    private void startPayment() {

        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Civil Deal");
            options.put("description", "App Payment");
            options.put("theme.color","#455A64");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://civildeal.com/public/assets/site/images/cd.png");
            options.put("currency", "INR");
            String payment = String.valueOf(price);
            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", mobile);
            options.put("prefill", preFill);
            co.open((Activity) context, options);
        } catch (Exception e) {
            Toast.makeText(context, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void getWalletamount() {

        Log.d("tag","vendor_id "+vendor_id);
        Call<WalletResultResponse> call = Api_Client
                .getInstance()
                .getWallet(vendor_id);
        call.enqueue(new Callback<WalletResultResponse>() {
            @Override
            public void onResponse(Call<WalletResultResponse> call, Response<WalletResultResponse> response) {

                try {
                    if (response.body().getCode().equals(200)){

                        WalletResultResponse walletResultResponse = response.body();
                        List<WalletResponse> walletList = Collections.singletonList(walletResultResponse.getData());

                        walletAmount = Integer.parseInt(walletList.get(0).getWalletAmount());
//                    Log.e("tag","amount "+walletList.get(0).getWalletAmount());
//                    Log.e("tag","wallet "+response.body().getMessage());
//                    Toast.makeText(context, "Sucess "+response.body().getData().getWalletAmount(), Toast.LENGTH_SHORT).show();

                    }else if (response.body().getCode().equals(400)){


                        Log.e("tag","failwallet "+response.body().getMessage());
//                    Toast.makeText(context, "fail ", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("tag","exceptionwallet "+e.getMessage());
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

    @Override
    public void onPaymentSuccess(String s) {
        Log.e(TAG, " payment successfull "+ s.toString());
        Toast.makeText(context, "Payment successfully done! " +s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {

        Log.e(TAG,  "error code "+String.valueOf(i)+" -- Payment failed "+s.toString()  );
        try {
            Toast.makeText(context, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView serviceImage;
        TextView serviceName, Desc, location, price,date,promoter;
        Button buy,purchased, add, added;

        public MyViewHolder(View itemView) {
            super(itemView);
            serviceImage = itemView.findViewById(R.id.serviceImage);
            serviceName = itemView.findViewById(R.id.servicename);
            Desc = itemView.findViewById(R.id.desc);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.price);
            buy = itemView.findViewById(R.id.buy);
            purchased = itemView.findViewById(R.id.purchased);
            add = itemView.findViewById(R.id.addtocart);
            added = itemView.findViewById(R.id.added);
            promoter = itemView.findViewById(R.id.promoter);

                    sharedPrefManager = new SharedPrefManager(context);
            vendor_id = String.valueOf(sharedPrefManager.getUserId());
            vendor_type = sharedPrefManager.getVendorType();
            type = sharedPrefManager.getUserType();
            mobile = sharedPrefManager.getUserMobile();
            email = sharedPrefManager.getEmail();
            vendor_type_id = sharedPrefManager.getUserType();
            name = sharedPrefManager.getUserName();


//            Toast.makeText(context, ""+type, Toast.LENGTH_SHORT).show();


        }
    }



    public interface ItemClickListener {
        void onClickBuyNow(RecyclerView.ViewHolder vh, Object item, int pos);
    }



}
