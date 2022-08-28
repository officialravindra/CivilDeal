package com.civildeal.civildeal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.SaveOrderInfoResponse;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    String price;
    SharedPrefManager sharedPrefManager;
    String mobile,email,vendor_id,vendor_type_id,vendor_type,name,razorpay_order_id;
    String checkout_type;
    int lead_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
        price = intent.getStringExtra("price");
        checkout_type = intent.getStringExtra("checkout_type");
        lead_id = Integer.parseInt(intent.getStringExtra("lead_id"));
//        Toast.makeText(this, ""+price, Toast.LENGTH_SHORT).show();
        Log.e("tag","price "+price);
        Log.e("tag","lead_id2 "+lead_id);
        Log.e("tag","checkout_type "+checkout_type);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        mobile = sharedPrefManager.getUserMobile();
        email = sharedPrefManager.getEmail();
        vendor_id = String.valueOf(sharedPrefManager.getUserId());
        vendor_type = sharedPrefManager.getVendorType();
        vendor_type_id = sharedPrefManager.getUserType();
        mobile = sharedPrefManager.getUserMobile();
        name = sharedPrefManager.getUserName();
        Log.e("tag","vendor_type_id "+vendor_type_id);

        startPayment();

    }

    private void startPayment() {

        final Activity activity = this;
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
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("tag",""+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        // payment successfull pay_DGU19rDsInjcF2
        Log.e("TAG", " payment successfull "+ s.toString());
//        Toast.makeText(this, "Payment successfully done! " +s, Toast.LENGTH_SHORT).show();
        razorpay_order_id = s;
//        if (checkout_type == "single_lead"){
//            saveOrder();
//        }else if (checkout_type == "cart"){
//            cartsaveOrder();
//        }
        saveOrder();


    }

    private void cartsaveOrder() {

        Log.e("tag","payment");


        Log.e("tag",""+vendor_type);
        Log.e("tag",""+vendor_type_id);
        Log.e("tag",""+name);
        Log.e("tag",""+mobile);
        Log.e("tag",""+email);
        Log.e("tag",""+razorpay_order_id);
        Log.e("tag",""+checkout_type);
        Log.e("tag",""+vendor_id);
        Call<SaveOrderInfoResponse> call = Api_Client
                .getInstance()
                .savecartOrderinfo(vendor_id,vendor_type,vendor_type_id,name,mobile,email,razorpay_order_id,checkout_type);
        call.enqueue(new Callback<SaveOrderInfoResponse>() {
            @Override
            public void onResponse(Call<SaveOrderInfoResponse> call, Response<SaveOrderInfoResponse> response) {

                if (response.body().getCode().equals(200)){

                    Log.e("tag","sucess "+response.body().getMessage());
                    Log.e("tag",""+response.body().getData());
                    Log.e("tag",""+vendor_type);
                    Log.e("tag",""+vendor_type_id);
                    Log.e("tag",""+name);
                    Log.e("tag",""+mobile);
                    Log.e("tag",""+email);
                    Log.e("tag",""+razorpay_order_id);
                    Log.e("tag",""+checkout_type);
                    Log.e("tag",""+vendor_id);

                    Intent intent = new Intent(PaymentActivity.this,Cart.class);
                    startActivity(intent);
                    finish();

                }else {

                    Log.e("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<SaveOrderInfoResponse> call, Throwable t) {

                Log.e("tag",""+t.getLocalizedMessage());

            }
        });
    }

    private void saveOrder() {

        Log.e("tag","payment");


        Log.e("tag",""+vendor_type);
        Log.e("tag",""+vendor_type_id);
        Log.e("tag",""+name);
        Log.e("tag",""+mobile);
        Log.e("tag",""+email);
        Log.e("tag",""+razorpay_order_id);
        Log.e("tag",""+checkout_type);
        Log.e("tag",""+vendor_id);
        Log.e("tag",""+lead_id);
        Call<SaveOrderInfoResponse> call = Api_Client
                .getInstance()
                .saveOrderinfo(vendor_id,vendor_type,vendor_type_id,name,mobile,email,lead_id,razorpay_order_id,checkout_type);
        call.enqueue(new Callback<SaveOrderInfoResponse>() {
            @Override
            public void onResponse(Call<SaveOrderInfoResponse> call, Response<SaveOrderInfoResponse> response) {

                if (response.body().getCode().equals(200)){
                    try {
                        Log.e("tag","sucess "+response.body().getMessage());
                        Log.e("tag",""+response.body().getData());
                        Toast.makeText(PaymentActivity.this, "Your lead purchased successfully", Toast.LENGTH_SHORT).show();
//                    Log.e("tag",""+vendor_type);
//                    Log.e("tag",""+vendor_type_id);
//                    Log.e("tag",""+name);
//                    Log.e("tag",""+mobile);
//                    Log.e("tag",""+email);
//                    Log.e("tag",""+razorpay_order_id);
//                    Log.e("tag",""+checkout_type);
//                    Log.e("tag",""+vendor_id);
//                    Log.e("tag",""+lead_id);

                        Intent intent = new Intent(PaymentActivity.this,Cart.class);
                        startActivity(intent);
                        finish();
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }


                }else {

                    Log.e("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<SaveOrderInfoResponse> call, Throwable t) {

                Log.e("tag","failuier "+t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {

        Log.e("TAG",  "error code "+String.valueOf(i)+" -- Payment failed "+s.toString()  );
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();

            onBackPressed();
            Log.e("OnPaymentError", "Exception in onPaymentError" +s);
            Log.e("tag","error"+s);
        } catch (Exception e) {

            Log.e("OnPaymentError", "Exception in onPaymentError" +s);
            Log.e("tag","error"+e+s);
        }

    }
}