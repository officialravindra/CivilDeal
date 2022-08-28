package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.MyCartAdapter;
import com.civildeal.civildeal.Model.MyCartModel;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.ItemAddToCartResponse;
import com.civildeal.civildeal.api.ModelResponse.ItemAddToCartResultResponse;
import com.civildeal.civildeal.api.ModelResponse.VendorCheckoutResponse;
import com.civildeal.civildeal.api.ModelResponse.WalletResponse;
import com.civildeal.civildeal.api.ModelResponse.WalletResultResponse;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity implements PaymentResultListener {

    Toolbar toolbar;
    TextView totalPrice,finalPrice,paymentLead;
    CardView payBtn,bagCart;
    LinearLayout linearLayout;

    RecyclerView recyclerView;
    MyCartAdapter myCartAdapter;
    List<ItemAddToCartResultResponse> myCartList;
    MyCartModel myCartModel;

    private ProgressDialog pDialog;
    private AlertDialog dialog;

    SharedPrefManager sharedPrefManager;
    String vendor_id,vendor_type,mobile,email,vendor_type_id,name,lead_id;
    int walletAmount;
    int sum;
    String cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        linearLayout = findViewById(R.id.cartLayout);
        totalPrice = findViewById(R.id.totalItemPrice);
        finalPrice = findViewById(R.id.finalPrice);
        payBtn = findViewById(R.id.pay);
        paymentLead = findViewById(R.id.payment);
        bagCart = findViewById(R.id.bagCart);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        vendor_id = String.valueOf(sharedPrefManager.getUserId());
        vendor_type = sharedPrefManager.getVendorType();
        vendor_type_id = sharedPrefManager.getUserType();
        mobile = sharedPrefManager.getUserMobile();
        email = sharedPrefManager.getEmail();
        name = sharedPrefManager.getUserName();

//        Toast.makeText(this, "vendor_id"+vendor_id, Toast.LENGTH_SHORT).show();
        Log.e("type",""+vendor_type_id);
        Log.e("type","vendor_type"+vendor_type);
        recyclerView = findViewById(R.id.cartRV);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        getCartList();

        getWalletamount();

    }

    private void getWalletamount() {

        Call<WalletResultResponse> call = Api_Client
                .getInstance()
                .getWallet(vendor_id);
        call.enqueue(new Callback<WalletResultResponse>() {
            @Override
            public void onResponse(Call<WalletResultResponse> call, Response<WalletResultResponse> response) {

                if (response.body().getCode().equals(200)){

                    try {
                        WalletResultResponse walletResultResponse = response.body();
                        List<WalletResponse> walletList = Collections.singletonList(walletResultResponse.getData());

                        walletAmount = Integer.parseInt(walletList.get(0).getWalletAmount());
                        Log.e("tag","amount "+walletList.get(0).getWalletAmount());
                        Log.e("tag","wallet "+response.body().getMessage());
                    }catch (Exception e){

                        Log.d("tag","Exception "+response.body().getMessage());
                    }


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



    public void getCartList() {
        ProgressDialog progressDialog = new ProgressDialog(Cart.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Cart Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Call<ItemAddToCartResultResponse> call = Api_Client
                .getInstance()
                .getCartList(vendor_id,vendor_type);
        call.enqueue(new Callback<ItemAddToCartResultResponse>() {
            @Override
            public void onResponse(Call<ItemAddToCartResultResponse> call, Response<ItemAddToCartResultResponse> response) {
                progressDialog.dismiss();
                if(response.body().getCode().equals(200)) {


                    bagCart.setVisibility(View.VISIBLE);
                    payBtn.setVisibility(View.VISIBLE);
                    ItemAddToCartResultResponse itemAddToCartResultResponse = response.body();
                    List<ItemAddToCartResponse> myCartList = itemAddToCartResultResponse.getData();

                    if (myCartList != null)
                    {
                        MyCartAdapter myCartAdapter = new MyCartAdapter(Cart.this,myCartList);
                        GridLayoutManager mgridLayoutManager = new GridLayoutManager(Cart.this,1);
                        recyclerView.setLayoutManager(mgridLayoutManager);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setAdapter(myCartAdapter);
                        myCartAdapter.notifyDataSetChanged();
                        Log.e("tag","mycartid "+myCartList.get(0).getCartId());



                        sum = 0;

                        for (int i = 0; i < myCartList.size(); i++){

                            int price = 0;
                            price = myCartList.get(i).getLeadprice();
                            sum += Integer.parseInt(String.valueOf(price));
                            Log.e("tag1",""+sum);
                        }

                        myCartAdapter.notifyDataSetChanged();
                        totalPrice.setText(String.valueOf("₹ "+sum));
                        finalPrice.setText(String.valueOf("₹ "+sum));
                        paymentLead.setText("Proceed to pay ₹ "+ sum);


                    }

                    if (myCartList.equals(null)){

                        recyclerView.setVisibility(View.GONE);

                        Toast.makeText(Cart.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                        recyclerView.setVisibility(View.GONE);
                    }else {

                        payBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (myCartList.equals(0)){

                                    Toast.makeText(Cart.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                                }else {

                                    if (walletAmount<sum){

                                        Log.e("tag","payment");
                                        String price;
                                        price = String.valueOf(sum);

                                        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("price", price);
                                        intent.putExtra("checkout_type", "cart");
                                        intent.putExtra("lead_id", "0");
//                                        Toast.makeText(getApplicationContext(), "price "+price, Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(Cart.this, "checkout_type "+"cart", Toast.LENGTH_SHORT).show();

                                        startActivity(intent);
//                                        startPayment();

                                    }else if (walletAmount>=sum){

                                        walletCheckout();
                                    }



                                }


                            }
                        });
                    }






                }
                else
                {
                    if (response.body().getMessage().equals("Sorry! not lead added in Cart?")){
                        linearLayout.setVisibility(View.VISIBLE);
                        bagCart.setVisibility(View.GONE);
                        payBtn.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        totalPrice.setText(String.valueOf("₹ "+0));
                        finalPrice.setText(String.valueOf("₹ "+0));
                        paymentLead.setText("Proceed to pay ₹ "+ 0);
                        Log.e("tag","fail "+response.body().getMessage());
//                        Toast.makeText(Cart.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onFailure(Call<ItemAddToCartResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("failure",t.getLocalizedMessage());
                hideDialog();

            }
        });
    }

    private void walletCheckout() {
        ProgressDialog progressDialog = new ProgressDialog(Cart.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait while we are checking your wallet balance...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Call<VendorCheckoutResponse> call = Api_Client
                .getInstance()
                .walletPurchase(vendor_id,vendor_type,vendor_type_id,name,mobile,email,"cart",lead_id);
        call.enqueue(new Callback<VendorCheckoutResponse>() {
            @Override
            public void onResponse(Call<VendorCheckoutResponse> call, Response<VendorCheckoutResponse> response) {
                progressDialog.dismiss();
                if (response.body().getCode().equals(200)) {

                    Toast.makeText(Cart.this, "We have successfully received the payment from your wallet", Toast.LENGTH_SHORT).show();
                    Log.e("tag","paid "+response.body().getMessage());
                    getCartList();
                }else {


                    Log.e("tag","fail "+response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<VendorCheckoutResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("tag",""+t.getLocalizedMessage());

            }
        });
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
            String payment = String.valueOf(sum);
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
        Toast.makeText(this, "Payment successfully done! " +s, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPaymentError(int i, String s) {
        Log.e("TAG",  "error code "+String.valueOf(i)+" -- Payment failed "+s.toString()  );
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId()==android.R.id.home){
//            Intent intent = new Intent(Cart.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}