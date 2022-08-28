package com.civildeal.civildeal.Adapter;

import android.app.Activity;
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
import com.civildeal.civildeal.Fragments.ProductLeadFragment;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.AddToCartResponse;
import com.civildeal.civildeal.api.ModelResponse.AddToCartResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductLeadResponse;
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

public class ProductLeadAdapter extends RecyclerView.Adapter<ProductLeadAdapter.MyViewHolder> implements PaymentResultListener {

    private Context context;
    private List<ProductLeadResponse> productList;
    ProductLeadFragment productLeadFragment;
    ItemClickListener listner;
    static int count;
    SharedPrefManager sharedPrefManager;
    String vendor_id,vendor_type,email,mobile,date,lead_id,name,vendor_type_id;
    String price;
    int walletAmount;

    public ProductLeadAdapter(Context context, List<ProductLeadResponse> productList, ItemClickListener listener, ProductLeadFragment productLeadFragment) {
        this.context = context;
        this.productList = productList;
        this.listner = listener;
        this.productLeadFragment = productLeadFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coloum_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get()
                .load("https://civildeal.com/public/assets/uploads/product/"+productList.get(position).getProductImage())
                .error(R.drawable.icon_image)
                .placeholder(R.drawable.icon_image)
                .into(holder.productImage);
        holder.productName.setText("Need : " +productList.get(position).getQuery().trim());
        holder.Desc.setText("Product : " +productList.get(position).getProductName());
        holder.location.setText("Location : " +productList.get(position).getLocation());
        holder.price.setText("price : ₹" + productList.get(position).getLeadprice().toString());
        price = String.valueOf(productList.get(position).getLeadprice());
        if (productList.get(position).getPromoterId()!=0){
            holder.promoter.setVisibility(View.VISIBLE);
            holder.promoter.setText("By Promoter");
        }else {
            holder.promoter.setVisibility(View.GONE);
        }
//        lead_id = productList.get(position).getId().toString();


        date = productList.get(position).getCreatedAt();

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

        if (productList.get(position).getIsAdded()==0){

            holder.add.setVisibility(View.VISIBLE);
            holder.buy.setVisibility(View.VISIBLE);
            holder.added.setVisibility(View.GONE);



        }
        if (productList.get(position).getIsPurchased()==0)
        {

            holder.buy.setVisibility(View.VISIBLE);
            holder.add.setVisibility(View.VISIBLE);
            holder.purchased.setVisibility(View.GONE);



        }
        if (productList.get(position).getIsAdded()==1){

//            Log.e("tag","A1");
            holder.add.setVisibility(View.GONE);
            holder.added.setVisibility(View.VISIBLE);
            holder.buy.setVisibility(View.GONE);
            holder.purchased.setVisibility(View.GONE);


        }
        if (productList.get(position).getIsPurchased()==1){

//            Log.e("tag","P1");
            holder.buy.setVisibility(View.GONE);
            holder.purchased.setVisibility(View.VISIBLE);
            holder.add.setVisibility(View.GONE);
            holder.added.setVisibility(View.GONE);

        }

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vendor_type.equals("service")){

                    Toast.makeText(context, "You can't add product lead because you are a type of"+vendor_type, Toast.LENGTH_SHORT).show();

                }else if (vendor_type.equals("maintenance")){

                    Toast.makeText(context, "You can't add product lead because you are a type of"+vendor_type, Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(context, ""+productList.get(position).getProductId(), Toast.LENGTH_SHORT).show();

                    addCartItem();

                }

            }

