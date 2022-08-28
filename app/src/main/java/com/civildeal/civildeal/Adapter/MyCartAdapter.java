package com.civildeal.civildeal.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.civildeal.civildeal.Activity.Cart;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.DeleteCartResponse;
import com.civildeal.civildeal.api.ModelResponse.ItemAddToCartResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyViewHolder> {

    private Context context;
    private List<ItemAddToCartResponse> myCartList;
    SharedPrefManager sharedPrefManager;
    String vendor_id,vendor_type;
    int cart_id;

    public MyCartAdapter(Context context, List<ItemAddToCartResponse> myCartList) {
        this.context = context;
        this.myCartList = myCartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (vendor_type.equals("service")){

            Picasso.get()
                    .load("https://civildeal.com/public/assets/uploadsservice/"+myCartList.get(position).getServiceImage())
                    .error(R.drawable.profile)
                    .placeholder(R.drawable.profile)
                    .into(holder.Image);

        }else if (vendor_type.equals("product")){

            Picasso.get()
                    .load("https://civildeal.com/public/assets/uploads/product/"+myCartList.get(position).getServiceImage())
                    .error(R.drawable.profile)
                    .placeholder(R.drawable.profile)
                    .into(holder.Image);
        }

        holder.Name.setText(myCartList.get(position).getServicename().trim());
        holder.query.setText(myCartList.get(position).getQuery().trim());
        holder.price.setText("₹ " +myCartList.get(position).getLeadprice().toString());
        Log.e("tag",""+myCartList.get(position).getLeadprice());
//        Picasso.get()
//                .load("https://civildeal.com/public/assets/uploadsservice/"+myCartList.get(position).getServiceImage())
//                .error(R.drawable.ic_error)
//                .placeholder(R.drawable.ic_error)
//                .into(holder.Image);
        cart_id = myCartList.get(position).getCartId();

//        Toast.makeText(context, ""+myCartList.get(position).getCartId(), Toast.LENGTH_SHORT).show();

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                deleteItem();
                Log.e("tag",""+myCartList.get(position).getCartId());
            }

            private void deleteItem() {

                Call<DeleteCartResponse> call = Api_Client
                        .getInstance()
                        .deleteCart(vendor_id,myCartList.get(position).getCartId().toString());
                call.enqueue(new Callback<DeleteCartResponse>() {
                    @Override
                    public void onResponse(Call<DeleteCartResponse> call, Response<DeleteCartResponse> response) {

                            if (response.body().getCode().equals(200)){


                                myCartList.remove(position);
                                notifyItemRemoved(position);
//                                Cart.getCartList();
                                if (context instanceof Cart) {
                                    ((Cart)context).getCartList();
                                }


                            Toast.makeText(context, "Lead deleted", Toast.LENGTH_SHORT).show();


                        }else{

                            Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteCartResponse> call, Throwable t) {

                        Log.e("failure",t.getLocalizedMessage());

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return myCartList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Name,price,dicountPrice,dicount,query,delete;
        ImageView Image;
        public MyViewHolder( View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            dicount = itemView.findViewById(R.id.dicount);
            dicountPrice = itemView.findViewById(R.id.discountPrice);
            Image = itemView.findViewById(R.id.Image);
            query = itemView.findViewById(R.id.query);
            delete = itemView.findViewById(R.id.delete);

            sharedPrefManager=new SharedPrefManager(context);
            vendor_id = String.valueOf(sharedPrefManager.getUserId());
            vendor_type = sharedPrefManager.getVendorType();
//            Toast.makeText(context, "vendor_id"+vendor_id, Toast.LENGTH_SHORT).show();
        }
    }


}