            private void addCartItem() {

                Call<AddToCartResponse> call1 = Api_Client
                        .getInstance()
                        .addCart(vendor_type,vendor_id,productList.get(position).getId().toString());
                call1.enqueue(new Callback<AddToCartResponse>() {
                    @Override
                    public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {

                        if (response.body().getCode().equals(200)){

                            AddToCartResponse addToCartResponse = response.body();
                            List<AddToCartResultResponse> leadList = Collections.singletonList(addToCartResponse.getData());
                            Toast.makeText(context, "Product added in Cart", Toast.LENGTH_SHORT).show();
                            productLeadFragment.getProductList();
                            listner.onClickBuyNow(holder, count,1);
                            Log.e("tag",""+count);

                        }else {
//                            Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("tag",""+response.body().getMessage());

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
            public void onClick(View v)
            {

                if (vendor_type.equals("service")){

                    Toast.makeText(context, "You can't buy product lead because you are a type of"+vendor_type, Toast.LENGTH_SHORT).show();

                }else if (vendor_type.equals("maintenance")){

                    Toast.makeText(context, "You can't buy product lead because you are a type of"+vendor_type, Toast.LENGTH_SHORT).show();
                }
                else {

                    if (walletAmount<productList.get(position).getLeadprice()){

                        Intent intent = new Intent(context, PaymentActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("price", productList.get(position).getLeadprice().toString());
                        intent.putExtra("lead_id", productList.get(position).getId().toString());
                        intent.putExtra("checkout_type", "single_lead");
//                    Toast.makeText(context, "price "+price, Toast.LENGTH_SHORT).show();

//                        Log.e("tag","price "+price);

                        context.startActivity(intent);

                    }else if (walletAmount>=productList.get(position).getLeadprice()){

                        ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Please wait while we are checking your wallet balance...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();
                        Call<VendorCheckoutResponse> call = Api_Client
                                .getInstance()
                                .walletPurchase(vendor_id,vendor_type,vendor_type_id,name,mobile,email,"single_lead", productList.get(position).getId().toString());
                        call.enqueue(new Callback<VendorCheckoutResponse>() {
                            @Override
                            public void onResponse(Call<VendorCheckoutResponse> call, Response<VendorCheckoutResponse> response) {
                                progressDialog.dismiss();
                                if (response.body().getCode().equals(200)) {

                                    Toast.makeText(context, "We have successfully received the payment from your wallet", Toast.LENGTH_SHORT).show();
                                    productLeadFragment.getProductList();
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

                    }



                }

            }
        });
    }

    private void walletCheckout() {


    }

    private void getWalletamount() {
        Call<WalletResultResponse> call = Api_Client
                .getInstance()
                .getWallet(vendor_id);
        call.enqueue(new Callback<WalletResultResponse>() {
            @Override
            public void onResponse(Call<WalletResultResponse> call, Response<WalletResultResponse> response) {

                try{
                    if (response.body().getCode().equals(200)){

                        WalletResultResponse walletResultResponse = response.body();
                        List<WalletResponse> walletList = Collections.singletonList(walletResultResponse.getData());

                        walletAmount = Integer.parseInt(walletList.get(0).getWalletAmount());
//                    Log.e("tag","amount "+walletList.get(0).getWalletAmount());
//                    Log.e("tag","wallet "+response.body().getMessage());

                    }else if (response.body().getCode().equals(400)){


                        Log.e("tag","failwallet "+response.body().getMessage());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("tag","exe ");
                }



            }

            @Override
            public void onFailure(Call<WalletResultResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });
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

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName,Desc,location,price,date,promoter;
        Button buy,purchased,add,added;
        public MyViewHolder( View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.serviceImage);
            productName = itemView.findViewById(R.id.servicename);
            Desc = itemView.findViewById(R.id.desc);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.price);
            buy = itemView.findViewById(R.id.buy);
            purchased = itemView.findViewById(R.id.purchased);
            add = itemView.findViewById(R.id.addtocart);
            added = itemView.findViewById(R.id.added);
            promoter = itemView.findViewById(R.id.promoter);

            sharedPrefManager=new SharedPrefManager(context);
            vendor_id = String.valueOf(sharedPrefManager.getUserId());
            vendor_type = sharedPrefManager.getVendorType();
            mobile = sharedPrefManager.getUserMobile();
            email = sharedPrefManager.getEmail();
            vendor_type_id = sharedPrefManager.getUserType();
            name = sharedPrefManager.getUserName();
//            Toast.makeText(context, ""+vendor_type, Toast.LENGTH_SHORT).show();
            getWalletamount();
        }
    }

    public interface ItemClickListener {
        void onClickBuyNow(RecyclerView.ViewHolder vh, Object item, int pos);
    }
}
